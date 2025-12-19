GitHub Repository Retriever

Small Spring Boot service that fetches a GitHub user's public repositories (excluding forks) and lists their branches with the last commit SHA.

Key features:
- Single REST endpoint returning a clean JSON view
- Fork repositories filtered out
- Friendly 404 JSON error when the user does not exist

Requirements
- Java 25 (toolchain configured in Gradle)
- Gradle (wrapper included)

Run locally
```bash
./gradlew bootRun
```

HTTP API
- GET `/github/users/{username}`
  - Returns a list of repositories with branches for the given GitHub user.

Example response
```json
[
  {
    "repositoryName": "Hello-World",
    "ownerLogin": "octocat",
    "branches": [
      { "name": "main", "lastCommitSha": "abc123..." },
      { "name": "dev",  "lastCommitSha": "def456..." }
    ]
  }
]
```

Error handling
- If the user is not found on GitHub, the service responds with HTTP 404:
```json
{ "status": 404, "message": "User not found on GitHub" }
```

Configuration
- Base GitHub API URL can be overridden in `src/main/resources/application.properties`:
```
github.api.url=https://api.github.com
```

Build & test
```bash
./gradlew clean test
```
