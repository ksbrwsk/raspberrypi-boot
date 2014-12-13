package de.ksbrwsk.bmp085;

/**
 * extract the {@link de.ksbrwsk.bmp085.Bmp085Message} from the
 * {@link de.ksbrwsk.bmp085.Bmp085DataEvent}.
 *
 * @author saborowski
 */
public class Bmp085MessageExtractor {

    public Bmp085Message extractMessage(Bmp085DataEvent bmp085Event) {
        Bmp085Message message = bmp085Event.getBmp085Message();
        return message;
    }
}
