import java.util.Scanner;

public class TP1Q10 {

    // Método iterativo para contar o número de palavras na string
    public static int contarPalavras(String str) {
        // Se a string estiver vazia ou composta apenas por espaços, não há palavras
        if (str.trim().isEmpty()) {
            return 0;
        }

        // Inicializa o contador de palavras
        int contador = 0;

        // Percorre a string, contando palavras
        boolean dentroDaPalavra = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            
            // Verifica se o caractere é um espaço
            if (Character.isWhitespace(c)) {
                dentroDaPalavra = false;  // Não está mais dentro de uma palavra
            } else if (!dentroDaPalavra) {
                // Se não estava dentro de uma palavra, então uma nova palavra começou
                contador++;
                dentroDaPalavra = true;
            }
        }

        return contador;
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
            
            // Chama o método contarPalavras e imprime o número de palavras
            System.out.println(contarPalavras(entrada));
        }

        scanner.close();
    }
}
