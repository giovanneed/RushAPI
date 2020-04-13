/**
 * 
 */
package ia.examples;

import ia.rna.Perceptron;


/**
 * Test of the perceptron class
 * 
 * @author  Alair Dias Júnior
 * @version 1.0
 */
public class PerceptronTwoLetterDiscriminator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Perceptron p = new Perceptron(9);
		
		double inputs[][] = {{1,1,1,0,1,0,0,1,0},{1,0,1,1,1,1,1,0,1}};
		double output[] = {1,0};
		
		double test[][] = {{1,0,1,0,1,0,0,1,0},{1,0,1,1,0,1,1,0,1}};
		
		try{
			p.train(inputs, output, 0.2, 0, 1000);
			
			System.out.println("T - " + p.process(test[0]));
			System.out.println("H - " + p.process(test[1]));
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
