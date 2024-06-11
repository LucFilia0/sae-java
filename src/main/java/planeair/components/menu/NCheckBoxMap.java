package planeair.components.menu;



import planeair.App;

// import SWING componant
import javax.swing.JCheckBox;


public class NCheckBoxMap extends JCheckBox {


    public NCheckBoxMap(String text){
        this.setText(text);
        this.setBackground(App.KINDAYELLOW);
        this.setFont(App.KINDACHEKBOX);
    }
   
    
}

