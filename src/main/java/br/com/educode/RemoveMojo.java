package br.com.educode;

import java.util.Iterator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 *
 * @author eduardo
 */
@Mojo(name = "remove")
public class RemoveMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException {
        System.out.println("Remove Goal!");
        System.out.println("file: " + file);
        Iterator iterator = super.getPluginContext().keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            System.out.println("key: " + key);
        }
        
        System.out.println("project: " + super.getPluginContext().get("project"));
    }

    @Parameter(property = "file")
    private String file;
}
