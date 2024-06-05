package planeair.components.imports.buttons;

// import SWING components
import javax.swing.JButton;

// Import of AWT composants
import java.awt.Color;
import java.awt.Dimension;


public class NButtonConfirmOrReturnImportApp extends JButton {

    public NButtonConfirmOrReturnImportApp(String title){

        this.setText(title);
        this.setForeground(Color.WHITE);
        this.setPreferredSize(new Dimension(80,40));
        this.setBackground(Color.BLACK);
    } 
}
