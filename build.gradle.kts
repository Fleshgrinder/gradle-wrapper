plugins {
    `kotlin-dsl`
}

group = "com.fleshgrinder.gradle"

gradlePlugin {
    plugins.register("wrapper") {
        id = "com.fleshgrinder.wrapper"
        implementationClass = "com.fleshgrinder.gradle.wrapper.WrapperPlugin"
    }
}

java {
    toolchain.languageVersion.set(libs.versions.java.map(JavaLanguageVersion::of))
}

kotlin {
    explicitApi()
}

tasks {
    register<NotImplemented>("publishSnapshot")
    register<NotImplemented>("publishRelease")
}

abstract class NotImplemented : DefaultTask() {
    @TaskAction fun execute() {
        if (System.getenv("GITHUB_ACTIONS") == "true") {
            val file = buildString {
                append(".github/workflows/gradle-")
                name.forEach { c ->
                    if (c in 'A'..'Z') append('-').append(c.toLowerCase())
                    else append(c)
                }
                append(".yaml")
            }
            println("::error file=$file,title=Task $path::Not implemented!")
        } else {
            println("\u001B[31mNot implemented!\u001B[0m")
        }
    }
}
