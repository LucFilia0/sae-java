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
public class Renderer implements ViewerListener {
	protected boolean loop = true;

	private Graph g ;
	private Viewer viewer ;
	private ViewerPipe fromViewer ;
	private ViewPanel panel ;

	/**
	 * Opens the graph in a JFrame and handles mouse events
	 * @param graph graph you are trying to render
	 */
	public Renderer(Graph graph) {
		g = graph ;
		// Generates the window that 
		viewer = new SwingViewer(g, ThreadingModel.GRAPH_IN_ANOTHER_THREAD) ;
		panel = (ViewPanel)viewer.addDefaultView(false) ;
		viewer.enableAutoLayout() ;
		viewer.getDefaultView().enableMouseOptions() ;

		fromViewer = viewer.newViewerPipe();
		fromViewer.addViewerListener(this);
		fromViewer.addSink(g) ;
		Thread thread = new Thread(new Runnable() {
			public void run() {
				while(loop) {
					fromViewer.pump() ;
				}
			}
		}) ;
		thread.start() ;
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
	}

	public void mouseLeft(String id) {
	}


	public boolean isLoop() {
		return this.loop;
	}

	public boolean getLoop() {
		return this.loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public Graph getGraph() {
		return this.g;
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