import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {

    public static ArrayList<State> states = new ArrayList<>();
    public static ArrayList<Transition> transitions = new ArrayList<>();
    public static ArrayList<Symbol> alphabet = new ArrayList<>();
    public static DFA dfa;
    public static void main(String [] argz) {
        if(argz.length != 2) {
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
            BufferedReader br = new BufferedReader(new FileReader("resources/dfa"));
            Flex scanner = new Flex(br);
            scanner.yylex();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dfa = new DFA(transitions, states, alphabet);
        switch(mode) {
            case 1:
                //TODO: Has e
                if (dfa.nullSymbolAcceptance()) {
                    System.out.println("Yes");
                }
                else {
                    System.out.println("No");
                }
                break;
            case 2:
                //TODO: Accessible states
                break;
            case 3:
                //TODO: Useful states
                break;
            case 4:
                //TODO: Empty language
                break;
            case 5:
                //TODO: Finite language
                break;
            default:
                System.err.println("Argument error");
                System.exit(1);
        }

    }
}