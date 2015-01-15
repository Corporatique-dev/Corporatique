package plugins;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation interface contains the specifications of a plugin.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Pluginspecs {
	String name();
	String version();
	String description();
	String author();
	String[] dependencies();
	String[] extensions();
}
