plugins {
  id("org.metaborg.gitonium") version "0.1.3"

  // Set versions for plugins to use, only applying them in subprojects (apply false here).
  id("org.metaborg.spoofax.compiler.gradle.language") version("0.16.17") apply false
  id("org.metaborg.spoofax.compiler.gradle.adapter") version("0.16.17") apply false
  id("org.metaborg.spoofax.lwb.compiler.gradle.language") version("0.16.17") apply false

  id("de.set.ecj") version "1.4.1" apply false
  id("org.metaborg.coronium.bundle") version "0.3.6" apply false
  id("biz.aQute.bnd.builder") version "5.3.0" apply false
  id("org.jetbrains.intellij") version "0.6.5" apply false
}

allprojects {
  group = "org.example"

  repositories {
    maven("https://artifacts.metaborg.org/content/groups/public/")
  }
}

subprojects {
  plugins.withId("java") {
    val javaVersion = JavaVersion.VERSION_1_8
    configure<JavaPluginExtension> {
      sourceCompatibility = javaVersion
      targetCompatibility = javaVersion
    }
    tasks.withType<JavaCompile> {
      options.encoding = "UTF-8"
    }
  }
}

tasks.register("buildAll") {
  group = "composite build"
  dependsOn(subprojects.map { it.tasks.named("build") })
}

