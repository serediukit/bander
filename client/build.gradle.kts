buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("org.sonarqube") version "4.4.1.3373"
}

sonar {
    properties {
        property("sonar.projectKey", "serediukit_bander")
        property("sonar.organization", "serediukit")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}