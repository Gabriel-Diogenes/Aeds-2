class TP1Q01 {

    // Verificacao se o programa chegou ao fim
    public static boolean eFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M'); 
    }

    // Metodo para dizer se palavra e um palindromo ou nao
    public static boolean ePalindromo(String palavra) {          
        boolean resp = true;
        int metade = palavra.length() / 2;                     

        for (int n = 0; n < metade; n++) {
            if (palavra.charAt(n) != palavra.charAt(palavra.length() - 1 - n))        
                resp = false;

       }

        return resp;
    }

    public static void main(String[] args) {
        String[] palavra = new String[1000];
        int x = 0;

        // Leitura das palavras ate chegar no fim
        do {
            palavra[x] = MyIO.readLine();
        } while (eFim(palavra[x++]) == false);
        x--; 
        //dizer se e palindro ou nao
        for (int i = 0; i < x; i++) {
            if (ePalindromo(palavra[i]) == true)
                MyIO.println("SIM");
            else
                MyIO.println("NAO");
        }

    }
}
