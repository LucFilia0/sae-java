package planeair.components.time;

// import SWING components
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import planeair.App;
import planeair.util.NTime;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;

// import AWT components
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

// import LAYOUT
import java.awt.FlowLayout;
import java.awt.GridLayout;


public class NTimePanelApp extends JPanel {

    /**
     * Variable for HOUR representation ComboBox
     */
    public static final int HOUR = 23;

    /**
     * Variable for MINUTES representation ComboBox
     */
    public static final int MIN = 59;


    /**
     * Layout Panel For hour's panel, link to the GridLayout of header
     */
    private JPanel hourPanelComboBox = new JPanel(new FlowLayout(FlowLayout.CENTER));
    /**
     * JComboBox for choose hour
     */
    private NComboBoxTime hourChoice = new NComboBoxTime(HOUR);
    /**
     * Label with " : " between hour ComboBox and minutes ComboBox
     */
    private JLabel betweenTime = new JLabel(" : ");
    /**
     * JComboBox for choose minutes
     */
    private NComboBoxTime minChoice = new NComboBoxTime(MIN);
    
    /**
     * Panel for the time's Slider
     * FlowLayout at CENTER
     */
    private JPanel hourSliderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    /**
     * Slider for Hour 
     * Location : Top of the center of the frame, below the comboBox
     */
    private JSlider sliderTime = new JSlider();
    /**
     * Icon for the playing button
     */
    private Icon iconPlay = new ImageIcon("./icons/play.png");
    /**
     * Button for playing the simulation 
     * Location : right to the slider
     */
    private JButton buttonPlay = new JButton(iconPlay);

    public NTimePanelApp(){

        this.setLayout(new GridLayout(11,1));

        sliderTime.setOpaque(false);
        hourSliderPanel.setOpaque(false);
        hourPanelComboBox.setOpaque(false);
        this.setOpaque(false);

        this.betweenTime.setFont(App.KINDABOLD);
    
        //SLIDER
        sliderTime.setMinimum(0);
        sliderTime.setMaximum(1439); // 24 * 60 - 1 : Nb of minutes in one day
        sliderTime.setPreferredSize(new Dimension(300,20));
        sliderTime.setValue(0000);
        //BUTTON PLAY
        buttonPlay.setBackground(App.KINDAYELLOW);
        buttonPlay.setBorderPainted(false);
        buttonPlay.setPreferredSize(new Dimension(35,35));
    }

    /**
     * Method adding components on the Panel
     */
    public void addComponents(){
        //Hour COMBOBOX
        hourPanelComboBox.add(hourChoice);
        hourPanelComboBox.add(betweenTime);
        hourPanelComboBox.add(minChoice);


        //TIME SLIDER
        hourSliderPanel.add(sliderTime);
        hourSliderPanel.add(buttonPlay);

        //BODY CENTER
        this.add(hourPanelComboBox);
        this.add(hourSliderPanel);

        this.setVisible(true);
    }

    /**
     * Method adding events of the JFrame
     */
    public void addEvents(){
        
        hourChoice.addActionListener((ActionEvent e) -> {
            
                int hour = (int)hourChoice.getSelectedItem();
                int newValSlid = (sliderTime.getValue()%60) + (hour*60);
                sliderTime.setValue(newValSlid);
            });
   
        minChoice.addActionListener((ActionEvent e) -> {
                int min = (int)minChoice.getSelectedItem();
                int newValSlid = (sliderTime.getValue()/60)*60 + min%60;
                sliderTime.setValue(newValSlid);
        });

        sliderTime.addChangeListener((ChangeEvent e) -> {
            int time = sliderTime.getValue();
            int hour = (time/60);
            int minutes = (time%60);
            hourChoice.setSelectedItem(hour);
            minChoice.setSelectedItem(minutes);
            ;
         });  

        buttonPlay.addActionListener(e -> {
            System.out.println(this.getSelectedTime());
        });
    }

    /**
     * //
     * @return
     */
    public NTime getSelectedTime() {
        int value = this.sliderTime.getValue();
        return new NTime(value/60, value%60);
    }
    
}
