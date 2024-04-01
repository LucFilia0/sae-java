package graph;

public class TestGraphInfo {
    private int kMax;
    private int nbNodes;

    public TestGraphInfo() {
        this.kMax = 0;
        this.nbNodes = 0;
    }

    public int getKMax() {
        return this.kMax;
    }
    public void setKMax(int kMax) {
        if(kMax >= 0) {
            this.kMax = kMax;
        }
    }

    public int getNbNodes() {
        return this.nbNodes;
    }
    public void setNbNodes(int nbNodes) {
        if(kMax >= 0) {
            this.nbNodes = nbNodes;
        }
    }
}
