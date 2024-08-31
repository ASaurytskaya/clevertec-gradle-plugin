package ru.clevertec.gradle_plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import ru.clevertec.gradle_plugin.service.GitExecutor

class CheckGitInstalled extends DefaultTask {

    @TaskAction
    void checkGitInstalled() {
        logger.lifecycle("GitVersioningPlugin is applied to the project.")
        logger.info("Executing checkGitInstalled task")

        def gitLogResult = GitExecutor.getInstance(project).gitLog
        if(gitLogResult.startsWith("fatal")) {
            logger.error("No .git directory found")
            throw new GradleException("Git is not installed in the project")
        }

        def gitRemoteResult = GitExecutor.getInstance(project).getRemote()
        if(gitRemoteResult.isEmpty()) {
            logger.error("No remote git repository found")
            throw new GradleException("Git is not installed in the project: no remote git repository found)")
        }

        logger.info("checkGitInstalled task successfully executed")
    }
}
