package Semaforo;

public class Cozinheiro extends Thread {
	private JantarDosCanibais panela;
	
	public Cozinheiro(JantarDosCanibais panela) {
		this.panela = panela;
	}
	
	@Override
	public void run() {
		while(true)
			try {
				panela.colocarPorçõesNaPanela(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}
