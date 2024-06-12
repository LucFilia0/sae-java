package planeair.graph.graphutil ;

import java.awt.* ;
import java.awt.event.* ;
import java.util.EnumSet;

import javax.swing.SwingUtilities;

import org.graphstream.graph.* ;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.view.* ;
import org.graphstream.ui.view.Viewer.ThreadingModel;
import org.graphstream.ui.view.camera.Camera;
import org.graphstream.ui.view.util.GraphMetrics;
import org.graphstream.ui.view.util.InteractiveElement;

import planeair.graph.coloring.ColoringUtilities;
import planeair.graph.graphtype.GraphSAE;

import org.graphstream.ui.swing_viewer.* ;
import org.graphstream.ui.swing_viewer.util.DefaultMouseManager;
import org.graphstream.ui.swing_viewer.util.DefaultShortcutManager;
import org.graphstream.ui.swing_viewer.util.MouseOverMouseManager;

/**
 * Class handling the rendering of Graphs and the events on its panel
 */
public class PanelCreator {

	/**
	 * boolean handling the pumping of events in a separate thread
	 */
	protected boolean isRendering = true ;

	/**
	 * Graph displayed in the panel
	 */
	protected GraphSAE graph ;

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
	 * Field storing the mouse position for where it first started dragging
	 */
	protected Point3 dragPos = null ;

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
		view.setMouseManager(new MouseOverMouseManager(EnumSet.of(InteractiveElement.NODE), 20) {
			@Override
			public void mouseDragged(MouseEvent event) {
				if (curElement != null) {
					elementMoving(curElement, event);
				}
				else {
					dragMovement(event) ;
				}
			}
		}) ;
		
		view.setShortcutManager(new DefaultShortcutManager());

		ColoringUtilities.setGraphStyle(graph, 0) ;

		// Adds a pipe to the graph which sends info from the GraphicGraph back to the actual graph
		// and also checks for events
		fromViewer = viewer.newViewerPipe() ;
		fromViewer.addViewerListener(new ViewerEventHandler()) ;
		panel.addMouseWheelListener(new MouseEventHandler()) ;
		panel.addMouseListener(new MouseEventHandler()) ;
		fromViewer.addSink(graph) ;
		
		// Thread running in the background constantly sending the changes to the Graph
		Thread graphPump = new Thread(new Runnable() {
			public void run() {
				while(isRendering) {
					try {
						fromViewer.blockingPump() ;
					}

					catch (Exception e) {
						// ^^'  
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
	public GraphSAE getGraph() {
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
	private Point3 getGraphPositionFromClick(Camera cam, Point mousePosPx) {
		//Initialisation
		SwingUtilities.convertPointFromScreen(mousePosPx, panel) ;

		// Retrieving important coordinates and distances
		Point3 mousePosGU = cam.transformPxToGu(mousePosPx.getX(), mousePosPx.getY()) ;

		return getAdjustedPosition(cam, mousePosGU) ;
	}

	/**
	 * Adjusts the point's position so that it stays inside the graph's bounds
	 * @param cam
	 * @param point The point we are adjusting
	 * @return 
	 */
	public Point3 getAdjustedPosition(Camera cam, Point3 point) {
		Point3 res ;
		GraphMetrics gm = cam.getMetrics() ;
		double scaleFactor = cam.getViewPercent() ;
		Point3 center = new Point3((gm.hi.x + gm.lo.x)/2, (gm.hi.y + gm.lo.y)/2) ;
		double maxDistance = (gm.diagonal/2)*scaleFactor ;
		double clickDistance = point.distance(center) ;
		
		// Restricts the movements of the user around the graph
		if (clickDistance < maxDistance) {
			res = point ;
		}
		// If the user clicked outside the authorized region, he will be snapped to the border
		else {
			// I don't think clickDistance can be equal to zero here so it should be fine
			res = center.interpolate(point, maxDistance/clickDistance) ;
		}

		return res ;
	}

	/**
	 * Moves the camera when dragging the mouse
	 */
	private void dragMovement(MouseEvent e) {
		double scaleFactor = 0.05 ;
		Camera cam = view.getCamera() ;
		Point3 viewCenter = cam.getViewCenter() ;
		Point3 mousePos = getGraphPositionFromClick(cam, e.getPoint()) ;

		double posX = viewCenter.x + (dragPos.x - mousePos.x)*scaleFactor ;
		double posY = viewCenter.y + (dragPos.y - mousePos.y)*scaleFactor ;
		Point3 newViewCenter = getAdjustedPosition(cam, new Point3(posX, posY, 0)) ;
		cam.setViewCenter(newViewCenter.x, newViewCenter.y, 0) ;
	}

	
	private class MouseEventHandler extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			dragPos = getGraphPositionFromClick(view.getCamera(), e.getPoint()) ;
		}

		public void mouseReleased(MouseEvent e) {
			dragPos = null ;
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
			if (zoom > 0.2 && zoom < 2) {
				cam.setViewPercent(zoom);
				Point3 camPos = cam.getViewCenter() ;
				Point3 finalCamPos ;

				if (me.getUnitsToScroll() < 0) {
					// Moves the ViewCenter closer to the mouse pointer while zooming in
					Point mousePosPx = me.getLocationOnScreen() ;
					Point3 mousePos = getGraphPositionFromClick(cam, mousePosPx) ;

					finalCamPos = camPos.interpolate(mousePos, 0.15 * cam.getViewPercent()) ;
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
	 * Class handling methods from ViewerListener
	 */
	private class ViewerEventHandler implements ViewerListener {
		/**
		 * Terminates the thread when the graph window is closed.
		 */
		@Override
		public void viewClosed(String id) {
			isRendering = false;
		}
		
		/**
		 * Makes nodes bigger when hovering over them
		 */
		@Override
		public void mouseOver(String id) {
			Node n = graph.getNode(id) ;
			n.removeAttribute("ui.size") ;
			n.setAttribute("ui.size", ColoringUtilities.DEFAULT_NODE_SIZE*2) ;
			n.setAttribute("ui.label", id) ;
			n.setAttribute("ui.style", "text-alignment : center ; text-color : white ;" + 
			"text-background-mode : rounded-box ; text-background-color : black ; text-padding : 2 ;" + 
			"text-style : bold ;") ;	
		}

		/**
		 * Returns the nodes to their original size
		 */
		@Override
		public void mouseLeft(String id) {
			Node n = graph.getNode(id) ;
			n.removeAttribute("ui.size") ;
			n.removeAttribute("ui.label") ;
			n.removeAttribute("ui.style") ;
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