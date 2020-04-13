
public class Perceptron {

	private int n;
	
	private double[] pesos;

	public Perceptron(int n){
		this.n = n;
		pesos = new double[n+1];
		
		for(int i=0;i<n+1;i++){
			pesos[i]=0;
			
		}
		
	}
	
	

	public double calcula(double entradas[]){
		
		return entradas[0]*entradas[1];
	}
	
	//entrada = x saida=yd
	
	//y=x[i]*w[i]-w[n]
	//erro=yd-y
	private void ajustaPesos(double taxa, double erro,double entradas[]){
		for(int i = 0; i<n; i++){
			pesos[i] = pesos[i] + taxa * erro * entradas[i]; 	
		}
		pesos[n] = pesos[n] + taxa * erro * -1; 	
	}
	
	public boolean treina(double taxa, double entradas[][],double saidas[], double maxIter){
		
		for(int i=0;i<n-1;i++){
			calcula(entradas[i]);
			
		}
		
		
		
		return true;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//2 entradas
		Perceptron p = new Perceptron(2);
	
		
		double entradas[][] = {{0,0},
							   {0,1},
							   {1,0},
	                           {1,1}}; 
		
	
		
		
		double saidas[]     = {0,
				   			   0,
				               0,
				               1};
		
			
	     p.treina(0.2, entradas, saidas, 100);
		
	}

	public Perceptron(double[] pesos) {
		super();
		this.pesos = pesos;
	}



}