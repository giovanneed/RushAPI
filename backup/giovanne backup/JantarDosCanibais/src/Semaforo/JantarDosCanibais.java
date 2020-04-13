package Semaforo;
import java.util.concurrent.Semaphore;


public class JantarDosCanibais {
	private int porcoes = 0;
	private final Semaphore panelacheia = new Semaphore(0);//como a panela começa vazia,não existem permissoes para os canibais comerem.
	private final Semaphore panelavazia = new Semaphore(1);//como a panela começa vazia, existe uma permissao para o cozinheiro encher a panela.
	private final Semaphore mutex = new Semaphore(1);
		
	//função chamada pelos canibais
	public void pegarPorçãoDaPanela(int id) throws InterruptedException {
			panelacheia.acquire();//libera um canibal para comer,se nao houver permissao para comer,ele fica bloqueado.
		
			mutex.acquire();           //entra na região critica
			porcoes--;                 //decrementa uma porcao   
			System.out.println("Canibal " + id + " pegou uma porção (restante = " + porcoes + ")");
			mutex.release();           //sai da região critica
			
			if(porcoes == 0) panelavazia.release();  //libera uma permissão para o cozinheiro encher a panela
	}
	
	//função chamada pelo cozinheiro
	public void colocarPorçõesNaPanela(int qtd) throws InterruptedException {
			System.out.println("Cozinheiro dormindo");
		    panelavazia.acquire();
		    System.out.println("Cozinheiro acordado");
		
			mutex.acquire();                  //entra na região critica
			porcoes += qtd;                   //serve as porções 
			System.out.println("Cozinheiro colocou " + qtd + " porções na panela");
			mutex.release();                  //sai da região critica
			
			panelacheia.release(qtd); //coloça 10 porções na panela e libera que 10 canibais peguem as porções na panela.
	}
}
