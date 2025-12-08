package raytracer.trace;

import raytracer.math.Color;
import raytracer.math.Ray;
import raytracer.math.Vector;
import raytracer.math.Point;
import raytracer.scene.Scene;
import raytracer.scene.Orthonormal;
import raytracer.geometry.Shape;
import raytracer.light.AbstractLight;
import raytracer.light.DirectionalLight;
import raytracer.light.PointLight;
import java.util.Optional;
import java.lang.Math;

/**
 * Moteur de lancer de rayons: calcule le rayon, trouve l'intersection et détermine la couleur.
 */
public class RayTracer {

    private final Scene scene;
    private final Orthonormal basis;
    private final double pixelHeight;
    private final double pixelWidth;
    private final Point lookFrom;

    // Jalon 5: Epsilon pour éviter les auto-intersections (Shadow Acne)
    private static final double SHADOW_EPSILON = 1e-4;

    public RayTracer(Scene scene) {
        this.scene = scene;
        this.basis = new Orthonormal(scene.getCamera());
        this.lookFrom = scene.getCamera().getLookFrom();

        double fov = scene.getCamera().getFov();

        double fovr = Math.toRadians(fov);
        this.pixelHeight = Math.tan(fovr / 2.0);
        this.pixelWidth = this.pixelHeight * ((double) scene.getWidth() / scene.getHeight());
    }

    public Color getPixelColor(int i, int j) {
        Ray ray = calculateRay(i, j);
        Optional<Intersection> closestIntersection = findClosestIntersection(ray);

        if (closestIntersection.isPresent()) {
            Intersection intersection = closestIntersection.get();
            return calculateColor(intersection);
        } else {
            return new Color(0, 0, 0);
        }
    }

    // (Méthodes calculateRay et findClosestIntersection inchangées, sauf l'utilisation de SHADOW_EPSILON dans findClosestIntersection)

    private Ray calculateRay(int i, int j) {
        double halfWidth = scene.getWidth() / 2.0;
        double halfHeight = scene.getHeight() / 2.0;

        double a = pixelWidth * (i - halfWidth + 0.5) / halfWidth;
        double b = pixelHeight * (j - halfHeight + 0.5) / halfHeight;

        Vector u = basis.getU();
        Vector v = basis.getV();
        Vector w = basis.getW();

        Vector directionUnnormalized = u.multiply(a).add(v.multiply(b)).subtract(w);

        return new Ray(lookFrom, directionUnnormalized);
    }

    private Optional<Intersection> findClosestIntersection(Ray ray) {
        Optional<Intersection> closest = Optional.empty();
        double minT = Double.MAX_VALUE;

        for (Shape shape : scene.getShapes()) {
            Optional<Intersection> current = shape.intersect(ray);

            if (current.isPresent()) {
                Intersection intersection = current.get();
                // Utilise SHADOW_EPSILON pour éviter l'intersection avec l'origine du rayon
                if (intersection.getT() > SHADOW_EPSILON && intersection.getT() < minT) {
                    minT = intersection.getT();
                    closest = current;
                }
            }
        }
        return closest;
    }

    /**
     * JALON 5: Test d'ombre. Vérifie si un objet obstrue le chemin entre p et la lumière.
     */
    private boolean isInShadow(Point p, AbstractLight light) {
        Vector L; // Direction du rayon d'ombre (du point P vers la lumière)
        double maxT; // Distance maximale à vérifier

        // Calculer L et maxT en fonction du type de lumière
        if (light instanceof DirectionalLight) {
            L = ((DirectionalLight) light).getDirection().multiply(-1).normalize();
            maxT = Double.MAX_VALUE; // Distance infinie
        } else if (light instanceof PointLight) {
            Point lightPos = ((PointLight) light).getPosition();
            Vector shadowVec = lightPos.subtract(p);
            maxT = shadowVec.length(); // Distance à la source de lumière
            L = shadowVec.normalize();
        } else {
            return false;
        }

        // Le rayon d'ombre part du point d'intersection p
        Ray shadowRay = new Ray(p, L);

        // Recherche d'une intersection
        for (Shape shape : scene.getShapes()) {
            Optional<Intersection> current = shape.intersect(shadowRay);

            if (current.isPresent()) {
                double t = current.get().getT();

                // L'objet est dans l'ombre si:
                // 1. L'intersection est devant nous (t > SHADOW_EPSILON)
                // 2. L'intersection est PLUS PROCHE que la source de lumière (t < maxT)
                if (t > SHADOW_EPSILON && t < maxT) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * JALON 5: Calcule la couleur finale en appliquant Ambiante, Diffuse (Lambert), et Spéculaire (Blinn-Phong).
     */
    private Color calculateColor(Intersection intersection) {
        Shape hitShape = intersection.getShape();
        Point p = intersection.getPoint();
        Vector N = intersection.getNormal();

        // 1. Lumière Ambiante (Toujours présente)
        Color finalColor = scene.getAmbient().schurProduct(hitShape.getDiffuse());

        // 2. Récupération du Vecteur de Vue (V: du point P vers l'oeil)
        Vector V = lookFrom.subtract(p).normalize();

        // 3. Boucle sur toutes les lumières pour Diffuse et Spéculaire
        for (AbstractLight light : scene.getLights()) {

            // JALON 5: Test d'ombre. Si en ombre, on passe à la lumière suivante.
            if (isInShadow(p, light)) {
                continue;
            }

            // --- A) Préparation des Vecteurs ---
            Vector L;
            if (light instanceof DirectionalLight) {
                L = ((DirectionalLight) light).getDirection().multiply(-1).normalize();
            } else if (light instanceof PointLight) {
                Point lightPos = ((PointLight) light).getPosition();
                L = lightPos.subtract(p).normalize();
            } else {
                continue;
            }

            // --- B) Composante Diffuse (Lambert, Jalon 4) ---
            double nDotL = N.dot(L);
            double lambertFactor = Math.max(nDotL, 0.0);

            Color diffuseLightComponent = light.getColor()
                    .multiply(lambertFactor)
                    .schurProduct(hitShape.getDiffuse());

            finalColor = finalColor.add(diffuseLightComponent);


            // --- C) Composante Spéculaire (Blinn-Phong, Jalon 5) ---
            // On calcule le spéculaire uniquement si la lumière éclaire la surface (lambertFactor > 0)
            if (lambertFactor > 0.0) {

                // 1. Calcul du Vecteur Moitié (Half-Vector H)
                Vector H = L.add(V).normalize(); // H = (L + V) / ||L + V|| [cite: 445]

                // 2. Calcul du facteur spéculaire (max(N . H, 0)^shininess)
                double nDotH = N.dot(H);
                double specularFactor = Math.max(nDotH, 0.0);

                double shininess = hitShape.getShininess();
                double phongPower = Math.pow(specularFactor, shininess);

                // 3. Calcul de la Composante Spéculaire
                Color specularLightComponent = light.getColor()
                        .multiply(phongPower)
                        .schurProduct(hitShape.getSpecular());

                finalColor = finalColor.add(specularLightComponent);
            }
        }

        // 4. Clamping
        return finalColor.clamp();
    }
}