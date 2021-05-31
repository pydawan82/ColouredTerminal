import terminal.Color;
import terminal.TerminalPrinter;

public class Main {
    
    public static void main(String ... args) {
        TerminalPrinter term = new TerminalPrinter(System.out);
        term.foreground(Color.BRIGHT_BLUE);
        term.println("Salut");
        term.reset();
        term.println("Ouais");

        term.reset();
    }
}