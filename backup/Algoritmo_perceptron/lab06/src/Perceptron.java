import java.util.Random;


public class Perceptron {
	private int n;
	private double[] pesos;
	
	public Perceptron(int n){
		this.n = n;
		Random r = new Random();
		pesos = new double[n+1];
		for (int i = 1; i < n+1; i++){ 
			pesos[i] = r.nextDouble()*2-1;
			//System.out.println(pesos[i]);
		}
	}
	public double calcula(double[] x) throws Exception{
		double soma = 0;
		
		if (x.length != pesos.length-1) throw new Exception("Tamanho dos vetores é incompativel");
		for (int i = 0; i < x.length; i++) {
			soma += x[i] * pesos[i];
		}
		soma -= pesos[n];
		if(soma >= 0) return 1.0;
		else return 0.0;
	}

	public void ajustaPesos(double taxa,double erro,double[] x){
		for (int i = 0; i < x.length; i++) {
			pesos[i] += taxa*erro*x[i];
		}
		pesos[n] -= taxa*erro;
	}
	public boolean treina(double taxa, double[][] x,double[] yd,int maxIter) throws Exception{
		double[] aprendido = new double[x.length];;
		int count = 0;
		for (int i = 0; i < x.length; i++) { //itera pelas linhas da matriz
			while( calcula(x[i]) != yd[i] ){
				//itera até que o valor calculado seja igual ao esperado(vai atualizando os pesos até que o perceptron aprenda)
				if(count == maxIter) return false;//ultrapassou o limite maximo de iteracoes e nao conseguiu aprender
				ajustaPesos(taxa, yd[i]-calcula(x[i]), x[i]);
				count++;
			}
			aprendido[i] =  calcula(x[i]);
			//System.out.println("calculado: " + calcula(x[i]) + " | esperado: " + yd[i] );
		}
		
		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print((int)x[i][j] + " ");
			}
			System.out.println("-> " + (int)aprendido[i]);
		}
	
		System.out.println("TOTAL DE ITERAÇÕES: " + count);
		return true; //treino realizado com sucesso;		
	}
	
	public static int calculaSaidaPortaAnd(double[] x){
		int saida = (int)x[0];
		for (int i = 1; i < x.length; i++) 
			saida &= (int)x[i];
		return saida;
	}
	public static int calculaSaidaPortaOr(double[] x){
		int saida = (int)x[0];
		for (int i = 1; i < x.length; i++) 
			saida |= (int)x[i];
		return saida;
	}
	public static int calculaSaidaPortaXor(double[] x){
		int saida = (int)x[0];
		for (int i = 1; i < x.length; i++) 
			saida ^= (int)x[i];
		return saida;
		
	}
	
}
