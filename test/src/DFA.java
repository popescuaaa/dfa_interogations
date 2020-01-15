
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

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

    /**
     *  State index mapping
     */
    public void populate() {
        for (int index = 0; index < states.size(); index++) {
            states.get(index).setIndex(index);
        }

        for (Transition t : transitions) {
            t.getTo().setIndex(getStateIndex(t.getTo()));
            t.getFrom().setIndex(getStateIndex(t.getFrom()));
        }
    }

    public boolean isPath(State initialState, State destination) {
        boolean[] visitedSates = new boolean[states.size()];
        Stack<State> stack = new Stack<>();
        visitedSates[initialState.getIndex()] = true;
        stack.add(initialState);

        while (stack.empty() == false) {
            State currentState = stack.peek();
            stack.pop();
            if (currentState.getIndex() == destination.getIndex()) {
                return true;
            }
            for (Transition t : transitions) {
                /**
                 *  Neighbouring condition in DFA Graph
                 */
                if (t.getFrom().getIndex() == currentState.getIndex()) {
                    if (!visitedSates[t.getTo().getIndex()]) {
                        visitedSates[t.getTo().getIndex()] = true;
                        stack.push(t.getTo());
                    }

                }
            }
        }

       return false;
    }

    public boolean isInCycle(State s) {
        boolean[] visitedSates = new boolean[states.size()];
        LinkedList<State> queue = new LinkedList<>();
        visitedSates[s.getIndex()] = true;
        queue.push(s);

        while (queue.size() != 0) {
            State currentState = queue.poll();

            if (currentState.getIndex() == s.getIndex() && queue.size() != 0) {
                return true;
            }
            for (Transition t : transitions) {
                /**
                 *  Neighbouring condition in DFA Graph
                 */
                if (t.getFrom().getIndex() == currentState.getIndex()) {
                    if (!visitedSates[t.getTo().getIndex()]) {
                        visitedSates[t.getTo().getIndex()] = true;
                        queue.push(t.getTo());
                    }

                }
            }
        }

        return false;
    }


    public ArrayList<State> accessibleStates() {
        ArrayList<State> accessibleStates = new ArrayList<>();
        for (State s : states) {
            if (isPath(startState, s) && !accessibleStates.contains(s)) {
                accessibleStates.add(s);
                s.setAccessible(true);
            }
        }
        return accessibleStates;
    }

    public ArrayList<State> productiveStates() {
        ArrayList<State> productiveStates = new ArrayList<>();
        for (State s : states) {
            for (State finalSate : finalStates) {
                if (isPath(s, finalSate) && !productiveStates.contains(s)) {
                    productiveStates.add(s);
                    s.setProductive(true);
                }
            }
        }

        return productiveStates;
    }


    public boolean isVoidLanguage() {
        ArrayList<State> accessibleStates = accessibleStates();
        for (State finalState : finalStates) {
            for (State acc : accessibleStates) {
                if (finalState.getIndex() == acc.getIndex()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isFiniteLanguage() {
        for (State s : states) {
            if (s.isAccessible() && s.isProductive()) {
                if (isInCycle(s)) {
                    return false;
                }
            }
        }

        return true;
    }
}
