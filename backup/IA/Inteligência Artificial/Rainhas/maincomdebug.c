#include <stdio.h>
#include <stdlib.h>

int *tabuleiro, *positiva, *negativa, numdiagswap;

// Função que executa o embaralhamento do vetor tabuleiro
void swap(int tam) {
     int i, aux1, aux2, aux, arand, atam;
     
     // Swap dentro do vetor de linhas/colunas
     //for (i=0; i<tam; i++) {
         aux = rand()%tam;
         atam = tam/2;
         arand = rand()%atam;
         while (arand == aux){
//               printf("Debug teste random");
               arand = rand()%atam;
         }
         printf("\naux = %d, tam = %d, arandom = %d",aux,tam,arand);
         if (aux >= tam/2)
         {   
           printf("\nMaior que a metade");               
           aux1 = tabuleiro[aux];
           aux2 = tabuleiro[aux-arand];
           printf("\naux1 = %d, aux2 = %d", aux1, aux2);
           positiva[aux1+aux]--;
           positiva[aux2+(aux-arand)]--;
           negativa[(aux-aux1)+(tam-1)]--;
           negativa[((aux-arand)-aux2)+(tam-1)]--;
           for (i=0; i<tam;i++){
               printf("\n%d", tabuleiro[i]);
           }
           for (i=0; i<numdiagswap;i++){
               printf("\n%d - %d", positiva[i], negativa[i]);
           }
           tabuleiro[aux] = aux2;
           tabuleiro[aux-arand] = aux1;
           positiva[aux+tabuleiro[aux]]++;
           positiva[(aux-arand)+tabuleiro[aux-arand]]++;          
           negativa[aux-tabuleiro[aux]+(tam-1)]++;
           negativa[(aux-arand)-tabuleiro[aux-arand]+(tam-1)]++;         
           for (i=0; i<tam;i++){
               printf("\n%d", tabuleiro[i]);
           }
           for (i=0; i<numdiagswap;i++){
               printf("\n%d - %d", positiva[i], negativa[i]);
           }          
         }
         else 
         {  
              printf("\nMenor que a metade");                   
           aux1 = tabuleiro[aux];
           aux2 = tabuleiro[aux+arand];
           printf("\naux1 = %d, aux2 = %d", aux1, aux2);
           positiva[aux1+aux]--;
           positiva[aux2+(aux+arand)]--;
           negativa[(aux-aux1)+(tam-1)]--;
           negativa[((aux+arand)-aux2)+(tam-1)]--;           
           for (i=0; i<tam;i++){
               printf("\n%d", tabuleiro[i]);
           }
           for (i=0; i<numdiagswap;i++){
               printf("\n%d - %d", positiva[i], negativa[i]);
           }       
           tabuleiro[aux] = aux2;
           tabuleiro[aux+arand] = aux1;
           positiva[aux+tabuleiro[aux]]++;
           positiva[(aux+arand)+tabuleiro[aux+arand]]++;          
           negativa[aux-tabuleiro[aux]+(tam-1)]++;
           negativa[(aux+arand)-tabuleiro[aux+arand]+(tam-1)]++;  
           for (i=0; i<tam;i++){
               printf("\n%d", tabuleiro[i]);
           }
           for (i=0; i<numdiagswap;i++){
               printf("\n%d - %d", positiva[i], negativa[i]);
           }        
         }
     //}
     
     // Swap nos vetores de diagonais
//     for (i=0; i<digaux-2; i++) {
//         positiva[i] = 
//         aux1 = positiva[i];
//         positiva[i] = positiva[i+2];
//         positiva[i+2] = aux1;
         
//         aux2 = positiva[i];
//         negativa[i] = negativa[i+2];
//         negativa[i+2] = aux2;
//     }
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
    int aux;
    
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
    printf("Debug1\n");
    
    // Alocação de memória do vetor do tabuleiro
    tabuleiro = (int*)malloc(tamanho*sizeof(int));
    if (tabuleiro == NULL) {
       printf("Falha de alocação do Tabuleiro...");
       exit(-1);
    }
    printf("Debug2\n");
     
    //// Alocação de memória dos vetores das numdiag
    positiva = (int*)malloc(numdiag*sizeof(int));
    negativa = (int*)malloc(numdiag*sizeof(int));
    printf("Debug3\n");

    
    // Definição inicial do vetor de linhas/colunas
    for (i=0; i<tamanho; i++) {
        tabuleiro[i] = i;
    }
    printf("Debug4\n");

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
    printf("Debug5\n");
           
    for (i=0; i<tamanho;i++){
       printf("\n%d", tabuleiro[i]);
    }
    for (i=0; i<numdiag;i++){
       printf("\n%d - %d", positiva[i], negativa[i]);
    }  

    int teste;
    aux = 0;    
    numdiagswap = numdiag;
    // Chamada da função swap
    while (1) {
        swap(tamanho);
/*        swap(tamanho);
        swap(tamanho);
        swap(tamanho);
        swap(tamanho);
        swap(tamanho);
        swap(tamanho);
        swap(tamanho);
        swap(tamanho);
        swap(tamanho);
        swap(tamanho);
        swap(tamanho);
        swap(tamanho);
        swap(tamanho);
        swap(tamanho);*/
        printf("Debug6\n");
        teste = colisao(numdiag);
        aux++;
//        printf("%d",teste); 
        if (teste == 0) {           
           printf("Solucao encontrada.\n");
//           printf("Debug7\n");
           break;
        }        
    }
    printf("Quantidade execucao: %d\n",aux);
    printf("Debug8\n");
    free(tabuleiro);
    free(positiva);
    free(negativa);
}
