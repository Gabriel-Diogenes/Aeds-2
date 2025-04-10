#include <stdio.h>
#include <string.h>

// Função recursiva para inverter a string
void inverterString(char str[], int index, int n) {
    // Caso base: se o índice ultrapassar o comprimento da string, termina a recursão
    if (index >= n) {
        return;
    }

    // Chama recursivamente para a próxima posição
    inverterString(str, index + 1, n);
    
    // Imprime o caractere na posição 'index' (inverso)
    printf("%c", str[index]);
}

int main() {
    char entrada[100];

    // Lê as strings até a palavra "FIM"
    while (1) {
        // Lê uma linha de entrada
        fgets(entrada, sizeof(entrada), stdin);
        
        // Remove o caractere de nova linha (caso exista)
        entrada[strcspn(entrada, "\n")] = '\0';
        
        // Se a entrada for "FIM", interrompe a leitura
        if (strcmp(entrada, "FIM") == 0) {
            break;
        }
        
        // Chama a função recursiva para inverter a string
        int n = strlen(entrada);
        inverterString(entrada, 0, n);
        printf("\n");
    }

    return 0;
}
