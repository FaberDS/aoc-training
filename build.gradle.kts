plugins {
    kotlin("jvm") version "2.1.0"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}



tasks.register<JavaExec>("fetchInput") {
    mainClass.set("Fetch_inputKt")

    classpath = sourceSets["main"].runtimeClasspath
    standardInput = System.`in`

    val dayArg = project.properties["day"] as? String
    val yearArg = project.properties["year"] as? String

    args(listOf(dayArg, yearArg).filterNotNull())

    workingDir = project.rootDir
}
tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
}
