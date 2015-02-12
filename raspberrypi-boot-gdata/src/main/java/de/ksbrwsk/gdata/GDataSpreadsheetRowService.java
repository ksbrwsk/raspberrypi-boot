package de.ksbrwsk.gdata;

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * @author saborowski
 */
@MessageEndpoint
public class GDataSpreadsheetRowService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GDataSpreadsheetRowService.class);
    private final static String SPREADSHEET_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";

    private GDataOAuth2Authenticator gDataOAuth2Authenticator;
    private SpreadsheetService googleService;

    @Autowired
    public GDataSpreadsheetRowService(GDataOAuth2Authenticator gDataOAuth2Authenticator) throws GDataException {
        this.gDataOAuth2Authenticator = gDataOAuth2Authenticator;
        this.init();
    }

    private void init() throws GDataException {
        Optional<Credential> optional = this.gDataOAuth2Authenticator.authorize();
        if(optional.isPresent()) {
            Credential credential = optional.get();
            this.googleService = new SpreadsheetService("oauth-sample-app");
            this.googleService.setOAuth2Credentials(credential);
        } else {
            throw new GDataException("No Credentials. Please check your Configuration.");
        }
    }

    @ServiceActivator(inputChannel = "temperatureMessageData")
    public void handleMessage(TemperatureMessage temperatureMessage) {
        try {
            URL feedUrl = new URL(SPREADSHEET_URL);
            SpreadsheetFeed feed = this.googleService.getFeed(feedUrl, SpreadsheetFeed.class);
            List<SpreadsheetEntry> spreadsheets = feed.getEntries();
            if (spreadsheets != null) {
                SpreadsheetEntry spreadsheet = spreadsheets.get(0);
                WorksheetEntry worksheet = spreadsheet.getDefaultWorksheet();
                URL listFeedUrl = worksheet.getListFeedUrl();
                ListEntry row = createSpreadsheetRow(temperatureMessage);
                googleService.insert(listFeedUrl, row);
            }
        } catch(Exception ex) {
            LOGGER.error("Error creating GData Spreadsheet Row.", ex);
        }
    }

    private ListEntry createSpreadsheetRow(TemperatureMessage temperatureMessage) {
        ListEntry row = new ListEntry();
        row.getCustomElements().setValueLocal("deviceId", temperatureMessage.getDeviceId());
        row.getCustomElements().setValueLocal("deviceLocation", temperatureMessage.getDeviceLocation());
        row.getCustomElements().setValueLocal("degreesInCelsius", Utils.doubleToString(temperatureMessage.getDegreesInCelsius()));
        row.getCustomElements().setValueLocal("pressureInHPa", Utils.doubleToString((temperatureMessage.getPressureInHPa())));
        row.getCustomElements().setValueLocal("heightInMeter", Utils.doubleToString((temperatureMessage.getHeightInMeter())));
        row.getCustomElements().setValueLocal("measuredAt", temperatureMessage.getMeasuredAt());
        return row;
    }
}
