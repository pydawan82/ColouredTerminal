import terminal.Color;
import terminal.TerminalPrinter;

public class Main {
    
    public static void main(String ... args) throws InterruptedException {
        try(TerminalPrinter term = new TerminalPrinter(System.out)){
            term.bold(true);
            term.invert(true);
            for(Color c: Color.values()) {
                term.foreground(c);
                term.println("Hello World!");
            }
            term.beep();
        }
    }
}