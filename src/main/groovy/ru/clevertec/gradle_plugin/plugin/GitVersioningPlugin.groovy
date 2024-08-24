package ru.clevertec.gradle_plugin.plugin

import org.gradle.api.Project
import org.gradle.api.Plugin
import ru.clevertec.gradle_plugin.extension.CustomExtension
import ru.clevertec.gradle_plugin.task.AddTag
import ru.clevertec.gradle_plugin.task.CheckCurrentTag
import ru.clevertec.gradle_plugin.task.CheckGitInstalled
import ru.clevertec.gradle_plugin.task.CheckUncommitedFiles
import ru.clevertec.gradle_plugin.task.PushToRemote

class GitVersioningPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.create('customExtension', CustomExtension)

        project.tasks.register("checkGitInstalled", CheckGitInstalled) {
            group = "Git Versioning"
            description = "Check whether or not git is installed"
        }

        project.tasks.register("checkCurrentTag", CheckCurrentTag) {
            group = "Git Versioning"
            description = "Check whether or not there is a tag on the current version"

            dependsOn("checkGitInstalled")
        }

        project.tasks.register("checkUncommitedFiles", CheckUncommitedFiles) {
            group = "Git Versioning"
            description = "Check uncommited files in the working directory"

            dependsOn("checkCurrentTag")
        }

        project.tasks.register("addTag", AddTag) {
            group = "Git Versioning"
            description = "Calculate tag for current version of project"

            dependsOn("checkUncommitedFiles")
        }

        project.tasks.register("pushToRemote", PushToRemote) {
            group = "Git Versioning"
            description = "Create tag and push it to remote repository"

            dependsOn("addTag")
        }

    }
}
