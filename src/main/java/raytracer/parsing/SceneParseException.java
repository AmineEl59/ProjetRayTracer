package raytracer.parsing;

/**
 * Exception spécifique pour les erreurs de lecture et de contraintes
 * (par exemple, indices de vertices invalides, contraintes de couleur non respectées).
 */
public class SceneParseException extends Exception {
    public SceneParseException(String message) {
        super(message);
    }
}