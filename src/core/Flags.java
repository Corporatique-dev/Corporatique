package core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Allows to get configfile.properties values.
 *
 * @author Fati CHEN
 * @version 1.0.0
 */
public class Flags {
    private static final String BUNDLE_NAME = "core.props.flags";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private Flags() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
