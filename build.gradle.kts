plugins {
    kotlin("jvm") version "2.1.0"
}

sourceSets {
    main {
        kotlin.srcDir("src")

        kotlin.exclude("test/**")
        kotlin.exclude(".idea/**")
        kotlin.exclude(".gradle/**")

        resources.srcDir("src/main/resources")
    }
    test {
        kotlin.srcDir("test")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))

    // Use TurnKey Z3 that bundles native libs for macOS/Windows/Linux
    implementation("tools.aqua:z3-turnkey:4.12.2")
}

// You no longer need to tweak java.library.path, so remove this:
//// val z3LibDir = "$projectDir/lib/z3"
//// tasks.withType<JavaExec>().configureEach {
////     jvmArgs("-Djava.library.path=$z3LibDir")
//// }

// Keep your JavaExec tasks as they were:
tasks.register<JavaExec>("setupDay") {
    mainClass.set("aoc.FetchAndSetupAoCDayKt")
    classpath = sourceSets["main"].runtimeClasspath
    standardInput = System.`in`

    val dayArg = project.properties["day"] as? String
    val yearArg = project.properties["year"] as? String
    args(listOfNotNull(dayArg, yearArg))

    workingDir = project.rootDir
}

tasks.register<JavaExec>("createDayFiles") {
    mainClass.set("aoc.CreateDayFilesKt")
    classpath = sourceSets["main"].runtimeClasspath
    standardInput = System.`in`

    val dayArg = project.properties["day"] as? String
    val yearArg = project.properties["year"] as? String
    args(listOfNotNull(dayArg, yearArg))

    workingDir = project.rootDir
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
}
