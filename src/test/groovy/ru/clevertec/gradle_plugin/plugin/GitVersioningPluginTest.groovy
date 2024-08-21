package ru.clevertec.gradle_plugin.plugin

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class GitVersioningPluginTest extends Specification {

    def "plugin applies successfully"() {
        given:
        def project = ProjectBuilder.builder().build()

        when:
        project.pluginManager.apply("ru.clevertec.git-versioning-plugin")

        then:
        project.tasks.named("checkGitInstalled") != null
        project.tasks.named("checkCurrentTag") != null
        project.tasks.named("checkUncommitedFiles") != null
        project.tasks.named("addTag") != null
        project.tasks.named("pushToRemote") != null
    }

}
