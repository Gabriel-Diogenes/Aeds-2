import java.util.Scanner;

public class Matriz {
    private int[][] matriz;
    private int linhas, colunas;

    public Matriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.matriz = new int[linhas][colunas];
    }

    public void preencher(java.util.Scanner sc) {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matriz[i][j] = sc.nextInt();
            }
        }
    }

    public Matriz soma(Matriz outra) {
        if (this.linhas != outra.linhas || this.colunas != outra.colunas) {
            return null; 
        }

        Matriz resultado = new Matriz(linhas, colunas);
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                resultado.matriz[i][j] = this.matriz[i][j] + outra.matriz[i][j];
            }
        }
        return resultado;
    }

    public Matriz multiplicacao(Matriz outra) {
        if (this.colunas != outra.linhas) {
            return null; 
        }

        Matriz resultado = new Matriz(this.linhas, outra.colunas);
        for (int i = 0; i < this.linhas; i++) {
            for (int j = 0; j < outra.colunas; j++) {
                for (int k = 0; k < this.colunas; k++) {
                    resultado.matriz[i][j] += this.matriz[i][k] * outra.matriz[k][j];
                }
            }
        }
        return resultado;
    }

    public void mostrarDiagonalPrincipal() {
        int limite = (linhas < colunas) ? linhas : colunas;
        for (int i = 0; i < limite; i++) {
            System.out.print(matriz[i][i] + " ");
        }
        System.out.println();
    }

    public void mostrarDiagonalSecundaria() {
        int limite = (linhas < colunas) ? linhas : colunas;
        for (int i = 0; i < limite; i++) {
            System.out.print(matriz[i][colunas - 1 - i] + " ");
        }
        System.out.println();
    }

    public void imprimir() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int casos = sc.nextInt();

        for (int t = 0; t < casos; t++) {
            int l1 = sc.nextInt();
            int c1 = sc.nextInt();
            Matriz m1 = new Matriz(l1, c1);
            m1.preencher(sc);
            int l2 = sc.nextInt();
            int c2 = sc.nextInt();
            Matriz m2 = new Matriz(l2, c2);
            m2.preencher(sc);
            m1.mostrarDiagonalPrincipal();
            m1.mostrarDiagonalSecundaria();
            Matriz soma = m1.soma(m2);
            if (soma != null) {
                soma.imprimir();
            }
            Matriz mult = m1.multiplicacao(m2);
            if (mult != null) {
                mult.imprimir();
            }
        }

        sc.close();
    }
}
