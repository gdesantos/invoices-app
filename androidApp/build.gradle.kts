plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    kotlin("android")
}

var composeVersion = "1.2.0"

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.overmind.invoiceapp.android"
        minSdk = 24
        targetSdk = 32
        versionCode = 2
        versionName = "1.0.1"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-db"))

    implementation("io.insert-koin:koin-androidx-compose:3.2.0")

    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.navigation:navigation-compose:2.5.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.23.1")

    implementation(platform("com.google.firebase:firebase-bom:30.2.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
}
