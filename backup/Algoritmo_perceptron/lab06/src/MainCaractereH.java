import java.util.Random;


public class MainCaractereH {
	public static void main(String[] args) throws Exception {
		//cria a matriz de entrada aleatoria
		System.out.println("Matriz de entrada");
		double[][] entradas = new double[9][1];
		Random r = new Random();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 1; j++) {
				if(i % 3 == 0) System.out.println("");
				entradas[i][j] = (double)r.nextInt(2);
				System.out.print(entradas[i][j] + " ");
			}
		}
		System.out.println("\n--------------");
		
		System.out.println("Matriz H(saida)");
		//cria a matriz de saída H
		double saidas[] = new double[9];
		saidas[0] = 1.0;
		saidas[1] = 0.0;
		saidas[2] = 1.0;
		saidas[3] = 1.0;
		saidas[4] = 1.0;
		saidas[5] = 1.0;
		saidas[6] = 1.0;
		saidas[7] = 0.0;
		saidas[8] = 1.0;
							
		for (int i = 0; i < saidas.length; i++) {
			if(i % 3 == 0) System.out.println("");
				System.out.print(saidas[i] + " ");
		}
		System.out.println("\n--------------");
		
		Perceptron p = new Perceptron(1);
		
		if (!p.treina(0.2, entradas, saidas, 1000))
			System.out.println("Erro no treinamento");
	
							
		
	}
	
}
