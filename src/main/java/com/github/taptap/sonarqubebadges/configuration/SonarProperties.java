package com.github.taptap.sonarqubebadges.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author kl (http://kailing.pub)
 * @since 2021/4/1
 */
@ConfigurationProperties(prefix = "sonar")
@Configuration
@Data
public class SonarProperties{

    private String baseUrl;

    private String badgesBaseUrl;


}
