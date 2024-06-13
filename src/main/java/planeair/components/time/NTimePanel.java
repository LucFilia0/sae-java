package planeair.components.time;

// import SWING components
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

import planeair.App;
import planeair.components.comboboxes.NComboBoxTime;
import planeair.graph.graphtype.FlightsIntersectionGraph;
import planeair.util.NTime;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;

// import AWT components
import java.awt.event.ActionEvent;
import java.lang.Thread;
import java.awt.Dimension;

// import LAYOUT
import java.awt.FlowLayout;
import java.awt.GridLayout;




public class NTimePanel extends JPanel {

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
    private NSliderTime sliderTime = new NSliderTime();
    /**
     * Icon for the playing button (play)
     */
    private Icon iconPlay = new ImageIcon("./icons/play.png");
    /**
     * Icon for the playing button (pause)
     */
    private Icon iconPause = new ImageIcon("./icons/pause.png");
    /**
     * Button for playing the simulation 
     * Location : right to the slider
     */
    private JButton playButton = new JButton(iconPlay);

    /**
     * The App whichh contains the NTimePanelApp
     */
    private App app;

    /**
     * The boolean which says if the simulation is currently playing (if the Flights are currenly moving)
     */
    private boolean simulationPlaying;

    /**
     * The Thread which will run the simulation
     */
    private Thread simulation;

    private JPanel container;

    public NTimePanel(App app) {

        this.app = app;
        this.simulationPlaying = false;

        //this.setLayout(new GridLayout(11,1));
        this.setLayout(new FlowLayout());

        this.container = new JPanel();
        this.container.setLayout(new GridLayout(2, 1));

        hourSliderPanel.setOpaque(false);
        hourPanelComboBox.setOpaque(false);

        this.setOpaque(false);
        this.container.setOpaque(false);

        this.betweenTime.setFont(App.KINDABOLD);

        //BUTTON PLAY
        playButton.setBackground(App.KINDAYELLOW);
        playButton.setBorderPainted(false);
        playButton.setPreferredSize(new Dimension(35,35));

        this.addComponents();
        this.addEvents();
    }

    /**
     * Method adding components on the Panel
     */
    private void addComponents(){
        //Hour COMBOBOX
        hourPanelComboBox.add(hourChoice);
        hourPanelComboBox.add(betweenTime);
        hourPanelComboBox.add(minChoice);


        //TIME SLIDER
        hourSliderPanel.add(sliderTime);
        hourSliderPanel.add(playButton);

        //BODY CENTER
        container.add(hourPanelComboBox);
        container.add(hourSliderPanel);

        this.add(this.container);

        this.setVisible(true);
    }

    /**
     * Method adding events of the JFrame
     */
    private void addEvents(){
        
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

            // Changes the value of the comboboxes
            int time = sliderTime.getValue();
            int hour = (time/60);
            int minutes = (time%60);
            hourChoice.setSelectedItem(hour);
            minChoice.setSelectedItem(minutes);
            
            // Paints the Flights on the Map, at the selected NTime
            if(this.app.getGraph() != null && this.app.getGraph() instanceof FlightsIntersectionGraph)
                this.app.getMainScreen().getMap().paintFlightsAtTime(getSelectedTime(), (FlightsIntersectionGraph)this.app.getGraph());
         });

        playButton.addActionListener(e -> {

            if(this.simulationPlaying) {
                // Stops the simulation
                this.simulationPlaying = false;
                this.playButton.setIcon(this.iconPlay);
                this.sliderTime.setEnabled(true);
            }else {
                // Starts the simulation
                this.simulationPlaying = true;
                this.playButton.setIcon(this.iconPause);
                this.sliderTime.setEnabled(false);

                this.simulation = new Thread() {
                    @Override
                    public void run() {
                        while(simulationPlaying) {
                            sliderTime.setValue(sliderTime.getValue() + 2); // Plus 2mn in the simulation every second in the real life
                            try {
                                Thread.sleep(1000);
                            }catch(InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };

                this.simulation.start();
            }
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

    public boolean isSimulationPlaying() {
        return this.simulationPlaying;
    }
    
}
