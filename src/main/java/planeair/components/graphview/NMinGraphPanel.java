package planeair.components.graphview;

//#region IMPORT
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

import planeair.App;
import planeair.graph.graphutil.PanelCreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
//#endregion

/**
 * This class create a Panel for see the graph 
 * Their is also a button expand for see the graph in a other JFrame (it's bigger)
 * 
 * @author GIRAUD Nila
 */
public class NMinGraphPanel extends JPanel {

    //#region  INSTANTIALISATION AND INITIALISATION
        //#region STRUCTURE
        /**
         * BoxLayout in Y_axis
         * 2 components --> the graph and the extend graph button
         */
        private JPanel layoutNMinGraphPanel = new JPanel();
        //#endregion

        //#region FIRST COMPONENT 
        /**
         * Panel of the graph representation
         * BorderLayout CENTER
         */
        private JPanel layoutGraphPanel = new JPanel(new BorderLayout());
        //#endregion

        //#region SECOND COMPONENT 
        /**
         * Expand Button for graph
         * Open a new Frame with the graph and this information
         * Location : in the panel Mingraph --> need here for Events
         */
        private JButton buttonAgr = new JButton("AGRANDIR");

        /**
         * Frame that will be spawned when the expend button is pressed
         * Contains a bigger view on the graph
         */
        private JFrame maxGraphFrame ;

        /**
         * Panel used to center the button
         */
        private JPanel buttonCenter = new JPanel(new GridLayout(1,1));
        //#endregion
    //#endregion
    
    //#region CONSTRUCTOR
    /**
     * Constructor of NMinGraphPanelApp
     */
    public NMinGraphPanel() {
        this.setBackground(App.KINDAYELLOW);  
        //STRUCT
        
        //GridPanel Background Color (YELLOW)
        layoutNMinGraphPanel.setBackground(App.KINDAYELLOW);
        layoutNMinGraphPanel.setLayout(new BoxLayout(layoutNMinGraphPanel, BoxLayout.Y_AXIS));

        /*FIRST COMPONENT */
        
        this.setMaximumSize(new Dimension(350,400));
        if (App.app.getGraphRenderer() == null) {
            layoutGraphPanel = new NSkullPanel() ;
            layoutGraphPanel.setLayout(new BorderLayout());
        }
        layoutGraphPanel.setPreferredSize(new Dimension(325,325));
         
        /*SECOND COMPONENT */

        buttonAgr.setBackground(Color.BLACK);
        buttonAgr.setForeground(Color.WHITE);
        buttonAgr.setFont(new Font("Arial", Font.BOLD, 20));
        buttonAgr.setBorderPainted(false);

        buttonCenter.setBackground(App.KINDAYELLOW);

        //#region ADD

        /*FIRST COMPONENT */
        layoutNMinGraphPanel.add(layoutGraphPanel);

        /*SECOND COMPONENT */
        layoutNMinGraphPanel.add(buttonAgr);

        this.add(layoutNMinGraphPanel);
        this.initListeners() ;

        //#endregion
    }
    //#endregion

    //#region ADD GRAPH
    /**
     * Puts the graph in the panel
     * @param graphRenderer Renderer which will take care of rendering the graph
     * contains the panel containing the view of the graph
     */
    public void addGraphToPanel(PanelCreator graphRenderer) {
        layoutGraphPanel.removeAll() ;
        JPanel panel ;
        if (graphRenderer != null) {
            panel = graphRenderer.getViewPanel() ;
        }
        else {
            panel = new NGraphNotHerePanel() ;
        }
        this.layoutGraphPanel.removeAll() ;
        this.layoutGraphPanel.add(panel, BorderLayout.CENTER) ;
        buttonCenter.setBackground(App.KINDAYELLOW);
    }
    //#endregion

    //#region REMOVE GRAPH
    /**
     * Clears the MinGraph from the panel
     * 
     * @author Luc le Manifik
     */
    public void removeGraphFromPanel() {
        this.layoutGraphPanel.removeAll();
        App.app.setGraph(null);
    }
    //#endregion

    //#region ADD
    public void addComponents(){

        /*FIRST COMPONENT */
        layoutNMinGraphPanel.add(Box.createRigidArea(new Dimension(10,5)));
        layoutNMinGraphPanel.add(layoutGraphPanel);
        layoutNMinGraphPanel.add(Box.createRigidArea(new Dimension(10,10)));

        /*SECOND COMPONENT */
        buttonCenter.add(buttonAgr);
        layoutNMinGraphPanel.add(buttonCenter);
        layoutNMinGraphPanel.add(Box.createRigidArea(new Dimension(10,10)));

        this.add(layoutNMinGraphPanel);
   }
   //#endregion

    private void initListeners() {
        buttonAgr.addActionListener((ActionEvent e) -> {
            buttonAgr.setEnabled(false) ;
            maxGraphFrame = new NMaxGraphFrame(App.app.getGraphRenderer());
            maxGraphFrame.setVisible(true) ;
            addGraphToPanel(null) ;
        });
    }

    public JButton getButtonAgr() {
        return this.buttonAgr ;
    }

    /**
     * Draws a centered String in a Rectangle
     * I didn't make this, I yonked it from StackOverflow
     * Here's the original question 
     * https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java 
     * 
     * @param g Graphic object used to paint the string
     * @param r Bounds of the String
     * @param s The String we are painting
     * @param font Font used to draw it
     * 
     * @author Gilbert Le Blanc
     */
    public static void centerString(Graphics g, Rectangle r, String s, 
            Font font) {
        FontRenderContext frc = new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;

        g.setFont(font);
        g.drawString(s, r.x + a, r.y + b);
    }

}
