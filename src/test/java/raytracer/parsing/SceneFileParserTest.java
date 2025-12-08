package raytracer.parsing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import raytracer.parsing.SceneFileParser;
import raytracer.parsing.SceneParseException;
import raytracer.scene.Scene;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SceneFileParserTest {
    private static final double EPSILON = 1e-6;

    // Utilitaire pour créer un fichier de scène temporaire et le parser
    private Scene parseTempScene(String content) throws IOException, SceneParseException {
        Path tempFile = Files.createTempFile("testscene", ".scene");
        Files.writeString(tempFile, content);

        SceneFileParser parser = new SceneFileParser();
        try {
            return parser.parse(tempFile.toString());
        } finally {
            Files.delete(tempFile);
        }
    }

    @Test
    void testParse_SuccessfulScene() throws Exception {
        String sceneContent =
                "size 100 100\n" +
                        "output image.png\n" +
                        "camera 0 0 0 0 0 -1 0 1 0 60\n" +
                        "ambient 0.1 0.1 0.1\n" +
                        "diffuse 0.5 0.5 0.5\n" +
                        "shininess 20\n" +
                        "sphere 0 0 -5 1.0\n" +
                        "point 10 10 10 0.5 0.5 0.5";

        Scene scene = parseTempScene(sceneContent);

        assertNotNull(scene.getCamera());
        assertEquals(100, scene.getWidth());
        assertEquals(1, scene.getShapes().size());
    }

    @Test
    void testParse_LightSumConstraint_Fail() {
        String sceneContent =
                "size 10 10\n" +
                        "ambient 0 0 0\n" +
                        "diffuse 1 1 1\n" +
                        "point 0 0 0 0.8 0.0 0.0\n" +
                        "directional 0 1 0 0.3 0.0 0.0\n"; // Total R = 1.1 > 1.0

        SceneParseException exception = assertThrows(SceneParseException.class, () -> {
            parseTempScene(sceneContent);
        });

        assertTrue(exception.getMessage().contains("CONTRAINTE DE LUMIÈRE"));
    }

    @Test
    void testParse_AmbientDiffuseConstraint_Fail() {
        String sceneContent =
                "size 10 10\n" +
                        "ambient 0.6 0 0\n" +
                        "diffuse 0.5 0 0\n"; // Total R = 1.1 > 1.0

        SceneParseException exception = assertThrows(SceneParseException.class, () -> {
            parseTempScene(sceneContent);
        });

        assertTrue(exception.getMessage().contains("CONTRAINTE DE COULEUR"));
    }
}