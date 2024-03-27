package graph;

import org.graphstream.graph.implementations.*;

public class Flight extends SingleNode {
    
    protected Flight(AbstractGraph graph, String id) {
        super(graph,id) ;
    }

    public void setFlightAttributes(String aeroportDepart, String aeroportArrivee, int heureDepart, int minuteDepart, int duree) {
        this.setAttribute("aeroportDepart", aeroportDepart) ;
        this.setAttribute("aeroportArrivee", aeroportArrivee) ;
        this.setAttribute("heureDepart", heureDepart) ;
        this.setAttribute("minuteDepart", minuteDepart) ;
        this.setAttribute("duree", duree) ;
    }

    
}
