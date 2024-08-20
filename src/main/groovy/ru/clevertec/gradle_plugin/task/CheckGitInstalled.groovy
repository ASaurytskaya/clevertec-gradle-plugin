package ru.clevertec.gradle_plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import ru.clevertec.gradle_plugin.util.GitExecutor

class CheckGitInstalled extends DefaultTask {

    @TaskAction
    void checkGitInstalled() {
        logger.lifecycle("GitVersioningPlugin is applied to the project.")
        logger.info("Executing checkGitInstalled task")

        def output = GitExecutor.getGitLog(project)
        if(output.startsWith("fatal")) {
            logger.error("No .git directory found")
            throw new GradleException("Git is not installed in the project")
        }

        logger.info("checkGitInstalled task successfully executed")
    }
}
