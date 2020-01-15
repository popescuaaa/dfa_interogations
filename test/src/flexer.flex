
%%

%class Flexer
%unicode
/*%debug*/
%int
/*%line*/
/*%column*/

%{
    public State from;
    public State to;
    public Symbol on;
%}


LineTerminator = \r|\n|\r\n
WS = {LineTerminator} | [ \t\f]
special = "`"|"-"|"="|"["|"]"|";"|"'"|"\\"|"."|"/"|"~"|"!"|"@"|"#"|"$"|"%"|"^"|"&"|"*"|"_"|"+"|":"|"\""|"|"|"<"|">"|"?"
Symbol = [:uppercase:] | [:lowercase:] | [:digit:] | {special}
Name = ([:uppercase:] | [:lowercase:] | [:digit:] | "_")+

%state STARTK ELEMK STOPK SEPKS STARTS INITS ELEMS STOPS SEPSD STARTD INITD ELEMD SRCT SEPSST SYMT SEPSDT DESTT ENDT ENDD STOPD SEPDS SS SEPSF STARTF INITF ELEMF STOPF ENDDFA FINAL

%state DFA STATES STSEP ALPHABET ALSEP DELTA DSEP START STASE STOP STOSE

%%
{WS}	{}
<YYINITIAL>"(" {
    yybegin(STARTK);
}

<STARTK> "{" {
    yybegin(ELEMK);
}

<ELEMK> {Name} {
    // State Processing
    State state = new State(yytext(), false, false);
    Main.states.add(state);
	yybegin(STOPK);
}

<STOPK> {
    "}" {
        yybegin(SEPKS);
    }
    ","	{
        yybegin(ELEMK);
    }
}

<SEPKS> "," {
    yybegin(STARTS);
}

<STARTS> "{" {
    yybegin(INITS);
}

<INITS> "}" {
    yybegin(SEPSD);
}

<ELEMS, INITS> {Symbol}	{
    // Symbol Processing
    Symbol symbol = new Symbol(yycharat(0));
    Main.alphabet.add(symbol);
    yybegin(STOPS);
}

<STOPS> {
    ","	{
        yybegin(ELEMS);
    }
    "}" {
        yybegin(SEPSD);
    }
}

<SEPSD> "," {
    yybegin(STARTD);
}

<STARTD> "(" {
    yybegin(INITD);
}

<INITD> ")" {
    yybegin(SEPDS);
}

<ELEMD,INITD> "(" {
    yybegin(SRCT);
}

<SRCT> {Name} {
    // Transition source processing
    from = new State(yytext(), false, false);
    yybegin(SEPSST);
}


<SEPSST> "," {
    yybegin(SYMT);
}

<SYMT> {Symbol} {
    // Transition symbol processing
    on = new Symbol(yycharat(0));
    yybegin(SEPSDT);
}

<SEPSDT> "," {
    yybegin(DESTT);
}

<DESTT> {Name} {
    // Transition destination processing
    to = new State(yytext(), false, false);
    Main.transitions.add(new Transition(from, to, on));
    yybegin(ENDT);
}

<ENDT> ")" {
    yybegin(STOPD);
}

<STOPD> {
    "," {
        yybegin(ELEMD);
    }
    ")" {
        yybegin(SEPDS);
    }
}

<SEPDS> "," {
    yybegin(SS);
}

<SS> {Name} {
    // Start state Processing
    for (State s : Main.states) {
         if (s.getName().equals(yytext())) {
            s.setStart(true);
         }
    }
    yybegin(SEPSF);
}

<SEPSF> "," {
    yybegin(STARTF);
}

<STARTF> "{" {
    yybegin(INITF);
}

<INITF> "}" {
    yybegin(ENDDFA);
}

<INITF,ELEMF> {Name} {
    // Final state Processing
    for (State s : Main.states) {
        if (s.getName().equals(yytext())) {
           s.setFinal(true);
        }
     }
    yybegin(STOPF);
}

<STOPF> {
    "," {
        yybegin(ELEMF);
    }
    "}" {
        yybegin(ENDDFA);
    }
}
<ENDDFA> ")" {
    return 0;
}

. {
    System.err.println("Syntax error");
    System.exit(0);
}
