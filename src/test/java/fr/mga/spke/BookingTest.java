package fr.mga.spke;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * @author mathieu.gandin
 */
public class BookingTest {

    @Test
    public void should_do_all_booking_process() {
        Orchestrator orchestrator = new Orchestrator(new Api());
        Booking booked = orchestrator.book("123456");
        Assertions.assertThat(booked.getAccumulator()).isEqualTo("::123456:booked::123456:payed::123456:assured");
        Assertions.assertThat(booked.getState()).isEqualTo(State.COMPTABILIZED);
    }
}
