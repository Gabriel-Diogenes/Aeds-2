#include <string.h>
#include <stdio.h>
#include <math.h>
#include <stdbool.h>

// metodo para dizer que o programa chegou ao fim
bool isFim(char palavra[40])
{
    return ((palavra[0] == 'F') && (palavra[1] == 'I') && (palavra[2] == 'M') && (strlen(palavra) == 3));
}

// metodo que verifica se palavra e um palindromo ou nao
bool ePalindromo(char palavra[])
{
    bool resposta = true;
    int tamanho = 0;

    for (int x = strlen(palavra) - 1; x >= 0; x--, tamanho++)
    {
        if (palavra[x] != palavra[tamanho])
            resposta = false;
    }

    return resposta;
}



int main()
{
    char palavra[1000][100];
    int a = 0;

    //Entrada das palavras

    do
    {
        scanf(" %[^\n]s", palavra[a]);

    } while (isFim(palavra[a++]) == false);
    a--;

    // dizer se palavra e um palindromo ou nao
    for (int i = 0; i < a; i++)
    {
        if (ePalindromo(palavra[i]) == true)
            printf("SIM\n");

        else
            printf("NAO\n");
    }

    return(0);//fim do programa
}
