package planeair.components.comboboxes;

//#region IMPORT
    //#region .SWING
    import javax.swing.JComboBox;
    import javax.swing.JLabel;
    //#endregion
    //#region .AWT
    // import AWT components
    import java.awt.Dimension;
    import java.awt.Font;
    import java.awt.Component;
    import java.awt.Color;
    //#endregion
    //#region .APP
    import planeair.App;
    //#endregion
    //#region .COMPONENTS.COMBOBOXES.RENDERS
    import planeair.components.comboboxes.renders.NTimeComboBoxRenderer;
    //#endregion
//#endregion

/**
 * This class is for create a custom JComboxBox 
 * Is a default one --> Time for Map and time for Safety security
 * 
 * @author GIRAUD Nila
 */
public class NComboBoxTime extends JComboBox<Integer> {

    //#region INSTANTIALISATION AND INITIALISATION 
    /**
     * Render for ComboBoxs, add 0 before 0 to 9 number (stay the 2 number composition)
     */
    NTimeComboBoxRenderer render ;
    //#endregion
    
    //#region DEFAULT CONSTRUCTOR
    /**
     * Default Constructor 
     * @param time know if you take minutes or hours --> [0 : time]
     */
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
    //#endregion

    //#region CONSTRUCTOR SIZE
    /**
     * Constructor with custom size of the ComboBox
     * @param time know if you take minutes or hours --> [0 : time]
     * @param size of type Dimension
     */
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
    //#endregion

    //#region INIT
    /**
     * Init default appearance
     */
    private void initComponents(){
        this.setFont(new Font("Arial", Font.CENTER_BASELINE, 16)); 
    }
    //#endregion
    
    
}
