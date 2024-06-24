//#region IMPORTS
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

import planeair.components.mapview.mapwp.MapWaypointButton;
import planeair.graph.coloring.ColoringUtilities;
import planeair.graph.graphtype.GraphSAE;

import org.graphstream.ui.swing_viewer.* ;
import org.graphstream.ui.swing_viewer.util.DefaultShortcutManager;
import org.graphstream.ui.swing_viewer.util.MouseOverMouseManager;
//#endregion

/**
 * Class handling the rendering of Graphs and the events on its panel
 * 
 * @author Nathan LIEGEON
 */
public class PanelCreator {

	//#region ATTRIBUTES

	/**
	 * boolean handling the pumping of events in a separate thread
	 */
	protected boolean isRendering ;

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
	protected Point3 dragPos ;
	
	//#endregion

	//#region CONSTRUCTORS

	/**
	 * Handles the creation of a view on the graph, giving access
	 * to a panel containing this graph, multiple events, shortcuts, zooming, 
	 * moving around the view...
	 * @param graph
	 * 
	 * @author Nathan LIEGEON
	 */
	public PanelCreator(GraphSAE graph) {
		this(graph, false) ;
	}

	/**
	 * Handles the creation of a view on the graph, giving access
	 * to a panel containing this graph, multiple events, shortcuts, zooming, 
	 * moving around the view...
	 * 
	 * @param graph graph you are trying to render
	 * @param inOwnFrame if true the graph will open its own frame, else 
	 * its viewPanel will have to be added to a JFrame
	 * @see ViewPanel
	 * 
	 * @author Nathan LIEGEON
	 */
	public PanelCreator(GraphSAE graph, boolean inOwnFrame) {
		this.graph = graph ;

		// Generates the ViewPanel containing the graph
		viewer = new SwingViewer(graph, ThreadingModel.GRAPH_IN_ANOTHER_THREAD) ;
		panel = (ViewPanel)viewer.addDefaultView(inOwnFrame) ;
		view = viewer.getDefaultView() ;
		viewer.enableAutoLayout() ;

		// Sets the way the graph handles mouse events over nodes
		this.dragPos = null ;
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

		ColoringUtilities.setGraphStyle(graph, graph.getKMax()) ;

		// Adds a pipe to the graph which sends info from the GraphicGraph back to the actual graph
		// and also checks for events
		fromViewer = viewer.newViewerPipe() ;
		fromViewer.addViewerListener(new ViewerEventHandler()) ;
		panel.addMouseWheelListener(new MouseEventHandler()) ;
		panel.addMouseListener(new MouseEventHandler()) ;
		fromViewer.addSink(graph) ;
		
		// Thread running in the background constantly sending events that happened on the graph
		Thread graphPump = new GraphEventPumper() ;
		graphPump.start() ;
	}
	//#endregion
	//#region GETTERS
		
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
	 * @see Viewer Viewer for more detail
	 */
	public Viewer getViewer() {
		return this.viewer;
	}

	/**
	 * Getter for the pipe handling events
	 * @see ViewerPipe
	 * @return the ViewerPipe object
	 */
	public ViewerPipe getViewerPipe() {
		return this.fromViewer;
	}

	/**
	 * Getter for the panel containing the graph
	 * @return the ViewPanel object
	 * @see ViewPanel
	 */
	public ViewPanel getViewPanel() {
		return this.panel;
	}

	/**
	 * Getter for the view of the Graph
	 * @return the view on this graph
	 * @see View
	 */
	public View getView() {
		return this.view ;
	}
	//#endregion

	//#region FUNCTIONS

	/**
	 * Returns the mouse position in Graph Units while making sure it stays inside the authorized area
	 * 
	 * @param cam The View Camera
	 * @param mousePosPx The position of the mouse in pixels
	 * @return The Point representing the mouse's effective Position
	 *
	 * @author Nathan LIEGEON
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
	 * @param cam The View Camera
	 * @param point The point we are adjusting
	 * @return The point with its position adjusted
	 * 
	 * @author Nathan LIEGEON
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
	 * 
	 * @param e the mouse event giving the information
	 * 
	 * @author Nathan LIEGEON
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

	/**
	 * <html>
	 * Sets the default style for selected nodes.
	 * <br><br>	
	 * This method is synchronized because it might be called twice at the
	 * same time which would result in an exception being thrown.
	 * @param n The Node
	 * </html>
	 */
	public synchronized static void setSelectedStyle(Node n) {
		n.removeAttribute("ui.size") ;
		n.setAttribute("ui.size", ColoringUtilities.DEFAULT_NODE_SIZE*2) ;
		n.setAttribute("ui.label", n.getId()) ;
		n.setAttribute("ui.style", "text-alignment : center ; text-color : white ;" + 
		"text-background-mode : rounded-box ; text-background-color : black ; text-padding : 2 ;" + 
		"text-style : bold ;") ;	
	}

