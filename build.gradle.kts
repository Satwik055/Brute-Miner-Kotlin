import groovy.xml.dom.DOMCategory.attributes
import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems.jar

plugins {
    kotlin("jvm") version "2.0.0"
    id ("application")

}

group = "org.ironclad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application{
    mainClass.set("com.ironclad.MainKt")
}

dependencies {
    testImplementation(kotlin("test"))

    //Apache http client
    implementation ("org.apache.httpcomponents.client5:httpclient5:5.2.1")

    //Logger
    implementation("org.slf4j:slf4j-api:1.6.1")
    implementation("org.slf4j:slf4j-simple:1.6.1")

}

//tasks.jar {
//    manifest {
//        attributes(
//            "Main-Class" to "com.ironclad.MainKt"
//        )
//    }
//}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(20)
}

