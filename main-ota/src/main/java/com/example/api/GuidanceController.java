package com.example.api;

import com.example.api.dto.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class GuidanceController implements GuidanceApi {
    private final GuidanceService service;

    public GuidanceController(GuidanceService service) { this.service = service; }

    @Override
    @GetMapping(value = "/api/public/guidance/topics", produces = "application/json")
    public ResponseEntity<GuidanceTopicsResponse> listGuidanceTopics() {
        return ResponseEntity.ok(service.listGuidanceTopics());
    }

    @Override
    @GetMapping(value = "/api/public/guidance/articles", produces = "application/json")
    public ResponseEntity<GuidanceArticleListResponse> listGuidanceArticles(
        @RequestParam(name = "topicId", required = false) String topicId,
        @RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "limit", defaultValue = "20") Integer limit,
        @RequestParam(name = "cursor", required = false) String cursor
    ) {
        return ResponseEntity.ok(service.listGuidanceArticles(topicId, keyword, limit, cursor));
    }

    @Override
    @GetMapping(value = "/api/public/guidance/articles/{articleId}", produces = "application/json")
    public ResponseEntity<GuidanceArticleResponse> getGuidanceArticle(
        @PathVariable("articleId") String articleId
    ) {
        return ResponseEntity.ok(service.getGuidanceArticle(articleId));
    }

    @Override
    @GetMapping(value = "/api/public/properties/{propertyId}/contact-methods", produces = "application/json")
    public ResponseEntity<GuidancePropertyContactsResponse> getPropertyContactMethods(
        @PathVariable("propertyId") String propertyId
    ) {
        return ResponseEntity.ok(service.getPropertyContactMethods(propertyId));
    }

}
