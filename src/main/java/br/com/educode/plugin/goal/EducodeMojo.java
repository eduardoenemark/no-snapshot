package br.com.educode.plugin.goal;

import static br.com.educode.plugin.environment.Constants.ARTIFACT_ID_TAGNAME;
import static br.com.educode.plugin.environment.Constants.DEFAULT_ENCODE;
import static br.com.educode.plugin.environment.Constants.DEFAULT_POM_NAME;
import static br.com.educode.plugin.environment.Constants.DEFAULT_POM_NO_SNAPSHOT_NAME;
import static br.com.educode.plugin.environment.Constants.DEFAULT_PRINT_CONSOLE;
import static br.com.educode.plugin.environment.Constants.DEPENDENCY_TAGNAME;
import static br.com.educode.plugin.environment.Constants.PARENT_TAGNAME;
import static br.com.educode.plugin.environment.Constants.PLUGIN_TAGNAME;
import static br.com.educode.plugin.environment.Constants.PROJECT_TAGNAME;
import static br.com.educode.plugin.environment.Constants.VERSION_SUFFIX;
import br.com.educode.plugin.util.Utils;
import static br.com.educode.plugin.util.Utils.getNoSnapshotArtifactIdsTextContent;
import static br.com.educode.plugin.util.Utils.getNodeByTagName;
import static br.com.educode.plugin.util.Utils.getNodeList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @since 1.0-beta
 * <a href="mailto:eduardo@educode.com.br">Eduardo Vieira</a>
 */
public abstract class EducodeMojo extends AbstractMojo {

    public EducodeMojo() {
        this.init();
    }

    public void init() {
        this.setPomFile(DEFAULT_POM_NAME);
        this.setOutputPomFile(DEFAULT_POM_NO_SNAPSHOT_NAME);
        this.setSuffix(VERSION_SUFFIX);
        this.setEncode(DEFAULT_ENCODE);
        this.setPrintConsole(DEFAULT_PRINT_CONSOLE);
    }

    
    public List<Node> getSelectedDependencyNode(Document document) throws MojoExecutionException {
        try {

            List<Node> selectedDependencyNode = new ArrayList<Node>();
            String[] artifactIdsTextContent = getNoSnapshotArtifactIdsTextContent(document);

            List<NodeList> allDependencyNodeList = new ArrayList<NodeList>();
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

                        if (Arrays.binarySearch(artifactIdsTextContent, artifactIdNodeOfDependencyNode.getTextContent()) >= 0) {
                            selectedDependencyNode.add(dependencyNode);
                        }
                    }
                }
            }
            return selectedDependencyNode;
        } catch (Exception exception) {
            throw new MojoExecutionException(exception.getMessage(), exception);
        }
    }
    
    public Document getDocument()
            throws ParserConfigurationException, SAXException, IOException {
        if (this.document == null) {
            this.document = Utils.getDocument(this.getPomFile());
        }
        return this.document;
    }

    public String getPomFile() {
        return pomFile;
    }

    public void setPomFile(String pomFile) {
        this.pomFile = pomFile;
    }

    public String getOutputPomFile() {
        return outputPomFile;
    }

    public void setOutputPomFile(String outputPomFile) {
        this.outputPomFile = outputPomFile;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public Boolean getPrintConsole() {
        return printConsole;
    }

    public void setPrintConsole(Boolean printConsole) {
        this.printConsole = printConsole;
    }

    @Parameter(property = "pomFile")
    private String pomFile;

    @Parameter(property = "outputPomFile")
    private String outputPomFile;

    @Parameter(property = "suffix")
    private String suffix;

    @Parameter(property = "encode")
    private String encode;

    @Parameter(property = "printConsole")
    private Boolean printConsole;

    private Document document;
//    @Parameter(property = "profiles")
//    private String profiles;
}
