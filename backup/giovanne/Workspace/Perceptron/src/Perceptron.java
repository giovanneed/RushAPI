
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
		
		
//	   System.out.println("pesos:" + pesos[0]);
//	   System.out.println("entradas[0]" + entradas[0]);
//	   System.out.println("pesos[1] " + pesos[1]);
//	   System.out.println("entradas[1]" + entradas[1]);
//	   System.out.println("pesos[2] " + pesos[2]);
		
	   if ( (-1 * pesos[0]) + (entradas[0]*pesos[1])+ (entradas[1]*pesos[2]) >= 0){
		   return 1;
	   }
	   return 0;
		
		
		
		//return entradas[0]*entradas[1];
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
		
		//(int i=0;i<n-1;i++)
		int c=0;
		while(c<maxIter){
			
		
		for(int i=0;i<n;i++){
		
			double y = calcula(entradas[i]);
			
			System.out.println("Resultado: " + y);
			System.out.println("Resultado Esperado: " + saidas[i]);
			
			if(y!=saidas[i]){
				
				//verifica maxInter
				//erro = 
				
				ajustaPesos(taxa, saidas[i]-calcula(entradas[i]),entradas[i]);
				
			}else{
				System.out.println("CERTO");
			}
			
		}
		
		c++;
		}
		
		
		return true;
	}
	
	public static void main(String[] args) {
		//2 entradas
		Perceptron p = new Perceptron(4);
	
		
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

}
