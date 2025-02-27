import java.util.Scanner;

public class TP1Q11 {

    // Método iterativo para encontrar o comprimento da substring mais longa sem caracteres repetidos
    public static int comprimentoSubstringSemRepeticao(String str) {
        int n = str.length();
        int maxLength = 0; // Comprimento máximo da substring sem repetição
        int start = 0; // Início da janela deslizante
        
        // Usaremos um array para marcar se um caractere já foi encontrado na substring
        int[] lastIndex = new int[256]; // Para armazenar o índice do último caractere encontrado (256 para todas as letras ASCII)
        
        // Inicializa o array lastIndex com -1 (indicando que nenhum caractere foi encontrado ainda)
        for (int i = 0; i < 256; i++) {
            lastIndex[i] = -1;
        }

        // Varre a string com uma janela deslizante
        for (int end = 0; end < n; end++) {
            // Se o caractere já apareceu na substring, mova o início da janela
            start = Math.max(start, lastIndex[str.charAt(end)] + 1);
            
            // Atualiza o índice do caractere atual
            lastIndex[str.charAt(end)] = end;
            
            // Atualiza o comprimento máximo
            maxLength = Math.max(maxLength, end - start + 1);
        }

        // Se a string não tem repetições, a resposta deve ser 0
        if (maxLength == n) {
            return 0;
        }

        return maxLength;
    }

    public static void main(String[] args) {
        // Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);
        
        // Lê as linhas até encontrar a palavra "FIM"
        while (true) {
            String entrada = scanner.nextLine();
            
            // Se a entrada for "FIM", interrompe a leitura
            if (entrada.equals("FIM")) {
                break;
            }
            
            // Chama o método comprimentoSubstringSemRepeticao e imprime o comprimento da substring
            System.out.println(comprimentoSubstringSemRepeticao(entrada));
        }

        scanner.close();
    }
}
