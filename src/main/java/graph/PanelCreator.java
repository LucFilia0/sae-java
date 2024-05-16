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
 * Class handling rendering of the Graph and mouse events
 */
public class PanelCreator implements ViewerListener, MouseWheelListener, MouseListener {
	/**
	 * boolean handling the pumping of events in a separate thread
	 */
	protected boolean loop = true;

	/**
	 * Graph displayed in the panel
	 */
	private Graph graph ;

	/**
	 * The Viewer of the graph, lets you display the graph
	 */
	private Viewer viewer ;

	/**
	 * The ViewerPipe of the graph, sends events from the display to the Graph
	 */
	private ViewerPipe fromViewer ;

	/**
	 * The ViewPanel of the graph, a type of Panel containing a View of the graph
	 */
	private ViewPanel panel ;

	/**
	 * View of the graph, used to zoom and stuff
	 */
	private View view ;

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
		fromViewer.addViewerListener(this) ;
		panel.addMouseWheelListener(this) ;
		panel.addMouseListener(this) ;
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
						System.err.println("bro got interrupted 😡😡😡") ;
					}
				}
			}
		}) ;
		graphPump.start() ;
	}

	public void viewClosed(String id) {
		loop = false;
	}

	public void buttonPushed(String id) {
	}

	public void buttonReleased(String id) {
	}
	
	/**
	 * Makes nodes bigger when hovering over them
	 */
	public void mouseOver(String id) {
		Node n = graph.getNode(id) ;
		n.removeAttribute("ui.style") ;
		n.setAttribute("ui.style", "size : 40px ;") ;
	}

	/**
	 * Returns the nodes to their original size
	 */
	public void mouseLeft(String id) {
		Node n = graph.getNode(id) ;
		n.removeAttribute("ui.style") ;
		n.setAttribute("ui.style", "size : 20px ;") ;
	}

	/**
	 * This event handles zooming on the graph from 0.1x to 2x
	 */
	public void mouseWheelMoved(MouseWheelEvent me) {
		Camera cam = view.getCamera() ;
		double zoom = cam.getViewPercent() + (double)me.getUnitsToScroll() / 100 ;
		if (zoom > 0.1 && zoom < 2) {
			cam.setViewPercent(zoom);
		}
	}

	/**
	 * This event handles moving around the graph by centering the camera on the point you clicked
	 */
	public void mouseClicked(MouseEvent e) {
		// Initialisation
		Camera cam = view.getCamera() ;
		GraphMetrics gm = cam.getMetrics() ;
		Point mouseLocation = MouseInfo.getPointerInfo().getLocation() ;
		double scaleFactor = 1 * cam.getViewPercent() ;
		
		// Recovers the mouse location
		double posX = mouseLocation.getX() ;
		double posY = mouseLocation.getY() ;

		// Retrieving important coordinates and distances
		Point3 pos = cam.transformPxToGu(posX, posY) ;
		Point3 origin = new Point3((gm.hi.x + gm.lo.x)/2, (gm.hi.y + gm.lo.y)/2) ;
		double maxDistance = (gm.diagonal/2)*scaleFactor ;
		double clickDistance = pos.distance(origin) ;
		
		// Restricts the movements of the user around the graph
		if (clickDistance < maxDistance) {
			cam.setViewCenter(pos.x, pos.y, pos.z) ;
		}
		// If the user clicked outside the authorized region, he will be snapped to the border
		else {
			Point3 interpolation = origin.interpolate(pos, maxDistance/clickDistance) ;
			cam.setViewCenter(interpolation.x, interpolation.y, pos.z) ;
		}

	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

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


}