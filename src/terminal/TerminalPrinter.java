package terminal;

import java.io.OutputStream;
import java.io.PrintStream;

import static terminal.EscapeCodes.*;
import static terminal.ControlSequenceCodes.*;
import static terminal.SGRCodes.*;

public class TerminalPrinter extends PrintStream {

    private static final String CSI = ESCAPE+"[";
    private static final String SGRFormat = CSI+"%dm";

    public TerminalPrinter(OutputStream out) {
        super(out);
    }

    private void printCode(int code){
        String cmd = SGRFormat.formatted(code);
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

    public TerminalPrinter bold(boolean bold) {
        printCode(bold ? BOLD : NOT_BOLD);

        return this;
    }
    
    public void reset() {
        printCode(DEFAULT);
    }

    public void cursorUp(int n) {
        print(CSI+n+CURSOR_UP);
    }

    public void cursorDown(int n) {
        print(CSI+n+CURSOR_DOWN);
    }

    public void cursorForward(int n) {
        print(CSI+n+CURSOR_FORWARD);
    }

    public void cursorBack(int n) {
        print(CSI+n+CURSOR_BACK);
    }

    public void cursorNext(int n){
        print(CSI+n+CURSOR_NEXT_LINE);
    }

    public void cursorPrevious(int n){
        print(CSI+n+CURSOR_PREVIOUS_LINE);
    }

    public void cursorPos(int x){
        print(CSI+x+CURSOR_NEXT_LINE);
    }

    public void cursorPos(int x, int y){
        print(CSI+x+";"+y+CURSOR_NEXT_LINE);
    }

    public void clear() {
        print(CSI+ALL+CLEAR_DISP);
        cursorPos(1, 1);
    }

    public void clearFrom() {
        print(CSI+FROM+CLEAR_DISP);
    }

    public void clearTo() {
        print(CSI+TO+CLEAR_DISP);
    }

    public void clearLine() {
        print(CSI+ALL+CLEAR_LINE);
        cursorPos(1);
    }

    public void clearLineFrom() {
        print(CSI+FROM+CLEAR_LINE);
    }

    public void clearLineTo() {
        print(CSI+TO+CLEAR_LINE);
    }
}
