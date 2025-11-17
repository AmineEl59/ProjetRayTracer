package raytracer;

import raytracer.math.AbstractVec3;
import raytracer.math.Vector;
import raytracer.math.Point;
import raytracer.math.Color;

/**
 * Cette classe sert à tester notre "boîte à outils" mathématique.
 * Elle contient son propre "main" pour être lancée.
 */
public class TestRunner {

    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("--- Lancement des tests pour le Calcul Vectoriel ---");

        // --- Tests pour la classe Vector ---
        testVectorAddition();
        testVectorSubtraction();
        testVectorScalarMultiply();
        testVectorDotProduct();
        testVectorCrossProduct();
        testVectorLength();
        testVectorNormalize();
        
        // --- Tests pour la classe Point ---
        testPointSubtract();
        testPointAddVector();

        // --- Tests pour la classe Color ---
        testColorCreationAndClamping();
        testColorOperations();
        testColorToRGB();

        System.out.println("--------------------------------------------------");
        if (testsFailed == 0) {
            System.out.println("✅ SUCCÈS : Tous les " + testsPassed + " tests sont passés !");
        } else {
            System.out.println("❌ ÉCHEC : " + testsFailed + " test(s) échoué(s) sur " + (testsPassed + testsFailed));
        }
        System.out.println("--------------------------------------------------");
    }

    // --- Les Tests Individuels ---

    private static void testVectorAddition() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(10, 20, 30);
        Vector expected = new Vector(11, 22, 33);
        Vector result = v1.add(v2);
        test(expected.equals(result), "Vector Addition");
    }

    private static void testVectorSubtraction() {
        Vector v1 = new Vector(10, 20, 30);
        Vector v2 = new Vector(1, 2, 3);
        Vector expected = new Vector(9, 18, 27);
        Vector result = v1.subtract(v2);
        test(expected.equals(result), "Vector Soustraction");
    }

    private static void testVectorScalarMultiply() {
        Vector v1 = new Vector(1, 2, 3);
        Vector expected = new Vector(10, 20, 30);
        Vector result = v1.multiply(10);
        test(expected.equals(result), "Vector Multiplication Scalaire");
    }

    private static void testVectorDotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        double expected = 32.0;
        double result = v1.dot(v2);
        testDouble(expected, result, "Vector Produit Scalaire (Dot)");
    }

    private static void testVectorCrossProduct() {
        Vector v1 = new Vector(1, 0, 0); // Axe X
        Vector v2 = new Vector(0, 1, 0); // Axe Y
        Vector expected = new Vector(0, 0, 1); // Doit donner Axe Z
        Vector result = v1.cross(v2);
        test(expected.equals(result), "Vector Produit Vectoriel (Cross)");
    }

    private static void testVectorLength() {
        Vector v = new Vector(3, 4, 0); // Triangle 3, 4, 5
        double expected = 5.0;
        double result = v.length();
        testDouble(expected, result, "Vector Longueur");
    }

    private static void testVectorNormalize() {
        Vector v = new Vector(5, 0, 0);
        Vector expected = new Vector(1, 0, 0);
        Vector result = v.normalize();
        test(expected.equals(result), "Vector Normalisation");
    }

    private static void testPointSubtract() {
        Point p1 = new Point(5, 5, 5);
        Point p2 = new Point(1, 2, 3);
        Vector expected = new Vector(4, 3, 2); // La soustraction de 2 points donne 1 vecteur
        Vector result = p1.subtract(p2);
        test(expected.equals(result), "Point - Point (donne Vector)");
    }

    private static void testPointAddVector() {
        Point p = new Point(1, 2, 3);
        Vector v = new Vector(10, 20, 30);
        Point expected = new Point(11, 22, 33); // L'ajout d'un vecteur à 1 point donne 1 point
        Point result = p.add(v);
        test(expected.equals(result), "Point + Vector (donne Point)");
    }

    private static void testColorCreationAndClamping() {
        // Teste le "clamping" (valeurs forcées entre 0 et 1)
        Color c = new Color(1.5, -0.5, 0.5);
        Color expected = new Color(1.0, 0.0, 0.5); // Doit être "clampé"
        test(expected.equals(c), "Color Création (Clamping)");
    }

    private static void testColorOperations() {
        Color c1 = new Color(0.1, 0.2, 0.3);
        Color c2 = new Color(0.5, 0.5, 0.5);
        Color expectedAdd = new Color(0.6, 0.7, 0.8);
        Color expectedSchur = new Color(0.05, 0.1, 0.15);
        
        test(expectedAdd.equals(c1.add(c2)), "Color Addition");
        test(expectedSchur.equals(c1.schur(c2)), "Color Produit Schur");
    }

    private static void testColorToRGB() {
        Color c = new Color(1.0, 0.0, 0.0); // Rouge pur
        int expectedRed = 255 << 16; // 0xFF0000
        test(c.toRGB() == expectedRed, "Color toRGB (Rouge)");

        Color c2 = new Color(0.5, 0.5, 0.5); // Gris moyen
        int expectedGray = (128 << 16) + (128 << 8) + 128; // 0x808080
        test(c2.toRGB() == expectedGray, "Color toRGB (Gris)");
    }


    // --- Méthodes Aides pour les Tests ---

    /**
     * Affiche le résultat d'un test pour les objets (Vector, Point, Color).
     */
    private static void test(boolean success, String testName) {
        if (success) {
            System.out.println("  [PASS] " + testName);
            testsPassed++;
        } else {
            System.out.println("  [FAIL] " + testName);
            testsFailed++;
        }
    }

    /**
     * Affiche le résultat d'un test pour les 'double' (Produit Scalaire, Longueur).
     */
    private static void testDouble(double expected, double result, String testName) {
        // On compare les 'double' avec la marge d'erreur (EPSILON)
        if (Math.abs(expected - result) <= AbstractVec3.EPSILON) {
            System.out.println("  [PASS] " + testName);
            testsPassed++;
        } else {
            System.out.println("  [FAIL] " + testName + " (Attendu: " + expected + ", Obtenu: " + result + ")");
            testsFailed++;
        }
    }
}