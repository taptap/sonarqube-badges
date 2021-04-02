package com.github.taptap.sonarqubebadges.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.taptap.sonarqubebadges.entity.Metric;
import org.springframework.stereotype.Repository;

/**
 * @author kl (http://kailing.pub)
 * @since 2021/4/1
 */
@Repository
public class MetricRepository extends ServiceImpl<Metric.Mapper, Metric> {

    public Metric findByName(String metricName){
        return super.lambdaQuery()
                .eq(Metric::getKee,metricName)
                .one();
    }

}
