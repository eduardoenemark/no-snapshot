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
    }

    @Override
    public void execute() throws MojoExecutionException {
//        System.out.println("Remove Goal!");
//        System.out.println("file: " + pomFile);
//        Iterator iterator = super.getPluginContext().keySet().iterator();
//        while (iterator.hasNext()) {
//            String key = (String) iterator.next();
//            System.out.println("key: " + key);
//        }
//        System.out.println("project: " + super.getPluginContext().get("project"));

        this.init();
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.pomFile);
            NodeList nodeList = (NodeList) document.getElementsByTagName("");

        } catch (ParserConfigurationException parserConfigurationException) {
            parserConfigurationException.printStackTrace();
        } catch (SAXException saxException) {
            saxException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Parameter(property = "pomFile")
    private String pomFile;

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
