import java.util.Scanner;

public class TP1Q08 {

    // Método recursivo para calcular a soma dos dígitos de um número
    public static int somaDigitos(int num) {
        // Caso base: quando o número for menor que 10, retornamos o próprio número
        if (num == 0) {
            return 0;
        }
        // Chamada recursiva: somamos o último dígito (num % 10) com a soma dos dígitos do resto (num / 10)
        return num % 10 + somaDigitos(num / 10);
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
            
            // Converte a entrada de String para inteiro
            int numero = Integer.parseInt(entrada);
            
            // Chama o método recursivo e imprime a soma dos dígitos
            System.out.println(somaDigitos(numero));
        }
        
        scanner.close();
    }
}
