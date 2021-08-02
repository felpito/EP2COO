import java.io.*;

public class ReservaException extends Exception {
    private String exception;

    public ReservaException(String exception) {
        this.exception = exception;
    }

    public String getException() {
        return exception;
    }
}