package Semaforo;
import java.util.concurrent.Semaphore;


public class JantarDosCanibais {
	private int porcoes = 0;
	private final Semaphore panelacheia = new Semaphore(0);//como a panela come�a vazia,n�o existem permissoes para os canibais comerem.
	private final Semaphore panelavazia = new Semaphore(1);//como a panela come�a vazia, existe uma permissao para o cozinheiro encher a panela.
	private final Semaphore mutex = new Semaphore(1);
		
	//fun��o chamada pelos canibais
	public void pegarPor��oDaPanela(int id) throws InterruptedException {
			panelacheia.acquire();//libera um canibal para comer,se nao houver permissao para comer,ele fica bloqueado.
		
			mutex.acquire();           //entra na regi�o critica
			porcoes--;                 //decrementa uma porcao   
			System.out.println("Canibal " + id + " pegou uma por��o (restante = " + porcoes + ")");
			mutex.release();           //sai da regi�o critica
			
			if(porcoes == 0) panelavazia.release();  //libera uma permiss�o para o cozinheiro encher a panela
	}
	
	//fun��o chamada pelo cozinheiro
	public void colocarPor��esNaPanela(int qtd) throws InterruptedException {
			System.out.println("Cozinheiro dormindo");
		    panelavazia.acquire();
		    System.out.println("Cozinheiro acordado");
		
			mutex.acquire();                  //entra na regi�o critica
			porcoes += qtd;                   //serve as por��es 
			System.out.println("Cozinheiro colocou " + qtd + " por��es na panela");
			mutex.release();                  //sai da regi�o critica
			
			panelacheia.release(qtd); //colo�a 10 por��es na panela e libera que 10 canibais peguem as por��es na panela.
	}
}
