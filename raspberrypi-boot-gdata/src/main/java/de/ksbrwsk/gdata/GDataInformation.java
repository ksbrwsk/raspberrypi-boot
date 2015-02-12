package de.ksbrwsk.gdata;

import org.springframework.util.Assert;

/**
 * @author saborowski
 */
public class GDataInformation {

    private String scopes;
    private String applicationName;
    private String pathClientSecretFile;

    public GDataInformation(String applicationName, String pathClientSecretFile, String scopes) {
        Assert.notNull(applicationName);
        Assert.notNull(pathClientSecretFile);
        Assert.notNull(scopes);
        this.applicationName = applicationName;
        this.pathClientSecretFile = pathClientSecretFile;
        this.scopes = scopes;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getPathClientSecretFile() {
        return pathClientSecretFile;
    }

    public String getScopes() {
        return scopes;
    }
}
