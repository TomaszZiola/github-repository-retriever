package com.ziola.github_repo_retriever.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.UncheckedIOException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public final class ApiStubs {
    private ApiStubs() {
        throw new IllegalStateException("Utility class should not be instantiated");
    }

    private static final String BRANCHES_CONSORTIUM_RESPONSE_FILE = "wiremock/BranchesConsortium.json";
    private static final String BRANCHES_HELLO_WORLD_RESPONSE_FILE = "wiremock/BranchesHelloWorld.json";
    private static final String NOT_FOUND = "wiremock/UserNotfound.json";
    private static final String REPOS_OCTOCAT_RESPONSE_FILE = "wiremock/ReposOctocat.json";

    public static void stubExternalApis() {
        stubFor(get(urlEqualTo("/users/octocat/repos"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(fromFile(REPOS_OCTOCAT_RESPONSE_FILE))));

        stubFor(get(urlEqualTo("/repos/octocat/git-consortium/branches"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(fromFile(BRANCHES_CONSORTIUM_RESPONSE_FILE))));

        stubFor(get(urlEqualTo("/repos/octocat/Hello-World/branches"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(fromFile(BRANCHES_HELLO_WORLD_RESPONSE_FILE))));
    }

    public static void stubReposNotFound() {
        stubFor(get(urlEqualTo("/users/octocat/repos"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody(fromFile(NOT_FOUND))));
    }

    private static String fromFile(String path) {
        try {
            var resource = new ClassPathResource(path);
            return StreamUtils.copyToString(resource.getInputStream(), UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
