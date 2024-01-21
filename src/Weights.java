import java.util.ArrayList;

public class Weights {

    private ArrayList<Array2D> weights;
    private Layers layers;

    public Weights(Layers layers) {
        this.weights = new ArrayList<>();
        this.layers = layers;
    }

    public Weights addWeights(int lastNodes, int nextNodes){
        this.weights.add(new Array2D(lastNodes, nextNodes));
        return this;
    }

    public Array2D getWeights(int fromLayer){
        return this.weights.get(fromLayer);
    }
    
    public Weights initializeFromLayers(){
        for (int i = 0; i < this.layers.getLayers().size()-1; i++) {
            addWeights(layers.getLayers().get(i).getRows(), layers.getLayers().get(i+1).getRows());
        }
        return this;
    }

    public Weights randomize(){
        for (int i = 0; i < this.weights.size(); i++) {
            this.weights.get(i).random();
        }
        return this;
    }

}
