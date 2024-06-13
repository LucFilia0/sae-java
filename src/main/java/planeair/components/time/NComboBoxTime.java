package planeair.components.time;

// import SWING components
import javax.swing.JComboBox;

import java.awt.Color;
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

        initComponents();

        this.setRenderer(render);

        for(int i = 0 ; i <= time ; i++){
            this.addItem(i);
        }

        this.setPreferredSize(new Dimension(50,35));
        this.setBackground(App.KINDAYELLOW);
           

        this.setRenderer(render);

    }

    public NComboBoxTime(int time, Dimension size){

        initComponents();

        this.setRenderer(render);

        for(int i = 0 ; i <= time ; i++){
            this.addItem(i);
        }

        this.setPreferredSize(size);
        this.setBackground(Color.BLACK);
        this.setForeground(Color.WHITE);

        this.setRenderer(render);
           


    }


    private void initComponents(){
        this.setFont(new Font("Arial", Font.CENTER_BASELINE, 16)); 
    }
    
    
}
