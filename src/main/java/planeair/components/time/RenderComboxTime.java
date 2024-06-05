package planeair.components.time;

// import SWING components
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

// import AWT components
import java.awt.Component;

public class RenderComboxTime implements ListCellRenderer<Integer>  {

    public Component getListCellRendererComponent(
            JList<? extends Integer> list,
            Integer value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        JLabel cellule = new JLabel() ;
        
        if (value < 10){
            cellule.setText("0" + String.valueOf(value));
        }
        else{
            cellule.setText(String.valueOf(value));
        }
        return cellule;
    }

}
