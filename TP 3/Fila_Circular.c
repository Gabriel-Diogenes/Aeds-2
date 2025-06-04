#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <math.h>


#define MAX_LINE 1024
#define MAX_CAST 20
#define MAX_LISTED 10
#define MAX_STACK 500

typedef struct {
    char show_id[20];
    char type[50];
    char title[200];
    char director[100];
    char cast[MAX_CAST][100];
    int cast_count;
    char country[100];
    char date_added[50];
    int release_year;
    char rating[20];
    char duration[30];
    char listed_in[MAX_LISTED][100];
    int listed_count;
} Show;

Show stack[MAX_STACK];
int top = 0;

void stripNewline(char *str) {
    str[strcspn(str, "\r\n")] = '\0';
}

void sortCast(char cast[][100], int n) {
    char temp[100];
    for (int i = 0; i < n-1; i++)
        for (int j = i+1; j < n; j++)
            if (strcmp(cast[i], cast[j]) > 0) {
                strcpy(temp, cast[i]);
                strcpy(cast[i], cast[j]);
                strcpy(cast[j], temp);
            }
}

Show cloneShow(Show *s) {
    Show newShow = *s;
    return newShow;
}

void imprimir(Show *s, int contador) {
    printf("[%d] => %s ## %s ## %s ## %s ## [", contador, s->show_id, s->title, s->type, s->director);
    for (int i = 0; i < s->cast_count; i++) {
        printf("%s", s->cast[i]);
        if (i < s->cast_count - 1) printf(", ");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## [", s->country, s->date_added, s->release_year, s->rating, s->duration);
    for (int i = 0; i < s->listed_count; i++) {
        printf("%s", s->listed_in[i]);
        if (i < s->listed_count - 1) printf(", ");
    }
    printf("] ##\n");
}

void cleanField(char *field) {
    int len = strlen(field);
    if (len > 1 && field[0] == '"' && field[len - 1] == '"') {
        field[len - 1] = '\0';
        memmove(field, field + 1, len - 2);
    }

    char *src = field, *dst = field;
    while (*src) {
        if (*src == '"' && *(src + 1) == '"') {
            *dst++ = '"';
            src += 2;
        } else {
            *dst++ = *src++;
        }
    }
    *dst = '\0';
}

int LinhaCSV(char *line, char fields[][200], int maxFields) {
    int count = 0;
    char *ptr = line;

    while (*ptr && count < maxFields) {
        if (*ptr == '"') {
            ptr++;
            char buffer[200];
            int bufIndex = 0;

            while (*ptr) {
                if (*ptr == '"' && *(ptr + 1) == '"') {
                    buffer[bufIndex++] = '"';
                    ptr += 2;
                } else if (*ptr == '"') {
                    ptr++;
                    break;
                } else {
                    buffer[bufIndex++] = *ptr++;
                }
            }
            buffer[bufIndex] = '\0';
            strcpy(fields[count], buffer);
            cleanField(fields[count]);
            if (*ptr == ',') ptr++;
        } else {
            char *start = ptr;
            while (*ptr && *ptr != ',') ptr++;
            strncpy(fields[count], start, ptr - start);
            fields[count][ptr - start] = '\0';
            cleanField(fields[count]);
            if (*ptr == ',') ptr++;
        }
        count++;
    }

    return count;
}

bool ler(const char *filename, const char *search_id, Show *show) {
    FILE *fp = fopen(filename, "r");
    if (!fp) {
        perror("Erro ao abrir o arquivo");
        return false;
    }

    char line[MAX_LINE];
    fgets(line, MAX_LINE, fp); // Cabeçalho

    while (fgets(line, MAX_LINE, fp)) {
        stripNewline(line);
        char fields[20][200];
        int total = LinhaCSV(line, fields, 20);
        if (strcmp(fields[0], search_id) != 0) continue;

        Show temp;
        strcpy(temp.show_id, fields[0]);
        strcpy(temp.type, fields[1]);
        strcpy(temp.title, fields[2]);
        strcpy(temp.director, strlen(fields[3]) > 0 ? fields[3] : "NaN");

        temp.cast_count = 0;
        if (strlen(fields[4]) > 0) {
            char *actor = strtok(fields[4], ",");
            while (actor && temp.cast_count < MAX_CAST) {
                while (*actor == ' ') actor++;
                strcpy(temp.cast[temp.cast_count++], actor);
                actor = strtok(NULL, ",");
            }
        } else {
            strcpy(temp.cast[0], "NaN");
            temp.cast_count = 1;
        }
        sortCast(temp.cast, temp.cast_count);

        strcpy(temp.country, strlen(fields[5]) > 0 ? fields[5] : "NaN");
        strcpy(temp.date_added, strlen(fields[6]) > 0 ? fields[6] : "NaN");
        temp.release_year = strlen(fields[7]) > 0 ? atoi(fields[7]) : 0;
        strcpy(temp.rating, strlen(fields[8]) > 0 ? fields[8] : "NaN");
        strcpy(temp.duration, strlen(fields[9]) > 0 ? fields[9] : "NaN");

        temp.listed_count = 0;
        if (strlen(fields[10]) > 0) {
            char *category = strtok(fields[10], ",");
            while (category && temp.listed_count < MAX_LISTED) {
                while (*category == ' ') category++;
                strcpy(temp.listed_in[temp.listed_count++], category);
                category = strtok(NULL, ",");
            }
        } else {
            strcpy(temp.listed_in[0], "NaN");
            temp.listed_count = 1;
        }

        *show = cloneShow(&temp);
        fclose(fp);
        return true;
    }

    fclose(fp);
    return false;
}

#define MAX 5

typedef struct {
    Show array[MAX];
    int primeiro, ultimo, total;
} FilaCircular;

void iniciarFila(FilaCircular *f) {
    f->primeiro = f->ultimo = f->total = 0;
}

bool filaCheia(FilaCircular *f) {
    return f->total == MAX;
}

bool filaVazia(FilaCircular *f) {
    return f->total == 0;
}

void inserirFila(FilaCircular *f, Show s) {
    if (filaCheia(f)) {
        Show removido = f->array[f->primeiro];
        f->primeiro = (f->primeiro + 1) % MAX;
        f->total--;
    }
    f->array[f->ultimo] = s;
    f->ultimo = (f->ultimo + 1) % MAX;
    f->total++;

    int soma = 0;
    for (int i = 0, j = f->primeiro; i < f->total; i++, j = (j + 1) % MAX) {
        soma += f->array[j].release_year;
    }
    int media = soma / f->total;
    printf("[Media] %d\n", media);
}

Show removerFila(FilaCircular *f) {
    Show removido;
    if (filaVazia(f)) {
        strcpy(removido.show_id, "");
        return removido;
    }
    removido = f->array[f->primeiro];
    f->primeiro = (f->primeiro + 1) % MAX;
    f->total--;
    return removido;
}

int main() {
    char input[2000];
    FilaCircular fila;
    iniciarFila(&fila);

    while (true) {
        if (!fgets(input, sizeof(input), stdin)) break;
        stripNewline(input);
        if (strcmp(input, "FIM") == 0) break;

        Show s;
        if (ler("/tmp/disneyplus.csv", input, &s)) {
            inserirFila(&fila, s);
        }
    }

    int N;
    scanf("%d\n", &N);
    for (int i = 0; i < N; i++) {
        fgets(input, sizeof(input), stdin);
        stripNewline(input);

        if (input[0] == 'I') {
            char id[20];
            sscanf(input + 2, "%s", id);
            Show s;
            if (ler("/tmp/disneyplus.csv", id, &s)) {
                inserirFila(&fila, s);
            }
        } else if (input[0] == 'R') {
            Show rem = removerFila(&fila);
            if (strlen(rem.show_id) > 0) {
                printf("(R) %s\n", rem.title);  
            }
        }
    }
    int idx = 0;
    for (int i = 0, j = fila.primeiro; i < fila.total; i++, j = (j + 1) % MAX) {
        imprimir(&fila.array[j], idx++);
    }

    return 0;
}

