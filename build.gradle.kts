// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // https://github.com/ben-manes/gradle-versions-plugin/releases
    id("com.github.ben-manes.versions") version "0.42.0"
    // https://github.com/gradle-nexus/publish-plugin/releases
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0" // api
    id("java")
    id("jacoco")
}

buildscript {
    val sgCompileSdk by extra(31) // Android 12 (S)
    val sgMinSdk by extra(21) // Android 5 (L)
    val sgTargetSdk by extra(31) // Android 12 (S)

    // version 21xxxyy -> min SDK 21, release xxx, build yy
    val sgVersionCode by extra(2106402)
    val sgVersionName by extra("64.0.2")

    val kotlinVersion by extra("1.6.10") // https://kotlinlang.org/docs/releases.html#release-details
    val coroutinesVersion by extra("1.6.0") // https://github.com/Kotlin/kotlinx.coroutines/blob/master/CHANGES.md

    // https://developer.android.com/jetpack/androidx/releases
    val coreVersion by extra("1.6.0") // https://developer.android.com/jetpack/androidx/releases/core
    val annotationVersion by extra("1.3.0")
    val lifecycleVersion by extra("2.4.0")
    val roomVersion by extra("2.4.1") // https://developer.android.com/jetpack/androidx/releases/room
    val fragmentVersion by extra("1.4.1") // https://developer.android.com/jetpack/androidx/releases/fragment

    val timberVersion by extra("5.0.1") // https://github.com/JakeWharton/timber/blob/master/CHANGELOG.md

    val isCiBuild by extra { System.getenv("CI") == "true" }

    // load some properties that should not be part of version control
    if (file("secret.properties").exists()) {
        val properties = java.util.Properties()
        properties.load(java.io.FileInputStream(file("secret.properties")))
        properties.forEach { property ->
            project.extra.set(property.key as String, property.value)
        }
    }

      repositories {
        maven {
          url = uri("https://plugins.gradle.org/m2/")
        }
      }

    dependencies {
        classpath("com.android.tools.build:gradle:7.1.2") // libraries, SeriesGuide
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.cloud.tools:endpoints-framework-gradle-plugin:2.1.0") // SeriesGuide
        // Firebase Crashlytics
        // https://firebase.google.com/support/release-notes/android
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.8.1")
        classpath("org.jacoco:org.jacoco.ant:0.8.5")
        classpath("org.jacoco:org.jacoco.core:0.8.8")
        classpath("org.jacoco:org.jacoco.agent:0.8.8")
        classpath("org.jacoco:org.jacoco.report.xml:0.8.8")
        
//         classpath("gradle.plugin.com.vanniktech:gradle-android-junit-jacoco-plugin:0.16.0")
    }
}
apply(plugin = "jacoco")


fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}


// private val classDirectoriesTree = fileTree(project.buildDir) {
//     include(
//             "**/classes/**/main/**",
//             "**/intermediates/classes/debug/**",
//             "**/intermediates/javac/debug/*/classes/**", // Android Gradle Plugin 3.2.x support.
//             "**/tmp/kotlin-classes/debug/**"
//     )
// }

// private val sourceDirectoriesTree = fileTree("${project.buildDir}") {
//     include(
//             "src/main/java/**",
//             "src/main/kotlin/**",
//             "src/debug/java/**",
//             "src/debug/kotlin/**"
//     )
// }

// private val executionDataTree = fileTree(project.buildDir) {
//     include(
//             "outputs/code_coverage/**/*.ec",
//             "jacoco/jacocoTestReportDebug.exec",
//             "jacoco/testDebugUnitTest.exec",
//             "jacoco/test.exec"
//     )
// }

// fun JacocoReport.setDirectories() {
//     sourceDirectories.setFrom(sourceDirectoriesTree)
//     classDirectories.setFrom(classDirectoriesTree)
//     executionData.setFrom(executionDataTree)
// }



tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

nexusPublishing {
    packageGroup.set("com.uwetrottmann")
    repositories {
        sonatype {
            if (rootProject.hasProperty("SONATYPE_NEXUS_USERNAME")
                && rootProject.hasProperty("SONATYPE_NEXUS_PASSWORD")) {
                username.set(rootProject.property("SONATYPE_NEXUS_USERNAME").toString())
                password.set(rootProject.property("SONATYPE_NEXUS_PASSWORD").toString())
            }
        }
    }
}

// jacoco {
//     toolVersion = "0.8.8"
//     reportsDirectory.set(layout.buildDirectory.dir("${buildDir}\\jacoco\\"))
// }



// tasks.withType<Test> {
//     configure<JacocoTaskExtension> {
//         isIncludeNoLocationClasses = true
//         isEnabled = true
//         destinationFile = layout.buildDirectory.file("jacoco/${name}.exec").get().asFile
//         classDumpDir = layout.buildDirectory.dir("jacoco/classpathdumps").get().asFile
//         output = JacocoTaskExtension.Output.FILE
//     }
// }



// tasks.test {
//     configure<JacocoTaskExtension> {
//         isEnabled = true
//         destinationFile = layout.buildDirectory.file("jacoco/${name}.exec").get().asFile
//         includes = emptyList()
//         excludes = emptyList()
//         excludeClassLoaders = emptyList()
//         isIncludeNoLocationClasses = false
//         sessionId = "<auto-generated value>"
//         isDumpOnExit = true
//         classDumpDir = null
//         output = JacocoTaskExtension.Output.FILE
//         address = "192.168.2.86"
//         port = 6300
//         isJmx = false
//     }
// }


// tasks.jacocoTestReport {
//     reports {
//         xml.required.set(true)
//         xml.destination = file("${buildDir}/reports/jacoco/jacocoTestReport.xml")
//         //xml.destination = file("${buildDir}\\reports\\jacoco\\jacocoTestReport.xml")
//         csv.required.set(false)
//         html.required.set(false)
//         html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
//     }
//     setDirectories()
// //     sourceDirectories.setFrom(fileTree(projectDir) {
// //         include (
// //             "**\\src\\main\\java\\**",
// //             "**\\src\\main\\kotlin\\**",
// //             "**\\src\\debug\\java\\**",
// //             "**\\src\\debug\\kotlin\\**")
// //     })
//     classDirectories.setFrom(fileTree(projectDir) { 
//         include ("**\\classes\\**")
//     })    
//     executionData.setFrom(fileTree(projectDir) { include ("app\\build\\jacoco\\testPureDebugUnitTest.exec") })
// }

// tasks.jacocoTestCoverageVerification {
//     violationRules {
//         rule {
//             limit {
//                 minimum = "0.9".toBigDecimal()
//             }
//         }
//     }
// }

// val testCoverage by tasks.registering {
//     group = "verification"
//     description = "Runs the unit tests with coverage."

//     dependsOn(":test", ":jacocoTestReport", ":jacocoTestCoverageVerification")
//     val jacocoTestReport = tasks.findByName("jacocoTestReport")
//     jacocoTestReport?.mustRunAfter(tasks.findByName("test"))
//     tasks.findByName("jacocoTestCoverageVerification")?.mustRunAfter(jacocoTestReport)
// }




// tasks.register("clean", Delete::class) {
//     group = "build"
//     delete(rootProject.buildDir)
// }

tasks.register("Custclean", Delete::class) {
    group = "build"
    delete(rootProject.buildDir)
}

tasks.wrapper {
    //noinspection UnnecessaryQualifiedReference
    distributionType = Wrapper.DistributionType.ALL
}
