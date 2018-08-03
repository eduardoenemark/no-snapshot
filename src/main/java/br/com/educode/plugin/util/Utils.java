package br.com.educode.plugin.util;

import static br.com.educode.plugin.environment.Constants.NO_SNAPSHOT_ARTIFACT_ID_TAGNAME;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

/**
 * Utils class for commons utilities to used by classes that contains actions
 * (execute method).
 *
 * @since 1.0-beta
 * @author <a href="mailto:eduardo@educode.com.br">Eduardo Vieira</a>
 */
public class Utils {

    /**
     * @see org.w3c.dom.Document
     * @param xmlFile represents localization or name file of the XML file (e.g.
     * pom.xml)
     * @return Document object for manipulation.
     * @throws ParserConfigurationException Consult documentation of the API.
     * @throws SAXException Consult documentation of the API.
     * @throws IOException Consult documentation of the API.
     */
    public static Document getDocument(String xmlFile)
            throws ParserConfigurationException, SAXException, IOException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
    }

    /**
     *
     * @see org.w3c.dom.Document
     * @see org.w3c.dom.Node
     * @see org.w3c.dom.NodeList
     * @param document that is Document object.
     * @param tagName that is name of tag to be recuperated.
     * @return NodeList that is subset of document by tagname.
     */
    public static NodeList getNodeList(Document document, String tagName) {
        return (NodeList) document.getElementsByTagName(tagName);
    }

    /**
     *
     * @see org.w3c.dom.Node
     * @see org.w3c.dom.NodeList
     * @param nodeList is set of Node.
     * @param tagName is the tag name to be recuperated of nodeList.
     * @return Node is first occurrence of agreed with tagName into of nodeList.
     */
    public static Node getNodeByTagName(NodeList nodeList, String tagName) {
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

    /**
     *
     * @see org.w3c.dom.Document
     * @param document is Document object.
     * @return String[] is array with all values of no-snapshot.artifactId
     * presents into of the pom file.
     */
    public static String[] getNoSnapshotArtifactIdsTextContent(Document document) {
        NodeList noSnapshotArtifactIdNodeList = getNodeList(document, NO_SNAPSHOT_ARTIFACT_ID_TAGNAME);
        int size = noSnapshotArtifactIdNodeList.getLength();
        String[] artifactIds = new String[size];
        for (int i = 0; i < size; i++) {
            artifactIds[i] = noSnapshotArtifactIdNodeList.item(i).getTextContent();
        }
        Arrays.sort(artifactIds);
        return artifactIds;
    }

    /**
     *
     * @see org.w3c.dom.Document
     * @param document is Document object that represents XML file.
     * @param encode is charset encode for document. 
     * @return String that is content of document.
     * @throws ClassNotFoundException Consult documentation of the API.
     * @throws InstantiationException Consult documentation of the API.
     * @throws IllegalAccessException Consult documentation of the API.
     */
    public static String getTextContentOfDocument(Document document, String encode)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        DOMImplementationLS domImplLS = (DOMImplementationLS) registry.getDOMImplementation("LS");
        LSSerializer lsSerializer = domImplLS.createLSSerializer();
        LSOutput lsOutput = domImplLS.createLSOutput();

        lsOutput.setEncoding(encode);
        lsOutput.setCharacterStream(new StringWriter());
        lsSerializer.write(document, lsOutput);

        return lsOutput.getCharacterStream().toString();
    }
}
