plugins {
    kotlin("jvm") version "2.1.0"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("test"))
}


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
    // calls aoc.CreateDayFilesKt.main
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
