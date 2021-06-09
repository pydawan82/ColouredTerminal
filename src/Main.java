import java.io.IOException;
import java.util.Scanner;

import com.pydawan.terminal.Color;
import com.pydawan.terminal.TerminalPrinter;

public class Main {

    public static void main(String ... args) throws IOException  {
        try(TerminalPrinter term = new TerminalPrinter(System.out)){
            Scanner in = new Scanner(System.in);
            term.foreground(Color.DARK_RED).bold(true);

            term.getCursorPos();
            term.print(System.in.read());
            term.print("Salut");
            term.flush();

            in.close();
        }
    }
}
