# Quarkus Update Automation

This repository includes automated Quarkus update mechanisms for multiple branches using GitHub Actions and Dependabot.

## Branches Monitored

The following branches are monitored for Quarkus updates:
- `ROSA` - Red Hat OpenShift Service on AWS
- `ARO` - Azure Red Hat OpenShift
- `AKS` - Azure Kubernetes Service
- `jdconf-24` - JDConf 2024 Demo

## Automation Components

### 1. Dependabot Configuration (`.github/dependabot.yml`)

Dependabot automatically monitors Maven dependencies and creates pull requests for updates.

**Features:**
- Runs weekly on Mondays at 09:00 UTC
- Groups Quarkus-related dependencies together
- Creates separate PRs for each branch
- Labels PRs with branch-specific tags
- Limits to 10 open PRs per branch
- **Auto-merges Quarkus updates after tests pass**

**Dependency Groups:**
- All `io.quarkus*` packages
- All `io.quarkiverse*` packages
- Includes minor and patch updates

### 2. GitHub Actions Workflow (`.github/workflows/quarkus-update.yml`)

A custom workflow that checks for Quarkus updates and creates PRs automatically.

**Features:**
- Scheduled to run every Monday at 10:00 UTC
- Can be manually triggered via workflow_dispatch
- Checks Maven Central for latest Quarkus version
- Updates `pom.xml` automatically
- Runs tests before creating PR
- Creates detailed PRs with release notes
- **Auto-merges PRs after successful tests**

**Workflow Steps:**
1. Checkout the target branch
2. Set up JDK 17 with Maven cache
3. Get current Quarkus version from pom.xml
4. Check Maven Central for latest version
5. Update version if newer available
6. Run tests (continues on error)
7. Create pull request with detailed information
8. Enable auto-merge on the PR
9. Automatically merge after checks pass

## Manual Triggering

You can manually trigger the Quarkus update workflow:

1. Go to **Actions** tab in GitHub
2. Select **Quarkus Update** workflow
3. Click **Run workflow**
4. Choose target branch or select "all" for all branches
5. Click **Run workflow** button

### 3. Dependabot Auto-Merge Workflow (`.github/workflows/dependabot-auto-merge.yml`)

Automatically approves and merges Dependabot PRs for Quarkus dependencies.

**Features:**
- Triggers on Dependabot PR creation
- Automatically approves Quarkus-related PRs
- Enables auto-merge
- Adds informative comment to PR
- Only merges after all checks pass

## Pull Request Labels

PRs created by automation will have the following labels:
- `dependencies` - Indicates dependency update
- `quarkus-update` - Specific to Quarkus updates
- `<branch-name>` - Branch-specific label (ROSA, ARO, AKS, jdconf-24)
- `automated` - Indicates automated PR (GitHub Actions only)
- `automerge` - Indicates PR will be auto-merged

## Auto-Merge Behavior

### How Auto-Merge Works

1. **PR Creation**: Workflow or Dependabot creates a PR
2. **Tests Run**: Automated tests execute
3. **Auto-Approval**: PR is automatically approved (Dependabot PRs only)
4. **Auto-Merge Enabled**: Merge is scheduled after checks pass
5. **Automatic Merge**: PR merges when all checks are green
6. **Branch Cleanup**: PR branch is automatically deleted

### When Auto-Merge Happens

✅ **Auto-merged:**
- Quarkus platform updates (minor/patch versions)
- Quarkiverse extension updates
- All tests pass successfully

⚠️ **Manual review needed:**
- Major version updates
- Test failures
- Merge conflicts

## Reviewing Updates (Optional)

While PRs auto-merge, you can still review them before merge:

1. **Monitor PR creation** via GitHub notifications
2. **Review changes** in `pom.xml` if desired
3. **Check release notes** linked in PR description
4. **Disable auto-merge** if manual review needed:
   ```bash
   gh pr merge --disable-auto <pr-number>
   ```
5. **Test locally** if concerned:
   ```bash
   git fetch origin
   git checkout <pr-branch>
   mvn clean verify
   ```

## Configuration

### Modifying Schedule

To change when updates run, edit the cron expressions:

**Dependabot** (`.github/dependabot.yml`):
```yaml
schedule:
  interval: "weekly"
  day: "monday"
  time: "09:00"
```

**GitHub Actions** (`.github/workflows/quarkus-update.yml`):
```yaml
on:
  schedule:
    - cron: '0 10 * * 1'  # Every Monday at 10:00 UTC
```

### Adding New Branches

To monitor additional branches:

1. Add a new update block in `.github/dependabot.yml`
2. Add the branch name to the matrix in `.github/workflows/quarkus-update.yml`

### Disabling Automation

To temporarily disable:
- **Dependabot**: Comment out or remove the branch configuration
- **GitHub Actions**: Disable the workflow in the Actions tab

## Troubleshooting

### PRs Not Being Created

1. Check workflow runs in the Actions tab
2. Verify branch exists and is accessible
3. Check GitHub token permissions
4. Review workflow logs for errors

### Test Failures

The workflow continues even if tests fail, but you should:
1. Review the test output in the workflow logs
2. Fix issues before merging the PR
3. Consider if the update introduces breaking changes

### Merge Conflicts

If a PR has conflicts:
1. Checkout the PR branch locally
2. Merge the target branch into it
3. Resolve conflicts
4. Push the updated branch

## Best Practices

1. **Review regularly**: Check for pending update PRs weekly
2. **Test thoroughly**: Always test updates in non-production environments first
3. **Read release notes**: Understand what's changing in each update
4. **Keep branches in sync**: Regularly merge updates across branches if applicable
5. **Monitor breaking changes**: Pay attention to major version updates

## Support

For issues with:
- **Dependabot**: Check [GitHub Dependabot documentation](https://docs.github.com/en/code-security/dependabot)
- **GitHub Actions**: Check [GitHub Actions documentation](https://docs.github.com/en/actions)
- **Quarkus**: Check [Quarkus documentation](https://quarkus.io/guides/)

## Current Quarkus Version

The project currently uses Quarkus **3.34.6** (as of the last update).