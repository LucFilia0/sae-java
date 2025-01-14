package planeair.components.time;

//#region IMPORT
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;

import planeair.App;
import planeair.components.comboboxes.NComboBoxTime;
import planeair.exceptions.InvalidTimeException;
import planeair.graph.graphtype.FlightsIntersectionGraph;
import planeair.util.NTime;

import java.awt.event.ActionEvent;
import java.awt.Dimension;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.lang.Thread;

/**
 * A time Panel for JcomboxBoxs (hour/min) + the Slider (time) + PLAY button (simulation)
 * ActionListener for the simulation here
 * 
 * @author GIRAUD Nila
 */
public class NTimePanel extends JPanel {

    //#region  INSTANTIALISATION AND INITIALISATION

        //#region STATIC
        /**
         * CONSTANT for HOUR representation ComboBox
         */
        public static final int HOUR = 23;

        /**
         * CONSTANT for MINUTES representation ComboBox
         */
        public static final int MIN = 59;
        //#endregion

        //#region COMBOBOXS
        /**
         * Layout Panel For hour panel, link to the GridLayout of header
         */
        private JPanel hourPanelComboBox = new JPanel(new FlowLayout(FlowLayout.CENTER));
        /**
         * JComboBox for choosen hours
         */
        private NComboBoxTime hourChoice = new NComboBoxTime(HOUR);
        /**
         * Label with " : " between hour ComboBox and minutes ComboBox
         */
        private JLabel betweenTime = new JLabel(" : ");
        /**
         * JComboBox for choosen minutes
         */
        private NComboBoxTime minChoice = new NComboBoxTime(MIN);
        //#endregion
        
        //#region SLIDER
        /**
         * Panel for the time's Slider
         * FlowLayout at CENTER
         */
        private JPanel hourSliderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        /**
         * Slider for time 
         * Location : Top of the center of the frame, below the comboBox
         */
        private NSliderTime sliderTime = new NSliderTime();
        //#endregion

        //#region BUTTON SIMULATION
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
        //#endregion

    /**
     * The boolean which says if the simulation is currently playing (if the Flights are currenly moving)
     */
    private boolean simulationPlaying;

    /**
     * The Thread which will run the simulation
     */
    private Thread simulation;

    /**
     * the panel that contains everything
     */
    private JPanel container;
    //#endregion

    //#region CONSTRUCTOR
    /**
     * Constructor of NTimePANEL
     */
    public NTimePanel() {
        this.simulationPlaying = false;
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
    //#endregion

    //#region ADD

        //#region COMPONENTS
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
        //#endregion

        //#region EVENTS
        /**
         * Method adding events (Listener)
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
                try {
                    if(App.app.getGraph() != null && App.app.getGraph() instanceof FlightsIntersectionGraph)
                        App.app.getMainScreen().getMap().paintFlightsAtTime(getSelectedTime(), (FlightsIntersectionGraph)App.app.getGraph());

                }

                catch (InvalidTimeException ite) {
                    System.err.println("how did you even do that\n" + ite) ;
                }
            });

            playButton.addActionListener(e -> {

                if(this.simulationPlaying) {
                    // Stops the simulation
                    this.simulationPlaying = false;
                    this.playButton.setIcon(this.iconPlay);
                }else {
                    // Starts the simulation
                    this.simulationPlaying = true;
                    this.playButton.setIcon(this.iconPause);

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
        //#endregion
   
        //#endregion

    //#region GETTER / SETTER
    /**
     * Returns the time selected on the slider as a NTime object
     * @return the time on the slider
     * @throws InvalidTimeException if there was a problem in the conversion
     */
    public NTime getSelectedTime() throws InvalidTimeException {
        int value = this.sliderTime.getValue();
        try {
            return new NTime(value/60, value%60);
        }
        catch (InvalidTimeException e) {
            throw e ;
        }
    }

    /**
     * @return {@code true} if the simulation is 
     * currently playing (so if the flights are moving) and
     * {@code false} else
     */
    public boolean isSimulationPlaying() {
        return this.simulationPlaying;
    }

    /**
     * @return the slider that changes the time
     */
    public NSliderTime getSliderTime() {
        return sliderTime;
    }
    //#endregion
    
}
