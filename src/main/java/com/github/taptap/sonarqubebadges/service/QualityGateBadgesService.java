package com.github.taptap.sonarqubebadges.service;

import com.github.taptap.sonarqubebadges.entity.Branch;
import com.github.taptap.sonarqubebadges.entity.LiveMeasure;
import com.github.taptap.sonarqubebadges.entity.Metric;
import com.github.taptap.sonarqubebadges.repository.LiveMeasureRepository;
import org.springframework.stereotype.Service;

/**
 * @author kl (http://kailing.pub)
 * @since 2021/4/2
 */
@Service
public class QualityGateBadgesService {

    public static final String ALERT_STATUS_KEY = "alert_status";

    private final BranchService branchService;
    private final LiveMeasureRepository liveMeasureRepository;
    private final SvgGeneratorService svgGenerator;

    public QualityGateBadgesService(BranchService branchService, LiveMeasureRepository liveMeasureRepository, SvgGeneratorService svgGenerator) {
        this.branchService = branchService;
        this.liveMeasureRepository = liveMeasureRepository;
        this.svgGenerator = svgGenerator;
    }

    public String badgesData(String projectKey, String branchName) {

        Branch branch = branchService.getBranch(branchName, projectKey);
        LiveMeasure measure = liveMeasureRepository.findByBranchUuid(branch.getUuid(), ALERT_STATUS_KEY);

        Metric.Level qualityGateStatus = Metric.Level.valueOf(measure.getTextValue());
        return svgGenerator.generateQualityGate(qualityGateStatus);
    }


}
