
import java.util.*;

public class DFA {
    private HashMap<String, ArrayList<String>> states;
    private String initialState;
    private Set<String> finalStates;
    private Set<String> accessibleStates;
    private Set<String> productiveStates;

    DFA(final HashMap<String, ArrayList<String>> states,
        final Set<String> finalStates,
        final String initialState){

        this.states = states;
        this.initialState = initialState;
        this.finalStates = finalStates;

        accessibleStates = new TreeSet<>();
        productiveStates = new TreeSet<>();
    }

    public boolean nullSymbolAcceptance() {
       return finalStates.contains(initialState);
    }

    /**
     *
     * @param initialState
     * @param destination
     * @return
     */
    public boolean isPath(String initialState, String destination) {
        HashMap<String, Boolean> visitedSates = new HashMap<>();
        for (String s : states.keySet()) {
            visitedSates.put(s, false);
        }
        Stack<String> stack = new Stack<>();
        visitedSates.put(initialState, true);
        stack.add(initialState);

        while (stack.empty() == false) {
            String currentState = stack.peek();
            stack.pop();
            if (currentState.equals(destination)) {
                return true;
            }
            for (String n : states.get(currentState)) {
                /**
                 *  Neighbouring condition in DFA Graph
                 */
                    if (!visitedSates.get(n)) {
                        visitedSates.put(n, true);
                        stack.push(n);
                    }
                }
            }
       return false;
    }


    public void accessibleStatesGenerator() {
        for (String s : states.keySet()) {
            if (isPath(initialState, s)) {
                accessibleStates.add(s);
            }
        }
    }

    public void productiveStatesGenerator() {
        for (String s : states.keySet()) {
            for (String finalSate : finalStates) {
                if (isPath(s, finalSate)) {
                    productiveStates.add(s);
                }
            }
        }
    }

    public Set<String> getAccessibleStates() {
        accessibleStatesGenerator();
        return accessibleStates;
    }

    public Set<String> getUtilStates() {
        productiveStatesGenerator();
        accessibleStatesGenerator();
        Set<String> intersection =  new HashSet<>(accessibleStates);
        intersection.retainAll(productiveStates);
        return intersection;
    }

    public boolean isVoidLanguage() {
        accessibleStatesGenerator();
        productiveStatesGenerator();
        boolean noFinalAccessible = false;
        boolean noProductiveAccessible = false;
        boolean initialStateProductive = false;
        Set<String> finalAndAccessibleStates = new HashSet<>(accessibleStates);
        finalAndAccessibleStates.retainAll(finalStates);
        if (finalAndAccessibleStates.size() == 0) {
            noFinalAccessible = true;
        }

        Set<String> intersection =  new HashSet<>(accessibleStates);
        intersection.retainAll(productiveStates);
        if (intersection.size() == 0) {
            noFinalAccessible = true;
        }

        if (!productiveStates.contains(initialState)) {
            initialStateProductive = true;
        }

        return noFinalAccessible || noProductiveAccessible || initialStateProductive;
    }

    public boolean isFiniteLanguage() {
        return true;
    }
}
