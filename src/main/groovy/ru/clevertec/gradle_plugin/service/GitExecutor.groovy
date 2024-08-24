package ru.clevertec.gradle_plugin.service

import org.gradle.api.Project

class GitExecutor {
    private static GitExecutor instance
    private Project project

    private GitExecutor(Project project) {
        this.project = project
    }

    static GitExecutor getInstance(Project project) {
        if (instance == null) {
            instance = new GitExecutor(project)
        }
        return instance
    }

    String getRemote() {
        return executeGitCommand('git', 'remote', '-v')
    }

    String getGitLog() {
        return executeGitCommand('git', 'log').trim()
    }

    String getCurrentTag() {
        return executeGitCommand('git', 'tag', '--points-at', 'HEAD').trim()
    }

    String getCurrentBranch() {
        return executeGitCommand('git', 'rev-parse', '--abbrev-ref', 'HEAD').trim()
    }

    String getLastTag() {
        return executeGitCommand('git', 'describe', '--tags', '--abbrev=0').trim()
    }

    String getUncommitedFiles() {
        return executeGitCommand('git', 'ls-files', '--modified').trim()
    }

    void createNewTag(String tag) {
        executeGitCommand('git', 'tag', tag)
    }

    void pushToRemote(String remoteName, String tag) {
        executeGitCommand('git', 'push', remoteName, tag)
    }

    private String executeGitCommand(String... args) {
        def output = new ByteArrayOutputStream()
        this.project.exec {
            commandLine args
            standardOutput = output
        }
        return output.toString()
    }
}
