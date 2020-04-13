import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Main {

	
	public static void main(String[] args) throws Exception {
		Random r = new Random();
		
		boolean valorInvalido = true;
		JPanel mainPanel = new JPanel();
		int numeroEntradas = 0;
		
		while(valorInvalido){
			try{
				String msg = JOptionPane.showInputDialog("Digite o número de entradas: "); 
				numeroEntradas = Integer.parseInt(msg);
				if(numeroEntradas < 2) {
					JOptionPane.showMessageDialog(mainPanel, "Valor inválido!", "Erro:", JOptionPane.ERROR_MESSAGE);
					valorInvalido = true;
				}
				else valorInvalido = false;
			}catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(mainPanel, "Valor inválido!", "Erro:", JOptionPane.ERROR_MESSAGE);
				valorInvalido = true;
			}
		}
		
		
		//Custom button text
		Object[] options = {"And",
		                    "Or",
		                    "XOR"};
		int portaLogica = JOptionPane.showOptionDialog(mainPanel,
		    "Selecione a porta:",
		    "Porta logica",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[2]);
		
		
		
		
		System.out.println("ENTRADAS:");
		//entradas com valores entre 0 e 1
		double[][] entradas = {{0,0,0},
	               {0,0,1},
	               {0,1,0},
	               {0,1,1},
	               {1,0,0},
	               {1,0,1},
	               {1,1,0},
	               {1,1,1},
};//new double[numeroEntradas][numeroEntradas*2];
//		for (int i = 0; i < numeroEntradas; i++){
//			for (int j = 0; j < entradas.length; j++) {
//				entradas[i][j] = (double)r.nextInt(2);
//				System.out.print(entradas[i][j]+ " ");
//			}
//			System.out.println(" ");
//		}	
		System.out.println("PESOS:");
		//pesos com valores entre -1 e 1
		double[] pesos = new double[numeroEntradas];
		for (int i = 0; i < numeroEntradas; i++){ 
			pesos[i] = r.nextDouble()*2-1;
			System.out.println(pesos[i]);
		}	
		
		
		if (portaLogica == 0)
			System.out.println("Treinado como AND");
		else if (portaLogica == 1)
			System.out.println("Treinado como OR");
		else
			System.out.println("Treinado como XOR");
		
		
		
		//cria o vetor de saídas
		double[] saidas = new double[numeroEntradas];
		for (int i = 0; i < saidas.length; i++) {
			if (portaLogica == 0)
				saidas[i] = Perceptron.calculaSaidaPortaAnd(entradas[i]);
			else if (portaLogica == 1)
				saidas[i] = Perceptron.calculaSaidaPortaOr(entradas[i]);
			else
				saidas[i] = Perceptron.calculaSaidaPortaXor(entradas[i]);
		}
		System.out.println("SAIDAS:");
		for (int i = 0; i < saidas.length; i++) {
			System.out.println(saidas[i]);
		}
		Perceptron p = new Perceptron(numeroEntradas);
		
		if (!p.treina(0.2, entradas, saidas, 1000))
			System.out.println("Erro no treinamento");
		
	
		
		
		
		

	}

}
