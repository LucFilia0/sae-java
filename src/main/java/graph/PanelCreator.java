package graph ;
import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;

import org.graphstream.graph.* ;
import org.graphstream.ui.swing.* ;
import org.graphstream.ui.swing_viewer.util.* ;
import org.graphstream.ui.view.* ;
import org.graphstream.ui.view.Viewer.ThreadingModel;
import org.graphstream.ui.view.util.MouseManager;
import org.graphstream.ui.swing_viewer.* ;
import org.graphstream.algorithm.Toolkit ;

/**
 * Class handling rendering of the Graph and mouse events
 */
public class PanelCreator implements ViewerListener {
	protected boolean loop = true;

	private Graph graph ;
	private Viewer viewer ;
	private ViewerPipe fromViewer ;
	private ViewPanel panel ;

	/**
	 * Opens the graph in a JFrame and handles mouse events
	 * @param graph graph you are trying to render
	 * @see ViewPanel 
	 */
	public PanelCreator(Graph graph) {
		// Looks stupid but lets the implemented ViewerListener events access the graph
		this.graph = graph ;

		// Generates the ViewPanel containing the graph
		viewer = new SwingViewer(graph, ThreadingModel.GRAPH_IN_ANOTHER_THREAD) ;
		panel = (ViewPanel)viewer.addDefaultView(false) ;
		viewer.enableAutoLayout() ;
		viewer.getDefaultView().enableMouseOptions() ;

		// Adds a pipe to the graph which sends info from the GraphicGraph back to the actual graph
		// and also checks for events
		fromViewer = viewer.newViewerPipe();
		fromViewer.addViewerListener(this);
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
		System.out.println("Button pushed on node "+id) ;
	}

	public void buttonReleased(String id) {
	}
 
	public void mouseOver(String id) {
		Node n = graph.getNode(id) ;
		n.removeAttribute("ui.style") ;
		n.setAttribute("ui.style", "size : 40px ;") ;
	}

	public void mouseLeft(String id) {
		Node n = graph.getNode(id) ;
		n.removeAttribute("ui.style") ;
		n.setAttribute("ui.style", "size : 20px ;") ;
	}

	public Graph getGraph() {
		return this.graph;
	}

	public Viewer getViewer() {
		return this.viewer;
	}

	public ViewerPipe getViewerPipe() {
		return this.fromViewer;
	}

	public ViewPanel getViewPanel() {
		return this.panel;
	}


}