package ia.rna;

import java.util.*;

/**
 * Implements an ensemble model of Perceptrons. Uses Bagging to 
 * define the output of the model. 
 * 
 * @author  Alair Dias Júnior
 * @version 1.0
 */
public class PerceptronEnsemble {
	
	int m_inputs;
	int m_classifiers;
	Perceptron m_nodes[];
	
	/** 
    * Class constructor.
    * @param inputs Number of inputs of the Model
    * @param classifiers Number of classifiers of the model
    */
	public PerceptronEnsemble(int inputs, int classifiers)
	{
		m_inputs = inputs;
		m_classifiers = classifiers;
		m_nodes = new Perceptron[classifiers];
		for (int i = 0; i < classifiers; ++i)
			m_nodes[i] = new Perceptron(inputs);
	}
	
	/** 
	    * Process the output of the model.
	    * @param inputs The inputs applied to the model
	    * @return The output of the model
	    */
	public double process(double inputs[]) throws Perceptron.InvalidParameterException
	{
		return process(inputs, m_classifiers);
	}
	
	private double process(double inputs[], int classifiers) throws Perceptron.InvalidParameterException
	{
		int votes = 0;
		for (int i = 0; i < classifiers; ++i)
		{
			if (m_nodes[i].process(inputs) > 0.0) ++votes;
		}
		
		if (votes > m_classifiers / 2) return 1.0;
		else return -1.0;
	}
	
	/** 
	 * Trains the perceptron ensemble with the set of input/output pairs
	 * @param inputs A matrix of inputs of the perceptron
	 * @param output A vector of the outputs to be trained
	 * @param rate The learning rate
	 * @param maxErrors Maximum number of errors 
	 * @param maxIterations The maximum number of iterations for training
	 * @return The number of errors in the training set
	 */	
	public int train(double inputs[][], double output[], double rate, int maxErrors, int maxIterations) throws Perceptron.InvalidParameterException
	{
		int count = 0;
		int totalErrors = 0;
		int bestErrors = inputs.length + 1;
		
		Perceptron bestNodes[] = new Perceptron[m_classifiers];
		for (int i = 0; i < m_classifiers; ++i)
			bestNodes[i] = new Perceptron(m_inputs);
		
		while(++count < maxIterations)
		{
			m_nodes[0].train(inputs, output, rate, maxErrors, maxIterations);	
			for (int i = 1; i < m_classifiers; ++i)
			{
				ArrayList<Integer> errors = errorExamples(i-1,inputs, output);
				double [][] newI = getNewInputs(errors,inputs);
				double []   newO = getNewOutputs(errors, output);
		
				m_nodes[i].train(newI, newO, rate, maxErrors, maxIterations);
			}
			totalErrors = countErrors(inputs, output);
			if (totalErrors < bestErrors)
			{
				bestErrors = totalErrors;
				System.arraycopy(m_nodes, 0, bestNodes, 0, m_classifiers);
			}
			if ( totalErrors <= maxErrors) break;
			
		}
		System.arraycopy(bestNodes, 0, m_nodes, 0, m_classifiers);
		return bestErrors;
	}
	
	private int countErrors(double inputs[][], double output[]) throws Perceptron.InvalidParameterException
	{
		int count = 0;
		for (int i = 0; i < inputs.length; ++i)
		{
			if (Perceptron.differentOutputs(process(inputs[i]), output[i]))
			{
				++count;
			}
		}
		return count;
	}
	
	private ArrayList<Integer> errorExamples(int node, double inputs[][], double output[]) throws Perceptron.InvalidParameterException
	{
		ArrayList<Integer> lst = new ArrayList<Integer>();
		for (int i = 0; i < inputs.length; ++i)
		{
			//if (Perceptron.differentOutputs(m_nodes[node].process(inputs[i]), output[i]) || Math.random() < 0.2)
			/*if (Math.random() < 0.8)
			{
				lst.add(new Integer(i));
			}*/
			//if (lst.size() < 1) lst.add((int)(Math.random() * (double)inputs.length));
			lst.add((int)(Math.random() * (double)inputs.length));
			
		}
		return lst;
	}
	
	private double[][] getNewInputs(ArrayList<Integer> lst, double inputs[][])
	{
		double newInputs[][] = new double[lst.size()][m_inputs];
		for (int i = 0; i < lst.size(); ++i)
		{
			for (int j = 0; j < inputs[0].length; ++j)
				newInputs[i][j] = inputs[lst.get(i)][j];
		}
		return newInputs;
	}
	
	private double[] getNewOutputs(ArrayList<Integer> lst, double output[])
	{
		double newOutputs[] = new double[lst.size()];
		for (int i = 0; i < lst.size(); ++i)
		{
			newOutputs[i] = output[lst.get(i)];
		}
		return newOutputs;
	}

}
