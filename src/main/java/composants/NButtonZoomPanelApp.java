package composants;
/**
 * Import of SWING composants
 */
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Import Layout
 */
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;




public class NButtonZoomPanelApp extends JPanel {

    /**
     * 2 button
     * 1) the "+" of the ZOOM
     * 2) the "-" of the ZOOM
     */
    JButton ButtonPlus = new JButton("+");
    JButton ButtonMinus = new JButton("−");

    /**
     * Panel for ButtonPlus + ButtonMinus
     * GridLayout
     * nb LINE : 2
     * nb COLUMN : 1
     */
    JPanel GridButtonPanel = new JPanel(new GridLayout(2,1));

    public NButtonZoomPanelApp(){

        /**
         * Set Panel's Background color (BLACK)
         */
        this.setBackground(Color.BLACK);


        /**
         * Having YELLOW background according to the Panel GridButtonPanel
         * Just see the caracter
         */
        ButtonPlus.setBackground(Color.BLACK);
        ButtonPlus.setBorderPainted(false);
        
        ButtonMinus.setBackground(Color.BLACK);
        ButtonMinus.setBorderPainted(false);

        /**
         * Text appareance
         * Name : Arial
         * Font : Bold
         * Size : 30
         */
        ButtonPlus.setFont(new Font("Arial", Font.BOLD, 30));
        ButtonPlus.setForeground(Color.WHITE);
        ButtonMinus.setFont(new Font("Arial", Font.BOLD, 30));
        ButtonMinus.setForeground(Color.WHITE);

        /**
         * Add button of Grid Panel
         * 1) +
         * 2) -
         */
        GridButtonPanel.add(ButtonPlus);
        GridButtonPanel.add(ButtonMinus);

        this.add(GridButtonPanel);


    }
    
}
