package graph ;
import java.awt.* ;
import java.awt.event.* ;

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
	 * Opens the graph in a JFrame and handles mouse events
	 * @param graph graph you are trying to render
	 * @see ViewPanel 
	 */
	public PanelCreator(Graph graph) {
		this.graph = graph ;

		// Generates the ViewPanel containing the graph
		viewer = new SwingViewer(graph, ThreadingModel.GRAPH_IN_ANOTHER_THREAD) ;
		panel = (ViewPanel)viewer.addDefaultView(false) ;
		view = viewer.getDefaultView() ;
		viewer.enableAutoLayout() ;
		viewer.getDefaultView().enableMouseOptions() ;

		// Adds a pipe to the graph which sends info from the GraphicGraph back to the actual graph
		// and also checks for events
		fromViewer = viewer.newViewerPipe() ;
		fromViewer.addViewerListener(new ViewerEventHandler()) ;
		panel.addMouseWheelListener(new MouseWheelEventHandler()) ;
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
					catch (InterruptedException e) {
						System.err.println("bro got interrupted 😡😡😡") ; // Bro, what did you do ?? XD
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
	public Point3 getGraphPositionFromClick(Camera cam) {
		//Initialisation
		GraphMetrics gm = cam.getMetrics() ;
		Point mouseLocation = MouseInfo.getPointerInfo().getLocation() ;
		Point3 res ;
		double scaleFactor = 1 * cam.getViewPercent() ;

		// Retrieving important coordinates and distances
		Point3 mousePos = cam.transformPxToGu(mouseLocation.getX(), mouseLocation.getY()) ;
		Point3 center = new Point3((gm.hi.x + gm.lo.x)/2, (gm.hi.y + gm.lo.y)/2) ;
		double maxDistance = (gm.diagonal/2)*scaleFactor ;
		double clickDistance = mousePos.distance(center) ;
		
		// Restricts the movements of the user around the graph
		if (clickDistance < maxDistance) {
			res = mousePos ;
		}
		// If the user clicked outside the authorized region, he will be snapped to the border
		else {
			// I don't think clickDistance can be equal to zero here so it should be fine
			res = center.interpolate(mousePos, maxDistance/clickDistance) ;
		}

		return res ;
	}
	
	/**
	 * class handling events from MouseWheelEvent
	 */
	private class MouseWheelEventHandler implements MouseWheelListener {
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
					Point3 mousePos = getGraphPositionFromClick(cam) ;
					finalCamPos = camPos.interpolate(mousePos, 
						cam.getViewPercent()/(10 * (1 + cam.getViewPercent()/10))) ;
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
	 * Class handling only the mouseClicked event from MouseEvent
	 */
	private class MouseEventHandler extends MouseAdapter {
		/**
		 * This event handles moving around the graph by centering the camera on the point you clicked
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			Camera cam = view.getCamera() ;
			Point3 viewCenter = getGraphPositionFromClick(cam) ;
			cam.setViewCenter(viewCenter.x, viewCenter.y, viewCenter.z) ;
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
		 * Class handling events from ViewerEvent
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
				n.setAttribute("ui.style", "size : 20px ;") ;
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