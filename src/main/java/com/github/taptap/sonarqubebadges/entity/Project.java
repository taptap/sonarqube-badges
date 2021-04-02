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
@TableName("projects")
public class Project {
    @TableId
    private String uuid;
    private String kee;
    private String qualifier;
    private String name;
    private String description;
    @TableField("private")
    private boolean isPrivate = false;
    private String tags;
    private long createdAt;
    private long updatedAt;

    @org.apache.ibatis.annotations.Mapper
    public interface Mapper extends BaseMapper<Project> {}

}
