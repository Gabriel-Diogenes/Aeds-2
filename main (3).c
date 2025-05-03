#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#define MAX_LINE 2048
#define MAX_CAST 20
#define MAX_LISTED 10
#define MAX_SHOWS 500

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

void removeNewline(char *str) {
    str[strcspn(str, "\r\n")] = '\0';
}

void cleanField(char *field) {
    int len = strlen(field);
    if (len > 1 && field[0] == '"' && field[len - 1] == '"') {
        field[len - 1] = '\0';
        memmove(field, field + 1, len - 1);
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

int splitCSVFields(char *line, char fields[][200], int maxFields) {
    int i = 0, j = 0, k = 0;
    bool inQuotes = false;

    while (line[i] && k < maxFields) {
        j = 0;
        inQuotes = false;

        while (line[i]) {
            if (line[i] == '"' && (i == 0 || line[i - 1] != '\\')) {
                inQuotes = !inQuotes;
            } else if (line[i] == ',' && !inQuotes) {
                i++;
                break;
            } else {
                fields[k][j++] = line[i];
            }
            i++;
        }

        fields[k][j] = '\0';
        cleanField(fields[k]);
        k++;
    }

    return k;
}

void sortList(char list[][100], int n) {
    char temp[100];
    for (int i = 0; i < n - 1; i++)
        for (int j = i + 1; j < n; j++)
            if (strcmp(list[i], list[j]) > 0) {
                strcpy(temp, list[i]);
                strcpy(list[i], list[j]);
                strcpy(list[j], temp);
            }
}

Show copyShow(Show *s) {
    Show copy = *s;
    return copy;
}

void printShow(Show *s) {
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

bool searchById(const char *filename, const char *id, Show *show) {
    FILE *fp = fopen(filename, "r");
    if (!fp) {
        perror("Error opening file");
        return false;
    }

    char line[MAX_LINE];
    fgets(line, MAX_LINE, fp); 

    while (fgets(line, MAX_LINE, fp)) {
        removeNewline(line);
        char fields[20][200];
        int total = splitCSVFields(line, fields, 20);
        if (strcmp(fields[0], id) != 0) continue;

        Show temp;
        strcpy(temp.show_id, fields[0]);
        strcpy(temp.type, fields[1]);
        strcpy(temp.title, strlen(fields[2]) > 0 ? fields[2] : "NaN");
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
        sortList(temp.cast, temp.cast_count);

        strcpy(temp.country, strlen(fields[5]) > 0 ? fields[5] : "NaN");
        strcpy(temp.date_added, strlen(fields[6]) > 0 ? fields[6] : "March 1, 1900");
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
        sortList(temp.listed_in, temp.listed_count);

        *show = copyShow(&temp);
        fclose(fp);
        return true;
    }

    fclose(fp);
    return false;
}

int comparacoes = 0;

int parseDate(const char *dateStr, struct tm *date) {
    if (strcmp(dateStr, "NaN") == 0 || strlen(dateStr) == 0) {
        strptime("March 1, 1900", "%B %d, %Y", date); 
        return 0;
    }
    return strptime(dateStr, "%B %d, %Y", date) != NULL;
}

int compararShows(Show *a, Show *b) {
    comparacoes++;

    struct tm dateA = {0}, dateB = {0};
    parseDate(a->date_added, &dateA);
    parseDate(b->date_added, &dateB);

    time_t timeA = mktime(&dateA);
    time_t timeB = mktime(&dateB);

    if (difftime(timeA, timeB) == 0) {
        return strcmp(a->title, b->title);
    } else {
        return (difftime(timeA, timeB) > 0) ? 1 : -1;
    }
}


int partition(Show arr[], int low, int high) {
    Show pivot = arr[high];
    int i = low - 1;

    for (int j = low; j <= high - 1; j++) {
        if (compararShows(&arr[j], &pivot) < 0) {
            i++;
            Show temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    Show temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;

    return i + 1;
}

void quickSort(Show arr[], int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

void escreverLog(double tempo) {
    FILE *log = fopen("692997_quicksort.txt", "w");
    if (log) {
        fprintf(log, "692997\t%.6f\t%d\n", tempo, comparacoes);
        fclose(log);
    }
}

int main() {
    char input[200];
    Show shows[MAX_SHOWS];
    int total = 0;

    while (true) {
        if (!fgets(input, sizeof(input), stdin)) break;
        removeNewline(input);
        if (strcmp(input, "FIM") == 0) break;

        if (searchById("/tmp/disneyplus.csv", input, &shows[total])) {
            total++;
        } else {
            printf("ID %s not found.\n", input);
        }
    }

    clock_t inicio = clock();
    quickSort(shows, 0, total - 1);
    clock_t fim = clock();

    double tempo_execucao = ((double)(fim - inicio)) / CLOCKS_PER_SEC;
    escreverLog(tempo_execucao);

    for (int i = 0; i < total; i++) {
        printShow(&shows[i]);
    }

    return 0;
}
