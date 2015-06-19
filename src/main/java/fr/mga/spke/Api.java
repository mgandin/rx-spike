package fr.mga.spke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author mathieu.gandin
 */
public class Api {

    private static final Logger logger = LoggerFactory.getLogger(Api.class);

    public String book(String id) {
        logger.info("We are booking ::: {}",id);
        maybeThrowAnError("book");
        return id + ":booked";
    }

    public String pay(String id) {
        logger.info("We are paying ::: {}",id);
        maybeThrowAnError("pay");
        return id + ":payed";
    }

    public String assurance(String id) {
        logger.info("We are assuring ::: {}",id);
        maybeThrowAnError("assurance");
        return id + ":assured";
    }

    public String compta(String id) {
        logger.info("We are making compta ::: {}",id);
        maybeThrowAnError("compta");
        return id + ":comptabilised";
    }

    private void maybeThrowAnError(String verb) {
        Random random = new Random();
       if (random.nextInt() % 2 == 0) {
            throw new RuntimeException("ERROR DURING API CALL :: " + verb);
        }
    }

    public void sendMail(Throwable throwable) {
        System.out.println("SEND MAIL ERROR : " + throwable);
    }

    public void sendMail(String message) {
        System.out.println("SEND MAIL OK : " + message);
    }
}
