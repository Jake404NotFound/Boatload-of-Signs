package net.mcreator.boatloadofsigns;

import net.mcreator.gradle.MCreatorGradlePlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class BoatloadOfSignsGradlePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        // Apply the MCreator Gradle plugin
        project.getPlugins().apply(MCreatorGradlePlugin.class);
        
        // Add any additional configuration needed for the plugin
        project.getTasks().register("buildBoatloadOfSignsPlugin", task -> {
            task.dependsOn("jar");
            task.setDescription("Builds the Boatload of Signs plugin");
            task.setGroup("build");
        });
    }
}
