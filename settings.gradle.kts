pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "InvoiceApp"
include(":androidApp")
include(":shared")
include(":core-db")
