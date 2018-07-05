package br.com.brb.hsm.plugin;

import br.com.brb.hsm.api.security.Key;
import br.com.brb.hsm.api.util.UtilFile;
import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

// EXEMPLO DE CONFIGURACAO NO POM.XML.
/*
<plugin>
    <inherited>true</inherited>
    <groupId>br.com.brb.hsm</groupId>
    <artifactId>hsm-plugin</artifactId>
    <version>1.2.0-SNAPSHOT</version>
    <executions>
        <execution>
            <id>encrypt-in-properties</id>
            <phase>package</phase>
            <goals>
                <goal>encrypt-in-properties</goal>
            </goals>
            <configuration>
                <prefix>hsm.</prefix>
                <hostname>hsmspb.brb.com.br</hostname>
                <username>${usuarioHSM}</username>
                <password>${senhaHSM}</password>
                <port>4433</port>
                <yourCertificate>usrspbh/00000208@DOM1</yourCertificate>
                <theirCertificate>usrspbh/00000208@DOM1</theirCertificate>
                <acceptExpiredCertificate>true</acceptExpiredCertificate>
                <libraryName>tacndjavalib-service</libraryName>
                <overKey>
                    <filePathInPropertiesFile>${overKeyFilePath}</filePathInPropertiesFile>
                    <outputFilePath>${project.build.directory}/${overKeyFileName}</outputFilePath>
                </overKey>
                <propertiesFile>
                    <inputFilePath>${project.build.outputDirectory}/META-INF/default.properties</inputFilePath>
                    <outputFilePath>${project.build.outputDirectory}/META-INF/default.properties</outputFilePath>
                </propertiesFile>
            </configuration>
        </execution>
    </executions>
</plugin>
*/
@Mojo(name = "encrypt-in-properties")
public class EncryptInPropertiesMojo extends AbstractMojo {

    public void init() {
        if (this.prefix == null || this.prefix.length() == 0) {
            this.prefix = "";
        }
        this.key = new Key();
    }

    @Override
    public void execute() throws MojoExecutionException {
        this.init();
        getLog().info(String.format(LOG_FORMAT, "Start generate keys"));

        byte[] overKeyGen = key.generateRandomKey(MIN_LENGTH, MAX_LENGTH);
        byte[] simmetricKey = key.generateRandomKey(MIN_LENGTH, MAX_LENGTH);

        String passwordEncrypt = key.encrypt(password, new String(simmetricKey));
        String simmetricKeyEncrypt = key.encrypt(new String(simmetricKey), overKeyGen);
        byte[] overKeyEncrypt = key.encrypt(new String(overKeyGen)).getBytes();

        getLog().info(String.format(LOG_FORMAT, "Finish generate keys"));
        try {
            getLog().info(String.format(LOG_FORMAT, "Read file path of inputFilePath tag"));
            String contentOfProperties = new String(UtilFile.read(this.propertiesFile.get("inputFilePath")));

            StringBuilder buffer = new StringBuilder();
            buffer.append("\n# --- HSM CONFIGURATION ---\n");
            buffer.append(String.format(this.KEY_VALUE_FORMAT, prefix, "hostname", this.hostname));
            buffer.append(String.format(this.KEY_VALUE_FORMAT, prefix, "port", this.port));
            buffer.append(String.format(this.KEY_VALUE_FORMAT, prefix, "username", this.username));
            buffer.append(String.format(this.KEY_VALUE_FORMAT, prefix, "password", passwordEncrypt));
            buffer.append(String.format(this.KEY_VALUE_FORMAT, prefix, "simmetricKey", simmetricKeyEncrypt));
            buffer.append(String.format(this.KEY_VALUE_FORMAT, prefix, "overKeyFilePath", this.overKey.get("filePathInPropertiesFile")));
            buffer.append(String.format(this.KEY_VALUE_FORMAT, prefix, "yourCertificate", this.yourCertificate));
            buffer.append(String.format(this.KEY_VALUE_FORMAT, prefix, "theirCertificate", this.theirCertificate));
            buffer.append(String.format(this.KEY_VALUE_FORMAT, prefix, "acceptExpiredCertificate", this.acceptExpiredCertificate));
            buffer.append(String.format(this.KEY_VALUE_FORMAT, prefix, "libraryName", this.libraryName));
            buffer.append("# --- END ---\n");

            UtilFile.write(this.propertiesFile.get("outputFilePath"), contentOfProperties.concat(buffer.toString()).getBytes());

            getLog().info(String.format(LOG_FORMAT, "Generate overKey file"));
            key.encode(overKeyEncrypt, this.overKey.get("outputFilePath"));

        } catch (Exception exception) {
            throw new MojoExecutionException(exception.getMessage());
        }
        getLog().info(String.format(LOG_FORMAT, "Finish!"));
    }

    @Parameter(property = "prefix")
    private String prefix;

    @Parameter(property = "hostname")
    private String hostname;

    @Parameter(property = "username")
    private String username;

    @Parameter(property = "password")
    private String password;

    @Parameter(property = "port")
    private String port;

    @Parameter(property = "yourCertificate")
    private String yourCertificate;

    @Parameter(property = "theirCertificate")
    private String theirCertificate;

    @Parameter(property = "acceptExpiredCertificate")
    private String acceptExpiredCertificate;

    @Parameter(property = "libraryName")
    private String libraryName;

    @Parameter(property = "overKey")
    private Map<String, String> overKey;

    @Parameter(property = "propertiesFile")
    private Map<String, String> propertiesFile;

    private Key key;
    private final int MIN_LENGTH = 15;
    private final int MAX_LENGTH = 20;
    private final String KEY_VALUE_FORMAT = "%s%s=%s\n";
    private final String LOG_FORMAT = "HSM Plugin encrypt-in-properties Goal: %s";
}