	/**
	 * Removes all attributes related to the selected node style.
	 * 
	 * This method is synchronized because it might be called twice at the
	 * same time which would result in an exception being thrown.
	 * @param n The node
	 */
	public synchronized static void removeSelectedStyle(Node n) {
		n.removeAttribute("ui.size") ;
		n.removeAttribute("ui.label") ;
		n.removeAttribute("ui.style") ;
	}
	//#endregion

	//#region MOUSE EVENTS

	/**
	 * Class handling all {@code Mouse Events} on this panel. Operations supported
	 * are zooming with the scrool wheel and moving by dragging the mouse
	 * 
	 * @author Nathan LIEGEON
	 */
	private class MouseEventHandler extends MouseAdapter {

		/**
		 * Initializes the position where the mouse started dragging from
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			dragPos = getGraphPositionFromClick(view.getCamera(), e.getPoint()) ;
		}

		/**
		 * Deletes the position the mouse started dragging from
		 */
		public void mouseReleased(MouseEvent e) {
			dragPos = null ;
		}

		/**
		 * This event handles {@code Zooming} on the graph from {@code 0.1x} toi
		 * {@code 2x} while also moving the camera a bit so the zoom feels
		 * less clunky to use
		 * 
		 * @author Nathan LIEGEON
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

					finalCamPos = camPos.interpolate(mousePos, 0.15 * Math.sqrt(cam.getViewPercent())) ;
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

	//#endregion

	//#region VIEWER EVENTS
		
	/**
	 * Class handling {@code Viewer Events} which are events related to 
	 * {@code graphical elements} in the viewer (nodes, edges and other stuff)
	 * 
	 * @author Nathan LIEGEON
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
		 * <html>
		 * Makes the {@code Node} bigger when hovering 
		 * over them and shows their {@code id}
		 * <br><br>
		 * If this node is a flight, selects the 
		 * corresponding {@code FlightWaypoint}
		 * 
		 * @param id the {@ode Id} of the node that is being hovered
		 *
		 * @author Nathan LIEGEON
		 * </html>
		 */
		@Override
		public void mouseOver(String id) {
			Node n = graph.getNode(id) ;
			setSelectedStyle(n) ;
			if (!(n instanceof Flight)) {
				return ;
			}
			
			Flight f = (Flight)n ;
			
			if (f.getFlightWaypoint() != null && 
				f.getFlightWaypoint().getWaypointButton() != null) {
				
				MapWaypointButton mwp = 
					((Flight)n).getFlightWaypoint().getWaypointButton() ;
				
				if (!mwp.isSelected()) {
					mwp.select() ;
				}
			}
		}

		/**
		 * Returns the {@code Node} to its original state
		 * If this node is a flight,
		 * removes the selection from this flight's
		 * {@code FlightWaypoint}
		 * 
		 * @param id The id of the node the mouse just left
		 * 
		 * @author Nathan LIEGEON
		 */
		@Override
		public void mouseLeft(String id) {
			Node n = graph.getNode(id) ;
			removeSelectedStyle(n) ;
			if (!(n instanceof Flight)) {
				return ;
			}
			
			Flight f = (Flight)n ;
			
			if (f.getFlightWaypoint() != null && 
				f.getFlightWaypoint().getWaypointButton() != null) {

				MapWaypointButton mwp = 
					((Flight)n).getFlightWaypoint().getWaypointButton() ;
				
				if (mwp.isSelected()) {
					mwp.deselect() ;
				}
			} 

			return ;
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
	//#endregion

	/**
	 * Thread used to pump graph events
	 * 
	 * @author Nathan LIEGEON
	 */
	public class GraphEventPumper extends Thread {

		/**
		 * Instantiates the {@code Thread}.
		 * If {@code interrupted}, it will try to start another Thread.
		 * To properly close it, close the {@link View}.
		 */
		public GraphEventPumper() {
			super(new Runnable() {
				public void run() {
					isRendering = true ;
					while(isRendering) {
						try {
							
							// Blocking pump waits for events so 
							//it's better for performances
							fromViewer.blockingPump() ;
						} catch (Exception e) {

							// Restarts a Thread if this one got interrupted,
							// else ignores the exception
							interrupted() ;
							isRendering = true ;
						}
					}
				}
			}) ;
		}
	} 

}