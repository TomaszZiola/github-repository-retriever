package com.ziola.github_repo_retriever.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import static com.ziola.github_repo_retriever.utils.ApiStubs.stubExternalApis;
import static com.ziola.github_repo_retriever.utils.ApiStubs.stubUserNotFound;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.client.RestTestClient.bindToServer;

@EnableWireMock(
        @ConfigureWireMock(
                baseUrlProperties = {"github.api.url"}
        )
)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class GithubControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private RestTestClient client;

    @BeforeEach
    void setup() {
        client = bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void shouldReturnRepositoriesAndBranchesForUser() {
        // given
        stubExternalApis();
        var expectedJson = """
                [
                  {
                    "repositoryName": "git-consortium",
                    "ownerLogin": "octocat",
                    "branches": [
                      { "name": "master", "lastCommitSha": "b33a9c7c02ad93f621fa38f0e9fc9e867e12fa0e" }
                    ]
                  },
                  {
                    "repositoryName": "Hello-World",
                    "ownerLogin": "octocat",
                    "branches": [
                      { "name": "master", "lastCommitSha": "7fd1a60b01f91b314f59955a4e4d4e80d8edf11d" },
                      { "name": "octocat-patch-1", "lastCommitSha": "b1b3f9723831141a31a1a7252a213e216ea76e56" },
                      { "name": "test", "lastCommitSha": "b3cbd5bbd7e81436d2eee04537ea2b4c0cad4cdf" }
                    ]
                  }
                ]
                """;
        // when & then
        client.get()
                .uri("/github/users/octocat")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json(expectedJson);
    }

    @Test
    void shouldReturn404WhenUserNotFound() {
        // given
        stubUserNotFound();
        var expectedJson = """
                        {
                            "status": 404,
                            "message": "User not found on GitHub"
                        }
                """;
        // when & then
        client.get()
                .uri("/github/users/octocat")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .json(expectedJson);
    }
}
