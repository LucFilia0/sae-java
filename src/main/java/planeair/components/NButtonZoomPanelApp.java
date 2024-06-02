package planeair.components;

//Import of SWING composants

import javax.swing.JButton;
import javax.swing.JPanel;

//Import Layout
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;

/**
 * Panel for zoom the map of the App
 */
public class NButtonZoomPanelApp extends JPanel {

    /**
     * button : +
     * Location : first in Y-axis
     */
    JButton ButtonPlus = new JButton("+");
    /**
     * button : -
     * Location : second in Y-axis
     */
    JButton ButtonMinus = new JButton("−");

    /**
     * Panel for ButtonPlus + ButtonMinus
     * GridLayout
     * nb LINE : 2
     * nb COLUMN : 1
     */
    JPanel GridButtonPanel = new JPanel(new GridLayout(2,1));

    public NButtonZoomPanelApp(){

        this.setBackground(Color.BLACK);

        ButtonPlus.setBackground(Color.BLACK);
        ButtonPlus.setBorderPainted(false);
        
        ButtonMinus.setBackground(Color.BLACK);
        ButtonMinus.setBorderPainted(false);

        //  * Text appareance
        //  * Name : Arial
        //  * Font : Bold
        //  * Size : 30
        ButtonPlus.setFont(new Font("Arial", Font.BOLD, 30));
        ButtonPlus.setForeground(Color.WHITE);
        ButtonMinus.setFont(new Font("Arial", Font.BOLD, 30));
        ButtonMinus.setForeground(Color.WHITE);

        GridButtonPanel.add(ButtonPlus);
        GridButtonPanel.add(ButtonMinus);

        this.add(GridButtonPanel);
    } 
}
