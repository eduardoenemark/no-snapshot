package br.com.educode.plugin.util;

import static br.com.educode.plugin.environment.Constants.NO_SNAPSHOT_ARTIFACT_ID_TAGNAME;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
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
 *
 * @author eduardo@educode.com.br
 */
public class Utils {

    public static Document getDocument(String xmlFile)
            throws ParserConfigurationException, SAXException, IOException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
    }

    public static NodeList getNodeList(Document document, String tagName) {
        return (NodeList) document.getElementsByTagName(tagName);
    }

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
