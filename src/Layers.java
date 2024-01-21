import java.util.ArrayList;

public class Layers {

    private ArrayList<Array2D> layers;

    public Layers(){
        this.layers = new ArrayList<>();
    }

    public Layers addInputLayer(int nodes){
        this.layers.add(new Array2D(nodes, 1));
        return this;
    }

    public Layers addHiddenLayer(int nodes){
        this.layers.add(new Array2D(nodes, 1));
        return this;
    }

    public Layers addOutputLayer(int nodes){
        this.layers.add(new Array2D(nodes, 1));
        return this;
    }

    public ArrayList<Array2D> getLayers(){
        return this.layers;
    }

    public Array2D getInputLayer(){
        return this.layers.get(0);
    }

    public Array2D getOutputLayer(){
        return this.layers.get(this.layers.size()-1);
    }

}
