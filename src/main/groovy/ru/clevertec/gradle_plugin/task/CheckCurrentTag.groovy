package ru.clevertec.gradle_plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import ru.clevertec.gradle_plugin.service.GitExecutor

class CheckCurrentTag extends DefaultTask {

    @TaskAction
    void checkCurrentTag() {
        logger.info("Executing checkCurrentTag task")

        def gitTagResult = GitExecutor.getInstance(project).currentTag

        if(!gitTagResult.isEmpty()) {
            String message = "Current commit already tagged: ${gitTagResult}"
            logger.error(message)
            throw new GradleException(message)
        }

        logger.info("checkCurrentTag task successfully executed")
    }
}
