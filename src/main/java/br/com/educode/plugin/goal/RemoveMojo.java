package br.com.educode.plugin.goal;

import static br.com.educode.plugin.environment.Constants.ARTIFACT_ID_TAGNAME;
import static br.com.educode.plugin.environment.Constants.DEPENDENCY_TAGNAME;
import static br.com.educode.plugin.environment.Constants.EMPTY;
import static br.com.educode.plugin.environment.Constants.PARENT_TAGNAME;
import static br.com.educode.plugin.environment.Constants.PLUGIN_TAGNAME;
import static br.com.educode.plugin.environment.Constants.PROJECT_TAGNAME;
import static br.com.educode.plugin.environment.Constants.VERSION_SUFFIX;
import static br.com.educode.plugin.environment.Constants.VERSION_TAGNAME;
import static br.com.educode.plugin.util.Utils.getDocument;
import static br.com.educode.plugin.util.Utils.getNoSnapshotArtifactIdsTextContent;
import static br.com.educode.plugin.util.Utils.getNodeByTagName;
import static br.com.educode.plugin.util.Utils.getNodeList;
import static br.com.educode.plugin.util.Utils.getTextContentOfDocument;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author eduardo@educode.com.br
 */
@Mojo(name = "remove")
public class RemoveMojo extends AbstractMojo {

    public void init() {
        if (StringUtils.isEmpty(this.pomFile)) {
            this.pomFile = "pom.xml";
        }
        if (StringUtils.isEmpty(this.suffix)) {
            this.suffix = VERSION_SUFFIX;
        }
        if (StringUtils.isEmpty(this.destinationFile)) {
            this.destinationFile = "no-snapshot.pom.xml";
        }
        if (StringUtils.isEmpty(this.encode)) {
            this.encode = "UTF-8";
        }
        if (StringUtils.isEmpty(this.profiles)) {
            this.profiles = "";
        }
    }

    @Override
    public void execute() throws MojoExecutionException {
        this.init();
        try {
            Document document = getDocument(this.pomFile);
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
                                    StringUtils.replaceOnce(
                                            versionNodeOfDependencyNode.getTextContent(), this.suffix, EMPTY
                                    )
                            );
                        }
                    }
                }
            }
            System.out.println(getTextContentOfDocument(document, encode));
//            Files.write(Paths.get(this.destinationFile), document.getTextContent().getBytes());
        } catch (Exception exception) {
            throw new MojoExecutionException(exception.getMessage(), exception);
        }
    }

    @Parameter(property = "pomFile")
    private String pomFile;

    @Parameter(property = "destinationFile")
    private String destinationFile;

    @Parameter(property = "suffix")
    private String suffix;

    @Parameter(property = "encode")
    private String encode;

    @Parameter(property = "profiles")
    private String profiles;
}
