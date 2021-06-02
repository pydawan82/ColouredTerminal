package terminal;

import static terminal.EscapeCodes.ESCAPE;

/**
 * A class to group control sequences
 */
final class ControlSequenceCodes {
    private ControlSequenceCodes() {}

    static final String CSI = ESCAPE+"[";

    static final String CURSOR_UP = "A";
    static final String CURSOR_DOWN = "B";
    static final String CURSOR_FORWARD = "C";
    static final String CURSOR_BACK = "D";
    static final String CURSOR_NEXT_LINE = "E";
    static final String CURSOR_PREVIOUS_LINE = "F";
    static final String CURSOR_H_POS = "G";
    static final String CURSOR_POS = "H";

    static final String CLEAR_DISP = "J";
    static final String CLEAR_LINE = "K";
    static final int FROM = 0;
    static final int TO = 1;
    static final int ALL = 2;

    static final String SCROLL_UP = "S";
    static final String SCROLL_DOWN = "T";

    static final String SGR = "m";

}
