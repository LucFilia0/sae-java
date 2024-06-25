package planeair.components.imports;

//#region IMPORTS
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import planeair.App;
import planeair.exceptions.InvalidFileFormatException;
//#endregion

/**
 * The customized file chooser, with all the correct filters, etc...
 * Used to chose Airports' File, Flights' File or TestGraphs' File
 * 
 * @author GIRAUD Nila - modified by Luc le Manifik
 */
public class NFileChooser extends JFileChooser {

    /**
     * Used to select an airport file
     */
    public static final int AIRPORT_FILE = 0;

    /**
     * Used to select a flight file
     */
    public static final int FLIGHT_FILE = 1;

    /**
     * Used to select a graph file
     */
    public static final int GRAPH_FILE = 12;

    //-- Attributes

    /**
     * A variable for stock the file that is import
     */
    private File file;

    /**
     * Constructor of NFileChooserForGraphApp
     * @param fileType know if it's an aeroport.txt, vol-test.csv or graphe-test.txt import
     */
    public NFileChooser(int fileType) throws InvalidFileFormatException {

        // Attributes settings
        this.file = null;
        this.setCurrentDirectory(new File(".")) ;

        // Filter
        FileNameExtensionFilter extensionFilter = null;
        switch(fileType) {
            case NFileChooser.AIRPORT_FILE :
                extensionFilter = new FileNameExtensionFilter("Fichier CSV (*.csv)", "csv");
                break;
            case NFileChooser.FLIGHT_FILE :
                extensionFilter = new FileNameExtensionFilter("Fichier CSV (*.csv)", "csv");
                break;
            case NFileChooser.GRAPH_FILE :
                extensionFilter = new FileNameExtensionFilter("Fichier TXT (*.txt)", "txt");
                break;
            default : throw new InvalidFileFormatException();
        }

        this.addChoosableFileFilter(extensionFilter);

        // Default settings for the NFileChooser
        this.setDialogTitle("Enregistrer sous");
        this.setDialogType(JFileChooser.SAVE_DIALOG);
        this.setApproveButtonText("Ouvrir");
        this.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.setMultiSelectionEnabled(false);
    }

    /**
     * Opens the JFileCHooser and imports the file chosen by the user.
     * Use "getFile" to use the chosen file
     * 
     * @author Luc le Manifik
     */
    public void userImportFile() throws InvalidFileFormatException {

        int result = this.showOpenDialog(App.app);
        if(result == JFileChooser.APPROVE_OPTION){
            this.file = this.getSelectedFile();
        }else if(result == JFileChooser.ERROR){
            throw new InvalidFileFormatException();
        }
    }

    /**
     * Get the file which the user want to import
     * @return file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Set the file which the user want to import
     * @param file the new file
     */
    public void setFile(File file) {
        this.file = file;
    }
}
