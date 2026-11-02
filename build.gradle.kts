plugins {
    java
    id("net.neoforged.moddev") version "2.0.148"
}

group = "dev.isxander"
version = "1.21.1+1.1"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    withSourcesJar()
}

sourceSets {
    val main by getting
    val client by creating {
        compileClasspath += main.output
        compileClasspath += main.compileClasspath
        runtimeClasspath += main.output + main.runtimeClasspath
    }
}

neoForge {
    version = "21.1.265"
    addModdingDependenciesTo(sourceSets.getByName("client"))

    runs {
        create("client") { client() }
        create("server") { server(); programArgument("--nogui") }
    }

    mods {
        create("debugify") {
            sourceSet(sourceSets.main.get())
            sourceSet(sourceSets.getByName("client"))
        }
    }
}

tasks.named("compileClientJava") {
    dependsOn(tasks.named("compileJava"))
}

repositories {
    mavenCentral()
    maven("https://maven.isxander.dev/releases") { name = "Xander Maven" }
}

dependencies {
    compileOnly("dev.isxander:yet-another-config-lib:3.8.2+1.21.1-neoforge")
}

tasks.jar {
    from(sourceSets.getByName("client").output)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(21)
}
