package com.github.taptap.sonarqubebadges.service;

import com.github.taptap.sonarqubebadges.entity.Branch;
import com.github.taptap.sonarqubebadges.entity.LiveMeasure;
import com.github.taptap.sonarqubebadges.entity.Metric;
import com.github.taptap.sonarqubebadges.repository.LiveMeasureRepository;
import com.github.taptap.sonarqubebadges.repository.MetricRepository;
import com.google.common.collect.ImmutableMap;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import static com.github.taptap.sonarqubebadges.entity.Metric.Level.ERROR;
import static com.github.taptap.sonarqubebadges.entity.Metric.Level.OK;
import static com.github.taptap.sonarqubebadges.entity.Metric.Level.WARN;
import static com.github.taptap.sonarqubebadges.utils.SvgFormatterUtils.formatDuration;
import static com.github.taptap.sonarqubebadges.utils.SvgFormatterUtils.formatNumeric;
import static com.github.taptap.sonarqubebadges.utils.SvgFormatterUtils.formatPercent;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;

import com.github.taptap.sonarqubebadges.service.SvgGeneratorService.Color;
import org.springframework.stereotype.Service;

import static com.github.taptap.sonarqubebadges.service.Rating.*;

/**
 * @author kl (http://kailing.pub)
 * @since 2021/4/1
 */
@Service
public class MeasureBadgesService {


    public static final String BUGS_KEY = "bugs";
    public static final String CODE_SMELLS_KEY = "code_smells";
    public static final String COVERAGE_KEY = "coverage";
    public static final String DUPLICATED_LINES_DENSITY_KEY = "duplicated_lines_density";
    public static final String NCLOC_KEY = "ncloc";
    public static final String SQALE_RATING_KEY = "sqale_rating";
    public static final String ALERT_STATUS_KEY = "alert_status";
    public static final String RELIABILITY_RATING_KEY = "reliability_rating";
    public static final String SECURITY_RATING_KEY = "security_rating";
    public static final String TECHNICAL_DEBT_KEY = "sqale_index";
    public static final String VULNERABILITIES_KEY = "vulnerabilities";

    public static final Map<String, String> METRIC_NAME_BY_KEY = ImmutableMap.<String, String>builder()
            .put(BUGS_KEY, "bugs")
            .put(CODE_SMELLS_KEY, "code smells")
            .put(COVERAGE_KEY, COVERAGE_KEY)
            .put(DUPLICATED_LINES_DENSITY_KEY, "duplicated lines")
            .put(NCLOC_KEY, "lines of code")
            .put(SQALE_RATING_KEY, "maintainability")
            .put(ALERT_STATUS_KEY, "quality gate")
            .put(RELIABILITY_RATING_KEY, "reliability")
            .put(SECURITY_RATING_KEY, "security")
            .put(TECHNICAL_DEBT_KEY, "technical debt")
            .put(VULNERABILITIES_KEY, VULNERABILITIES_KEY)
            .build();

    private static final Map<Metric.Level, String> QUALITY_GATE_MESSAGE_BY_STATUS = new EnumMap<>(ImmutableMap.of(
            OK, "passed",
            WARN, "warning",
            ERROR, "failed"));

    private static final Map<Metric.Level, Color> COLOR_BY_QUALITY_GATE_STATUS = new EnumMap<>(ImmutableMap.of(
            OK, Color.QUALITY_GATE_OK,
            WARN, Color.QUALITY_GATE_WARN,
            ERROR, Color.QUALITY_GATE_ERROR));

    private static final Map<Rating, Color> COLOR_BY_RATING = new EnumMap<>(ImmutableMap.of(
            A, Color.RATING_A,
            B, Color.RATING_B,
            C, Color.RATING_C,
            D, Color.RATING_D,
            E, Color.RATING_E));


    private final BranchService branchService;
    private final MetricRepository metricRepository;
    private final SvgGeneratorService svgGenerator;
    private final LiveMeasureRepository liveMeasureRepository;

    public MeasureBadgesService(BranchService branchService, MetricRepository metricRepository, SvgGeneratorService svgGenerator, LiveMeasureRepository liveMeasureRepository) {
        this.branchService = branchService;
        this.metricRepository = metricRepository;
        this.svgGenerator = svgGenerator;
        this.liveMeasureRepository = liveMeasureRepository;
    }


    public String badgesData(String metricKey, String projectKey, String branchName) {
        Branch branch = branchService.getBranch(branchName, projectKey);
        Metric metric = metricRepository.findByName(metricKey);
        checkState(metric != null && metric.isEnabled(), "Metric '%s' hasn't been found", metricKey);
        LiveMeasure measure = liveMeasureRepository.findByBranchUuid(branch.getUuid(), metricKey);
        return this.generateSvg(metric, measure);
    }

    private String generateSvg(Metric metric, LiveMeasure measure) {
        String metricType = metric.getValueType();
        switch (Metric.ValueType.valueOf(metricType)) {
            case INT:
                return generateBadge(metric, formatNumeric(getNonNullValue(measure, LiveMeasure::getValue).longValue()), Color.DEFAULT);
            case PERCENT:
                return generateBadge(metric, formatPercent(getNonNullValue(measure, LiveMeasure::getValue)), Color.DEFAULT);
            case LEVEL:
                return generateQualityGate(metric, measure);
            case WORK_DUR:
                return generateBadge(metric, formatDuration(getNonNullValue(measure, LiveMeasure::getValue).longValue()), Color.DEFAULT);
            case RATING:
                return generateRating(metric, measure);
            default:
                throw new IllegalStateException(format("Invalid metric type '%s'", metricType));
        }
    }

    private String generateQualityGate(Metric metric, LiveMeasure measure) {
        Metric.Level qualityGate = Metric.Level.valueOf(getNonNullValue(measure, LiveMeasure::getTextValue));
        return generateBadge(metric, QUALITY_GATE_MESSAGE_BY_STATUS.get(qualityGate), COLOR_BY_QUALITY_GATE_STATUS.get(qualityGate));
    }

    private String generateRating(Metric metric, LiveMeasure measure) {
        Rating rating = valueOf(getNonNullValue(measure, LiveMeasure::getValue).intValue());
        return generateBadge(metric, rating.name(), COLOR_BY_RATING.get(rating));
    }

    private String generateBadge(Metric metric, String value, Color color) {
        String label = METRIC_NAME_BY_KEY.get(metric.getKee());
        return svgGenerator.generateBadge(label, value, color);
    }

    private static <PARAM> PARAM getNonNullValue(LiveMeasure measure, Function<LiveMeasure, PARAM> function) {
        PARAM value = function.apply(measure);
        checkState(value != null, "Measure has not been found");
        return value;
    }

}
