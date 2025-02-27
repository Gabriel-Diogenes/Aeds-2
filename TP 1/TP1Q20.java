public class TP1Q20 {

    public static boolean isFim(String s) {
        return s.equals("FIM");
    }

    public static boolean Consoante(String palavra, int x) {
        if (x == palavra.length()) return true;
        char c = Character.toLowerCase(palavra.charAt(x));
        if ("aeiou".indexOf(c) != -1) return false;
        return Consoante(palavra, x + 1);
    }

    public static boolean Vogal(String palavra, int x) {
        if (x == palavra.length()) return true;
        char c = Character.toLowerCase(palavra.charAt(x));
        if ("aeiou".indexOf(c) == -1) return false;
        return Vogal(palavra, x + 1);
    }

    public static boolean Int(String palavra, int x) {
        if (x == palavra.length()) return true;
        if (!Character.isDigit(palavra.charAt(x))) return false;
        return Int(palavra, x + 1);
    }

    public static boolean Real(String palavra, int x, int pontos, int virgulas) {
        if (x == palavra.length()) return pontos + virgulas <= 1;
        char c = palavra.charAt(x);
        if (c == '.') pontos++;
        else if (c == ',') virgulas++;
        else if (!Character.isDigit(c)) return false;
        return Real(palavra, x + 1, pontos, virgulas);
    }

    public static void processarEntrada(String[] palavras, int x) {
        if (x < 0) return;
        
        System.out.print(Consoante(palavras[x], 0) ? "SIM " : "NAO ");
        System.out.print(Vogal(palavras[x], 0) ? "SIM " : "NAO ");
        System.out.print(Int(palavras[x], 0) ? "SIM " : "NAO ");
        System.out.println(Real(palavras[x], 0, 0, 0) ? "SIM" : "NAO");
        
        processarEntrada(palavras, x - 1);
    }

    public static void main(String[] args) {
        String[] palavras = new String[1000];
        int x = 0;

        do {
            palavras[x] = MyIO.readLine();
        } while (!isFim(palavras[x++]));
        
        processarEntrada(palavras, x - 2);
    }
}
