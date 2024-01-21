public class NeuralNetworkNew {

    private float learningRate;

    private Weights weights;
    private Layers layers;

    private long lastTrainStart;
    private long lastTrainEnd;

    public NeuralNetworkNew(Layers layers, float learningRate){
        this.learningRate = learningRate;
        this.layers = layers;
        this.weights = new Weights(layers).initializeFromLayers().randomize();
    }

    public void train(Array2D trainingInput, Array2D trainingOutput, int iterations, boolean printLoss){

        for (int i = 0; i < iterations; i++) {

            Array2D outputPredictions = predict(trainingInput);
            float error = error(outputPredictions, trainingOutput);

            Array2D gradient = activationDerivative(outputPredictions).multiply(error).multiply(learningRate);
            Array2D hiddenT = getValuesOnHiddenLayer(0, trainingInput).T();
            Array2D weightsDeltaHO = gradient.dot(hiddenT);

        }

    }

    public Array2D predict(Array2D input){
        Array2D result = activation(input.dot(weights.getWeights(0)));
        for (int i = 1; i < layers.getLayers().size()-1; i++) {
            result = activation(result.dot(weights.getWeights(i)));
        }
        return result;
    }

    public Array2D activation(Array2D input){
        return MathUtil.ReLU(input);
    }

    public Array2D activationDerivative(Array2D input){
        Array2D result = new Array2D(input.getRows(), input.getColumns());
        for (int i = 0; i < input.getRows(); i++) {
            if (input.getValue(i, 0) <= 0){
                result.setValue(i, 0, 0F);
            } else {
                result.setValue(i, 0, 1F);
            }
        }
        return result;
    }

    public Array2D getValuesOnHiddenLayer(int hiddenLayerIndex, Array2D input){
        Array2D result = activation(input.dot(weights.getWeights(0)));
        for (int i = 0; i < hiddenLayerIndex; i++) {
            result = activation(result.dot(weights.getWeights(i)));
        }
        return result;
    }

    public float error(Array2D predictedOutput, Array2D actualOutput){
        return MathUtil.difference(predictedOutput, actualOutput).power(2).sum();
    }

}
