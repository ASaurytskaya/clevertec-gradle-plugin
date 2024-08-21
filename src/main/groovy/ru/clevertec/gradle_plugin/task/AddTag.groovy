package ru.clevertec.gradle_plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import ru.clevertec.gradle_plugin.util.GitExecutor

class AddTag extends DefaultTask {

    @TaskAction
    void addTag() {

        logger.info("Executing addTag task")

        def output = GitExecutor.getLastTag(project)

        String currentVersion
        if(output.isEmpty()) {
            currentVersion = "0.0"
        } else {
            currentVersion = output.replace("v", "")
        }

        def branch = GitExecutor.getCurrentBranch(project)
        def newTag = determineNewVersion(currentVersion, branch)

        GitExecutor.createNewTag(project, newTag)

        logger.info("New version tagged: ${newTag}")

        logger.info("addTag task successfully executed, current version: ${newTag}")
    }

    private String determineNewVersion(String lastTag, String branch) {
        def parts = lastTag.replace("v", "").split('[.\\-]')
        def major = parts[0].toInteger()
        def minor = parts[1].toInteger()

        switch (branch) {
            case "dev":
            case "develop":
            case "qa":
                minor++
                return "v${major}.${minor}"
            case "stage":
                minor++
                return "v${major}.${minor}-rc"
            case "master":
                major++
                minor = 0
                return "v${major}.${minor}"
            default:
                return "v${major}.${minor}-SNAPSHOT"
        }

    }
}
