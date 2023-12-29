plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Stop Clenching Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "MultiPlatformLibrary"
            isStatic = false
            export(libs.moko.mvvm.core)
            export(libs.moko.mvvm.flow)
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(libs.koin.core)
            api(libs.moko.mvvm.core)
            api(libs.moko.mvvm.flow)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.moko.mvvm.test)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.koin.android)
        }
    }
}

android {
    namespace = "com.toquete.stopclenching"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}