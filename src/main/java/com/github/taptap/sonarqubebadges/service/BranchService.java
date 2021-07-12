package com.github.taptap.sonarqubebadges.service;

import com.github.taptap.sonarqubebadges.entity.Branch;
import com.github.taptap.sonarqubebadges.entity.Project;
import com.github.taptap.sonarqubebadges.repository.BranchRepository;
import com.github.taptap.sonarqubebadges.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import static com.github.taptap.sonarqubebadges.entity.BranchType.BRANCH;

/**
 * @author kl (http://kailing.pub)
 * @since 2021/4/1
 */
@Service
public class BranchService {

    private final BranchRepository branchRepository;
    private final ProjectRepository projectRepository;

    public BranchService(BranchRepository branchRepository, ProjectRepository projectRepository) {
        this.branchRepository = branchRepository;
        this.projectRepository = projectRepository;
    }

    public Branch getBranch(String branchName, String projectKey) {
        Project project = projectRepository.findByKee(projectKey);
        Branch branch;
        if (branchName == null) {
            branch = branchRepository.findByProjectUid(project.getUuid());
        } else {
            branch = branchRepository.findByBranchName(projectKey, branchName);
        }

        if (!BRANCH.equals(branch.getBranchType())) {
            throw new ProjectBadgesException("Project is invalid");
        }

        return branch;
    }
}
