package com.ziola.github_repo_retriever;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.ImportHttpServices;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Configuration
@ImportHttpServices(group = "github", types = GitHubApiClient.class)
class GitHubClientConfig {

    @Bean
    RestClientHttpServiceGroupConfigurer groupConfigurer(@Value("${github.api.url}") String baseUrl) {
        return groups -> groups.filterByName("github").forEachClient((_, builder) -> builder
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/vnd.github.v3+json")
                .defaultStatusHandler(
                        httpStatus -> httpStatus.equals(NOT_FOUND),
                        (_, _) -> {
                            throw new UserNotFoundException("User not found on GitHub");
                        }
                ));
    }
}
