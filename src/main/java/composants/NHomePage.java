package composants;




import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
//import SWING composants
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



//Import AWT composants
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.Font;

//import Layout
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;






public class NHomePage extends JFrame {

    //STRUCT

    /**
     * Panel header, explication of the App
     */
    JPanel header = new JPanel();

    /**
     * Panel body of the Frame
     * Where buttons are
     */
    JPanel body = new JPanel(new GridBagLayout());

    //HEADER
    /**
     * Description of the App (part 1)
     */
    JLabel descriptionApp1 = new JLabel("<html><i> Information</i> : Cette application a pour but de simuler</html>", SwingConstants.CENTER );
    /**
     * Description of the App (part 2)
     */
    JLabel descriptionApp2 = new JLabel("<html>une journée de vols pour detecter de possibles collisions</html>", SwingConstants.CENTER);

    /**
     * Button Importation
     * vols.csv
     */
    JButton buttonVols = new JButton("vols.csv");
    /**
     * Button Importation
     * aeroport.csv
     */
    JButton buttonAeroport = new JButton("aeroport.csv");
    /**
     * Button Importation
     * graph.csv
     */
    JButton buttonGraph = new JButton("Graphes.csv");

     /**
     * Contain the panel of MinGraph + InfoGraph + ButtonImport
     * Add Border
     */
    JPanel spaceBorderButtonImport = new JPanel();

    /**
     * Contain an Empty Panel and spaceBorderButtonImport
     * Forcing bottom
     */
    JPanel importButtonLRightBottom = new JPanel();

    /**
     * Import Button
     */
    NButtonImportPanelApp buttonImport = new NButtonImportPanelApp(this) ;

    // /**
    //  * Label for logo
    //  */
    // JLabel logoApp = new JLabel(new ImageIcon("./src/main/java/Graph France.png") );

    NPrincipaleFrameApp framePrinc = new NPrincipaleFrameApp(this);

    public NHomePage(){

        this.setTitle("Plane AIR | PAGE D'IMPORTATION");
        this.setSize(900,600);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));


        descriptionApp1.setFont(new Font("Arial", Font.BOLD, 20));
        descriptionApp2.setFont(new Font("Arial", Font.BOLD, 20));

        buttonImport.addEvents();

        framePrinc.addComposants();
        framePrinc.addEvents();

        //Color of the Frame (when it's menu)
        header.setBackground(Color.YELLOW);
        body.setBackground(Color.YELLOW);

        // logoApp.setOpaque(true);
        // logoApp.setBackground(Color.red);


       /* NPrincipaleFrameApp framePrinc = new NPrincipaleFrameApp(this);
        framePrinc.addComposants();
        framePrinc.addEvents();

        this.getContentPane().add(descriptionApp, BorderLayout.NORTH);
        this.add(framePrinc, BorderLayout.CENTER);
        this.pack();
        this.setLocationRelativeTo(null);*/

    }

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
        // buttonImport.setAlignmentX(CENTER_ALIGNMENT);
        // buttonImport.setAlignmentY(CENTER_ALIGNMENT);
        

        //ADD to CONTENT PANE

        this.getContentPane().add(header, BorderLayout.NORTH);
        this.getContentPane().add(body, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
    }

    public void addBodyPanelPrinc(JPanel buttons){
        this.setTitle("Plane AIR");
        NPrincipaleFrameApp framePrincAf = new NPrincipaleFrameApp(this);
        framePrincAf.addComposants();
        framePrincAf.addEvents();
        body.setLayout(new GridLayout(1,1));
        body.removeAll();
        this.remove(header);
        body.add(framePrincAf);
        this.setVisible(true);
    }

    public void removeBodyPanelPrinc(NPrincipaleFrameApp framePrinc){
        this.setTitle("Plane AIR | PAGE D'IMPORTATION");

        NButtonImportPanelApp buttonImportAf = new NButtonImportPanelApp(this) ;

        buttonImportAf.addEvents();

        body.removeAll();
        this.remove(body);
        header.add(descriptionApp1);
        header.add(descriptionApp2);

        body.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        body.add(buttonImportAf,gbc);
        this.add(body, BorderLayout.CENTER);

        this.add(header, BorderLayout.NORTH);
        this.setVisible(true);

    }

    /*buttonAeroport.addActionListener((ActionEvent e) -> {

        importButtonLRightBottom.removeAll();
        spaceBorderButtonImport.removeAll();
        body.remove(importButtonLRightBottom);

        empty2.setPreferredSize(new Dimension(250,200));
        importButtonLRightBottom.add(empty2);
        test = new NButtonImportPanelApp(2,buttonAeroport,buttonVols);
        spaceBorderButtonImport.add(test);
        importButtonLRightBottom.add(spaceBorderButtonImport);

        body.add(importButtonLRightBottom, BorderLayout.EAST);
        
        
        this.setVisible(true);

    } );

    buttonVols.addActionListener((ActionEvent e) -> {
        
        createInfoGraph();
        
    });*/
    








}