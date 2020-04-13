/**
 * 
 */
package ia.rna;

/**
 * Tanh activation function  
 * 
 * @author  Alair Dias Júnior
 * @version 1.0
 */
public class Tanh implements ActivationFunction {
	/** 
	 * Calculates the value of the function for the input value
	 * @param value the input value of the function
	 * @return The output value of the function
	 */
	public double calc(double value)
	{
		return Math.tanh(value);
	}
	
	/** 
	 * Calculates the value of the derivative of the function for the input value
	 * @param value the input value of the function
	 * @return The derivative of the value of the function
	 */
	public double derivative(double value)
	{
		return 1.0/Math.pow(Math.cosh(value), 2);
	}
}
