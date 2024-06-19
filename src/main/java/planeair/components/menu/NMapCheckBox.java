package planeair.components.menu;


//#region IMPORT
    //#region PLANEAIR
    import planeair.App;
    //#endregion

    //#region Swing
    import javax.swing.JCheckBox;
    //#endregion
//#endregion

/**
 * Default CheckBox style for MAP MENU 
 * 
 * @author GIRAUD Nila
 */
public class NMapCheckBox extends JCheckBox {

    //#region CONSTRUCTOR
    /**
     * Constructor of NMapCheckBox
     * @param text String for checkBox text
     */
    public NMapCheckBox(String text){
        this.setText(text);
        this.setBackground(App.KINDAYELLOW);
        this.setFont(App.KINDACHEKBOX);
    }
    //#endregion
   
    
}

