package com.ziola.github_repo_retriever;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
interface GitHubApiClient {

    @GetExchange(url = "/users/{username}/repos")
    List<GitHubRepositoryResponse> getRepositories(@PathVariable String username);

    @GetExchange(url = "/repos/{username}/{repoName}/branches")
    List<GitHubBranchResponse> getBranches(@PathVariable String username, @PathVariable String repoName);
}
