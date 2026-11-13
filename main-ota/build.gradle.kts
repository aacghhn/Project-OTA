plugins {
	java
	id("org.springframework.boot") version "4.1.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "main-ota"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:3.2.0")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testCompileOnly("org.projectlombok:lombok")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	inputs.file(layout.projectDirectory.file("openapi.yaml"))
}

tasks.register<Test>("generateOpenApi") {
	group = "documentation"
	description = "Generate openapi.yaml from the running Spring MVC API documentation."
	testClassesDirs = sourceSets.test.get().output.classesDirs
	classpath = sourceSets.test.get().runtimeClasspath
	filter {
		includeTestsMatching("com.example.api.MainOtaApplicationTests.apiDocumentationSnapshot")
	}
	val destination = layout.projectDirectory.file("openapi.yaml")
	systemProperty("openapi.output", destination.asFile.absolutePath)
	outputs.file(destination)
	outputs.upToDateWhen { false }
}
