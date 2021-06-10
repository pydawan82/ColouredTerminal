package com.pydawan.rainbowTerm;

import static com.pydawan.rainbowTerm.ControlSequenceCodes.*;
import static com.pydawan.rainbowTerm.EscapeCodes.*;
import static com.pydawan.rainbowTerm.SGRCodes.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
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
    private void controlSequence(String cmdChar, int... params) {
        String joinedParams = IntStream.of(params).mapToObj(Integer::toString).collect(Collectors.joining(";"));
        print(CSI + joinedParams + cmdChar);
    }

    /**
     * Prints the given SGR code to the output.
     * @param code - the SGR code
     */
    private void setGraphics(int code) {
        controlSequence(SGR, code);
    }

    /**
     * Resets the current terminal graphics and sets the new
     * configuration.
     * @param config - the configuration to be set
     */
    public void setConfiguration(Configuration config) {
        reset();

        foreground(config.foreground());
        background(config.background());
        bold(config.bold());
    }

    /**
     * Sets the foreground {@link Color} of the terminal. Returns itself to simplify
     * multiple call of text formatting at once.
     * 
     * @param color - The new foreground color
     * @return this terminal printer
     */
    public TerminalPrinter foreground(Color color) {
        setGraphics(color.fgCode);

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
        setGraphics(color.bgCode);

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
        setGraphics(inverted ? INVERT : NO_INVERT);

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
        setGraphics(bold ? BOLD : NOT_BOLD);

        return this;
    }

    /**
     * Resets all the graphics properties of this terminal.
     */
    public void reset() {
        setGraphics(DEFAULT);
    }

    /**
     * Moves the cursor up by a given number of lines.
     * 
     * @param n - the number of lines
     */
    public void cursorUp(int n) {
        controlSequence(CURSOR_UP, n);
    }

    /**
     * Moves the cursor down by a given number of lines.
     * 
     * @param n - the number of lines
     */
    public void cursorDown(int n) {
        controlSequence(CURSOR_DOWN, n);
    }

    /**
     * Moves the cursor forward (right) by a given offset.
     * 
     * @param n - the offset
     */
    public void cursorForward(int n) {
        controlSequence(CURSOR_FORWARD, n);
    }

    /**
     * Moves the cursor backwards (left) by a given offset.
     * 
     * @param n - the offset
     */
    public void cursorBack(int n) {
        controlSequence(CURSOR_BACK, n);
    }

    /**
     * Moves the cursor to the start of the nth next line.
     * 
     * @param n - the number of line
     */
    public void cursorNext(int n) {
        controlSequence(CURSOR_NEXT_LINE, n);
    }

    /**
     * Moves the cursor to the start of the nth previous line.
     * 
     * @param n - the number of line
     */
    public void cursorPrevious(int n) {
        controlSequence(CURSOR_PREVIOUS_LINE, n);
    }
    
    /**
     * Moves the cursor relatively by given distance
     *
     * @param dx - the horizontal shift
     * @param dy - the vertical shift
     */
    public void cursorMove(int dx, int dy) {
	if(dx<0)
	    cursorBack(-dx);
	else if(dx>0)
	    cursorForward(dx);

	if(dy<0)
	    cursorPrevious(-dy);
	else if(dy>0)
	    cursorNext(dy);
    }

    /**
     * Sets the cursor position in the current line.
     * 
     * @param x - the absolute position
     */
    public void cursorPos(int x) {
        controlSequence(CURSOR_H_POS, x);
    }

    /**
     * Sets the cursors position in the display
     * 
     * @param x - the horizontal absolute position
     * @param y - the vertical absolute position
     */
    public void cursorPos(int x, int y) {
        controlSequence(CURSOR_POS, x, y);
    }

    /**
     * Clears the display and sets the cursor position to the top-left of the
     * display.
     * @see #clear(boolean)
     */
    public void clear() {
        clear(true);
    }

    /**
     * Clears the display and sets the cursor position to the top-left of the
     * display if argument is true
     * @param replaceCursor - if the cursor should be moved to the top-left
     */
    public void clear(boolean replaceCursor) {
        controlSequence(CLEAR_DISP, ALL);
        if(replaceCursor)
            cursorPos(1, 1);
    }

    /**
     * Clears the display from the cursor's position to the end of the display.
     */
    public void clearFrom() {
        controlSequence(CLEAR_DISP, FROM);
    }

    /**
     * Clears the display from the begin ning to the cursor's position.
     */
    public void clearTo() {
        controlSequence(CLEAR_DISP, TO);
    }

    /**
     * Clears the whole line of the cursor's position. And move the
     * cursor the beginning of the line.
     * @see #clearLine(boolean)
     */
    public void clearLine() {
        clearLine(true);
    }

    /**
     * Clears the whole line and replace the cursor at the beginning of the line
     * if passed argument is true.
     * @param replaceCursor - if cursor should be moved to the beginning of the line
     */
    public void clearLine(boolean replaceCursor) {
        controlSequence(CLEAR_LINE, ALL);
        if(replaceCursor)
            cursorPos(1);
    }

    /**
     * Clears a line from the cursor's position to its end
     */
    public void clearLineFrom() {
        controlSequence(CLEAR_LINE, FROM);
    }

    /**
     * Clears a line from the the beginning to the cursor's position
     */
    public void clearLineTo() {
        controlSequence(CLEAR_LINE, TO);
    }

    public void getCursorPos() {
        controlSequence(DSR, GET_CURSOR_POS);
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
