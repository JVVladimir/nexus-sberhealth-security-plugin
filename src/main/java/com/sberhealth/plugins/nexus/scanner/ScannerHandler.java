package com.sberhealth.plugins.nexus.scanner;

import com.sberhealth.plugins.nexus.model.ScanResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.types.ProxyType;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.Response;
import org.sonatype.nexus.repository.view.handlers.ContributedHandler;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class ScannerHandler implements ContributedHandler {
  private static final Logger LOG = LoggerFactory.getLogger(ScannerHandler.class);

  private final MavenScanner mavenScanner;
  private final NpmScanner npmScanner;

  @Inject
  public ScannerHandler(final MavenScanner mavenScanner,
                        final NpmScanner npmScanner) {
    this.mavenScanner = mavenScanner;
    this.npmScanner = npmScanner;

//    initializeModuleIfNeeded();
  }

  @Nonnull
  @Override
  public Response handle(@Nonnull Context context) throws Exception {
    Response response = context.proceed();
//    if (!configurationHelper.isCapabilityEnabled()) {
//      LOG.debug("SnykSecurityCapability is not enabled.");
//      return response;
//    }

    Repository repository = context.getRepository();
    if (!ProxyType.NAME.equals(repository.getType().getValue())) {
      LOG.warn("Only proxy repositories are supported: {} - {}", repository.getName(), repository.getType());
      return response;
    }

    Payload payload = response.getPayload();
    ScanResult scanResult;
    String repositoryFormat = repository.getFormat().getValue();
    switch (repositoryFormat) {
      case "maven2": {
        scanResult = mavenScanner.scan(context, payload);
        break;
      }
      case "npm": {
        scanResult = npmScanner.scan(context, payload);
        break;
      }
      default:
        LOG.error("format {} is not supported", repositoryFormat);
        return response;
    }

    if (scanResult == null) {
      return response;
    }

//    validateVulnerabilityIssues(scanResult, context.getRequest().getPath());
//    validateLicenseIssues(scanResult, context.getRequest().getPath());

    return response;
  }

//  private void initializeModuleIfNeeded() {
//    if (snykClient == null) {
//      snykClient = configurationHelper.getSnykClient();
//    }
//    if (configuration == null) {
//      configuration = configurationHelper.getConfiguration();
//    }
//  }

//  private void validateVulnerabilityIssues(ScanResult scanResult, @Nonnull String path) {
//    if (scanResult == null) {
//      LOG.warn("Component could not be scanned, check the logs scanner modules: {}", path);
//      return;
//    }
//
//    String vulnerabilityThreshold = configuration.getVulnerabilityThreshold();
//
//    if ("none".equals(vulnerabilityThreshold)) {
//      LOG.info("Property 'Vulnerability Threshold' is none, so we allow to download artifact.");
//      return;
//    }
//
//    if (hasVulnerabilityIssues(scanResult, vulnerabilityThreshold)) {
//      throw new RuntimeException(String.format("Artifact '%s' has vulnerability issues: '%s'", path, Formatter.getVulnerabilityIssuesAsFormattedString(scanResult)));
//    }
//  }

//  private void validateLicenseIssues(ScanResult scanResult, @Nonnull String path) {
//    if (scanResult == null) {
//      LOG.warn("Component could not be scanned, check the logs scanner modules: {}", path);
//      return;
//    }
//
//    String licenseThreshold = configuration.getLicenseThreshold();
//
//    if ("none".equals(licenseThreshold)) {
//      LOG.info("Property 'License Threshold' is none, so we allow to download artifact.");
//      return;
//    }
//
//    if (hasLicenseIssues(scanResult, licenseThreshold)) {
//      throw new RuntimeException(String.format("Artifact '%s' has license issues: '%s'", path, Formatter.getLicenseIssuesAsFormattedString(scanResult)));
//    }
//  }

//  private boolean hasVulnerabilityIssues(ScanResult scanResult, String threshold) {
//    if ("none".equals(threshold)) {
//      LOG.info("Property 'Vulnerability Threshold' is none, so we allow to download artifact.");
//      return false;
//    }
//
//    if ("low".equals(threshold) &&
//      (scanResult.lowVulnerabilityIssueCount > 0 || scanResult.mediumVulnerabilityIssueCount > 0 || scanResult.highVulnerabilityIssueCount > 0 || scanResult.criticalVulnerabilityIssueCount > 0)) {
//      return true;
//    } else if ("medium".equals(threshold) &&
//      (scanResult.mediumVulnerabilityIssueCount > 0 || scanResult.highVulnerabilityIssueCount > 0 || scanResult.criticalVulnerabilityIssueCount > 0)) {
//      return true;
//    } else if ("high".equals(threshold) &&
//      (scanResult.highVulnerabilityIssueCount > 0 || scanResult.criticalVulnerabilityIssueCount > 0)) {
//      return true;
//    } else if ("critical".equals(threshold) &&
//      (scanResult.criticalVulnerabilityIssueCount > 0)) {
//      return true;
//    }
//    else {
//      return false;
//    }
//  }
//
//  private boolean hasLicenseIssues(ScanResult scanResult, String threshold) {
//    if ("none".equals(threshold)) {
//      LOG.info("Property 'License Threshold' is none, so we allow to download artifact.");
//      return false;
//    }
//
//    if ("low".equals(threshold) &&
//      (scanResult.lowLicenseIssueCount > 0 || scanResult.mediumLicenseIssueCount > 0 || scanResult.highLicenseIssueCount > 0)) {
//      return true;
//    } else if ("medium".equals(threshold) &&
//      (scanResult.mediumLicenseIssueCount > 0 || scanResult.highLicenseIssueCount > 0)) {
//      return true;
//    } else if ("high".equals(threshold) &&
//      (scanResult.highLicenseIssueCount > 0)) {
//      return true;
//    } else {
//      return false;
//    }
//  }
}
