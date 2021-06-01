import terminal.Color;
import terminal.TerminalPrinter;

public class Main {
    
    public static void main(String ... args) {
        TerminalPrinter term = new TerminalPrinter(System.out);
        term.foreground(Color.BRIGHT_BLUE).background(Color.WHITE);
        term.println("Salut");
        term.reset();
        term.print("Ouais");
        term.clearLine();
        term.clear();
        term.print("J'ai clear");

        term.reset();
    }
}