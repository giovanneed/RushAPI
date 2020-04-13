import java.util.Random;


public class JantarCanibal {

	public static void main(String[] args) {
		PanelaMonitor panela = new PanelaMonitor();
		new Cozinheiro(panela).start();
		
		for(int i = 1; i <= 4; i++)
			new Canibal(i, panela).start();
	}

}

class Canibal extends Thread {
	
	private int id;
	private PanelaMonitor panela;
	
	private final Random r = new Random();
	
	public Canibal(int id, PanelaMonitor panela) {
		this.id = id;
		this.panela = panela;
	}
	
	@Override
	public void run() {
		while(true) {
			System.out.println("Canibal " + id + " faminto");
			panela.pegarPorcaoDaPanela(id);
			comer();
		}
	}
	
	public void comer() {
		System.out.println("Canibal " + id + " comendo porção");
		try {
			Thread.sleep(r.nextInt(5) * 1000);
		} catch (InterruptedException e) {}
	}
	
}

class Cozinheiro extends Thread {
	private PanelaMonitor panela;
	
	public Cozinheiro(PanelaMonitor panela) {
		this.panela = panela;
	}
	
	@Override
	public void run() {
		while(true)
			panela.colocarPorcoesNaPanela(10);
	}
}

class PanelaMonitor {
	
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

