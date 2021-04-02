package com.github.taptap.sonarqubebadges.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author kl (http://kailing.pub)
 * @since 2021/4/1
 */
@Getter
@Setter
@TableName("live_measures")
public class LiveMeasure {
    @TableId("uuid")
    private String uuidForUpsert;
    private String componentUuid;
    private String projectUuid;
    private String metricUuid;
    private Double value;
    private String textValue;
    @TableField("measure_data")
    private byte[] data;
    private Double variation;

    @org.apache.ibatis.annotations.Mapper
    public interface Mapper extends BaseMapper<LiveMeasure> {

         String FIND_BY_BRANCH_UUID_SQL = "select lm.* from live_measures lm\n" +
                "    inner join metrics m on m.uuid = lm.metric_uuid\n" +
                "    where\n" +
                "    m.name = #{metricKey, jdbcType=VARCHAR}\n" +
                "    and lm.component_uuid = #{componentUuid, jdbcType=VARCHAR}";

        @Select(FIND_BY_BRANCH_UUID_SQL)
        LiveMeasure findByBranchUuid(@Param("metricKey") String metricKey, @Param("componentUuid") String componentUuid);

    }
}
