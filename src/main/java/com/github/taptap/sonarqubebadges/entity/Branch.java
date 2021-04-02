package com.github.taptap.sonarqubebadges.entity;

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
@TableName("project_branches")
public class Branch {
    @TableId
    private String uuid;
    private String projectUuid;
    private String kee;
    private BranchType branchType;
    private String mergeBranchUuid;
    private byte[] pullRequestBinary;
    private boolean excludeFromPurge;
    private boolean needIssueSync = false;

    @org.apache.ibatis.annotations.Mapper
    public interface Mapper extends BaseMapper<Branch> {}

}
