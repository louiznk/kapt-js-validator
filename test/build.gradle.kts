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
    //compile("javax.validation:validation-api:1.1.0.Final")
    compile("org.hibernate.validator:hibernate-validator:6.0.7.Final")
    runtime("org.glassfish:javax.el:3.0.1-b08")
    testCompile("org.junit.jupiter:junit-jupiter-api:5.0.0")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
