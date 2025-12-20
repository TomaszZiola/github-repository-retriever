package com.ziola.github_repository_retriever;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/github/users")
class RepositoryController {
    private final RepositoryService repositoryService;

    RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping("/{username}")
    List<RepositoryDto> getUserRepositoriesWithBranches(@PathVariable String username) {
        return repositoryService.getUserRepositoriesWithBranches(username);
    }
}
