import java.util.ArrayList;

public class DFA {
    private static final int DFA_FINAL_STATES_INITIAL_NUMBER= 10;
    private ArrayList<Transition> transitions;
    private ArrayList<State> states;
    private ArrayList<Symbol> alphabet;
    private State startState;
    private ArrayList<State> finalStates = new ArrayList<>(DFA_FINAL_STATES_INITIAL_NUMBER);

    DFA(final ArrayList<Transition> transitions,
        final ArrayList<State> states,
        final ArrayList<Symbol> alphabet){

        this.transitions = transitions;
        this.alphabet = alphabet;
        this.states = states;

        for (State s : states) {
            if (s.isStart()) {
                startState = s;
            }
        }

        for (State s : states) {
            if (s.isFinal()) {
                finalStates.add(s);
            }
        }

    }

    public boolean nullSymbolAcceptance() {
        for (State s: finalStates) {
            if (startState.getName().equals(s.getName())) {
                return true;
            }
        }
        return false;
    }
}
