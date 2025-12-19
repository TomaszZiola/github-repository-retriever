package com.ziola.github_repo_retriever;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class GitHubService {
    private final GitHubApiClient githubClient;

    GitHubService(GitHubApiClient githubClient) {
        this.githubClient = githubClient;
    }

    List<RepositoryDto> getUserRepositoriesWithBranches(String username) {
        return githubClient.getRepositories(username)
                .stream()
                .filter(repo -> !repo.fork())
                .map(repo -> {
                    var branches = githubClient.getBranches(username, repo.name())
                            .stream()
                            .map(branch -> new BranchDto(branch.name(), branch.commit().sha()))
                            .toList();

                    return new RepositoryDto(repo.name(), repo.owner().login(), branches);
                })
                .toList();
    }
}
