import java.util.Scanner;

public class TP1Q12 {

    // Método que verifica se a senha é válida
    public static boolean isValidPassword(String senha) {
        // A senha deve ter pelo menos 8 caracteres
        if (senha.length() < 8) {
            return false;
        }

        // Flags para verificar as condições da senha
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        // Itera sobre cada caractere da senha
        for (int i = 0; i < senha.length(); i++) {
            char c = senha.charAt(i);

            // Verifica se é uma letra maiúscula
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            }
            // Verifica se é uma letra minúscula
            if (Character.isLowerCase(c)) {
                hasLower = true;
            }
            // Verifica se é um dígito
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
            // Verifica se é um caractere especial
            if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        // A senha é válida se atende todas as condições
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    public static void main(String[] args) {
        // Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Lê as linhas até encontrar a palavra "FIM"
        while (true) {
            String senha = scanner.nextLine();

            // Se a entrada for "FIM", interrompe a leitura
            if (senha.equals("FIM")) {
                break;
            }

            // Verifica se a senha é válida e imprime "SIM" ou "NAO"
            if (isValidPassword(senha)) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        }

        scanner.close();
    }
}
