import terminal.Color;
import terminal.TerminalPrinter;

public class Main {
    
    public static void main(String ... args) {
        try(TerminalPrinter term = new TerminalPrinter(System.out)){
            term.foreground(Color.BRIGHT_BLUE).background(Color.WHITE).bold(true);
            
            term.println("Hello World");
        }
    }
}