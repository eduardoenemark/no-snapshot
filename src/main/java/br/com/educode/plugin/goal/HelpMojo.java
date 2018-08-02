package br.com.educode.plugin.goal;

import static br.com.educode.plugin.environment.Constants.DEFAULT_ENCODE;
import static br.com.educode.plugin.environment.Constants.DEFAULT_POM_NAME;
import static br.com.educode.plugin.environment.Constants.DEFAULT_POM_NO_SNAPSHOT_NAME;
import static br.com.educode.plugin.environment.Constants.DEFAULT_PRINT_CONSOLE;
import static br.com.educode.plugin.environment.Constants.VERSION_SUFFIX;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * @since 1.0-beta
 * @author eduardo@educode.com.br
 */
@Mojo(name = "help")
public class HelpMojo extends EducodeMojo {

    @Override
    public void execute() throws MojoExecutionException {

        try {

            System.out.println("---------------------------------------------");
            System.out.println("[NO-SNAPSHOT] PROPERTIES FOR USE: ");
            //
            System.out.println(" |-> pomFile");
            System.out.println(" |--  description: name of pom.xml file");
            System.out.println(" |--  default value: ".concat(DEFAULT_POM_NAME));
            //
            System.out.println(" |-> outputPomFile");
            System.out.println(" |--  description: name for new pom.xml generated");
            System.out.println(" |--  default value: ".concat(DEFAULT_POM_NO_SNAPSHOT_NAME));
            //
            System.out.println(" |-> suffix");
            System.out.println(" |--  description: final terminal in the version tag");
            System.out.println(" |--  default value: ".concat(VERSION_SUFFIX));
            //
            System.out.println(" |-> encode");
            System.out.println(" |--  description: encode apply to POM file");
            System.out.println(" |--  default value: ".concat(DEFAULT_ENCODE));
            //
            System.out.println(" |-> printConsole");
            System.out.println(" |--  description: flag to result output in console");
            System.out.println(" |--  default value: ".concat(String.valueOf(DEFAULT_PRINT_CONSOLE)));
            System.out.println("---------------------------------------------");
        } catch (Exception exception) {
            throw new MojoExecutionException(exception.getMessage(), exception);
        }
    }
}
