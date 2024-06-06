package planeair.graph ;

import java.awt.* ;
import java.awt.event.* ;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.graphstream.graph.* ;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.view.* ;
import org.graphstream.ui.view.Viewer.ThreadingModel;
import org.graphstream.ui.view.camera.Camera;
import org.graphstream.ui.view.util.GraphMetrics;
import org.graphstream.ui.swing_viewer.* ;

/**
 * Class handling the rendering of Graphs and the events on its panel
 */
public class PanelCreator {

	/**
	 * boolean handling the pumping of events in a separate thread
	 */
	protected boolean loop = true ;

	/**
	 * Graph displayed in the panel
	 */
	protected Graph graph ;

	/**
	 * The Viewer of the graph, lets you display the graph
	 */
	protected Viewer viewer ;

	/**
	 * The ViewerPipe of the graph, sends events from the display to the Graph
	 */
	protected ViewerPipe fromViewer ;

	/**
	 * The ViewPanel of the graph, a type of Panel containing a View of the graph
	 */
	protected ViewPanel panel ;

	/**
	 * View of the graph, used to zoom and stuff
	 */
	protected View view ;

	/**
	 * Calls the default constructor with inOwnFrame set to false
	 * @param graph
	 */
	public PanelCreator(GraphSAE graph) {
		this(graph, false) ;
	}
	/**
	 * Opens the graph in a JFrame and handles mouse events
	 * @param graph graph you are trying to render
	 * @param inOwnFrame 
	 * @see ViewPanel 
	 */
	public PanelCreator(GraphSAE graph, boolean inOwnFrame) {
		this.graph = graph ;

		// Generates the ViewPanel containing the graph
		viewer = new SwingViewer(graph, ThreadingModel.GRAPH_IN_ANOTHER_THREAD) ;
		panel = (ViewPanel)viewer.addDefaultView(inOwnFrame) ;
		view = viewer.getDefaultView() ;
		viewer.enableAutoLayout() ;
		viewer.getDefaultView().enableMouseOptions() ;
		Coloration.setGraphStyle(graph, 0) ;

		// Adds a pipe to the graph which sends info from the GraphicGraph back to the actual graph
		// and also checks for events
		fromViewer = viewer.newViewerPipe() ;
		fromViewer.addViewerListener(new ViewerEventHandler()) ;
		panel.addMouseWheelListener(new MouseEventHandler()) ;
		panel.addMouseListener(new MouseEventHandler()) ;
		panel.addKeyListener(new KeyboardEventHandler()) ;
		fromViewer.addSink(graph) ;
		
		// Thread running in the background constantly sending the changes to the Graph
		Thread graphPump = new Thread(new Runnable() {
			public void run() {
				while(loop) {
					try {
						fromViewer.blockingPump() ;
					}

					// Don't mind this idk why or when this gets thrown
					catch (Exception e) {
					}
				}
			}
		}) ;
		graphPump.start() ;
	}
	
	// Getters
		
	/**
	 * Getter for the graph contained in the panel
	 * @return the Graph object
	 */
	public Graph getGraph() {
		return this.graph;
	}

	/**
	 * Getter for the viewer related to the Graph in the panel
	 * @return the Viewer object
	 */
	public Viewer getViewer() {
		return this.viewer;
	}

	/**
	 * Getter for the pipe handling events
	 * @return the ViewerPipe object
	 */
	public ViewerPipe getViewerPipe() {
		return this.fromViewer;
	}

	/**
	 * Getter for the panel containing the graph
	 * @return the ViewPanel object
	 */
	public ViewPanel getViewPanel() {
		return this.panel;
	}

	/**
	 * Getter for the view of the Graph
	 * @return
	 */
	public View getView() {
		return this.view ;
	}

	/**
	 * Returns the mouse position in Graph Units while making sure it stays inside the authorized area
	 * @param cam The View Camera
	 * @return The Point representing the mouse's effective Position
	 */
	public Point3 getGraphPositionFromClick(Camera cam, Point mousePosPx) {
		//Initialisation
		GraphMetrics gm = cam.getMetrics() ;
		Point3 res ;
		double scaleFactor = cam.getViewPercent() ;
		offsetMousePosition(panel, mousePosPx) ;

		// Retrieving important coordinates and distances
		Point3 mousePosGU = cam.transformPxToGu(mousePosPx.getX(), mousePosPx.getY()) ;
		Point3 center = new Point3((gm.hi.x + gm.lo.x)/2, (gm.hi.y + gm.lo.y)/2) ;
		double maxDistance = (gm.diagonal/2)*scaleFactor ;
		double clickDistance = mousePosGU.distance(center) ;
		
		// Restricts the movements of the user around the graph
		if (clickDistance < maxDistance) {
			res = mousePosGU ;
		}
		// If the user clicked outside the authorized region, he will be snapped to the border
		else {
			// I don't think clickDistance can be equal to zero here so it should be fine
			res = center.interpolate(mousePosGU, maxDistance/clickDistance) ;
		}

		return res ;
	}

