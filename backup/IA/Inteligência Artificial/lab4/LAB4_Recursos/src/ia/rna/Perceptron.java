package ia.rna;

import java.util.*;

/**
 * Implements the Perceptron 
 * 
 * @author  Alair Dias Júnior
 * @version 1.0
 */

public class Perceptron {

	/**
	 * Invalid parameter exception 
	 * 
	 * @author  Alair Dias Júnior
	 * @version 1.0
	 */
	public static class InvalidParameterException extends Exception
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -8729490626290878475L;
	}
	
	
	/**
	 * number of inputs of the perceptron
	 */
	private int m_inputs;
	
	/**
	 * values of the weights of the perceptron
	 */
	private double m_weights[];
	
   /** 
    * Class constructor.
    * @param N Number of inputs of the Perceptron
    */
	public Perceptron(int N)
	{
		m_inputs = N;
		m_weights = new double[N+1];
	}
	
	/** 
	 * Calculates the Perceptron output
	 * @param inputs The inputs of the perceptron
	 * @return The output of the perceptron
	 */
	public double process(double inputs[]) throws InvalidParameterException
	{
		if (inputs.length != m_inputs) throw new InvalidParameterException();
		
		double result = 0.0;
		
		for (int i = 0; i < m_inputs; ++i)
			result += (inputs[i])*m_weights[i];
		
		if (result >= m_weights[m_inputs]) return 1.0;
		else return -1.0;
	}
	
	/** 
	 * Trains the perceptron with the set of input/output pairs
	 * @param inputs A matrix of inputs of the perceptron
	 * @param output A vector of the outputs to be trained
	 * @param rate The learning rate
	 * @param maxIterations The maximum number of iterations for training
	 * @return The number of errors in the training set
	 */	
	public int train(double inputs[][], double output[], double rate, int maxErrors, int maxIterations) throws InvalidParameterException
	{
		
		if (inputs.length != output.length || maxIterations < 1) throw new InvalidParameterException();
		
		int error = maxErrors + 1;
		
		int count = 0;
		
		ArrayList<Integer> lst = new ArrayList<Integer>();
		for (int i = 0; i < output.length; ++i)
			lst.add(i);
		
		int bestError = inputs.length + 1;
		double bestWeights[] = new double[m_weights.length]; 
		
		while (error > maxErrors && ++count < maxIterations)
		{
			Collections.shuffle(lst);
			error = 0;
			for (int i = 0; i < inputs.length;++i)
			{
				int example = lst.get(i);
				double out = process(inputs[example]);
				double e = output[example] - process(inputs[example]);
				if (differentOutputs(output[example],out))
				{
					++error;
					for (int j = 0; j < m_inputs; ++j)
					{
						m_weights[j] += rate*e*inputs[example][j];
					}
					
					m_weights[m_inputs] += -rate*e;
				}
			}
			if (error < bestError)
			{
				bestError = error;
				System.arraycopy(m_weights, 0, bestWeights, 0, m_weights.length);
			}
		}
		System.arraycopy(bestWeights, 0, m_weights, 0, m_weights.length);

		return bestError;
	}
   /** 
    * Checks if the outputs are different. Uses the step function to determine
    * the real output value. (x > 0.0 -> Out = 1.0; x <= 0.0 -> Out = -1.0) 
    * @param boardWidth The width of the board.
    * @param boardHeight The height of the board.
    * @return True if the values are different.
    */
	public static boolean differentOutputs(double o1, double o2)
	{
		return o1 > 0.0 && o2 <= 0.0 || o1 <= 0.0 && o2 > 0.0;
	}
}
