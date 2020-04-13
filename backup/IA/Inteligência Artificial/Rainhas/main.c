#include <stdio.h>
#include <stdlib.h>

int *tabuleiro, *positiva, *negativa, numdiagswap;

// Função que executa o embaralhamento do vetor tabuleiro
void swap(int tam) {
     int i, aux1, aux2, aux, arand, atam;
     
     // Swap dentro do vetor de linhas/colunas
         aux = rand()%tam;
         atam = tam/2;
         arand = rand()%atam;
         while (arand == aux){
               arand = rand()%atam;
         }
         if (aux >= tam/2)
         {   
           aux1 = tabuleiro[aux];
           aux2 = tabuleiro[aux-arand];
           positiva[aux1+aux]--;
           positiva[aux2+(aux-arand)]--;
           negativa[(aux-aux1)+(tam-1)]--;
           negativa[((aux-arand)-aux2)+(tam-1)]--;
           tabuleiro[aux] = aux2;
           tabuleiro[aux-arand] = aux1;
           positiva[aux+tabuleiro[aux]]++;
           positiva[(aux-arand)+tabuleiro[aux-arand]]++;          
           negativa[aux-tabuleiro[aux]+(tam-1)]++;
           negativa[(aux-arand)-tabuleiro[aux-arand]+(tam-1)]++;         
         }
         else 
         {  
           aux1 = tabuleiro[aux];
           aux2 = tabuleiro[aux+arand];
           positiva[aux1+aux]--;
           positiva[aux2+(aux+arand)]--;
           negativa[(aux-aux1)+(tam-1)]--;
           negativa[((aux+arand)-aux2)+(tam-1)]--;           
           tabuleiro[aux] = aux2;
           tabuleiro[aux+arand] = aux1;
           positiva[aux+tabuleiro[aux]]++;
           positiva[(aux+arand)+tabuleiro[aux+arand]]++;          
           negativa[aux-tabuleiro[aux]+(tam-1)]++;
           negativa[(aux+arand)-tabuleiro[aux+arand]+(tam-1)]++;  
         }
}

// Função que verifica se há colisão entre as rainhas
int colisao(int diagaux) {
     int i;
     for (i=0; i<diagaux; i++) {
         if ((positiva[i]-1 > 0) || (negativa[i]-1 > 0)) {
            return 1;
            break;
         }
     }
     return 0;
}

int main(int argc, char *argv[])
{
    int tamanho, i, numdiag;
    long int aux,aux1, aux2;
    
    // Consistência da chamada com dois parâmetros
    if (argc != 2) {
       printf("C:\\>rainhas\nUtilizacao: rainhas <tamanho>\n<tamanho>: quantidade de rainhas.");
       exit(-1);
    }

    // Consistência da quantidade de Rainhas / Tamanho do Tabuleiro
    tamanho = atoi(argv[1]);
    if (tamanho < 4) {
       printf("C:\\>rainhas\nUtilizacao: rainhas <tamanho>\n<tamanho>: quantidade de rainhas.");
       exit(-1);
    }
    
    // Definição da quantidade de diagonais pares e ímpares
    numdiag = 2*tamanho-1;
    
    // Alocação de memória do vetor do tabuleiro
    tabuleiro = (int*)malloc(tamanho*sizeof(int));
    if (tabuleiro == NULL) {
       printf("Falha de alocação do Tabuleiro...");
       exit(-1);
    }
     
    //// Alocação de memória dos vetores das numdiag
    positiva = (int*)malloc(numdiag*sizeof(int));
    negativa = (int*)malloc(numdiag*sizeof(int));

    // Definição inicial do vetor de linhas/colunas
    for (i=0; i<tamanho; i++) {
        tabuleiro[i] = i;
    }

    // Definição inicial dos vetores das numdiag
    for (i=0; i<numdiag; i++) {
        if (i%2 == 0)
           positiva[i]= 1;
        else
           positiva[i] = 0;
        aux = (numdiag/2)-0,5;
        if (i == aux){
           negativa[i] = tamanho;
        }
        else 
           negativa[i] = 0;
    }
           
    int teste;
    aux1 = 0;    
    numdiagswap = numdiag;
    // Chamada da função swap
    while (1) {
        aux1 = gettime();    
        swap(tamanho);
        teste = colisao(numdiag);
        //aux++;
        if (teste == 0) {
           aux2 = gettime();
           aux = aux2 - aux1;
           printf("Solucao encontrada.\n");
           printf("Tempo de execução: %d",aux);
           break;
        }        
    }
    printf("Quantidade execucao: %d\n",aux);
    free(tabuleiro);
    free(positiva);
    free(negativa);
}
