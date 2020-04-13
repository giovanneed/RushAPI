package Monitor;
import java.util.Random;


public class Canibal extends Thread {
	
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
