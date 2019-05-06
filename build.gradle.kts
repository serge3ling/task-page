plugins {
    java
    application
    id("war")
    id("idea")
    id("org.springframework.boot").version("2.1.4.RELEASE")
    id("io.spring.dependency-management").version("1.0.7.RELEASE")
}

tasks.getByName("bootWar") {
    //baseName = "taskpage"
    version = "0.1.5"
    //classifier = "boot"
    //launchScript()
}

repositories {
    // Or jcenter. Or any Maven/Ivy/file repository here.
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    // https://mvnrepository.com/artifact/com.hp.autonomy.aci.client/aci-api
    compile("com.hp.autonomy.aci.client:aci-api:12.0.0")
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-thymeleaf")
    testCompile("junit:junit")
}

application {
    mainClassName = "taskpage.Application"
}
