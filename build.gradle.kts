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

configure<com.diffplug.gradle.spotless.SpotlessExtension> { // if you are using build.gradle.kts, instead of 'spotless {' use:
    // configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
        // by default the target is every '.kt' and '.kts` file in the java sourcesets
        ktfmt() // has its own section below
    }
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}
