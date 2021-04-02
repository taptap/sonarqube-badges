package com.github.taptap.sonarqubebadges.web;

import com.github.taptap.sonarqubebadges.service.MeasureBadgesService;
import com.github.taptap.sonarqubebadges.service.QualityGateBadgesService;
import com.github.taptap.sonarqubebadges.utils.ETagUtils;
import lombok.SneakyThrows;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kl (http://kailing.pub)
 * @since 2021/4/1
 */
@RestController
@RequestMapping("/api/project_badges")
public class BadgesController {
    private static final MediaType SVG_MEDIA_TYPE = MediaType.valueOf(com.google.common.net.MediaType.SVG_UTF_8.toString());
    private final MeasureBadgesService badgesService;
    private final QualityGateBadgesService qualityGateBadgesService;

    public BadgesController(MeasureBadgesService badgesService, QualityGateBadgesService qualityGateBadgesService) {
        this.badgesService = badgesService;
        this.qualityGateBadgesService = qualityGateBadgesService;
    }

    @SneakyThrows
    @GetMapping("/measure")
    public ResponseEntity<String> measure(String metric, String project, @RequestParam(required = false) String branch) {
        String badgesData = badgesService.badgesData(metric, project, branch);
        String eTag = ETagUtils.getETag(badgesData);

        return ResponseEntity.ok()
                .contentType(SVG_MEDIA_TYPE)
                .cacheControl(CacheControl.noCache())
                .eTag(eTag)
                .body(badgesData);

    }

    @SneakyThrows
    @GetMapping("/quality_gate")
    public ResponseEntity<String> qualityGate(String project, @RequestParam(required = false) String branch) {
        String badgesData = qualityGateBadgesService.badgesData(project, branch);
        String eTag = ETagUtils.getETag(badgesData);

        return ResponseEntity.ok()
                .contentType(SVG_MEDIA_TYPE)
                .cacheControl(CacheControl.noCache())
                .eTag(eTag)
                .body(badgesData);
    }

}
