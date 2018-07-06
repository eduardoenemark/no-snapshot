package br.com.educode.plugin;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
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
//        if (StringUtils.isEmpty(this.profiles)) {
//            this._profiles = ;
//        }
    }

    @Override
    public void execute() throws MojoExecutionException {
        // tags: parent, dependency and plugin -> artifactId
        this.init();
        try {
            Document document = this.getDocument(this.pomFile);
            String[] artifactIds = this.getNoSnapshotArtifactIds(document);
            
//            NodeList parentNodeList = document.getElementsByTagName(this.PARENT);
//            for (int i = 0, size = parentNodeList.getLength(); i < size; i++){
//                Node parent = parentNodeList.item(i);
//                NodeList childrens = parent.getChildNodes();
//                for (int j = 0, sizeChildrens = childrens.getLength(); j < sizeChildrens; j++) {
//                    Node children = childrens.item(j);
//                    if (children.getNodeName().equals(ARTIFACT_ID)) {
//                        if (Arrays.binarySearch(artifactIds, children.getTextContent()) >= 0){
//                            
//                        }
//                    }
//                }
//            }
            
        } catch (ParserConfigurationException parserConfigurationException) {
            throw new MojoExecutionException(parserConfigurationException.getMessage(), parserConfigurationException);
        } catch (SAXException saxException) {
            throw new MojoExecutionException(saxException.getMessage(), saxException);
        } catch (IOException ioException) {
            throw new MojoExecutionException(ioException.getMessage(), ioException);
        }
    }

    
    
    private Document getDocument(String pomFile) throws ParserConfigurationException, SAXException, IOException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.pomFile);
    }

    private String[] getNoSnapshotArtifactIds(Document pomDocument) {
        NodeList nodeList = (NodeList) pomDocument.getElementsByTagName(this.NO_SNAPSHOT_ARTIFACT_ID);
        int size = nodeList.getLength();
        String[] artifactIds = new String[size];
        for (int i = 0; i < size; i++) {
            artifactIds[i] = nodeList.item(i).getTextContent();
        }
        return artifactIds;
    }

    @Parameter(property = "pomFile")
    private String pomFile;

    @Parameter(property = "suffix")
    private String suffix;

    private final String NO_SNAPSHOT_ARTIFACT_ID = "no-snapshot.artifactId";
    private final String ARTIFACT_ID = "artifactId";
    private final String VERSION = "version";
    private final String PARENT = "parent";
    private final String DEPENDENCY = "dependency";
    private final String PLUGIN = "plugin";

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
