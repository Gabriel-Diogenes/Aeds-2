import java.util.Scanner;

public class TP1Q07 {

    // Método iterativo para inverter a string
    public static String inverterString(String str) {
        StringBuilder invertida = new StringBuilder();
        
        // Percorre a string de trás para frente e constrói a string invertida
        for (int i = str.length() - 1; i >= 0; i--) {
            invertida.append(str.charAt(i));
        }
        
        return invertida.toString();
    }
    
    public static void main(String[] args) {
        // Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);
        
        // Lê as strings até a palavra "FIM"
        while (true) {
            String entrada = scanner.nextLine();
            
            // Se a entrada for "FIM", interrompe a leitura
            if (entrada.equals("FIM")) {
                break;
            }
            
            // Chama o método de inversão e imprime a string invertida
            System.out.println(inverterString(entrada));
        }
        
        scanner.close();
    }
}
