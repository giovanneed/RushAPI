#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int *tabuleiro, *positiva, *negativa, numdiagswap;

// Fun��o que executa o embaralhamento do vetor tabuleiro
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

// Fun��o que verifica se h� colis�o entre as rainhas
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
    time_t tempo1, tempo2, tempo_tot;
    int teste;
    
    // Consist�ncia da chamada com dois par�metros
    if (argc != 2) {
       printf("C:\\>rainhas\nUtilizacao: rainhas <tamanho>\n<tamanho>: quantidade de rainhas.");
       exit(-1);
    }

    // Consist�ncia da quantidade de Rainhas / Tamanho do Tabuleiro
    tamanho = atoi(argv[1]);
    if (tamanho < 4) {
       printf("C:\\>rainhas\nUtilizacao: rainhas <tamanho>\n<tamanho>: quantidade de rainhas.");
       exit(-1);
    }
    
    // Defini��o da quantidade de diagonais pares e �mpares
    numdiag = 2*tamanho-1;
    
    // Aloca��o de mem�ria do vetor do tabuleiro
    tabuleiro = (int*)malloc(tamanho*sizeof(int));
    if (tabuleiro == NULL) {
       printf("Falha de aloca��o do Tabuleiro...");
       exit(-1);
    }
     
    //// Aloca��o de mem�ria dos vetores das numdiag
    positiva = (int*)malloc(numdiag*sizeof(int));
    negativa = (int*)malloc(numdiag*sizeof(int));

    // Defini��o inicial do vetor de linhas/colunas
    for (i=0; i<tamanho; i++) {
        tabuleiro[i] = i;
    }

    // Defini��o inicial dos vetores das numdiag
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
    
    // Contagem de tempo
    tempo1 = time(NULL);
    
    // Loop para encontrar a solu��o
    while (1) {
   
        swap(tamanho);
        teste = colisao(numdiag);
        
        if (teste == 0) {
           tempo2 = time(NULL);
           tempo_tot = tempo2 - tempo1;
           printf("Solucao encontrada.\n");
           printf("Tempo de execucao: %ld segundo(s)\n",tempo_tot);
           break;
        }        
    }
    
    printf("Quantidade de execucoes: %d\n",aux);
    
    // Libera��o de mem�ria
    free(tabuleiro);
    free(positiva);
    free(negativa);
}
