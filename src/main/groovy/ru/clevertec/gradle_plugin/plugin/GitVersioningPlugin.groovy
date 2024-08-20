package ru.clevertec.gradle_plugin.plugin

import org.gradle.api.Project
import org.gradle.api.Plugin
import ru.clevertec.gradle_plugin.task.AddTag
import ru.clevertec.gradle_plugin.task.CheckCurrentTag
import ru.clevertec.gradle_plugin.task.CheckGitInstalled
import ru.clevertec.gradle_plugin.task.CheckUncommitedFiles

class GitVersioningPlugin implements Plugin<Project> {

    /*
    что-то не то. давай по шагам.
1 - мы проверяем наличие тага у текущего состояния проекта. если таг есть - пишем, сто таг уже присвоен и заканчиваем работу,
    если тага нет - продолжаем.
2 - проверяем наличие какого-либо тага у проекта. если есть - запоминаем. если нет - используем базовую версия 0.1
3 - проверяем наличие незакомиченных файлов в рабочей директории. если есть - пишем сообщение, что есть незакомиченные файлы
    и последнюю версию проекта (последний таг или базовая версия), если таких файлов нет - продолжаем работу
4 - проверяем ветку, из которой вызвана задача. в зависимости от ветки инкрементим версию проекта. создаем новый таг на
    основе новой версии и пушим изменения. пишем ообщение об успешном присвоении тага и с номером версии. заканчиваем работу
     */

    @Override
    void apply(Project project) {

        project.tasks.register("checkGitInstalled", CheckGitInstalled) {
            group = "Git Versioning"
            description = "Check whether or not git is installed"
        }

        project.tasks.register("checkCurrentTag", CheckCurrentTag) {
            group = "Git Versioning"
            description = "Check whether or not there is a tag on the current version"

            dependsOn("checkGitInstalled")
        }

        project.tasks.register("checkUncommitedFiles", CheckUncommitedFiles) {
            group = "Git Versioning"
            description = "Check uncommited files in the working directory"

            dependsOn("checkCurrentTag")
        }

        project.tasks.register("addTag", AddTag) {
            group = "Git Versioning"
            description = "Create tag for current version of project and push changes to remote repository"

            dependsOn("checkUncommitedFiles")
        }

    }
}
