package com.github.taptap.sonarqubebadges.web;

import com.github.taptap.sonarqubebadges.service.ToolsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author kl (http://kailing.pub)
 * @since 2021/4/2
 */
@RestController
@RequestMapping("/api/project_badges_tools")
public class ToolsController {

    private final ToolsService toolsService;

    public ToolsController(ToolsService toolsService) {
        this.toolsService = toolsService;
    }

    @GetMapping("/measure/url")
    public List<Map<String,String>> measureUrls(@RequestParam String project, String branch) {
        return toolsService.getUrlList(project, branch);
    }


    @GetMapping("/measure/markdown")
    public List<String> measureMarkDowns(String project, @RequestParam(required = false) String branch) {
        return toolsService.markDownList(project, branch);
    }
}
