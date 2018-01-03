//import groovy.lang.Closure
//import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet


plugins {
    //application
    java
    kotlin("jvm") version "1.2.10"
    kotlin("kapt") version "1.2.10"
}

kapt {
    generateStubs = true
    correctErrorTypes = true
}

//java.sourceSets.forEach {
//    it.withConvention(KotlinSourceSet::class) {
//        kotlin.srcDir("$buildDir/generated/source/kapt/${it.name}")
//    }
//}

dependencies {
    compile(kotlin("stdlib:1.2.10"))
    compileOnly(kapt(project(":processor"))) // or use librairie in external project
    compile("javax.validation:validation-api:1.1.0.Final")
}

