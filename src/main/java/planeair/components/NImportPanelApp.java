package planeair.components;

//import SWING composants
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//Import AWT composants
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

//import Layout
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;

import planeair.ihm.Map;
import planeair.App;

public class NImportPanelApp extends JPanel {

    //STRUCT

    /**
     * Panel header, explication of the App
     */
    JPanel header = new JPanel();

    /**
     * Panel body of the Frame
     * Center bouttonsImport panel
     */
    JPanel body = new JPanel(new GridBagLayout());

    //HEADER
    /**
     * Description of the App (part 1)
     * Location : in the header of the panel
     */
    JLabel descriptionApp1 = new JLabel("<html><i> Information</i> : Cette application a pour but de simuler</html>", SwingConstants.CENTER );
    /**
     * Description of the App (part 2)
     * Location : in the header of the panel
     */
    JLabel descriptionApp2 = new JLabel("<html>une journée de vols pour detecter de possibles collisions</html>", SwingConstants.CENTER);

    /**
     * Import Button Panel
     * Location : In the center of the panel (with gridBagLayouts)
     */
    NButtonImportPanelApp buttonImport = new NButtonImportPanelApp(this) ;

    // /**
    //  * Label for logo
    //  */
    // JLabel logoApp = new JLabel(new ImageIcon("./src/main/java/Graph France.png") );
    
    
    public NImportPanelApp(App app){

        this.setLayout(new BorderLayout());
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        descriptionApp1.setFont(new Font("Arial", Font.BOLD, 20));
        descriptionApp2.setFont(new Font("Arial", Font.BOLD, 20));

        buttonImport.addEvents();

        header.setBackground(Color.YELLOW);
        body.setBackground(Color.YELLOW);

    }

    /**
     * This method adding componant to the panel
     */
    public void addComposants(){

        //HEADER
        // header.add(logoApp);
        header.add(Box.createRigidArea(new Dimension(0, 30)));
        header.add(descriptionApp1);
        header.add(descriptionApp2);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        body.add(buttonImport,gbc);
        gbc.gridx = 2;
        //ADD to CONTENT PANE

        this.add(header, BorderLayout.NORTH);
        this.add(body, BorderLayout.CENTER);
    }
}