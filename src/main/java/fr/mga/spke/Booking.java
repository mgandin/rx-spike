package fr.mga.spke;

/**
 * @author mathieu.gandin
 */
public class Booking {

    private final String id;

    private String accumulator = "";

    private State state;


    public Booking(String id, State state) {
        this.id = id;
        this.state = state;
    }

    private Booking(String id, State state, String accumulator) {
        this.id = id;
        this.state = state;
        this.accumulator = accumulator;
    }

    public static Booking copy(String id, State state, String accumulator) {
        return new Booking(id, state, accumulator);
    }

    public void change(String accumulator,State state) {
        this.state = state;
        this.accumulator += "::" + accumulator;
    }

    public String getAccumulator() {
        return accumulator;
    }

    public State getState() {
        return state;
    }
}
