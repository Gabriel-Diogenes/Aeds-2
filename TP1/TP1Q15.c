#include <stdio.h>
#include <stdbool.h>

int main(){
    int x;
    double y;
    //criar arquivo
    FILE* arquivo = fopen("arquivo.txt", "wb+");

    scanf("%d", &x);

    //escrever no arquivo
    for(int z = 0; z < x; z++){
        scanf("%lf", &y);
        fwrite(&y, 8, 1, arquivo);
    }
    //alterar arquivo
    for(int z = 0; z < x; z++){
        fseek(arquivo, (x - z - 1) * 8, SEEK_SET);

        fread(&y, 8, 1, arquivo);

        printf("%g\n", y);
    }
    //fechar arquivo
    fclose(arquivo);

    return 0;
}
