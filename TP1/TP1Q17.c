#include <string.h>
#include <stdio.h>
#include <stdbool.h>

// metodo que verifica se o programa chegou ao fim
bool eFim(char palavra[40])
{
    return ((palavra[0] == 'F') && (palavra[1] == 'I') && (palavra[2] == 'M') && (strlen(palavra) == 3));
}

bool ePalindromo(char palavra[], int x, int y)
{
    bool resposta = true;

    if (x <= y)
    {
        if (palavra[x] != palavra[y])
        {
            resposta = false;
            x = strlen(palavra);
        }

        else
            return ePalindromo(palavra, x + 1, y - 1);
    }

    return resposta;
}

int main()
{
    char palavra[1000][100];
    int x = 0;


    //ler as palavras
    do
    {
        scanf(" %[^\n]s", palavra[x]);

    } while (eFim(palavra[x++]) == false);
    x--;

    //Realizar o metodo para dizer se palavra e um palindromo ou nao
    for (int i = 0; i < x; i++)
    {
        if (ePalindromo(palavra[i], 0, strlen(palavra[i]) - 1) == true)
            printf("SIM\n");

        else
            printf("NAO\n");
    }

    return 0;
}
