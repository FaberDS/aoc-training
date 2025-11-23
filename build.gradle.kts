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
    args(project.properties["day"] as? String)

    workingDir = project.rootDir
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
}
