package com.ziola.github_repository_retriever;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/github/users")
class RepositoryController {
    private final RepositoryService githubService;

    RepositoryController(RepositoryService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/{username}")
    List<RepositoryDto> getUserRepositoriesWithBranches(@PathVariable String username) {
        return githubService.getUserRepositoriesWithBranches(username);
    }
}
