package com.github.taptap.sonarqubebadges.repository;

/**
 * @author kl (http://kailing.pub)
 * @since 2021/4/1
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.taptap.sonarqubebadges.entity.Branch;
import com.github.taptap.sonarqubebadges.entity.BranchType;
import org.springframework.stereotype.Repository;

@Repository
public class BranchRepository extends ServiceImpl<Branch.Mapper, Branch> {

    public Branch findByProjectUid(String projectUid){
        return super.lambdaQuery()
                .eq(Branch::getUuid,projectUid)
                .one();
    }

    public Branch findByBranchName(String projectUuid,String branchName){
        return super.lambdaQuery()
                .eq(Branch::getProjectUuid,projectUuid)
                .eq(Branch::getKee,branchName)
                .eq(Branch::getBranchType, BranchType.BRANCH)
                .one();
    }
}
