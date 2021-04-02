package com.github.taptap.sonarqubebadges.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.taptap.sonarqubebadges.entity.Project;
import org.springframework.stereotype.Repository;

/**
 * @author kl (http://kailing.pub)
 * @since 2021/4/1
 */
@Repository
public class ProjectRepository extends ServiceImpl<Project.Mapper, Project> {

    public Project findByKee(String projectKey) {
        return super.lambdaQuery()
                .eq(Project::getKee, projectKey)
                .one();
    }
}
