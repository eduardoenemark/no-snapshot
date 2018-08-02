package br.com.educode.plugin.goal;

import static br.com.educode.plugin.environment.Constants.ARTIFACT_ID_TAGNAME;
import static br.com.educode.plugin.environment.Constants.DEPENDENCY_TAGNAME;
import static br.com.educode.plugin.environment.Constants.PARENT_TAGNAME;
import static br.com.educode.plugin.environment.Constants.PLUGIN_TAGNAME;
import static br.com.educode.plugin.environment.Constants.PROJECT_TAGNAME;
import static br.com.educode.plugin.environment.Constants.VERSION_TAGNAME;
import static br.com.educode.plugin.util.Utils.getDocument;
import static br.com.educode.plugin.util.Utils.getNoSnapshotArtifactIdsTextContent;
import static br.com.educode.plugin.util.Utils.getNodeByTagName;
import static br.com.educode.plugin.util.Utils.getNodeList;
import static br.com.educode.plugin.util.Utils.getTextContentOfDocument;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @since 1.0-beta
 * @author eduardo@educode.com.br
 */
@Mojo(name = "put-again")
public class PutAgainMojo extends EducodeMojo {

    public PutAgainMojo() {
        super();
    }

    @Override
    public void execute() throws MojoExecutionException {
        try {
            Document document = getDocument(this.getPomFile());
            String[] artifactIdsTextContent = getNoSnapshotArtifactIdsTextContent(document);

            List<NodeList> allDependencyNodeList = new ArrayList<>();
            allDependencyNodeList.add(getNodeList(document, PROJECT_TAGNAME));
            allDependencyNodeList.add(getNodeList(document, PARENT_TAGNAME));
            allDependencyNodeList.add(getNodeList(document, DEPENDENCY_TAGNAME));
            allDependencyNodeList.add(getNodeList(document, PLUGIN_TAGNAME));

            for (int c = 0, size = allDependencyNodeList.size(); c < size; c++) {
                NodeList dependencyNodeList = allDependencyNodeList.get(c);
                if (dependencyNodeList != null) {
                    for (int i = 0, dsize = dependencyNodeList.getLength(); i < dsize; i++) {
                        Node dependencyNode = dependencyNodeList.item(i);
                        Node artifactIdNodeOfDependencyNode = getNodeByTagName(dependencyNode.getChildNodes(), ARTIFACT_ID_TAGNAME);
                        Node versionNodeOfDependencyNode = getNodeByTagName(dependencyNode.getChildNodes(), VERSION_TAGNAME);

                        if (Arrays.binarySearch(artifactIdsTextContent, artifactIdNodeOfDependencyNode.getTextContent()) >= 0) {
                            versionNodeOfDependencyNode.setTextContent(
                                    versionNodeOfDependencyNode.getTextContent().concat(this.getSuffix())
                            );
                        }
                    }
                }
            }
            if (this.getPrintConsole()) {
                System.out.println(getTextContentOfDocument(document, this.getEncode()));
            } else {
                Files.write(
                        Paths.get(this.getOutputPomFile()),
                        getTextContentOfDocument(document, this.getEncode()).getBytes()
                );
            }
        } catch (Exception exception) {
            throw new MojoExecutionException(exception.getMessage(), exception);
        }
    }
}
