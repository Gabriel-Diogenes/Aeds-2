import java.text.Normalizer;
import java.util.Arrays;
import java.util.Scanner;

public class TP1Q09 {

    private static String normalizarString(String str) {
    // Remove acentos e espaços, coloca em minúsculo
    str = str.toLowerCase().replaceAll("\\s", "");
    str = Normalizer.normalize(str, Normalizer.Form.NFD);
    str = str.replaceAll("[^\\p{ASCII}]", ""); // Remove caracteres não-ASCII (acentos)
    return str;
  }

    // Método iterativo para verificar se duas strings são anagramas
    public static boolean saoAnagramas(String str1, String str2) {
        // Se as duas strings têm tamanhos diferentes, não são anagramas
        if (str1.length() != str2.length()) {
            return false;
        }

        // Converte as strings para arrays de caracteres, remove espaços, coloca em
        char[] array1 = normalizarString(str1).toCharArray();
        char[] array2 = normalizarString(str2).toCharArray();
        // Ordena os arrays
        Arrays.sort(array1);
        Arrays.sort(array2);

        // Compara os arrays ordenados
        return Arrays.equals(array1, array2);
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
            
            // Divide a entrada em duas partes (strings separadas por " - ")
            String[] partes = entrada.split(" - ");
            if (partes.length == 2) {
                String str1 = partes[0].trim();
                String str2 = partes[1].trim();
                
             // Verifica se são anagramas e imprime "SIM" ou "NÃO"
            System.out.println(saoAnagramas(str1, str2) ? "SIM" : "N\u00C3O");
            }
        }

        scanner.close();
    }
}
