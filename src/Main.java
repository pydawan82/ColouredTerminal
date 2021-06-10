import java.io.IOException;

import com.pydawan.rainbowTerm.Color;
import com.pydawan.rainbowTerm.TerminalPrinter;

public class Main {

    public static void main(String ... args) throws IOException  {
        try(TerminalPrinter term = new TerminalPrinter(System.out)) {
            term.foreground(Color.DARK_RED).bold(true);
            term.clear();
            term.cursorPos(4, 4);
            term.print("Ok");
            term.cursorMove(2, 2);
            term.print("Salut à tous");
        }
    }
}
