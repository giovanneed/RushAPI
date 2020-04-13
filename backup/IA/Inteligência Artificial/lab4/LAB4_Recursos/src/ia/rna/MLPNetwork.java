package ia.rna;

//import java.util.ArrayList;
//import java.util.Collections;

//import ia.rna.Perceptron.InvalidParameterException;


/**
 * MLP Network Definition  
 * 
 * @author  Alair Dias Júnior
 * @version 1.0
 */
public class MLPNetwork {

	/*
	int m_inputs;
	int m_hiddenLayers;
	int m_nodesPerLayer;
	ActivationFunction m_function;
	ActivationFunction m_outFunction;
	Node m_nodes[][];
	Node m_outNode;
	
	private class Node
	{
		public Node(int inputs)
		{
			weights = new double[inputs + 1];
		}
		double [] weights;
		double delta;
		double net;
	}
	
	public MLPNetwork(int inputs, int hiddenLayers, int nodesPerLayer, ActivationFunction function, ActivationFunction outFunction)
											throws Perceptron.InvalidParameterException
	{
		if (hiddenLayers < 1) throw new Perceptron.InvalidParameterException();
		m_inputs = inputs;
		m_hiddenLayers = hiddenLayers;
		m_function = function;
		m_outFunction = outFunction;
		m_nodesPerLayer = nodesPerLayer;
		createNetwork();
	}

	private void createNetwork()
	{
		m_nodes = new Node[m_hiddenLayers][m_nodesPerLayer];
		
		for (int j = 0; j < m_nodesPerLayer; ++j)
			m_nodes[0][j] = new Node(m_inputs);
		
		for (int i = 1; i < m_hiddenLayers; ++i)
			for (int j = 0; j < m_nodesPerLayer; ++j)
				m_nodes[i][j] = new Node(m_nodesPerLayer);
		
		m_outNode = new Node(m_nodesPerLayer);
	}
		
	public double process(double input[]) throws Perceptron.InvalidParameterException
	{
		if (input.length != m_inputs) throw new Perceptron.InvalidParameterException();

		for (int j = 0; j < m_nodesPerLayer; ++j)
		{
			m_nodes[0][j].net = 0.0;
			for (int k = 0; k < m_inputs; ++k)
				m_nodes[0][j].net += input[k]*m_nodes[0][j].weights[j];
			
			m_nodes[0][j].net += -1*m_nodes[0][j].weights[m_inputs];
		}
		
		for (int i = 1; i < m_hiddenLayers; ++i)
		{
			for (int j = 0; j < m_nodesPerLayer; ++j)
			{
				m_nodes[i][j].net = 0.0;
				for (int k = 0; k < m_nodesPerLayer; ++k)
					m_nodes[i][j].net += m_function.calc(m_nodes[i-1][k].net)*m_nodes[i][j].weights[j];
				
				m_nodes[i][j].net += -1*m_nodes[i][j].weights[m_nodesPerLayer];
			}
		}
		
		m_outNode.net = 0.0;
		for (int k = 0; k < m_nodesPerLayer; ++k)
			m_outNode.net += m_function.calc(m_nodes[m_hiddenLayers-1][k].net)*m_outNode.weights[k];
			
			m_outNode.net += -1*m_outNode.weights[m_nodesPerLayer];
		
		return m_outFunction.calc(m_outNode.net);
	}
	
	public double train(double inputs[][], double output[], double rate, double maxError, int maxIterations) throws Perceptron.InvalidParameterException
	{
		if (inputs.length != output.length || inputs[0].length != m_inputs) throw new Perceptron.InvalidParameterException();
		
		double mseValue = 0.0;
		
		ArrayList<Integer> lst = new ArrayList<Integer>();
		for (int i = 0; i < output.length; ++i)
			lst.add(i);
		
		do
		{
			Collections.shuffle(lst);
			
			for (int l = 0; l < output.length; ++l)
			{
				
				// Update the output layer's weights
				int example = lst.get(l);
				m_outNode.delta = m_outFunction.derivative(m_outNode.net)*(output[example] - process(inputs[example]));
				
				for (int k = 0; k < m_nodesPerLayer; ++k)
					m_outNode.weights[k] += rate*m_outNode.delta*m_function.calc(m_nodes[m_hiddenLayers-1][k].net);
				m_outNode.weights[m_nodesPerLayer] -= rate*m_outNode.delta;
				
				// Update the others hidden Layers				
				for (int i = m_hiddenLayers - 1; i >=0; --i)
				{
					int layerOutputs = (i == m_hiddenLayers - 1 ? 1 : m_nodesPerLayer);
					int layerInputs = (i == 0 ? m_inputs : m_nodesPerLayer);
					for (int j = 0; j < m_nodesPerLayer; ++j)
					{
						// calculates the delta for the node
						//m_node[i][j]
					}
				}
				
				for (int j = 0; j < m_nodesPerLayer; ++j)
				{
					m_nodes[m_hiddenLayers-1][j].delta = m_outNode.delta*m_outNode.weights[j];
					
					for (int k = 0; k < m_nodesPerLayer; ++k)
						m_nodes[m_hiddenLayers-1][j].weights[k] += rate*m_nodes[m_hiddenLayers-1][j].delta*m_function.calc(m_nodes[m_hiddenLayers-2][k].net);
					m_nodes[m_hiddenLayers-1][j].weights[m_inputs] -= rate*m_nodes[m_hiddenLayers-1][j].delta;
				}
				
				for (int i = m_hiddenLayers - 2; i > 1; --i)
				{
					for (int j = 0; j < m_nodesPerLayer; ++j)
					{
						m_nodes[i][j].delta = 0.0;
						for (int k = 0; k < m_nodesPerLayer; ++k)
							m_nodes[i][j].delta += m_nodes[i+1][k].delta*m_nodes[i+1][k].weights[j];
						
						for (int k = 0; k < m_nodesPerLayer; ++k)
							m_nodes[i][j].weights[k] += rate*m_nodes[i][j].delta*inputs[example][k];
						m_nodes[m_hiddenLayers-1][j].weights[m_inputs] -= rate*m_nodes[m_hiddenLayers-1][j].delta;
					}	
				}
				
			}
			
			
		}while(mseValue > maxError);
		
		
		return 0.0;
	}	
	
	private double delta(int layer)
	{
		return 0.0;
	}
	
	private double mse(double inputs[][], double output[]) throws Perceptron.InvalidParameterException
	{
		if (inputs.length != output.length || inputs[0].length != m_inputs) throw new Perceptron.InvalidParameterException(); 
		double error = 0.0;
		for (int i = 0; i < inputs.length; ++i)
		{
			error += Math.pow(output[i] - process(inputs[i]), 2.0);
		}
		
		return error/2.0;
	}*/
	
}
