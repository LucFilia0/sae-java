package planeair.components.imports;

//import SWING composants
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//Import AWT composants
import java.awt.Dimension;

//import Layout
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;

// Import PlaneAIR
import planeair.App;

/**
 * This class create a panel for information of the App and importantion of files necesseray
 * It's the first panel you see when you open the App
 * 
 * @author GIRAUD Nila
 */
public class NImportScreen extends JPanel {

    //STRUCT

    /**
     * Panel header, explication of the App
     */
    private JPanel header;

    /**
     * Panel body of the Frame
     * Center bouttonsImport panel
     */
    private JPanel body;

    //HEADER

    /**
     * Description of the App (part 1)
     * Location : in the header of the panel
     */
    private JLabel descriptionApp1;
    /**
     * Description of the App (part 2)
     * Location : in the header of the panel
     */
    private JLabel descriptionApp2;

    /**
     * Import Button Panel
     * Location : In the center of the panel (with gridBagLayouts)
     */
    private NButtonImportPanelApp buttonImport;

    /**
     * The current App
     */
    private App app;

    /**
     * Creates a new NImportScreen, which is in charge of the importation of data, from Files to the data structures in the App class
     * @param app ({@link planeair.App App}) - The current App
     */
    public NImportScreen(App app){

        this.app = app;

        this.initComponents();
        this.placeComponents();
        this.addComponents();
    }

    /**
     * Creates all the components of the NImportScreen
     * 
     * @author Luc le Manifik
     */
    private void initComponents() {

        this.header = new JPanel();
        this.body = new JPanel(new GridBagLayout());

        header.setBackground(App.KINDAYELLOW);
        body.setBackground(App.KINDAYELLOW);

        this.descriptionApp1 = new JLabel("<html><i> Information</i> : Cette application a pour but de simuler</html>", SwingConstants.CENTER );
        this.descriptionApp2 = new JLabel("<html>une journée de vols afin de détecter de possibles collisions</html>", SwingConstants.CENTER);

        descriptionApp1.setFont(App.KINDABOLD);
        descriptionApp2.setFont(App.KINDABOLD);

        buttonImport = new NButtonImportPanelApp(this.app);
    }

    /**
     * Places all the components of the NImportScreen
     */
    private void placeComponents() {

        this.setLayout(new BorderLayout());
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
    }

    /**
     * This method adding componant to the panel
     */
    private void addComponents(){

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