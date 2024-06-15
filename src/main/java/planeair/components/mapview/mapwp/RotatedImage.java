package planeair.components.mapview.mapwp;

//#region IMPORTS

	//#region JAVA

	import java.io.File;

	//#endregion

	//#region AWT

	import java.awt.Component;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
	import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

//#endregion

	//#region SWING
	
	import javax.swing.ImageIcon;

	//#endregion

//#endregion

/**
 * A RotatedImage is an Image which is rotated from a radian value, passed in parameter
 * 
 * @author Luc le Manifik
 */
public class RotatedImage extends ImageIcon  {

	//#region ATTRIBUTES

	/**
	 * The Image we wish to rotate
	 */
	private Image image;

	/**
	 * The degree used to rotate the image
	 */
	private double radian;

	//#endregion

	//#region CONSTRUCTORS

	/**
	 * Creates a new RotatedImage.
	 * A RotatedImage is an image which is rotated from the radian attribute.
	 * 
	 * @param iconFile ({@link java.io.File File}) - The File from which is prompt the RotatedImage
	 * @param radian (double) - The value of the rotation, in radians
	 * 
	 * @throws IOException Threw if the File read is not correct
	 * 
	 * @author Luc le Manifik
	 */
	public RotatedImage(File iconFile, double radian) throws IOException {
		try {
            // The scaled image is the "resized" version of the "iconFile" image. To fit the wanted size on the map (BUTTON_SIZE)
            this.image = ImageIO.read(iconFile).getScaledInstance(MapWaypointButton.BUTTON_SIZE, MapWaypointButton.BUTTON_SIZE, Image.SCALE_SMOOTH);
        }catch(IOException e) {
            throw e; // What the heck is IOException ?? Seriously XD
        }
		this.radian = radian;
	}

	//#endregion

	//#region PUBLIC METHODS
	
	/**
	 * Paints the RotatedImage.
	 */
	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {

		Graphics2D g2 = (Graphics2D) g.create();

		g2.rotate(this.radian, x, y);
		g2.drawImage(this.image, 0, 0, c);

		g2.dispose();
	}

	//#endregion
}
