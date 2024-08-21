package ru.clevertec.gradle_plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import ru.clevertec.gradle_plugin.util.GitExecutor

class PushToRemote extends DefaultTask {

    private static final String REMOTE_NAME = "origin"

    @TaskAction
    void pushToRemote() {

        logger.info("Executing pushToRemote task")

        def tag = GitExecutor.getLastTag(project)

        try {
            logger.info("Pushing new tag to remote repository")

            GitExecutor.pushToRemote(project, REMOTE_NAME, tag)

        } catch (Exception e) {
            String message = "Exception during pushing to remote occurs"
            logger.error(message)
            throw new GradleException(message, e)
        }

        logger.info("pushToRemote task successfully executed, current version: ${tag}")
    }

}
