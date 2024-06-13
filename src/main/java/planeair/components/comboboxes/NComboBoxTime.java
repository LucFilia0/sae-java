package planeair.components.comboboxes;

// import SWING components
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Color;
// import AWT components
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;

import planeair.App;
import planeair.components.comboboxes.renders.NTimeComboBoxRenderer;

public class NComboBoxTime extends JComboBox<Integer> {

    /**
     * Render for ComboBoxs, add 0 before 0 to 9 number (stay the 2 number composition)
     */
    NTimeComboBoxRenderer render ;
    
    public NComboBoxTime(int time){

        initComponents();
        render = new NTimeComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(
                javax.swing.JList<? extends Integer> list, 
                Integer value, 
                int index, 
                boolean isSelected, 
                boolean cellHasFocus) {

                JLabel cell = (JLabel)super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus) ;
                
                cell.setForeground(Color.BLACK) ;
                return cell ;
            }
        } ;

        this.setRenderer(render);

        for(int i = 0 ; i <= time ; i++){
            this.addItem(i);
        }

        this.setPreferredSize(new Dimension(50,35));
        this.setFont(new Font("Arial", Font.BOLD, 18)) ;
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
