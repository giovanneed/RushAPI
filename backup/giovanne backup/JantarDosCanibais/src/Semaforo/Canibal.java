package Semaforo;
import java.util.Random;


public class Canibal extends Thread {
	
	private int id;
	private JantarDosCanibais panela;
	
	private final Random r = new Random();
	
	public Canibal(int id, JantarDosCanibais panela) {
		this.id = id;
		this.panela = panela;
	}
	
	@Override
	public void run() {
		while(true) {
			System.out.println("Canibal " + id + " faminto");
			try {
				panela.pegarPorçãoDaPanela(id);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
