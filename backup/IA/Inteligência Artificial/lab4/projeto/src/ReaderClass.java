import ia.rna.PerceptronEnsemble;
import ia.util.CsvDBRead;

public class ReaderClass {
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
		
		for (int i=0; i<outputs.length;i++) {
			if (outputs[i] == 0) outputs[i] = -1;
			else outputs[i] = 1;
		}
		
		PerceptronEnsemble p = new PerceptronEnsemble(13,5);
		
		try{
			int soma = 0;
			for (int j=0; j<10; j++) {
				soma += p.train(inputs, outputs, 0.2, 0, 100);
			}
			double media = soma/10;
			double perc = media/297 * 100;
			System.out.println("Media = " +media);
			System.out.println("Porcentagem = " +perc);			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}