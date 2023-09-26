package com.sberhealth.plugins.nexus.capability;

import java.util.Map;

import org.sonatype.nexus.capability.CapabilityConfigurationSupport;

public class SnykSecurityCapabilityConfiguration extends CapabilityConfigurationSupport {
  private String apiUrl;
  private String apiToken;
  private String organizationId;
  private String vulnerabilityThreshold;
  private String licenseThreshold;
  private String proxyHost;
  private String proxyPort;
  private String proxyUser;
  private String proxyPassword;

  SnykSecurityCapabilityConfiguration(Map<String, String> properties) {
    apiUrl = properties.getOrDefault(SnykSecurityCapabilityKey.API_URL.propertyKey(), SnykSecurityCapabilityKey.API_URL.defaultValue());
    apiToken = properties.get(SnykSecurityCapabilityKey.API_TOKEN.propertyKey());
    organizationId = properties.get(SnykSecurityCapabilityKey.ORGANIZATION_ID.propertyKey());
    vulnerabilityThreshold = properties.getOrDefault(SnykSecurityCapabilityKey.VULNERABILITY_THRESHOLD.propertyKey(), SnykSecurityCapabilityKey.VULNERABILITY_THRESHOLD.defaultValue());
    licenseThreshold = properties.getOrDefault(SnykSecurityCapabilityKey.LICENSE_THRESHOLD.propertyKey(), SnykSecurityCapabilityKey.LICENSE_THRESHOLD.defaultValue());
    proxyHost = properties.getOrDefault(SnykSecurityCapabilityKey.PROXY_HOST.propertyKey(), SnykSecurityCapabilityKey.PROXY_HOST.defaultValue());
    proxyPort = properties.getOrDefault(SnykSecurityCapabilityKey.PROXY_PORT.propertyKey(), SnykSecurityCapabilityKey.PROXY_PORT.defaultValue());
    proxyUser = properties.getOrDefault(SnykSecurityCapabilityKey.PROXY_USER.propertyKey(), SnykSecurityCapabilityKey.PROXY_USER.defaultValue());
    proxyPassword = properties.getOrDefault(SnykSecurityCapabilityKey.PROXY_PASSWORD.propertyKey(), SnykSecurityCapabilityKey.PROXY_PASSWORD.defaultValue());

  }

  public String getApiUrl() {
    return apiUrl;
  }

  public String getApiToken() {
    return apiToken;
  }

  public String getOrganizationId() {
    return organizationId;
  }

  public String getVulnerabilityThreshold() {
    return vulnerabilityThreshold;
  }

  public String getLicenseThreshold() {
    return licenseThreshold;
  }

  public String getProxyHost() {
    return proxyHost;
  }
  public String getProxyPort() {
    return proxyPort;
  }
  public String getProxyUser() {
    return proxyUser;
  }
  public String getProxyPassword() {
    return proxyPassword;
  }
}
