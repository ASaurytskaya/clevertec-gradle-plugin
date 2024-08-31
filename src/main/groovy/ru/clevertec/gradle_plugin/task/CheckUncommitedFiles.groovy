package ru.clevertec.gradle_plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import ru.clevertec.gradle_plugin.service.GitExecutor

class CheckUncommitedFiles extends DefaultTask {

    @TaskAction
    void checkUncommitedFiles() {

        logger.info("Executing checkUncommitedFiles task")

        def gitUncommitedFilesResult = GitExecutor.getInstance(project).getUncommitedFiles()

        if(!gitUncommitedFilesResult.isEmpty()) {
            def gitLastTagResult = GitExecutor.getInstance(project).getLastTag()
            String version = gitLastTagResult.isEmpty() ? "v0.1" : gitLastTagResult
            String message = "There are uncommitted changes. Please commit your changes before proceeding. Current version: ${version}.uncommitted"
            logger.error(message)
            throw new GradleException(message)
        }

        logger.info("checkUncommitedFiles task successfully executed")
    }
}
