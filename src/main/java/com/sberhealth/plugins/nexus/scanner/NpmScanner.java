package com.sberhealth.plugins.nexus.scanner;

import com.sberhealth.plugins.nexus.model.ScanResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.AssetStore;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Payload;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class NpmScanner {
  private static final Logger LOG = LoggerFactory.getLogger(NpmScanner.class);

  private final AssetStore assetStore;

  @Inject
  public NpmScanner(final AssetStore assetStore) {
    this.assetStore = assetStore;
  }

  ScanResult scan(@Nonnull Context context, Payload payload) {
    if (payload == null) {
      return null;
    }

    String packageName = "";
    String packageVersion = "";
    if (payload instanceof Content) {
      Asset asset = ((Content) payload).getAttributes().get(Asset.class);
      if (asset == null) {
        return null;
      }

      if (!asset.name().endsWith("tgz")) {
        LOG.debug("Only 'tgz' extension is supported. Skip scanning");
        return null;
      }

      NestedAttributesMap npmAttributes = asset.attributes().child("npm");
      Object nameAttribute = npmAttributes.get("name");
      packageName = nameAttribute != null ? nameAttribute.toString() : "";
      Object versionAttribute = npmAttributes.get("version");
      packageVersion = versionAttribute != null ? versionAttribute.toString() : "";
    }

    if (packageName.isEmpty()) {
      LOG.warn("Name is empty for {}", context.getRequest().getPath());
      return null;
    }
    if (packageVersion.isEmpty()) {
      LOG.warn("Version is empty for {}", context.getRequest().getPath());
      return null;
    }

    ScanResult scanResult = new ScanResult();

//    if (snykPropertiesExist(payload)) {
//      LOG.debug("NPM artifact {}:{} was already scanned. Skip scanning", packageName, packageVersion);
//
//      NestedAttributesMap snykSecurityMap = getSnykSecurityAttributes(payload);
//      // vulnerabilities
//      Object vulnerabilityIssues = snykSecurityMap.get("issues_vulnerabilities");
//      if (vulnerabilityIssues instanceof String) {
//        Formatter.enrichScanResultWithVulnerabilityIssues(scanResult, (String) vulnerabilityIssues);
//      }
//      // licences
//      Object licenseIssues = snykSecurityMap.get("issues_licenses");
//      if (licenseIssues instanceof String) {
//        Formatter.enrichScanResultWithLicenseIssues(scanResult, (String) licenseIssues);
//      }
//    } else {
//      TestResult testResult = null;
//      try {
//        Response<TestResult> response = snykClient.testNpm(packageName,
//                                                           packageVersion,
//                                                           organizationId).execute();
//        if (response.isSuccessful() && response.body() != null) {
//          testResult = response.body();
//          String responseAsText = new ObjectMapper().writeValueAsString(response.body());
//          LOG.debug("testNpm response: {}", responseAsText);
//        }
//
//        if (testResult != null) {
//          updateAssetAttributes(testResult, packageName, packageVersion, payload);
//          scanResult.criticalVulnerabilityIssueCount = Formatter.getIssuesCountBySeverity(testResult.issues.vulnerabilities, Severity.CRITICAL);
//          scanResult.highVulnerabilityIssueCount = Formatter.getIssuesCountBySeverity(testResult.issues.vulnerabilities, Severity.HIGH);
//          scanResult.mediumVulnerabilityIssueCount = Formatter.getIssuesCountBySeverity(testResult.issues.vulnerabilities, Severity.MEDIUM);
//          scanResult.lowVulnerabilityIssueCount = Formatter.getIssuesCountBySeverity(testResult.issues.vulnerabilities, Severity.LOW);
//
//          scanResult.highLicenseIssueCount = Formatter.getIssuesCountBySeverity(testResult.issues.licenses, Severity.HIGH);
//          scanResult.mediumLicenseIssueCount = Formatter.getIssuesCountBySeverity(testResult.issues.licenses, Severity.MEDIUM);
//          scanResult.lowLicenseIssueCount = Formatter.getIssuesCountBySeverity(testResult.issues.licenses, Severity.LOW);
//        }
//      } catch (IOException ex) {
//        LOG.error("Cloud not test npm artifact: {}", context.getRequest().getPath(), ex);
//      }
//    }

    return scanResult;
  }

//  private boolean snykPropertiesExist(Payload payload) {
//    NestedAttributesMap snykSecurityMap = getSnykSecurityAttributes(payload);
//    if (snykSecurityMap == null || snykSecurityMap.isEmpty()) {
//      return false;
//    }
//    Object vulnerabilityIssues = snykSecurityMap.get("issues_vulnerabilities");
//    if (vulnerabilityIssues instanceof String && !((String) vulnerabilityIssues).isEmpty()) {
//      return true;
//    }
//    Object licenseIssues = snykSecurityMap.get("issues_licenses");
//    if (licenseIssues instanceof String && !((String) licenseIssues).isEmpty()) {
//      return true;
//    }
//    return false;
//  }
//
//  private void updateAssetAttributes(@Nonnull TestResult testResult, @Nonnull String packageName, @Nonnull String packageVersion, Payload payload) {
//    if (payload instanceof Content) {
//      Asset asset = ((Content) payload).getAttributes().get(Asset.class);
//      if (asset == null) {
//        return;
//      }
//
//      NestedAttributesMap snykSecurityMap = asset.attributes().child("Snyk Security");
//      snykSecurityMap.clear();
//
//      snykSecurityMap.set("issues_vulnerabilities", getIssuesAsFormattedString(testResult.issues.vulnerabilities));
//      snykSecurityMap.set("issues_licenses", getIssuesAsFormattedString(testResult.issues.licenses));
//      StringBuilder snykIssueUrl = new StringBuilder("https://snyk.io/vuln/");
//      snykIssueUrl.append(testResult.packageManager).append(":")
//                  .append(packageName).append("@")
//                  .append(packageVersion);
//      snykSecurityMap.set("issues_url", snykIssueUrl.toString());
//
//      assetStore.save(asset);
//    }
//  }
//
//  private NestedAttributesMap getSnykSecurityAttributes(Payload payload) {
//    if (!(payload instanceof Content)) {
//      return null;
//    }
//    Asset asset = ((Content) payload).getAttributes().get(Asset.class);
//    if (asset == null) {
//      return null;
//    }
//
//    return asset.attributes().child("Snyk Security");
//  }
}
