package br.com.educode.plugin;

import java.io.IOException;
import java.util.Arrays;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

/**
 *
 * @author eduardo
 */
@Mojo(name = "remove")
public class RemoveMojo extends AbstractMojo {

    public void init() {
        if (StringUtils.isEmpty(this.pomFile)) {
            this.pomFile = "pom.xml";
        }
        if (StringUtils.isEmpty(this.suffix)) {
            this.suffix = "-SNAPSHOT";
        }
        if (StringUtils.isEmpty(this.destinationFile)) {
            this.destinationFile = "no-snapshot.pom.xml";
        }
        if (StringUtils.isEmpty(this.encode)) {
            this.encode = "UTF-8";
        }
    }

    @Override
    public void execute() throws MojoExecutionException {
        // tags: parent, dependency and plugin -> artifactId
        this.init();
        try {
            Document document = this.getDocument(this.pomFile);
            String[] artifactIdsTextContent = this.getNoSnapshotArtifactIdsTextContent(document);

//            for (String a : artifactIdsTextContent) {
//                System.out.println("a: " + a);
//            }

            NodeList dependencyNodeList = this.getNodeList(document, this.DEPENDENCY_TAGNAME);
            int dependencyNodeListSize = dependencyNodeList.getLength();

            for (int i = 0; i < dependencyNodeListSize; i++) {
                Node dependencyNode = dependencyNodeList.item(i);
                Node artifactIdNodeOfDependencyNode = this.getNodeByTagName(dependencyNode.getChildNodes(), this.ARTIFACT_ID_TAGNAME);
                Node versionNodeOfDependencyNode = this.getNodeByTagName(dependencyNode.getChildNodes(), this.VERSION_TAGNAME);

                System.out.println(artifactIdNodeOfDependencyNode.getTextContent() + ":" + Arrays.binarySearch(artifactIdsTextContent, artifactIdNodeOfDependencyNode.getTextContent()));

                if (Arrays.binarySearch(artifactIdsTextContent, artifactIdNodeOfDependencyNode.getTextContent()) != -1) {
                    versionNodeOfDependencyNode.setTextContent(StringUtils.replaceOnce(versionNodeOfDependencyNode.getTextContent(), this.suffix, this.EMPTY));
                }
            }
            System.out.println(this.getTextContentOfDocument(document));
//            Files.write(Paths.get(this.destinationFile), document.getTextContent().getBytes());

        /*} catch (ParserConfigurationException parserConfigurationException) {
            throw new MojoExecutionException(parserConfigurationException.getMessage(), parserConfigurationException);
        } catch (SAXException saxException) {
            throw new MojoExecutionException(saxException.getMessage(), saxException);
        } catch (IOException ioException) {
            throw new MojoExecutionException(ioException.getMessage(), ioException);*/
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new MojoExecutionException(exception.getMessage(), exception);
        }
    }

    private String getTextContentOfDocument(Document document)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        DOMImplementationLS domImplLS = (DOMImplementationLS) registry.getDOMImplementation("LS");

        LSSerializer lsSerializer = domImplLS.createLSSerializer();
        DOMConfiguration domConfig = lsSerializer.getDomConfig();
        domConfig.setParameter("format-pretty-print", true);

        LSOutput lsOutput = domImplLS.createLSOutput();
        lsOutput.setEncoding(this.encode);

        return lsSerializer.writeToString(document);
    }

    private Document getDocument(String pomFile) throws ParserConfigurationException, SAXException, IOException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(pomFile);
    }

    private NodeList getNodeList(Document document, String tagName) {
        return (NodeList) document.getElementsByTagName(tagName);
    }

    private Node getNodeByTagName(NodeList nodeList, String tagName) {
        Node wanted = null;
        for (int i = 0, size = nodeList.getLength(); i < size; i++) {
            wanted = nodeList.item(i);
            if (!wanted.getNodeName().equals(tagName)) {
                wanted = null;
            } else {
                break;
            }
        }
        return wanted;
    }

    private String[] getNoSnapshotArtifactIdsTextContent(Document document) {
        NodeList noSnapshotArtifactIdNodeList = this.getNodeList(document, this.NO_SNAPSHOT_ARTIFACT_ID_TAGNAME);
        int size = noSnapshotArtifactIdNodeList.getLength();
        String[] artifactIds = new String[size];
        for (int i = 0; i < size; i++) {
            artifactIds[i] = noSnapshotArtifactIdNodeList.item(i).getTextContent();
        }
        Arrays.sort(artifactIds);
        return artifactIds;
    }

    @Parameter(property = "pomFile")
    private String pomFile;

    @Parameter(property = "destinationFile")
    private String destinationFile;

    @Parameter(property = "suffix")
    private String suffix;

    @Parameter(property = "encode")
    private String encode;

    private Document document;

    private final String NO_SNAPSHOT_ARTIFACT_ID_TAGNAME = "no-snapshot.artifactId";
    private final String ARTIFACT_ID_TAGNAME = "artifactId";
    private final String VERSION_TAGNAME = "version";
    private final String PARENT_TAGNAME = "parent";
    private final String DEPENDENCY_TAGNAME = "dependency";
    private final String PLUGIN_TAGNAME = "plugin";
    private final String EMPTY = "";

//    @Parameter(property = "profiles")
//    private String profiles;
//    private String[] _profiles;
    /*
       // Cria o documento virtual XML para o processamento dos nós do arquivo
        Unmarshaller unmarshaller = JAXBContext.newInstance(TarifacaoMensal.class).createUnmarshaller();
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(path.toFile());
        NodeList nodeList = (NodeList) document.getElementsByTagName("Grupo_Tar");

        // Loop para percorrer todos os nós do arquivo temporário XML e converte cada nó na entidade TarifacaoMensal.
        JAXBElement<TarifacaoMensal> jaxbElement;
        TarifacaoMensal tarifacaoMensal;
        for (int i = 0, size = nodeList.getLength(); i < size; i++) {

            jaxbElement = unmarshaller.unmarshal(nodeList.item(i), TarifacaoMensal.class);
            tarifacaoMensal = jaxbElement.getValue();
            System.out.println(tarifacaoMensal);
        }
     */
}
