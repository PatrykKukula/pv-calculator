package pl.patrykkukula.Exceptions;

public class ModulesDataInputMismatchException extends RuntimeException {
    public ModulesDataInputMismatchException() {
    }
    public ModulesDataInputMismatchException(String message) {
        super(message);
    }
}
