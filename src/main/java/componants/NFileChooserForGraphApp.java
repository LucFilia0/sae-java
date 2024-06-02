package composants;

import java.io.File;

//import swing componant
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NFileChooserForGraphApp {

    /**
     * A variable for stock the file that is import
     */
    private File file = null;
    /**
     * This boolean is false if the code decide that it cannot import the file
     * else is true
     */
    private boolean isSelected = false;

    /**
     * The filechooser for the file 
     * He will be different 
     * Depends of the fileType
     */
    JFileChooser chooser = new JFileChooser();

    /**
     * Constructor of NFileChooserForGraphApp
     * @param fileType know if it's an aeroport.txt, vol-test.csv or graphe-test.txt import
     */
    NFileChooserForGraphApp(int fileType){

        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichier CSV (*.csv)", "csv"));
        chooser.setDialogTitle("Enregistrer sous");
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        chooser.setApproveButtonText("Ouvrir");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setMultiSelectionEnabled(false);
        int result = chooser.showSaveDialog(null);
        
        if(result == JFileChooser.APPROVE_OPTION){
            file = chooser.getSelectedFile();
            //LUC IMPORTATION
            if(!isSelected){
                JOptionPane.showMessageDialog(null, "Le fichier importé ne corresponds pas au type demandé", "Erreur d'importation", JOptionPane.ERROR_MESSAGE);
            }
            else{
                // est ce qu'on met une message de succes ?
            }
            
        }else if(result == JFileChooser.ERROR_OPTION){
            JOptionPane.showMessageDialog(null, "Erreur d'importation", "Erreur", JOptionPane.ERROR_MESSAGE);
        }


    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }



    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean is_selected) {
        this.isSelected = is_selected;
    }

    
}
