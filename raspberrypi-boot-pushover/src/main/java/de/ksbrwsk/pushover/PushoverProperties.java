package de.ksbrwsk.pushover;

/**
 * @author saborowski
 */
public class PushoverProperties {

    private String apiToken;
    private String userId;
    private String device;
    private String priority;
    private String title;
    private String url;
    private String titleForURL;
    private String sound;

    public PushoverProperties() {
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitleForURL(String titleForURL) {
        this.titleForURL = titleForURL;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getApiToken() {
        return apiToken;
    }

    public String getUserId() {
        return userId;
    }

    public String getDevice() {
        return device;
    }

    public String getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitleForURL() {
        return titleForURL;
    }

    public String getSound() {
        return sound;
    }
}
