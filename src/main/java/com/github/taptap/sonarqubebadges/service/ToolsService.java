package com.github.taptap.sonarqubebadges.service;

import com.github.taptap.sonarqubebadges.configuration.SonarProperties;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author kl (http://kailing.pub)
 * @since 2021/4/2
 */
@Service
public class ToolsService {

    private final SonarProperties props;
    private static final String URL = "/api/project_badges/measure?project=%s&metric=%s";
    private static final String MARKDOWN = "[![%s](%s)](%s/dashboard?id=%s)";

    public ToolsService(SonarProperties props) {
        this.props = props;
    }

    public List<Map<String,String>> getUrlList(String project, String branch) {

        List<Map<String,String>> urlList = new ArrayList<>();
        for (String metricKey : MeasureBadgesService.METRIC_NAME_BY_KEY.keySet()) {
            String url = this.getUrlByMetric(metricKey, project, branch);
            urlList.add(ImmutableMap.of(metricKey,url));
        }
        return urlList;
    }

    public List<String> markDownList(String project, String branch) {
        List<String> markdownList = new ArrayList<>();
        for (String metricKey : MeasureBadgesService.METRIC_NAME_BY_KEY.keySet()) {
           String url = this.getUrlByMetric(metricKey,project,branch);
           String markdown = String.format(MARKDOWN,metricKey,url,props.getBaseUrl(),project);
           markdownList.add(markdown);
        }
        return markdownList;
    }

    private String getUrlByMetric(String metricKey, String project, String branch) {
        StringBuilder urlBuilder = new StringBuilder(props.getBadgesBaseUrl());
        String url = String.format(URL, project, metricKey);
        urlBuilder.append(url);
        if (StringUtils.hasText(branch)) {
            urlBuilder.append("&branch=");
            urlBuilder.append(branch);
        }
        return urlBuilder.toString();
    }

}
