package ru.clevertec.gradle_plugin.service

class VersionCalculator {

    String calculateNewVersion(String lastTag, String branch) {

        String version = (lastTag == null || lastTag.isEmpty()) ? "0.0" : lastTag.replace("v", "")

        def parts =version.split('[.\\-]')
        def major = parts[0].toInteger()
        def minor = parts[1].toInteger()

        switch (branch) {
            case "dev":
            case "develop":
            case "qa":
                minor++
                return "v${major}.${minor}"
            case "stage":
                minor++
                return "v${major}.${minor}-rc"
            case "master":
                major++
                minor = 0
                return "v${major}.${minor}"
            default:
                return "v${major}.${minor}-SNAPSHOT"
        }

    }
}
