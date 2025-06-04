#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define MAX_LINE 1024
#define MAX_CAST 20
#define MAX_LISTED 10

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

typedef struct Node {
    Show data;
    struct Node *next;
} Node;

Node *inicio = NULL;
int count = 0;

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

void imprimir(Show *s) {
    printf("=> %s ## %s ## %s ## %s ## [", s->show_id, s->title, s->type, s->director);
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
    fgets(line, MAX_LINE, fp); 

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


bool inserirInicio(Show s) {
    Node *novo = (Node *)malloc(sizeof(Node));
    if (!novo) return false;
    novo->data = s;
    novo->next = inicio;
    inicio = novo;
    count++;
    return true;
}

bool inserirFim(Show s) {
    Node *novo = (Node *)malloc(sizeof(Node));
    if (!novo) return false;
    novo->data = s;
    novo->next = NULL;

    if (!inicio) {
        inicio = novo;
    } else {
        Node *tmp = inicio;
        while (tmp->next) tmp = tmp->next;
        tmp->next = novo;
    }
    count++;
    return true;
}

bool inserirPos(Show s, int pos) {
    if (pos < 0 || pos > count) return false;
    if (pos == 0) return inserirInicio(s);

    Node *novo = (Node *)malloc(sizeof(Node));
    if (!novo) return false;
    novo->data = s;

    Node *tmp = inicio;
    for (int i = 0; i < pos - 1; i++) tmp = tmp->next;
    novo->next = tmp->next;
    tmp->next = novo;

    count++;
    return true;
}


Show removerInicio() {
    Show rem;
    if (!inicio) {
        strcpy(rem.show_id, "");
        return rem;
    }

    Node *tmp = inicio;
    rem = tmp->data;
    inicio = inicio->next;
    free(tmp);
    count--;
    return rem;
}

Show removerFim() {
    Show rem;
    if (!inicio) {
        strcpy(rem.show_id, "");
        return rem;
    }

    if (!inicio->next) {
        rem = inicio->data;
        free(inicio);
        inicio = NULL;
        count--;
        return rem;
    }

    Node *tmp = inicio;
    while (tmp->next->next) tmp = tmp->next;

    rem = tmp->next->data;
    free(tmp->next);
    tmp->next = NULL;
    count--;
    return rem;
}

Show removerPos(int pos) {
    Show rem;
    if (pos < 0 || pos >= count || !inicio) {
        strcpy(rem.show_id, "");
        return rem;
    }

    if (pos == 0) return removerInicio();

    Node *tmp = inicio;
    for (int i = 0; i < pos - 1; i++) tmp = tmp->next;

    Node *toDelete = tmp->next;
    rem = toDelete->data;
    tmp->next = toDelete->next;
    free(toDelete);
    count--;
    return rem;
}

void imprimirLista() {
    Node *tmp = inicio;
    while (tmp) {
        imprimir(&tmp->data);
        tmp = tmp->next;
    }
}

int main() {
    char input[2000];

    while (true) {
        if (!fgets(input, sizeof(input), stdin)) break;
        stripNewline(input);
        if (strcmp(input, "FIM") == 0) break;

        Show s;
        if (ler("/tmp/disneyplus.csv", input, &s)) {
            inserirFim(s);
        } else {
            printf("ID %s não encontrado.\n", input);
        }
    }

    int N;
    scanf("%d\n", &N);

    for (int i = 0; i < N; i++) {
        fgets(input, sizeof(input), stdin);
        stripNewline(input);

        char cmd[5];
        int pos;
        char id[20];
        Show s;

        if (sscanf(input, "%s", cmd) != 1) continue;

        if (strcmp(cmd, "II") == 0) {
            sscanf(input, "%*s %s", id);
            if (ler("/tmp/disneyplus.csv", id, &s)) {
                inserirInicio(s);
            }
        } else if (strcmp(cmd, "IF") == 0) {
            sscanf(input, "%*s %s", id);
            if (ler("/tmp/disneyplus.csv", id, &s)) {
                inserirFim(s);
            }
        } else if (strcmp(cmd, "I*") == 0) {
            sscanf(input, "%*s %d %s", &pos, id);
            if (ler("/tmp/disneyplus.csv", id, &s)) {
                inserirPos(s, pos);
            }
        } else if (strcmp(cmd, "RI") == 0) {
            Show rem = removerInicio();
            if (strlen(rem.show_id) > 0)
                printf("(R) %s\n", rem.title);
        } else if (strcmp(cmd, "RF") == 0) {
            Show rem = removerFim();
            if (strlen(rem.show_id) > 0)
                printf("(R) %s\n", rem.title);
        } else if (strcmp(cmd, "R*") == 0) {
            sscanf(input, "%*s %d", &pos);
            Show rem = removerPos(pos);
            if (strlen(rem.show_id) > 0)
                printf("(R) %s\n", rem.title);
        }
    }

    imprimirLista();
    return 0;
}
