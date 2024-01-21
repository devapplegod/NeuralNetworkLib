public class NeuralNetwork {

    private int inNodes;
    private int outNodes;
    private int hiddenNodes;
    private float adjustOffset;

    private Array2D weights1;
    private Array2D weights2;

    private long lastTrainStart;
    private long lastTrainEnd;

    public NeuralNetwork(int inputNodes, int outputNodes, int hiddenNodes, float adjustOffset){
        this.inNodes = inputNodes;
        this.outNodes = outputNodes;
        this.hiddenNodes = hiddenNodes;
        this.adjustOffset = adjustOffset;
    }

    public void train(Array2D inputData, Array2D outputData, int iterations, boolean printLoss){
        lastTrainStart = System.currentTimeMillis();
        weights1 = randomWeights(inNodes, hiddenNodes);
        weights2 = randomWeights(hiddenNodes, outNodes);
        for (int i = 0; i < iterations; i++) {
            Array2D outputPredictions = predict(inputData);
            if (printLoss) {System.out.println(loss(outputPredictions, outputData));}
            Array2D gradientPredictions = gradientPredictions(outputPredictions, outputData);
            weights2 = adjustedWeights(weights2, gradientWeights2(getHiddenReLU(inputData), gradientPredictions));
            weights1 = adjustedWeights(weights1, gradientWeights1(inputData, gradientHiddenValues(gradientHiddenReLU(gradientPredictions))));
        }
        lastTrainEnd = System.currentTimeMillis();
    }

    public float getLastTrainTime(){
        return (lastTrainEnd - lastTrainStart) / 1000F;
    }

    private Array2D adjustedWeights(Array2D oldWeights, Array2D weightsGradient){
        return MathUtil.difference(oldWeights, weightsGradient.multiply(adjustOffset));
    }

    private Array2D randomWeights(int start, int end){
        return new Array2D(start, end).random();
    }

    private Array2D getHiddenValues(Array2D inputData){
        return inputData.dot(weights1);
    }

    private Array2D getHiddenReLU(Array2D inputData){
        return MathUtil.ReLU(getHiddenValues(inputData));
    }

    public Array2D predict(Array2D inputData){
        return getHiddenReLU(inputData).dot(weights2);
    }

    public float loss(Array2D predictedOutput, Array2D actualOutput){
        return MathUtil.difference(predictedOutput, actualOutput).power(2).sum();
    }

    private Array2D gradientPredictions(Array2D predictedOutput, Array2D actualOutput){
        return MathUtil.difference(predictedOutput, actualOutput).multiply(2);
    }

    private Array2D gradientHiddenValues(Array2D gradientHiddenReLU){
        return MathUtil.ReLU(gradientHiddenReLU);
    }

    private Array2D gradientHiddenReLU(Array2D gradientPredictions){
        return gradientPredictions.dot(weights2.T());
    }

    private Array2D gradientWeights2(Array2D hiddenReLU, Array2D gradientPredictions){
        return hiddenReLU.T().dot(gradientPredictions);
    }

    private Array2D gradientWeights1(Array2D inputData, Array2D gradientHiddenValues){
        return inputData.T().dot(gradientHiddenValues);
    }

}
