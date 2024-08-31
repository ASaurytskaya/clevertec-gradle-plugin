package ru.clevertec.gradle_plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import ru.clevertec.gradle_plugin.service.GitExecutor
import ru.clevertec.gradle_plugin.service.VersionCalculator

class AddTag extends DefaultTask {

    @TaskAction
    void addTag() {

        logger.info("Executing addTag task")

        def lastTag = GitExecutor.getInstance(project).getLastTag()
        def branch = GitExecutor.getInstance(project).getCurrentBranch()
        def newTag = new VersionCalculator().calculateNewVersion(lastTag, branch)

        logger.info("New version tagged: ${newTag}")

        project.customExtension.tag = newTag

        logger.info("addTag task successfully executed, current version: ${newTag}")
    }

}
