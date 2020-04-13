package Monitor;

public class Cozinheiro extends Thread {
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