package ia.examples;

import ia.rna.PerceptronEnsemble;
import ia.util.CsvDBRead;

public class PerceptronEnsembleXOR {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length==0)
		{
			System.out.println("No input file specified in args");
			System.exit(-1);
		}
		
		CsvDBRead db = new CsvDBRead();
		try{
			db.readDB(args[0]);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		double inputs[][] = db.getInputs();
		double outputs[] = db.getOutputs();
		
		PerceptronEnsemble p = new PerceptronEnsemble(2,3);

		try{
			p.train(inputs, outputs, 0.2, 0, 100);
			
			System.out.println("00 -> " + (p.process(inputs[0]) > 0.0 ? 1 : 0 ));
			System.out.println("01 -> " + (p.process(inputs[1]) > 0.0 ? 1 : 0 ));
			System.out.println("10 -> " + (p.process(inputs[2]) > 0.0 ? 1 : 0 ));
			System.out.println("11 -> " + (p.process(inputs[3]) > 0.0 ? 1 : 0 ));
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
