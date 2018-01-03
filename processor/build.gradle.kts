plugins {
    //application
    //java
    //maven-publish
    kotlin("jvm") version "1.2.10"
    //kotlin("kapt") version "1.2.0"

}
dependencies {
    compile(kotlin("stdlib:1.2.10"))
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

