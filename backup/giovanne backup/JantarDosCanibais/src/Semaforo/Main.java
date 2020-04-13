package Semaforo;

public class Main {

	
	public static void main(String[] args) {
		JantarDosCanibais panela = new JantarDosCanibais();
		new Cozinheiro(panela).start();
		
		for(int i = 1; i <= 4; i++)
			new Canibal(i, panela).start();

	}

}
