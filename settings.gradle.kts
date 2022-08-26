rootProject.name = "chocopy.frontend"

pluginManagement {
  repositories {
    maven("https://artifacts.metaborg.org/content/groups/public/")
  }
}

if(org.gradle.util.VersionNumber.parse(gradle.gradleVersion).major < 6) {
  enableFeaturePreview("GRADLE_METADATA")
}

fun includeLanguageIfExists(path: String) {
  if (File(path).exists()) {
    include(path)
    project(":$path").buildFileName = "../language.build.gradle.kts"
  }
}

includeLanguageIfExists("ministratego")
