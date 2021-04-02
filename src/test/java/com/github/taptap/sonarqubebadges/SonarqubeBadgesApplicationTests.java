package com.github.taptap.sonarqubebadges;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.taptap.sonarqubebadges.entity.Branch;
import com.github.taptap.sonarqubebadges.entity.LiveMeasure;
import com.github.taptap.sonarqubebadges.entity.Metric;
import com.github.taptap.sonarqubebadges.entity.Project;
import com.github.taptap.sonarqubebadges.repository.BranchRepository;
import com.github.taptap.sonarqubebadges.repository.LiveMeasureRepository;
import com.github.taptap.sonarqubebadges.repository.MetricRepository;
import com.github.taptap.sonarqubebadges.repository.ProjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SonarqubeBadgesApplicationTests {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private MetricRepository metricRepository;
    @Autowired
    private LiveMeasureRepository liveMeasureRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testProject() {
        List<Project> list = projectRepository.list();
        Assertions.assertNotNull(list);

    }

    @Test
    void testBranch() {
        Page<Branch> page = new Page<>(1, 10);
        List<Branch> list = branchRepository.page(page).getRecords();
        Assertions.assertNotNull(list);
    }

    @Test
    void testMetric(){
        Page<Metric> page = new Page<>(1, 10);
        List<Metric> list = metricRepository.page(page).getRecords();
        Assertions.assertNotNull(list);
    }

    @Test
    void testLiveMeasure(){
        Page<LiveMeasure> page = new Page<>(1, 10);
        List<LiveMeasure> list = liveMeasureRepository.page(page).getRecords();
        Assertions.assertNotNull(list);    }
}
