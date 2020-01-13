import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

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

    private Integer getStateIndex(State s) {
        for (int index = 0; index < states.size(); index++) {
            if (states.get(index).getName().equals(s.getName())) {
                return index;
            }
        }

        return -1;
    }

    public boolean isPath(State initialState, State destination, ArrayList<Boolean> visitedStates) {
        if (initialState == destination) {
            return true;
        }

        for (Transition t : transitions) {
            if (initialState.getName().equals(t.getFrom().getName()) &&
                    !visitedStates.get(getStateIndex(t.getFrom()))) {
                visitedStates.set(getStateIndex(t.getFrom()), true);
                return isPath(t.getTo(), destination, visitedStates);
            }
        }

        return false;
    }

    public ArrayList<State> accessibleStates() {
        ArrayList<State> accessibleStates = new ArrayList<>(states.size());
        for (State s : states) {
            ArrayList<Boolean> visitedStates = new ArrayList<>(states.size());
            for (Boolean b : visitedStates) {
                b = false;
            }
            if (isPath(startState, s, visitedStates)) {
                accessibleStates.add(s);
            }
        }

        return accessibleStates;
    }
}
