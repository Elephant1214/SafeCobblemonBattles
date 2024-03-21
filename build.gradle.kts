plugins {
    kotlin("jvm") version ("1.9.22")
    id("fabric-loom") version ("1.5-SNAPSHOT")
}

version = properties["mod_version"]!! as String
group = properties["maven_group"]!! as String

base {
    archivesName = properties["archives_base_name"]!! as String
}

loom {
    splitEnvironmentSourceSets()

    mods {
        create("safecobblebattles") {
            sourceSet(sourceSets["main"])
        }
    }
}

repositories {
    mavenCentral()
    maven("https://maven.impactdev.net/repository/development/")
}

dependencies {
    minecraft("com.mojang:minecraft:${properties["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${properties["yarn_mappings"]}:v2")
    modImplementation("net.fabricmc:fabric-loader:${properties["loader_version"]}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${properties["fabric_version"]}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${properties["fabric_kotlin_version"]}")

    modImplementation("com.cobblemon:fabric:${properties["cobblemon_version"]}")
}

tasks {
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(mutableMapOf("version" to project.version))
        }
    }

    named<Copy>("processResources") {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        from(sourceSets["main"].resources.srcDirs) {
            include("fabric.mod.json")
            expand(project.properties)
        }
    }

    jar {
        from("LICENSE") {
            rename { "${it}_${project.base.archivesName.get()}" }
        }
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}
