package ru.clevertec.gradle_plugin.util

import org.gradle.api.Project

class GitExecutor {

    static String getGitLog(Project project) {
        return executeGitCommand(project, 'git', 'log').trim()
    }

    static String getCurrentTag(Project project) {
        return executeGitCommand(project, 'git', 'tag', '--points-at', 'HEAD').trim()
    }

    static String getCurrentBranch(Project project) {
        return executeGitCommand(project, 'git', 'rev-parse', '--abbrev-ref', 'HEAD').trim()
    }

    static String getLastTag(Project project) {
        return executeGitCommand(project, 'git', 'describe', '--tags', '--abbrev=0').trim()
    }

    static  String getUncommitedFiles(Project project) {
        return executeGitCommand(project, 'git', 'ls-files', '--modified').trim()
    }

    static void createNewTag(Project project, String tag) {
        executeGitCommand(project, 'git', 'tag', tag)
    }

    static void pushToRemote(Project project, String remoteName, String tag) {
        executeGitCommand(project, 'git', 'push', remoteName, tag)
    }

    private static String executeGitCommand(Project project, String... args) {
        def output = new ByteArrayOutputStream()
        project.exec {
            commandLine args
            standardOutput = output
        }
        return output.toString()
    }
}
