package br.com.educode.plugin.environment;

/**
 * Constants interface contains all constants that are used no-snapshot
 * plugin.
 * @since 1.0-beta
 * @author <a href="mailto:eduardo@educode.com.br">Eduardo Vieira</a>
 */
public interface Constants {

    /**
     * {@value}
     */
    public static final String NO_SNAPSHOT_ARTIFACT_ID_TAGNAME = "no-snapshot.artifactId";

    /**
     * {@value}
     */
    public static final String ARTIFACT_ID_TAGNAME = "artifactId";

    /**
     * {@value}
     */
    public static final String VERSION_TAGNAME = "version";

    /**
     * {@value}
     */
    public static final String PROJECT_TAGNAME = "project";

    /**
     * {@value}
     */
    public static final String PROFILES_TAGNAME = "profiles";

    /**
     * {@value}
     */
    public static final String PROFILE_TAGNAME = "profile";

    /**
     * {@value}
     */
    public static final String ID_PROFILE_TAGNAME = "id";

    /**
     * {@value}
     */
    public static final String PARENT_TAGNAME = "parent";

    /**
     * {@value}
     */
    public static final String DEPENDENCY_TAGNAME = "dependency";

    /**
     * {@value}
     */
    public static final String PLUGIN_TAGNAME = "plugin";

    /**
     * {@value}
     */
    public static final String EMPTY = "";

    /**
     * {@value}
     */
    public static final String VERSION_SUFFIX = "-SNAPSHOT";

    /**
     * {@value}
     */
    public static final String DEFAULT_POM_NAME = "pom.xml";

    /**
     * {@value}
     */
    public static final String DEFAULT_POM_NO_SNAPSHOT_NAME = "pom-no-snapshot.xml";

    /**
     * {@value}
     */
    public static final String DEFAULT_ENCODE = "UTF-8";

    /**
     * {@value}
     */
    public static final boolean DEFAULT_PRINT_CONSOLE = false;
}
