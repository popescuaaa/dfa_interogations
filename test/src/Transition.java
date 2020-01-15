public class Transition {
    private State from;
    private State to;
    private Symbol on;

    Transition(final State from, final State to, final Symbol on) {
        this.from = from;
        this.to = to;
        this.on = on;
    }

    public State getFrom() {
        return from;
    }

    public State getTo() {
        return to;
    }

    public Symbol getOn() {
        return on;
    }

    public void setFrom(State from) {
        this.from = from;
    }

    public void setTo(State to) {
        this.to = to;
    }

    public void setOn(Symbol on) {
        this.on = on;
    }

    @Override
    public String toString() {
        return "Transition{" +
                "from=" + from.getName() +
                ", to=" + to.getName() +
                ", on=" + on.getSymbol() +
                '}';
    }
}
