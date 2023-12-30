plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.mokoResources)
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
            export(libs.moko.resources)
        }
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(libs.koin.core)
            api(libs.moko.mvvm.core)
            api(libs.moko.mvvm.flow)
            api(libs.moko.resources)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.moko.mvvm.test)
            implementation(libs.moko.resources.test)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.koin.android)
        }
        iosX64 {
            getting {
                resources.srcDirs("build/generated/moko/iosX64Main/src")
            }
        }
        iosArm64 {
            getting {
                resources.srcDirs("build/generated/moko/iosArm64Main/src")
            }
        }
        iosSimulatorArm64 {
            getting {
                resources.srcDirs("build/generated/moko/iosSimulatorArm64Main/src")
            }
        }
    }
}

android {
    namespace = "com.toquete.stopclenching"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    sourceSets {
        getByName("main").java.srcDirs("build/generated/moko/androidMain/src")
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.toquete.stopclenching"
    multiplatformResourcesClassName = "Resources"
}