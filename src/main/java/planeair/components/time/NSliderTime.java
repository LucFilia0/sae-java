package planeair.components.time;

//#region IMPORT
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

import planeair.App;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
//#endregion

/**
 * Slider situe in te center of the Body of the App
 * The slider give the user access to change simulation time
 * If the user push the button right to the Slider The simulation go and the slider move
 * 
 * @author GIRAUD Nila
 */
public class NSliderTime extends JSlider{

    //#region CONSTRUCTOR
    /**
     * Constructor of the Slider
     */
    public NSliderTime(){

        initComponent();
        
    }
    //#endregion

    /**
     * Init components
     * 
     */
    private void initComponent(){

        this.setMinimum(0);
        this.setMaximum(1439); // 24 * 60 - 1 : Nb of minutes in one day
        this.setPreferredSize(new Dimension(300,40));
        this.setValue(0000);
        this.setForeground(Color.BLACK);
        this.setOpaque(false);

    }


    /**
     * Reset the Slider
     *
     */
    public void resetSlider(){

        initComponent();

    }

    /**
     * Moves the slider fast to refresh the position of the Flights. #Brigand
     * 
     * @author Luc le Manifik
     */
    public void refreshTime() {
        int value = this.getValue();
        this.setValue(12);
        this.setValue(value); // Briganderie
    }

    //#region SLIDER MODEL
        @Override
        public void updateUI() {
            setUI(new CustomSliderUI(this));
        }

        private static class CustomSliderUI extends BasicSliderUI {

            private static final int TRACK_HEIGHT = 8;
            private static final int TRACK_WIDTH = 8;
            private static final int TRACK_ARC = 5;
            private static final Dimension THUMB_SIZE = new Dimension(20, 20);
            private final RoundRectangle2D.Float trackShape = new RoundRectangle2D.Float();

            public CustomSliderUI(final JSlider b) {
                super(b);
            }

            @Override
            protected void calculateTrackRect() {
                super.calculateTrackRect();
                if (isHorizontal()) {
                    trackRect.y = trackRect.y + (trackRect.height - TRACK_HEIGHT) / 2;
                    trackRect.height = TRACK_HEIGHT;
                } else {
                    trackRect.x = trackRect.x + (trackRect.width - TRACK_WIDTH) / 2;
                    trackRect.width = TRACK_WIDTH;
                }
                trackShape.setRoundRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height, TRACK_ARC, TRACK_ARC);
            }

            @Override
            protected void calculateThumbLocation() {
                super.calculateThumbLocation();
                if (isHorizontal()) {
                    thumbRect.y = trackRect.y + (trackRect.height - thumbRect.height) / 2;
                } else {
                    thumbRect.x = trackRect.x + (trackRect.width - thumbRect.width) / 2;
                }
            }

            @Override
            protected Dimension getThumbSize() {
                return THUMB_SIZE;
            }

            private boolean isHorizontal() {
                return slider.getOrientation() == JSlider.HORIZONTAL;
            }

            @Override
            public void paint(final Graphics g, final JComponent c) {
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paint(g, c);
            }

            @Override
            public void paintTrack(final Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                Shape clip = g2.getClip();

                boolean horizontal = isHorizontal();
                boolean inverted = slider.getInverted();

                // Paint shadow.
                g2.setColor(new Color(170, 170 ,170));
                g2.fill(trackShape);

                // Paint track background.
                g2.setColor(new Color(200, 200 ,200));
                g2.setClip(trackShape);
                trackShape.y += 1;
                g2.fill(trackShape);
                trackShape.y = trackRect.y;

                g2.setClip(clip);

                // Paint selected track.
                if (horizontal) {
                    boolean ltr = slider.getComponentOrientation().isLeftToRight();
                    if (ltr) inverted = !inverted;
                    int thumbPos = thumbRect.x + thumbRect.width / 2;
                    if (inverted) {
                        g2.clipRect(0, 0, thumbPos, slider.getHeight());
                    } else {
                        g2.clipRect(thumbPos, 0, slider.getWidth() - thumbPos, slider.getHeight());
                    }

                } else {
                    int thumbPos = thumbRect.y + thumbRect.height / 2;
                    if (inverted) {
                        g2.clipRect(0, 0, slider.getHeight(), thumbPos);
                    } else {
                        g2.clipRect(0, thumbPos, slider.getWidth(), slider.getHeight() - thumbPos);
                    }
                }
                g2.setColor(App.KINDAYELLOW);
                g2.fill(trackShape);
                g2.setClip(clip);
            }

            @Override
            public void paintThumb(final Graphics g) {
                g.setColor(Color.BLACK);
                g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
            }

            @Override
            public void paintFocus(final Graphics g) {}
        }
    //#endregion
}


