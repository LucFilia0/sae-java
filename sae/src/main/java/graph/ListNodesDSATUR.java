package graph;

import org.graphstream.graph.*;


import java.util.ArrayList;

public class ListNodesDSATUR<T> {
    
    protected static int nbNodes;
    protected int id;
    protected T node;
    protected int color;
    protected int Dsatur;
    protected int util;


    ListNodesDSATUR(T newNode){
        nbNodes ++;
        this.id = nbNodes;
        this.node = newNode;
        this.Dsatur = newNode.getNbAdj();
        util = 1;


    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public T getNode() {
        return this.node;
    }

    public void setNode(T node) {
        this.node = node;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public int getDsatur() {
        return this.Dsatur;
    }

    public void setDsatur(Integer Dsatur) {
        this.Dsatur = Dsatur;
    }


    public int getUtil() {
        return this.util;
    }

    public void setUtil(int util) {
        this.util = util;
    }


}
