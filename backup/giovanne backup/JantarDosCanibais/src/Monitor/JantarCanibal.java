package Monitor;



public class JantarCanibal {

	
	public static void main(String[] args) {
		PanelaMonitor panela = new PanelaMonitor();
		new Cozinheiro(panela).start();
		
		for(int i = 1; i <= 4; i++)
			new Canibal(i, panela).start();

	}

}
