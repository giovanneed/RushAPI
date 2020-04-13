public class PerceptronTest {
	
	public static void main(String[] args) throws Exception {
		
		Perceptron p2 = new Perceptron(2);
		
		double entradas[][] = {{0,0},
				               {0,1},
				               {1,0},
				               {1,1}};
		
		//TESTANDO O TREINAMENTO COMO AND
		System.out.println("Treinado como AND com 2 entradas");
		double saidas[] = new double[4];
		
		for (int i = 0; i < entradas.length; i++) {
			saidas[i] = Perceptron.calculaSaidaPortaAnd(entradas[i]);
		}
		
		if (!p2.treina(0.2, entradas, saidas, 1000))
			System.out.println("Erro no treinamento");
		
		System.out.println("--------------");

		// TESTANDO O TREINAMENTO COMO OR
		System.out.println("Treinado como OR com 2 entradas");
		
		for (int i = 0; i < entradas.length; i++) {
			saidas[i] = Perceptron.calculaSaidaPortaOr(entradas[i]);
		}
		
		if (!p2.treina(0.2, entradas, saidas, 1000))
			System.out.println("Erro no treinamento");
		
		System.out.println("--------------");
		
		// TESTANDO O TREINAMENTO COMO XOR
		System.out.println("Treinado como XOR com 2 entradas");
		
		for (int i = 0; i < entradas.length; i++) {
			saidas[i] = Perceptron.calculaSaidaPortaXor(entradas[i]);
		}
		
		if (!p2.treina(0.2, entradas, saidas, 1000))
			System.out.println("Erro no treinamento");
		
		
		
		
		Perceptron p3 = new Perceptron(3);
		
		double entradas3[][] = {{0,0,0},  
				               {0,0,1},  
				               {0,1,0},  
				               {0,1,1},  
				               {1,0,0},  
				               {1,0,1},  
				               {1,1,0},  
				               {1,1,1},  
		};
		
		//TESTANDO O TREINAMENTO COMO AND
		System.out.println("Treinado como AND com 3 entradas");
		double saidas3[] = new double[8];
		
		for (int i = 0; i < entradas3.length; i++) {
			saidas3[i] = Perceptron.calculaSaidaPortaAnd(entradas3[i]);
		}
		
		if (!p3.treina(0.2, entradas3, saidas3, 1000))
			System.out.println("Erro no treinamento");
		
		System.out.println("--------------");

		// TESTANDO O TREINAMENTO COMO OR
		System.out.println("Treinado como OR com 3 entradas");
		
		for (int i = 0; i < entradas3.length; i++) {
			saidas3[i] = Perceptron.calculaSaidaPortaOr(entradas3[i]);
		}
		
		if (!p3.treina(0.2, entradas3, saidas3, 1000))
			System.out.println("Erro no treinamento");
		
		System.out.println("--------------");
		
//		// TESTANDO O TREINAMENTO COMO XOR
		System.out.println("Treinado como XOR com 3 entradas");
		
		for (int i = 0; i < entradas3.length; i++) {
			saidas3[i] = Perceptron.calculaSaidaPortaXor(entradas3[i]);
		}
		
		if (!p3.treina(0.2, entradas3, saidas3, 1000))
			System.out.println("Erro no treinamento");
		
		
		
		
	}
}
