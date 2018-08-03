package br.com.educode.plugin.goal;

import static br.com.educode.plugin.environment.Constants.EMPTY;
import static br.com.educode.plugin.environment.Constants.VERSION_TAGNAME;
import static br.com.educode.plugin.util.Utils.getNodeByTagName;
import static br.com.educode.plugin.util.Utils.getTextContentOfDocument;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.codehaus.plexus.util.StringUtils;
import org.w3c.dom.Node;

/**
 * RemoveMojo Class realize operation of remove suffix (-SNAPSHOT) of version
 * tag into nodes of the informed pom file.
 *
 * @since 1.0-beta
 * @author <a href="mailto:eduardo@educode.com.br">Eduardo Vieira</a>
 */
@Mojo(name = "remove")
public class RemoveMojo extends EducodeMojo {

    /**
     * @see EducodeMojo Constructor.
     */
    public RemoveMojo() {
        super();
    }

    /**
     * Execute action of remove suffix.
     *
     * @throws MojoExecutionException Exception to any failure of execution.
     */
    @Override
    public void execute() throws MojoExecutionException {
        try {
            for (Node dependencyNode : getSelectedDependencyNode(this.getDocument())) {
                Node versionNodeOfDependencyNode = getNodeByTagName(dependencyNode.getChildNodes(), VERSION_TAGNAME);
                versionNodeOfDependencyNode.setTextContent(
                        StringUtils.replaceOnce(
                                versionNodeOfDependencyNode.getTextContent(),
                                this.getSuffix(),
                                EMPTY
                        )
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
