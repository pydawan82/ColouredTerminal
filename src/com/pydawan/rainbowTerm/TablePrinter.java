package com.pydawan.rainbowTerm;

public final class TablePrinter {
    
    public static void print(TerminalPrinter terminal, int[] colWidths, String[] colNames, String[][] table) {
        if(colWidths.length != colNames.length)
            throw new IndexOutOfBoundsException("Arrays should be of the same width");

        int width = colWidths.length;

        /*
         * Prints headers
         */
        for(int i=0; i<width; i++) {
            String colName = colNames[i];
            colWidths[i] = Math.max(colWidths[i], colName.length());
            int colWidth = colWidths[i];

            terminal.print(colName);
            terminal.cursorForward(colWidth-colName.length()+1);
        }

        terminal.println();

        /*
         * Prints underlines
         */
        for(int i=0; i<width; i++) {
            String colName = colNames[i];
            int colWidth = colWidths[i];

            terminal.print("-".repeat(colName.length()));
            terminal.cursorForward(colWidth-colName.length()+1);
        }

        terminal.println();

        int height = table.length;

        for(int i=0; i<height; i++) {
            String[] row = table[i];
            if(row.length != width)
                throw new IndexOutOfBoundsException("Arrays must be of the same width");

            int rowHeight = 1;
            int x = 1;
            for(int j=0; j<width; j++) {
                int y = 0;
                String value = row[j];
                int colWidth = colWidths[j];
                int pos = 0;

                while(true) {
                    terminal.print(value.substring(pos, Math.min(pos+colWidth, value.length())));
                    pos+=colWidth;
                    if(pos>=value.length())
                        break;

                    terminal.cursorDown(1);
                    terminal.cursorPos(x);
                }

                x+=colWidth+1;

                rowHeight = Math.max(rowHeight, y+1);
                if(y != 0)
                    terminal.cursorUp(y);
                terminal.cursorPos(x);
            }

            terminal.cursorDown(rowHeight);
            terminal.cursorPos(0);
        }
    }

    
    public static void print(int[] colWidths, String[] colNames, Iterable<String[]> table) {
        if(colWidths.length != colNames.length)
            throw new IndexOutOfBoundsException("Arrays should be of the same length");

    }
}
