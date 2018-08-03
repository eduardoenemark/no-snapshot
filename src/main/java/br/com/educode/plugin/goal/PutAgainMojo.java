package br.com.educode.plugin.goal;

import static br.com.educode.plugin.environment.Constants.VERSION_TAGNAME;
import static br.com.educode.plugin.util.Utils.getNodeByTagName;
import static br.com.educode.plugin.util.Utils.getTextContentOfDocument;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.w3c.dom.Node;

/**
 * PutAgainMojo Class realize operation of put again suffix (-SNAPSHOT) of
 * version tag into nodes of the informed pom file.
 *
 * @since 1.0-beta
 * @author <a href="mailto:eduardo@educode.com.br">Eduardo Vieira</a>
 */
@Mojo(name = "put-again")
public class PutAgainMojo extends EducodeMojo {

    /**
     * @see EducodeMojo Constructor.
     */
    public PutAgainMojo() {
        super();
    }

    /**
     * Execute action of put again suffix.
     *
     * @throws MojoExecutionException Exception to any failure of execution.
     */
    @Override
    public void execute() throws MojoExecutionException {
        try {
            for (Node dependencyNode : getSelectedDependencyNode(this.getDocument())) {
                Node versionNodeOfDependencyNode = getNodeByTagName(dependencyNode.getChildNodes(), VERSION_TAGNAME);
                versionNodeOfDependencyNode.setTextContent(
                        versionNodeOfDependencyNode.getTextContent().concat(this.getSuffix())
                );
            }
            if (this.getPrintConsole()) {
                System.out.println(getTextContentOfDocument(this.getDocument(), this.getEncode()));
            } else {
                Files.write(
                        Paths.get(this.getOutputPomFile()),
                        getTextContentOfDocument(this.getDocument(), this.getEncode()).getBytes()
                );
            }
        } catch (Exception exception) {
            throw new MojoExecutionException(exception.getMessage(), exception);
        }
    }
}
