package com.github.taptap.sonarqubebadges.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * @author kl (http://kailing.pub)
 * @since 2021/4/1
 */
@Getter
@Setter
@TableName("metrics")
public class Metric {
    @TableId
    private String uuid;
    @TableField("name")
    private String kee;
    private String shortName;
    @TableField("val_type")
    private String valueType;
    private String description;
    private String domain;
    private int direction;
    private boolean qualitative;
    private boolean userManaged;
    private Double worstValue;
    private Double bestValue;
    private boolean optimizedBestValue;
    private boolean hidden;
    private boolean deleteHistoricalData;
    private boolean enabled;
    private Integer decimalScale;

    @org.apache.ibatis.annotations.Mapper
    public interface Mapper extends BaseMapper<Metric> {}

    public enum Level {
        OK("Green"),
        WARN("Orange"),
        ERROR("Red");

        private final String colorName;

        Level(String colorName) {
            this.colorName = colorName;
        }

        public String getColorName() {
            return this.colorName;
        }

    }

    public enum ValueType {
        INT,
        FLOAT,
        PERCENT,
        BOOL,
        STRING,
        MILLISEC,
        DATA,
        LEVEL,
        DISTRIB,
        RATING,
        WORK_DUR
    }
}
