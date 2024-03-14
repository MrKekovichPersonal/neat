plugins {
    kotlin("jvm") version "1.9.22"
    `maven-publish`
}

group = "io.github.mrkekovich.neat"
version = "0.1.0a2"


repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

sourceSets {
    main {
        resources {
            srcDir("src/main/resources")
        }
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.github.mrkekovich.neat"
            artifactId = "neat"
            version = "0.1.0a2"

            from(components["java"])
        }
    }
}
