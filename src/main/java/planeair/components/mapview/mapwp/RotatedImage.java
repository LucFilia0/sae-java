package planeair.components.mapview.mapwp;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class RotatedImage extends ImageIcon  {

	private Image image;

	private double radian;

	public RotatedImage(Image image, double radian) {
		this.image = image;
		this.radian = radian;
	}

	@Override
	public synchronized void paintIcon(Component c, Graphics g, int x, int y) {

		Graphics2D g2 = (Graphics2D) g.create();

		g2.rotate(this.radian, x, y);
		g2.drawImage(this.image, 0, 0, c);


		g2.dispose();
	 }
}
