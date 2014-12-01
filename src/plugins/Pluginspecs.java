package plugins;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Pluginspecs {

	String name();

	String version();

	String description();

	String author();

	String[] dependencies();

	String[] extensions();

}
