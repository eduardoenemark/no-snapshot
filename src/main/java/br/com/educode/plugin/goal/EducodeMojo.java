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
 * EducodeMojo Abstract class is super class to the daughter class that
 * implements specific actions.
 *
 * @since 1.0-beta
 * @author <a href="mailto:eduardo@educode.com.br">Eduardo Vieira</a>
 */
public abstract class EducodeMojo extends AbstractMojo {

    /**
     *
     * @see init method.
     */
    protected EducodeMojo() {
        this.init();
    }

    /**
     * It is for initialization of default values to EducodeMojo fields.
     */
    public void init() {
        this.setPomFile(DEFAULT_POM_NAME);
        this.setOutputPomFile(DEFAULT_POM_NO_SNAPSHOT_NAME);
        this.setSuffix(VERSION_SUFFIX);
        this.setEncode(DEFAULT_ENCODE);
        this.setPrintConsole(DEFAULT_PRINT_CONSOLE);
    }

    /**
     * Select in project tag, parent tag, dependency tag and plugin tag that
     * have in yours artifactId value equals to no-snapshot.artifactId values.
     *
     * @see org.w3c.dom.Node
     * @param document represents XML file.
     * @return List of Node that contains all selected nodes.
     * @throws MojoExecutionException Exception to any failure of execution.
     */
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

    /**
     * @see org.w3c.dom.Document
     * @return Document represents XML File (POM).
     * @throws ParserConfigurationException Consult documentation of the API.
     * @throws SAXException Consult documentation of the API.
     * @throws IOException Consult documentation of the API.
     */
    public Document getDocument()
            throws ParserConfigurationException, SAXException, IOException {
        if (this.document == null) {
            this.document = Utils.getDocument(this.getPomFile());
        }
        return this.document;
    }

    /**
     *
     * @return String that is address pom file.
     */
    public String getPomFile() {
        return pomFile;
    }

    /**
     *
     * @param pomFile is the address to access pom file.
     */
    public void setPomFile(String pomFile) {
        this.pomFile = pomFile;
    }

    /**
     *
     * @return String is output name to generated pom file.
     */
    public String getOutputPomFile() {
        return outputPomFile;
    }

    /**
     *
     * @param outputPomFile that is output name to future generated pom file.
     */
    public void setOutputPomFile(String outputPomFile) {
        this.outputPomFile = outputPomFile;
    }

    /**
     *
     * @return String that is represents suffix to concatenate with version
     * value of the dependency.
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     *
     * @param suffix is a String to be used in work with value version tag of
     * dependency.
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     *
     * @return String that is represents charset encode of pom file.
     */
    public String getEncode() {
        return encode;
    }

    /**
     *
     * @param encode is a String that represents charset encode of pom file.
     */
    public void setEncode(String encode) {
        this.encode = encode;
    }

    /**
     *
     * @return Boolean is to decide whether of content file is print in screen
     * or not.
     */
    public Boolean getPrintConsole() {
        return printConsole;
    }

    /**
     *
     * @param printConsole is Boolean value for print or not in screen.
     */
    public void setPrintConsole(Boolean printConsole) {
        this.printConsole = printConsole;
    }

    /**
     *
     */
    @Parameter(property = "pomFile")
    private String pomFile;

    /**
     *
     */
    @Parameter(property = "outputPomFile")
    private String outputPomFile;

    /**
     *
     */
    @Parameter(property = "suffix")
    private String suffix;

    /**
     *
     */
    @Parameter(property = "encode")
    private String encode;

    /**
     *
     */
    @Parameter(property = "printConsole")
    private Boolean printConsole;

    /**
     *
     */
    private Document document;
}
