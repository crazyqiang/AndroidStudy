repositories {
    google()
    jcenter()
    mavenCentral()
    maven {
        url = uri("$rootDir/spi_repo")
    }
}

allprojects {
    repositories {
//        mavenCentral()
//        google()
//        jcenter()
    }
}

plugins {
    `kotlin-dsl`
}