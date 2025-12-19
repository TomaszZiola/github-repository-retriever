package com.ziola.github_repo_retriever;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/github/users")
class GitHubController {
    private final GitHubService githubService;

    GitHubController(GitHubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/{username}")
    List<RepositoryDto> getUserRepositoriesWithBranches(@PathVariable String username) {
        return githubService.getUserRepositoriesWithBranches(username);
    }
}
