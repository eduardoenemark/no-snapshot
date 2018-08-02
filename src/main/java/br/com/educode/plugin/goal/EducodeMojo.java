package br.com.educode.plugin.goal;

import static br.com.educode.plugin.environment.Constants.DEFAULT_ENCODE;
import static br.com.educode.plugin.environment.Constants.DEFAULT_POM_NAME;
import static br.com.educode.plugin.environment.Constants.DEFAULT_POM_NO_SNAPSHOT_NAME;
import static br.com.educode.plugin.environment.Constants.DEFAULT_PRINT_CONSOLE;
import static br.com.educode.plugin.environment.Constants.VERSION_SUFFIX;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * @since 1.0-beta
 * @author eduardo@educode.com.br
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

//    @Parameter(property = "profiles")
//    private String profiles;
}
