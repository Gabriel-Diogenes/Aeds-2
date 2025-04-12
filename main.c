#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_LINE 1000
#define MAX_CAST 30
#define MAX_LISTED_IN 10

typedef struct {
    char show_id[20];
    char type[50];
    char title[100];
    char director[100];
    char cast[MAX_CAST][100];
    int cast_count;
    char country[100];
    char date_added[50];
    int release_year;
    char rating[20];
    char duration[30];
    char listed_in[MAX_LISTED_IN][100];
    int listed_count;
} Shows;

// Função para remover quebra de linha
void removeNewline(char *str) {
    str[strcspn(str, "\r\n")] = '\0';
}

// Função para separar string por vírgula e armazenar no array
int split(char *str, char array[][100], int max) {
    int count = 0;
    char *token = strtok(str, ",");
    while (token != NULL && count < max) {
        while (*token == ' ') token++;
        strcpy(array[count++], token);
        token = strtok(NULL, ",");
    }
    return count;
}

// Construtor vazio
Shows newShow() {
    Shows s;
    strcpy(s.show_id, "NaN");
    strcpy(s.type, "NaN");
    strcpy(s.title, "NaN");
    strcpy(s.director, "NaN");
    s.cast_count = 0;
    strcpy(s.country, "NaN");
    strcpy(s.date_added, "NaN");
    s.release_year = 0;
    strcpy(s.rating, "NaN");
    strcpy(s.duration, "NaN");
    s.listed_count = 0;
    return s;
}

// Construtor completo
Shows createShow(char *show_id, char *type, char *title, char *director, char *cast, char *country,
                 char *date_added, int release_year, char *rating, char *duration, char *listed_in) {
    Shows s;
    strcpy(s.show_id, show_id);
    strcpy(s.type, type);
    strcpy(s.title, title);
    strcpy(s.director, (*director) ? director : "NaN");

    s.cast_count = (*cast) ? split(cast, s.cast, MAX_CAST) : 0;
    if (s.cast_count == 0) strcpy(s.cast[0], "NaN");

    strcpy(s.country, (*country) ? country : "NaN");
    strcpy(s.date_added, (*date_added) ? date_added : "NaN");
    s.release_year = release_year;
    strcpy(s.rating, (*rating) ? rating : "NaN");
    strcpy(s.duration, (*duration) ? duration : "NaN");
    s.listed_count = (*listed_in) ? split(listed_in, s.listed_in, MAX_LISTED_IN) : 0;
    return s;
}

// Clone
Shows clone(Shows *s) {
    return *s;
}

// Função de comparação para qsort (ordenar cast)
int cmpCast(const void *a, const void *b) {
    return strcmp(*(char **)a, *(char **)b);
}

// Imprimir
void imprimir(Shows *s) {
    printf("=> %s ## %s ## %s ## %s ## [", s->show_id, s->type, s->title, s->director);
    if (s->cast_count == 0 || strcmp(s->cast[0], "NaN") == 0) {
        printf("NaN");
    } else {
        char *sorted[MAX_CAST];
        for (int i = 0; i < s->cast_count; i++) {
            sorted[i] = s->cast[i];
        }
        qsort(sorted, s->cast_count, sizeof(char *), cmpCast);
        for (int i = 0; i < s->cast_count; i++) {
            printf("%s", sorted[i]);
            if (i < s->cast_count - 1) printf(", ");
        }
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [", s->country, s->date_added, s->release_year, s->rating, s->duration);
    for (int i = 0; i < s->listed_count; i++) {
        printf("%s", s->listed_in[i]);
        if (i < s->listed_count - 1) printf(", ");
    }
    printf("] ##\n");
}

// Função para ler um show do CSV
Shows ler(char *id_buscado) {
    FILE *file = fopen("disneyplus.csv", "r");
    if (!file) {
        perror("Erro ao abrir o arquivo.");
        exit(1);
    }

    char linha[MAX_LINE];
    fgets(linha, MAX_LINE, file); // Ignorar cabeçalho

    while (fgets(linha, MAX_LINE, file)) {
        char *campos[12];
        char *token;
        int i = 0;

        token = strtok(linha, "\t\n");
        while (token != NULL && i < 12) {
            campos[i++] = token;
            token = strtok(NULL, "\t\n");
        }

        if (strcmp(campos[0], id_buscado) == 0) {
            Shows s = createShow(
                campos[0],
                campos[1],
                campos[2],
                (i > 3 ? campos[3] : "NaN"),
                (i > 4 ? campos[4] : "NaN"),
                (i > 5 ? campos[5] : "NaN"),
                (i > 6 ? campos[6] : "NaN"),
                (i > 7 ? atoi(campos[7]) : 0),
                (i > 8 ? campos[8] : "NaN"),
                (i > 9 ? campos[9] : "NaN"),
                (i > 10 ? campos[10] : "NaN")
            );
            fclose(file);
            return s;
        }
    }

    fclose(file);
    Shows vazio = newShow();
    return vazio;
}

// Função principal
int main() {
    char entrada[20];
    while (1) {
        scanf("%s", entrada);
        removeNewline(entrada);
        if (strcmp(entrada, "FIM") == 0) break;

        Shows s = ler(entrada);
        imprimir(&s);
    }

    return 0;
}
