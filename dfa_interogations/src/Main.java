import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Main {
    /**
     *  Graph model
     */
    public static HashMap<String, Set<String>> states = new HashMap<>();
    public static String initialState;
    public static Set<String> finalStates = new HashSet<>();
    public static DFA dfa;

    public static void main(String [] argz) {
        if(argz.length != 1) {
            System.err.println("Argument error");
        }
        int mode = -1;
        if(argz[0].equals("-e")) {
            mode = 1;
        } else if(argz[0].equals("-a")) {
            mode=2;
        } else if(argz[0].equals("-u")) {
            mode=3;
        } else if(argz[0].equals("-v")) {
            mode=4;
        } else if(argz[0].equals("-f")) {
            mode=5;
        } else {
            System.err.println("Argument error");
            System.exit(1);
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("dfa"));
            Flexer scanner = new Flexer(br);
            scanner.yylex();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dfa = new DFA(states, finalStates, initialState);

        switch(mode) {
            case 1:
                //TODO: Has e
                if (dfa.nullSymbolAcceptance()) {
                    System.out.println("Yes");
                } else {
                    System.out.println("No");
                }
                break;
            case 2:
                //TODO: Accessible states
                for (String s : dfa.getAccessibleStates()) {
                    System.out.println(s);
                }
                break;
            case 3:
                //TODO: Useful states
                for (String s : dfa.getUtilStates()) {
                    System.out.println(s);
                }
                break;
            case 4:
                //TODO: Empty language
                if (dfa.isVoidLanguage()) {
                    System.out.println("Yes");
                } else {
                    System.out.println("No");
                }
                break;
            case 5:
                //TODO: Finite language
                if (dfa.isDAG()) {
                    System.out.println("Yes");
                } else {
                    System.out.println("No");
                }
                break;
            default:
                System.err.println("Argument error");
                System.exit(1);
        }

    }
}
