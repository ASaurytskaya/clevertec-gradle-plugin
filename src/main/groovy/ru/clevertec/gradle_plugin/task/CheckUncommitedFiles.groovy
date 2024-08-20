package ru.clevertec.gradle_plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import ru.clevertec.gradle_plugin.util.GitExecutor

class CheckUncommitedFiles extends DefaultTask {

    @TaskAction
    void checkUncommitedFiles() {

        logger.info("Executing checkUncommitedFiles task")

        def output = GitExecutor.getUncommitedFiles(project)

        if(!output.isEmpty()) {
            def output2 = GitExecutor.getLastTag(project)

            String version
            if(output2.isEmpty()) version = "v0.1"
             else version = output

            String message = "There are uncommitted changes. Please commit your changes before proceeding. Current version: ${version}.uncommitted"
            logger.error(message)
            throw new GradleException(message)
        }

        logger.info("checkUncommitedFiles task successfully executed")
    }
}
