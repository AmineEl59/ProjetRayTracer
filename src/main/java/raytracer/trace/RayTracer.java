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
        return computeColor(ray, scene.getMaxDepth());
    }


    /**
     * JALON 6 BONUS: Calcule la couleur d'un point, incluant la lumière directe et indirecte (réflexion).
     * @param ray Le rayon à tracer.
     * @param depth La profondeur de récursion restante.
     * @return La couleur finale.
     */
    private Color computeColor(Ray ray, int depth) {
        Optional<Intersection> closestIntersection = findClosestIntersection(ray);

        if (closestIntersection.isEmpty()) {
            return new Color(0, 0, 0);
        }

        Intersection intersection = closestIntersection.get();
        Shape hitShape = intersection.getShape();

        Color finalColor = calculateDirectLighting(intersection, ray);

        if (depth > 1 && hitShape.getSpecular().getR() > SHADOW_EPSILON) {

            Ray reflectedRay = intersection.generateReflectedRay(ray);

            Color cPrime = computeColor(reflectedRay, depth - 1);

            Color reflectedContribution = hitShape.getSpecular().schurProduct(cPrime);
            finalColor = finalColor.add(reflectedContribution);
        }

        return finalColor.clamp();
    }


    /**
     * Calcule la couleur directe (Ambiante + Diffuse + Spéculaire)
     */
    private Color calculateDirectLighting(Intersection intersection, Ray incidentRay) {
        Shape hitShape = intersection.getShape();
        Point p = intersection.getPoint();
        Vector N = intersection.getNormal();

        Color finalColor = scene.getAmbient().schurProduct(hitShape.getDiffuse());

        Vector V = incidentRay.getDirection().multiply(-1.0).normalize();

        for (AbstractLight light : scene.getLights()) {

            if (isInShadow(p, light)) {
                continue;
            }

            Vector L;
            if (light instanceof DirectionalLight) {
                L = ((DirectionalLight) light).getDirection().multiply(-1).normalize();
            } else if (light instanceof PointLight) {
                Point lightPos = ((PointLight) light).getPosition();
                L = lightPos.subtract(p).normalize();
            } else {
                continue;
            }

            double nDotL = N.dot(L);
            double lambertFactor = Math.max(nDotL, 0.0);

            Color diffuseLightComponent = light.getColor()
                    .multiply(lambertFactor)
                    .schurProduct(hitShape.getDiffuse());

            finalColor = finalColor.add(diffuseLightComponent);

            if (lambertFactor > 0.0) {

                Vector H = L.add(V).normalize();

                double nDotH = N.dot(H);
                double specularFactor = Math.max(nDotH, 0.0);

                double shininess = hitShape.getShininess();
                double phongPower = Math.pow(specularFactor, shininess);

                Color specularLightComponent = light.getColor()
                        .multiply(phongPower)
                        .schurProduct(hitShape.getSpecular());

                finalColor = finalColor.add(specularLightComponent);
            }
        }

        return finalColor;
    }

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
                if (intersection.getT() > SHADOW_EPSILON && intersection.getT() < minT) {
                    minT = intersection.getT();
                    closest = current;
                }
            }
        }
        return closest;
    }

    private boolean isInShadow(Point p, AbstractLight light) {
        Vector L;
        double maxT;

        if (light instanceof DirectionalLight) {
            L = ((DirectionalLight) light).getDirection().multiply(-1).normalize();
            maxT = Double.MAX_VALUE;
        } else if (light instanceof PointLight) {
            Point lightPos = ((PointLight) light).getPosition();
            Vector shadowVec = lightPos.subtract(p);
            maxT = shadowVec.length();
            L = shadowVec.normalize();
        } else {
            return false;
        }

        Ray shadowRay = new Ray(p.add(L.scale(SHADOW_EPSILON)), L);

        for (Shape shape : scene.getShapes()) {
            Optional<Intersection> current = shape.intersect(shadowRay);

            if (current.isPresent()) {
                double t = current.get().getT();

                if (t > 0 && t < maxT) {
                    return true;
                }
            }
        }
        return false;
    }
}