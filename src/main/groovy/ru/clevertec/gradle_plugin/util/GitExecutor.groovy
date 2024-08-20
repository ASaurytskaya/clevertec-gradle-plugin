package ru.clevertec.gradle_plugin.util

import org.gradle.api.Project

class GitExecutor {

    static String getGitLog(Project project) {
        return executeGitCommand(project, 'log').trim()
    }

    static String getCurrentTag(Project project) {
        return executeGitCommand(project, 'tag', '--points-at', 'HEAD').trim()
    }

    static String getCurrentBranch(Project project) {
        return executeGitCommand(project, 'rev-parse', '--abbrev-ref', 'HEAD').trim()
    }

    static String getLastTag(Project project) {
        return executeGitCommand(project, 'describe', '--tags', '--abbrev=0').trim()
    }

    static  String getUncommitedFiles(Project project) {
        return executeGitCommand(project, 'status', '--porcelain').trim()
    }
    static void createNewTag(Project project, String tag) {
        executeGitCommand(project, 'tag', tag)
    }

    static void pushToRemote(Project project, String remoteName, String tag) {
        executeGitCommand(project, 'push', 'master', remoteName, tag)
    }

    private static String executeGitCommand(Project project, String... args) {
        def output = new ByteArrayOutputStream()
        project.exec {
            commandLine 'git', args
            standardOutput = output
        }
        return output.toString()
    }
}
