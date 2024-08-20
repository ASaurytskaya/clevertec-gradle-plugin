package ru.clevertec.gradle_plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import ru.clevertec.gradle_plugin.util.GitExecutor

class AddTag extends DefaultTask {

    private static final String REMOTE_NAME = "origin"

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

        try {
            logger.info("Pushing new tag to remote repository")

            GitExecutor.pushToRemote(project, remoteName, newTag)

        } catch (Exception e) {
            String message = "Exception during pushing to remote occurs"
            logger.error(message)
            throw new GradleException(message)
        }

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
