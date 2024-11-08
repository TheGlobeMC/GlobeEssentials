## GlobeEssentials GitHub Repository Ruleset

### 1. Repository Structure
- **`src/`**: Source code directory containing all Java classes for the plugin.
- **`src/main/resources/`**: Configuration files, messages, and other resources.
- **`LICENSE.md`**: License file specifying the open-source license used for GlobeEssentials.
- **`README.md`**: Repository overview, installation instructions, and usage details.

### 2. Contribution Guidelines
- **Issue Reporting**: All bugs, feature requests, and questions should be submitted via GitHub issues or Discord. Follow the provided template and tag appropriately.
- **Pull Requests (PRs)**: Contributions should follow the steps below:
  - Fork the repository, make changes, and submit PRs.
  - Ensure code passes all tests and follows the coding standards before submission.
  - Describe changes clearly in the PR description.
  - Tag PRs with `enhancement`, `bug`, or other applicable labels.
- **Branch Naming**: Use meaningful branch names. Prefix like:
  - `feature/` for new features
  - `bugfix/` for bug fixes
  - `hotfix/` for urgent production issues
  - `chore/` for maintenance work
- **Review Process**: One or more contributors (or maintainers) must review each PR. Only admins may approve merges to the `main` branch.

### 3. Version Control and Releases
- **Versioning**: Follow semantic versioning (`MAJOR.MINOR.PATCH`). 
- **Release Process**:
  - Only stable, thoroughly tested code can be merged to the `main` branch.
  - Tag all major releases and provide release notes in the `CHANGELOG.md`.
- **Changelog**: All updates should be documented in `CHANGELOG.md` with details on new features, improvements, and bug fixes.

### 4. Code Style & Standards
- **Java Style Guide**: Follow standard Java conventions and aim for clean, readable, and modular futureproof code.
- **Javadocs**: Most classes and methods must be documented with Javadocs and comments.
- **Formatting**: Use a consistent code formatter (e.g., Google Java Format) before committing code.

### 5. Security & Stability
- **Sensitive Data**: No sensitive data or private server configurations should be committed.
- **Dependencies**: Use official Spigot/Paper dependencies and vetted third-party libraries only.
- **Security Fixes**: Security-related issues should be reported directly to repository maintainers rather than through public issues.

### 6. Community & Conduct
- **Feedback & Suggestions**: Contributors and users are encouraged to suggest features and improvements on Discord or by opening a feature request issue.
