package com.pydawan.terminal;

import static com.pydawan.terminal.ControlSequenceCodes.*;
import static com.pydawan.terminal.EscapeCodes.*;
import static com.pydawan.terminal.SGRCodes.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.ref.Cleaner.Cleanable;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A class to wrap a terminal style outputstream. Can display colors and format
 * text if supported by the implementation of the terminal.
 * 
 * @see https://en.wikipedia.org/wiki/ANSI_escape_code
 */
public class TerminalPrinter extends PrintStream {

    /**
     * Creates a new {@link TerminalPrinter} from a given {@link OutputStream}
     * 
     * @param out - the outputstream to wrap.
     */
    public TerminalPrinter(OutputStream out) {
        super(out);
    }

    /**
     * Prints a Control Sequence code to the output.
     * 
     * @param cmdChar - the command
     * @param params  - the parameters
     */
    private void printCSICode(String cmdChar, int... params) {
        String joinedParams = IntStream.of(params).mapToObj(Integer::toString).collect(Collectors.joining(";"));
        print(CSI + joinedParams + cmdChar);
    }

    /**
     * Prints the given SGR code to the output.
     * 
     * @param code - the SGR code
     */
    private void printSGRCode(int code) {
        printCSICode(SGR, code);
    }

    /**
     * Sets the foreground {@link Color} of the terminal. Returns itself to simplify
     * multiple call of text formatting at once.
     * 
     * @param color - The new foreground color
     * @return this terminal printer
     */
    public TerminalPrinter foreground(Color color) {
        printSGRCode(color.fgCode);

        return this;
    }

    /**
     * Sets the background {@link Color} of the terminal. Returns itself to simplify
     * multiple call of text formatting at once.
     * 
     * @param color - The new background color
     * @return this terminal printer
     */
    public TerminalPrinter background(Color color) {
        printSGRCode(color.bgCode);

        return this;
    }

    /**
     * Invert or not the foreground and the background {@link Color} of the
     * terminal. Returns itself to simplify multiple call of text formatting at
     * once.
     * 
     * @param inverted - if the colors should be inverted or not
     * @return this terminal printer
     */
    public TerminalPrinter invert(boolean inverted) {
        printSGRCode(inverted ? INVERT : NO_INVERT);

        return this;
    }

    /**
     * Sets the text to bold or back to normal. Returns itself to simplify multiple
     * call of text formatting at once.
     * 
     * @param bold - if the text should be bold or not.
     * @return this terminal printer
     */
    public TerminalPrinter bold(boolean bold) {
        printSGRCode(bold ? BOLD : NOT_BOLD);

        return this;
    }

    /**
     * Resets all the graphics properties of this terminal.
     */
    public void reset() {
        printSGRCode(DEFAULT);
    }

    /**
     * Moves the cursor up by a given number of lines.
     * 
     * @param n - the number of lines
     */
    public void cursorUp(int n) {
        printCSICode(CURSOR_UP, n);
    }

    /**
     * Moves the cursor down by a given number of lines.
     * 
     * @param n - the number of lines
     */
    public void cursorDown(int n) {
        printCSICode(CURSOR_DOWN, n);
    }

    /**
     * Moves the cursor forward (right) by a given offset.
     * 
     * @param n - the offset
     */
    public void cursorForward(int n) {
        printCSICode(CURSOR_FORWARD, n);
    }

    /**
     * Moves the cursor backwards (left) by a given offset.
     * 
     * @param n - the offset
     */
    public void cursorBack(int n) {
        printCSICode(CURSOR_BACK, n);
    }

    /**
     * Moves the cursor to the start of the nth next line.
     * 
     * @param n - the number of line
     */
    public void cursorNext(int n) {
        printCSICode(CURSOR_NEXT_LINE, n);
    }

    /**
     * Moves the cursor to the start of the nth previous line.
     * 
     * @param n - the number of line
     */
    public void cursorPrevious(int n) {
        printCSICode(CURSOR_PREVIOUS_LINE, n);
    }

    /**
     * Sets the cursor position in the current line.
     * 
     * @param x - the absolute position
     */
    public void cursorPos(int x) {
        printCSICode(CURSOR_H_POS, x);
    }

    /**
     * Sets the cursors position in the display
     * 
     * @param x - the horizontal absolute position
     * @param y - the vertical absolute position
     */
    public void cursorPos(int x, int y) {
        printCSICode(CURSOR_POS, x, y);
    }

    /**
     * Clears the display and sets the cursor position to the top-left of the
     * display.
     */
    public void clear() {
        printCSICode(CLEAR_DISP, ALL);
        cursorPos(1, 1);
    }

    /**
     * Clears the display from the cursor's position to the end of the display.
     */
    public void clearFrom() {
        printCSICode(CLEAR_DISP, FROM);
    }

    /**
     * Clears the display from the begin ning to the cursor's position.
     */
    public void clearTo() {
        printCSICode(CLEAR_DISP, TO);
    }

    /**
     * Clears the whole line of the cursor's position.
     */
    public void clearLine() {
        printCSICode(CLEAR_LINE, ALL);
        cursorPos(1);
    }

    /**
     * Clears a line from the cursor's position to its end
     */
    public void clearLineFrom() {
        printCSICode(CLEAR_LINE, FROM);
    }

    /**
     * Clears a line from the the beginning to the cursor's position
     */
    public void clearLineTo() {
        printCSICode(CLEAR_LINE, TO);
    }

    public void getCursorPos() {
        printCSICode(DSR, GET_CURSOR_POS);
    }

    /**
     * Beeps and the flushes this {@link TerminalPrinter}
     */
    public void beep() {
        print(BELL);
        flush();
    }

    public void printLoading(int width, double completion) {
        clearLineFrom();
        print('[');
        for(int i=0; i<width; i++) {
            print((((double)i/width) < completion)?'-':' ');
        }
        print(']');
        cursorBack(width+2);
        flush();
    }

    public void printLoading(int width, double lastStatus, double newStatus) {
        
    }

    /**
     * Resets the graphics of the terminal and flushes this {@link TerminalPrinter}. Then closes the underlying
     * resource.
     * 
     * @see PrintStream#close()
     */
    @Override
    public void close() {
        reset();
        flush();
        try {
            out.close();
        } catch (IOException e) {
            setError();
        }
    }
}
