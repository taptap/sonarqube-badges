package com.github.taptap.sonarqubebadges.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.taptap.sonarqubebadges.entity.LiveMeasure;
import org.springframework.stereotype.Repository;

/**
 * @author kl (http://kailing.pub)
 * @since 2021/4/1
 */
@Repository
public class LiveMeasureRepository extends ServiceImpl<LiveMeasure.Mapper, LiveMeasure> {

    public LiveMeasure findByBranchUuid(String branchUuid,String metricKey){
        return baseMapper.findByBranchUuid(metricKey,branchUuid);
    }

}
