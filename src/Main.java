import java.io.IOException;

import com.pydawan.terminal.Color;
import com.pydawan.terminal.TerminalPrinter;

public class Main {

    public static void main(String ... args) throws IOException  {
        try(TerminalPrinter term = new TerminalPrinter(System.out)){
            term.foreground(Color.DARK_RED).bold(true);
            term.println("Salut");
            term.clear();
            term.println("Hello World!");
            term.print("SalutMichel");
            term.clearLine();
            term.println("Salut Michel!");
        }
    }
}
