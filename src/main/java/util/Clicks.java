package util ;
import java.awt.* ;
import java.awt.event.* ;

import org.graphstream.graph.* ;
import org.graphstream.ui.swing.* ;
import org.graphstream.ui.swing_viewer.util.* ;
import org.graphstream.ui.view.* ;
import org.graphstream.ui.swing_viewer.* ;
import org.graphstream.algorithm.Toolkit ;

public class Clicks implements ViewerListener {
	protected boolean loop = true;

	public Clicks(Graph graph) {
		// We do as usual to display a graph. This
		// connect the graph outputs to the viewer.
		// The viewer is a sink of the graph.
		Viewer viewer = graph.display();
		viewer.getDefaultView().enableMouseOptions();

		// We connect back the viewer to the graph,
		// the graph becomes a sink for the viewer.
		// We also install us as a viewer listener to
		// intercept the graphic events.
		ViewerPipe fromViewer = viewer.newViewerPipe();
		fromViewer.addViewerListener(this);
		fromViewer.addSink(graph);

		// Then we need a loop to do our work and to wait for events.
		// In this loop we will need to call the
		// pump() method before each use of the graph to copy back events
		// that have already occurred in the viewer thread inside
		// our thread.

		while(loop) {
			fromViewer.pump(); // or fromViewer.blockingPump(); in the nightly builds
			//Button overed on node 3
			// : 758.0 y : 452.0
		}
	}

	public void viewClosed(String id) {
		loop = false;
	}

	public void buttonPushed(String id) {
		System.out.println("Button pushed on node "+id) ;
	}

	public void buttonReleased(String id) {
		System.out.println("Button released on node "+id);
	}

	public void mouseOver(String id) {
		System.out.println("Button overed on node "+id) ;
		loop = false ;
	}

	public void mouseLeft(String id) {
		System.out.println("Need the Mouse Options to be activated");
	}

}