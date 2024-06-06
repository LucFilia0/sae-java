package planeair.components.imports;

//import SWING composants
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//Import AWT composants
import java.awt.Dimension;
import java.awt.Font;

//import Layout
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;

import planeair.App;

/**
 * This class create a panel for information of the App and importantion of files necesseray
 * It's the first panel you see when you open the App
 * 
 * @author GIRAUD Nila
 */
public class NImportPanelApp extends JPanel {

    //STRUCT

    /**
     * Panel header, explication of the App
     */
    private JPanel header = new JPanel();

    /**
     * Panel body of the Frame
     * Center bouttonsImport panel
     */
    private JPanel body = new JPanel(new GridBagLayout());

    //HEADER
    /**
     * Description of the App (part 1)
     * Location : in the header of the panel
     */
    private JLabel descriptionApp1 = new JLabel("<html><i> Information</i> : Cette application a pour but de simuler</html>", SwingConstants.CENTER );
    /**
     * Description of the App (part 2)
     * Location : in the header of the panel
     */
    private JLabel descriptionApp2 = new JLabel("<html>une journée de vols pour detecter de possibles collisions</html>", SwingConstants.CENTER);

    /**
     * Import Button Panel
     * Location : In the center of the panel (with gridBagLayouts)
     */
    private NButtonImportPanelApp buttonImport;

    // /**
    //  * Label for logo
    //  */
    // JLabel logoApp = new JLabel(new ImageIcon("./src/main/java/Graph France.png") );
    
    
    public NImportPanelApp(App app){

        this.setLayout(new BorderLayout());
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));

        buttonImport = new NButtonImportPanelApp(app) ;

        buttonImport.addComponents();
        buttonImport.addEvents();

        descriptionApp1.setFont(new Font("Arial", Font.BOLD, 20));
        descriptionApp2.setFont(new Font("Arial", Font.BOLD, 20));

        header.setBackground(App.KINDAYELLOW);
        body.setBackground(App.KINDAYELLOW);

        
    }

    public void addComponents(){

        //HEADER
        header.add(Box.createRigidArea(new Dimension(0, 30)));
        header.add(descriptionApp1);
        header.add(descriptionApp2);

        // BODY
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        body.add(buttonImport,gbc);

        //ADD to CONTENT PANE
        this.add(header, BorderLayout.NORTH);
        this.add(body, BorderLayout.CENTER);
    }
}