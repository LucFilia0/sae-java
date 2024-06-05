package planeair.components.time;

// import SWING components
import javax.swing.JComboBox;

// import AWT components
import java.awt.Dimension;
import java.awt.Font;

import planeair.App;

public class NComboBoxTime extends JComboBox<Integer> {

    /**
     * Render for ComboBoxs, add 0 before 0 to 9 number (stay the 2 number composition)
     */
    RenderComboxTime render = new RenderComboxTime();
    
    public NComboBoxTime(int time){

        this.setRenderer(render);

        for(int i = 0 ; i <= time ; i++){
            this.addItem(i);
        }
        this.setBackground(App.KINDAYELLOW);
        this.setPreferredSize(new Dimension(50,35));
        this.setFont(new Font("Arial", Font.CENTER_BASELINE, 16));    

        this.setRenderer(render);

    }
    
    
}
