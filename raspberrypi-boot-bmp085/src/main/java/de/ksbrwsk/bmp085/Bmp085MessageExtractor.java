package de.ksbrwsk.bmp085;

import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

/**
 * extract the {@link de.ksbrwsk.bmp085.Bmp085Message} from the
 * {@link de.ksbrwsk.bmp085.Bmp085DataEvent}.
 *
 * @author saborowski
 */
@Component
public class Bmp085MessageExtractor {

    @Transformer(inputChannel = "newBmp085DataEvent", outputChannel = "bmp085Data")
    public Bmp085Message extractMessage(Bmp085DataEvent bmp085Event) {
        return bmp085Event.getBmp085Message();
    }
}
