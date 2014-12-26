package exceptions;

/**
 * Created by stardisblue on 24/12/2014.
 */
public class FormatNotFoundException extends Exception {
    public FormatNotFoundException(String message) {
        super("The format of the file isn't specified" + message);
    }
}
