package raytracer.parsing;

/**
 
Une exception personnalisée pour les erreurs lors de la lecture
du fichier de scène. */
public class SceneParseException extends Exception {

    public SceneParseException(String message) {
        super(message);
    }

    public SceneParseException(String message, int lineNumber) {
        super("Erreur à la ligne " + lineNumber + ": " + message);
    }
}