package com.sberhealth.plugins.nexus.scanner;

import com.sberhealth.plugins.nexus.model.ScanResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.maven.MavenPathParser;
import org.sonatype.nexus.repository.storage.AssetStore;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Payload;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MavenScanner {
  private static final Logger LOG = LoggerFactory.getLogger(MavenScanner.class);

  private final AssetStore assetStore;
  private final MavenPathParser mavenPathParser;

  @Inject
  public MavenScanner(final AssetStore assetStore, final MavenPathParser mavenPathParser) {
    this.assetStore = assetStore;
    this.mavenPathParser = mavenPathParser;
  }

  ScanResult scan(@Nonnull Context context, Payload payload) {
    LOG.info("I m work, Vova!!!");
    Object mavenPathAttribute = context.getAttributes().get(MavenPath.class.getName());
    if (!(mavenPathAttribute instanceof MavenPath)) {
      LOG.warn("Could not extract maven path from {}", context.getRequest().getPath());
      return null;
    }

    MavenPath mavenPath = (MavenPath) mavenPathAttribute;
    MavenPath parsedMavenPath = mavenPathParser.parsePath(mavenPath.getPath());
    MavenPath.Coordinates coordinates = parsedMavenPath.getCoordinates();
    if (coordinates == null) {
      LOG.warn("Coordinates are null for {}", parsedMavenPath);
      return null;
    }

    if (!"jar".equals(coordinates.getExtension())) {
      LOG.debug("Only 'jar' extension is supported. Skip scanning");
      return null;
    }

    ScanResult scanResult = new ScanResult();

//    if (snykPropertiesExist(payload)) {
//      LOG.debug("Maven artifact {} was already scanned. Skip scanning", parsedMavenPath);
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
//      try {
//        TestResult testResult = null;
//        Response<TestResult> response = snykClient.testMaven(coordinates.getGroupId(),
//                                                             coordinates.getArtifactId(),
//                                                             coordinates.getVersion(),
//                                                             organizationId,
//                                                             null).execute();
//        if (response.isSuccessful() && response.body() != null) {
//          testResult = response.body();
//          String responseAsText = new ObjectMapper().writeValueAsString(response.body());
//          LOG.debug("testMaven response: {}", responseAsText);
//        }
//
//        if (testResult != null) {
//          updateAssetAttributes(testResult, coordinates, payload);
//
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
//        LOG.error("Could not test maven artifact: {}", parsedMavenPath, ex);
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
//  private void updateAssetAttributes(@Nonnull TestResult testResult, @Nonnull MavenPath.Coordinates coordinates, Payload payload) {
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
//      snykSecurityMap.set("issues_licenses", getLicenseIssuesAsFormattedString(testResult.issues.licenses));
//      StringBuilder snykIssueUrl = new StringBuilder("https://snyk.io/vuln/");
//      snykIssueUrl.append(testResult.packageManager).append(":")
//                  .append(coordinates.getGroupId()).append("%3A")
//                  .append(coordinates.getArtifactId()).append("@")
//                  .append(coordinates.getVersion());
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
