package terminal;

import java.io.OutputStream;
import java.io.PrintStream;

import static terminal.SGRCodes.*;

public class TerminalPrinter extends PrintStream {

    private static final String codeFormat = "\u001B[%dm";

    public TerminalPrinter(OutputStream out) {
        super(out);
    }

    private void printCode(int code){
        String cmd = codeFormat.formatted(code);
        printf(cmd);
    }

    public TerminalPrinter foreground(Color color) {
        printCode(color.fgCode);

        return this;
    }

    public TerminalPrinter background(Color color) {
        printCode(color.bgCode);

        return this;
    }

    public TerminalPrinter invert(boolean inverted) {
        printCode(inverted ? INVERT : NO_INVERT);

        return this;
    }
    
    public void reset() {
        printCode(DEFAULT);
    }
    
}
