package planeair.components.imports.buttons;

// import SWING components
import javax.swing.JButton;

// import AWT components
import java.awt.Color;
import java.awt.Dimension;

public class NButtonImportFileApp extends JButton {

    public NButtonImportFileApp(String title){

        this.setText(title);
        this.setForeground(Color.WHITE);
        this.setPreferredSize(new Dimension(200,125));
        this.setBackground(Color.BLACK);
    } 
}
