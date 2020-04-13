package Monitor;



public class PanelaMonitor {
	
	private volatile int porcoes = 0;
	
	public synchronized void pegarPorcaoDaPanela(int id) {
		
		if(porcoes < 1) {
			System.out.println("Canibal " + id + " acordando cozinheiro");
			notifyAll();
		}
		
		while(porcoes < 1)
			try {wait();} catch (InterruptedException e){}
			
		porcoes--;
		System.out.println("Canibal " + id + " pegou uma porção (restante = " + porcoes + ")");
		
	}
	
	public synchronized void colocarPorcoesNaPanela(int qtd) {
		
		System.out.println("Cozinheiro dormindo");
		
		while(porcoes > 0)
			try {wait();} catch (InterruptedException e){}
			
		System.out.println("Cozinheiro acordado");
			
		porcoes += qtd;
		System.out.println("Cozinheiro colocou " + qtd + " porções na panela");
		notifyAll();
		
	}

}