package de.ksbrwsk.gdata;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author saborowski
 */
@Component(value = "gDataOAuth2Authenticator")
@DependsOn(value = "gDataInformation")
public class GDataOAuth2Authenticator {

    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".store/oauth2_sample");
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static List<String> scopesList;
    private static FileDataStoreFactory dataStoreFactory;
    private static HttpTransport httpTransport;
    private static GoogleClientSecrets clientSecrets;

    @Autowired
    public GDataOAuth2Authenticator(GDataInformation gDataInformation) throws GDataException {
        scopesList = Arrays.asList(gDataInformation.getScopes());
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new FileReader(gDataInformation.getPathClientSecretFile()));
        } catch(Exception e) {
            throw new GDataException(e.getCause());
        }
    }

    public Optional<Credential> authorize() throws GDataException {
        Credential credential;
        try {
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, scopesList)
                    .setDataStoreFactory(dataStoreFactory)
                    .build();
            credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        } catch (Exception e) {
            throw new GDataException(e.getCause());
        }
        return Optional.ofNullable(credential);
    }
}
