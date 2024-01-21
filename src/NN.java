public class NN {

    private float learningRate;

    private Weights weights;
    private Layers layers;

    private long lastTrainStart;
    private long lastTrainEnd;

    public NN(Layers layers, float learningRate){
        this.learningRate = learningRate;

        this.layers = layers;
        this.weights = new Weights(layers).initializeFromLayers().randomize();
    }

    public void train(Array2D trainingInput, Array2D trainingOutput, int iterations, boolean printLoss){
        lastTrainStart = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            Array2D outputPredictions = predict(trainingInput);
            if (printLoss) { System.out.println(loss(outputPredictions, trainingOutput)); }
            backpropagation(outputPredictions, trainingOutput);

        }
        lastTrainEnd = System.currentTimeMillis();
    }

    public void backpropagation(Array2D predictedOutput, Array2D actualOutput){
        chainRuleOutput(predictedOutput, actualOutput);
        for (int i = 0; i < layers.getOutputLayer().getRows(); i++) {
            for (int j = 0; j < layers.getLayers().get(layers.getLayers().size()-2).getRows(); j++) {

            }
        }

    }

    public Array2D chainRuleOutput(Array2D predictedOutput, Array2D actualOutput){

        Array2D gradientPredictions = MathUtil.difference(predictedOutput, actualOutput).multiply(2);
        Array2D y = activationDerivative(predictedOutput);
        Array2D z = layers.getLayers().get(layers.getLayers().size()-2);

        return gradientPredictions.dot(y).dot(z);
    }

    public Array2D predict(Array2D input){
        Array2D result = input.dot(weights.getWeights(0));
        for (int i = 1; i < layers.getLayers().size()-1; i++) {
            result = activation(result).dot(weights.getWeights(i));
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

    public float loss(Array2D predictedOutput, Array2D actualOutput){
        return MathUtil.difference(predictedOutput, actualOutput).power(2).sum();
    }

}
