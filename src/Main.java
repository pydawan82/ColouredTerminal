import java.io.IOException;

import com.pydawan.rainbowTerm.Color;
import com.pydawan.rainbowTerm.TablePrinter;
import com.pydawan.rainbowTerm.TerminalPrinter;


public class Main {

    public static void main(String ... args) throws IOException  {
        try(TerminalPrinter term = new TerminalPrinter(System.out)) {
            term.foreground(Color.DARK_RED).bold(true);
            term.clear();
           
            String[] colNames = {"Name", "First Name", "Born", "Address"};
            int[] colWidths = {15, 15, 10, 20};
            String[][] table = {
                {"Dewi", "Brittle", "08/02/2000", "10 Monk Street 7534 Clay"},
                {"Lowe", "Brittle", "08/02/2000", "Clay"},
                {"Michel", "Dumas", "07/01/1959", "43 Rue de la Cannebière 54060 Montréal"},
            };

            TablePrinter.print(term, colWidths, colNames, table);
            term.println();
        }
    }
}