	/**
	 * Offsets the mouse position based on the panel's position relative to the screen
	 * @param panel parent panel 
	 * @param mousePosition position of the mouse relative to the screen
	 * @return position of the mouse relative to panel
	 */
	public Point offsetMousePosition(JPanel panel, Point mousePosition) {
		SwingUtilities.convertPointFromScreen(mousePosition, panel) ;
		return mousePosition ;
	}

	/**
	 * Class handling only the mouseClicked method from MouseListener
	 * and the mouseWheelMoved method from MouseWheelListener
	 */
	private class MouseEventHandler extends MouseAdapter {
		/**
		 * This event handles moving around the graph by centering the camera on the point you clicked
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			Camera cam = view.getCamera() ;
			Point3 viewCenter = getGraphPositionFromClick(cam, e.getLocationOnScreen()) ;
			cam.setViewCenter(viewCenter.x, viewCenter.y, viewCenter.z) ;
		}

		/**
		 * This event handles zooming on the graph from 0.1x to 2x while 
		 * also moving the camera a bit so the zoom is less clunky to use
		 */
		@Override
		public void mouseWheelMoved(MouseWheelEvent me) {
			Camera cam = view.getCamera() ;
			double zoom = cam.getViewPercent() + (double)me.getUnitsToScroll() / 100 ;

			// Limits the range of the zoom
			if (zoom > 0.1 && zoom < 2) {
				cam.setViewPercent(zoom);
				Point3 camPos = cam.getViewCenter() ;
				Point3 finalCamPos ;

				if (me.getUnitsToScroll() < 0) {
					// Moves the ViewCenter closer to the mouse pointer while zooming in
					Point mousePosPx = me.getLocationOnScreen() ;
					Point3 mousePos = getGraphPositionFromClick(cam, mousePosPx) ;

					// Based on the function (x+1)^2/5 - 0.4x^1.3 where x is the viewPercent of the camera (yes I graphed it on Desmos)
					// This function is increasing and has values in the interval [0.2 ; 0.815] for x in the interval [0 ; 2]
					// Basically makes it so the more zoomed in the camera is, the less it will move around the plane while zooming
					finalCamPos = camPos.interpolate(mousePos, 
						Math.pow((cam.getViewPercent() + 1),2)/5 - 0.4*Math.pow(cam.getViewPercent(), 1.3)/1.5) ;
					cam.setViewCenter(finalCamPos.x, finalCamPos.y, finalCamPos.z) ;
				}

				else {
					// Moves the ViewCenter closer to the center while zooming out
					GraphMetrics gm = cam.getMetrics() ;
					Point3 center = new Point3((gm.hi.x + gm.lo.x)/2, (gm.hi.y + gm.lo.y)/2) ;
					finalCamPos = camPos.interpolate(center, cam.getViewPercent()/2) ;
				}
			}
		}
	}
	
	/**
	 * Class handling only the keyPressed event from KeyEvent
	 */
	private class KeyboardEventHandler extends KeyAdapter {
		/**
		 * Spacebar resets the view
		 */
		@Override
		public void keyPressed(KeyEvent arg0) {
			Camera cam = view.getCamera() ;
			GraphMetrics gm = cam.getMetrics() ;
			switch (arg0.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					Point3 center = new Point3((gm.hi.x + gm.lo.x)/2, (gm.hi.y + gm.lo.y)/2) ;
					cam.setViewCenter(center.x, center.y, center.z) ;
					break ;
				default:
					break ;
			}
		}
	}
		
	/**
	 * Class handling methods from ViewerListener
	 */
	private class ViewerEventHandler implements ViewerListener {
		/**
		 * Terminates the thread when the graph window is closed.
		 */
		@Override
		public void viewClosed(String id) {
			loop = false;
		}
		
		/**
		 * Makes nodes bigger when hovering over them
		 */
		@Override
		public void mouseOver(String id) {
			Node n = graph.getNode(id) ;
			n.removeAttribute("ui.style") ;
			n.setAttribute("ui.style", "size : 40px ;") ;
		}

		/**
		 * Returns the nodes to their original size
		 */
		@Override
		public void mouseLeft(String id) {
			Node n = graph.getNode(id) ;
			n.removeAttribute("ui.style") ;
			n.setAttribute("ui.style", "size : " + Coloration.DEFAULT_NODE_SIZE + " ;") ;
		}

		/**
		 * Unused
		 */
		@Override
		public void buttonPushed(String id) {
		}

		/**
		 * Unused
		 */
		@Override
		public void buttonReleased(String id) {
		}
	}

}