package planeair.composants;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NFileChooserForGraphApp {

    private File file = null;
    private boolean isSelected = false;

    NFileChooserForGraphApp(int fileType){

        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fichier TXT (*.txt)", "txt"));
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
