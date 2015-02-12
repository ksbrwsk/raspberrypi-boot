package de.ksbrwsk.gdata;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * @author saborowski
 */
public class Utils {

    private static DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance(Locale.getDefault());

    public static String doubleToString(double doubleValue) {

        DecimalFormat decimalFormat = new DecimalFormat("0.0", decimalFormatSymbols);
        return decimalFormat.format(doubleValue);
    }
}
