plugins {
    kotlin("jvm") version "1.9.20"
    id("com.diffplug.spotless") version "6.23.3"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        ktfmt()
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}
