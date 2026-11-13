package com.example.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    properties = "server.port=18080")
@MockitoBean(types = {BookingService.class, CancellationService.class,
    CustomerReservationService.class, DailySalesService.class, GuidanceService.class,
    PartnerReservationService.class, ProductConfigurationService.class,
    RefundBalanceService.class, SalesStatusService.class, ScheduledPaymentService.class,
    UnavailabilityService.class})
class MainOtaApplicationTests {

	@Test
	void contextLoads() {
	}

    @Test
    void apiDocumentationSnapshot() throws Exception {
        try (var client = HttpClient.newHttpClient()) {
            var response = client.send(HttpRequest.newBuilder(
                    URI.create("http://localhost:18080/v3/api-docs.yaml")).GET().build(),
                HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            assertTrue(response.body().contains("openapi:"));
            var exportPath = System.getProperty("openapi.output");
            var destination = Path.of(exportPath != null ? exportPath :
                System.getenv().getOrDefault("API_DOCS_SNAPSHOT", "build/api-docs.yaml"));
            if (destination.getParent() != null) Files.createDirectories(destination.getParent());
            if (exportPath != null) {
                Files.writeString(destination,
                    "# Generated from Java by: bash gradlew generateOpenApi\n"
                    + "# Do not edit manually. Business rules and decisions are maintained in docs.\n"
                    + response.body());
            } else {
                Files.writeString(destination, response.body());
                var yaml = new Yaml(new SafeConstructor(new LoaderOptions()));
                Object committed = yaml.load(Files.readString(Path.of("openapi.yaml")));
                Object generated = yaml.load(response.body());
                assertEquals(committed, generated,
                    "Java 계약을 확인한 뒤 bash gradlew generateOpenApi로 명세를 갱신하세요. "
                    + "현재 실행 명세는 " + destination + "에 저장됐습니다.");
            }
        }
    }

    @Test
    void requestValidationDeclaredOnApiInterfaceStillApplies() throws Exception {
        try (var client = HttpClient.newHttpClient()) {
            var request = HttpRequest.newBuilder(
                    URI.create("http://localhost:18080/api/public/property-searches"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{}"))
                .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(400, response.statusCode());
            assertTrue(response.body().contains("INVALID_REQUEST"));
        }
    }

    @Test
    void accessDeniedUsesUnifiedErrorCode() {
        var response = new ApiExceptionAdvice().handleAccessDeniedException(
            new AccessDeniedException("접근 권한이 없습니다."));
        assertEquals(403, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("ACCESS_DENIED", response.getBody().code());
        assertEquals("접근 권한이 없습니다.", response.getBody().message());
    }

}
