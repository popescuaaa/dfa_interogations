
import java.util.*;

public class DFA {
    private static String DEFAULT_STATE = "default-state";
    private HashMap<String, Set<String>> states;
    private String initialState;
    private Set<String> finalStates;
    private Set<String> accessibleStates;
    private Set<String> utilStates;
    private Set<String> productiveStates;
    private String productiveResultState = DEFAULT_STATE;
    DFA(final HashMap<String, Set<String>> states,
        final Set<String> finalStates,
        final String initialState) {

        this.states = states;
        this.initialState = initialState;
        this.finalStates = finalStates;

        accessibleStates = new TreeSet<>();
        utilStates = new TreeSet<>();
        productiveStates = new TreeSet<>();

    }

    public boolean nullSymbolAcceptance() {
        return finalStates.contains(initialState);
    }

    /**
     * DFS iterative for accessible paths in full states set of the DFA
     * @param initialState
     * @param destination
     * @return
     */
    public boolean isPathAccessible(String initialState, String destination) {
        Set<String> visitedSates = new HashSet<>();
        Stack<String> stack = new Stack<>();
        visitedSates.add(initialState);
        stack.add(initialState);

        while (!stack.empty()) {
            String currentState = stack.peek();
            stack.pop();
            if (currentState.equals(destination)) {
                return true;
            }
            for (String n : states.get(currentState)) {
                /**
                 *  Neighbouring condition in DFA Graph
                 */
                if (!visitedSates.contains(n)) {
                    visitedSates.add(n);
                    stack.push(n);
                }
            }
        }
        return false;
    }

    /**
     * DFS variant for productive paths. The main idea is to stop the
     * dfs when the global variable returnStateProductive is a final state
     * as the rule is to land in a final state indifferent what state
     * @param start
     */
    public void isPathProductive(String start) {
        Set<String> visitedStates = new HashSet<>();
        Stack<String> stack = new Stack<>();
        visitedStates.add(start);
        stack.add(start);

        while (!stack.empty()) {
            String currentState = stack.peek();
            stack.pop();
            if (finalStates.contains(currentState)) {
                productiveResultState = currentState;
                break;
            }

            for (String n : states.get(currentState)) {
                if (!visitedStates.contains(n)) {
                    visitedStates.add(n);
                    stack.add(n);
                }
            }
        }
    }

    public void accessibleStatesGenerator() {
        for (String s : states.keySet()) {
            if (isPathAccessible(initialState, s)) {
                accessibleStates.add(s);
            }
        }
    }

    public void utilStatesGenerator() {
        accessibleStatesGenerator();
        for (String s : accessibleStates) {
            isPathProductive(s);
            if (productiveResultState != DEFAULT_STATE) {
                utilStates.add(s);
                productiveResultState = DEFAULT_STATE;
            }
        }
    }

    public void productiveStatesGenerator() {
        for (String s : states.keySet()) {
            isPathProductive(s);
            if (productiveResultState != DEFAULT_STATE) {
                productiveStates.add(s);
            }
        }
    }

    public Set<String> getAccessibleStates() {
        accessibleStatesGenerator();
        return accessibleStates;
    }

    public Set<String> getUtilStates() {
        utilStatesGenerator();
        return utilStates;
    }

    /**
     * The void language checker is made to be as fast as possible.
     * The main idea here is that the first condition and the second are
     * sufficient as the last one is somehow covered in the first condition.
     * @return
     */
    public boolean isVoidLanguage() {
        accessibleStatesGenerator();
        productiveStatesGenerator();

        boolean noFinalAccessible = false;
        boolean noProductiveAccessible = false;

        Set<String> finalAndAccessibleStates = new HashSet<>(accessibleStates);
        finalAndAccessibleStates.retainAll(finalStates);
        if (finalAndAccessibleStates.size() == 0) {
            noFinalAccessible = true;
        }

        Set<String> intersection = new HashSet<>(accessibleStates);
        intersection.retainAll(productiveStates);
        if (intersection.size() == 0) {
            noFinalAccessible = true;
        }

        return noFinalAccessible || noProductiveAccessible;

    }

    /**
     * DFS checker for DAG, is basically the condition to verify if a specific DFA has a
     * topological sort.
     * @param state
     * @param discovered
     * @param departure
     * @param time
     * @return
     */
    private int DFS(String state,Set<String> discovered, HashMap<String, Integer> departure, int time)
    {
        discovered.add(state);

        for (String n : states.get(state))
        {
            if (!discovered.contains(n))
                time = DFS(n, discovered, departure, time);
        }

        time++;
        departure.put(state, time);
        return time;
    }

    /**
     * Checker for finite language. The check is performed on all states but only the
     * util ones are verified for cycles, as in the definition.
     * @return
     */
    public boolean isDAG()
    {
        Set<String> discovered = new HashSet<>();
        HashMap<String, Integer> departure = new HashMap<>();

        for (String s : states.keySet()) {
            departure.put(s, 0);
        }

        int time = 0;

        for (String s : states.keySet()){
            if (!discovered.contains(s)){
                time = DFS(s, discovered, departure, time);
            }
        }

        utilStatesGenerator();
        for (String u : utilStates) {
            for (String n : states.get(u)) {
                if (departure.get(u) <= departure.get(n))
                    return false;
            }
        }
        return true;
    }
}

