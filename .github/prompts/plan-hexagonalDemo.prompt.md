# Plan: Activate Sonar in Hexagonal Demo

1. **Update `pom.xml`**:
   - Add `jacoco-maven-plugin` to generate code coverage reports.
   - Add `sonar-maven-plugin` to enable Sonar analysis.

2. **Execute Sonar Analysis**:
   - For local SonarQube:
     ```bash
     ./mvnw clean verify sonar:sonar \
       -Dsonar.projectKey=hexagonal-demo \
       -Dsonar.host.url=http://localhost:9000 \
       -Dsonar.login=YOUR_TOKEN
     ```
   - For SonarCloud:
     ```bash
     ./mvnw clean verify sonar:sonar \
       -Dsonar.projectKey=YOUR_ORG_hexagonal-demo \
       -Dsonar.organization=YOUR_ORG \
       -Dsonar.host.url=https://sonarcloud.io \
       -Dsonar.login=YOUR_TOKEN
     ```

3. **Verify and Refine**:
   - Check the generated reports in the Sonar dashboard.
   - Address any initial code smells, bugs, or missing test coverage.
