class TP1Q10 {

    //metodo que verifica se o programa chegou ao FIM
    static boolean eFim(String s) {
        return ((s.charAt(0) == 'F') && s.charAt(1) == 'I' && (s.charAt(2) == 'M') && (s.length() == 3)); 

    }

    //metodo que mostra se palavra e um palindromo ou nao
    static boolean ePalindromo(String palavra, int x, int y) { 
        boolean resposta = true;

        if (x <= y) {           
            if (palavra.charAt(x) != palavra.charAt(y))        
                resposta = false;

            else
                resposta = ePalindromo(palavra, x + 1, y - 1);    
        }

        return resposta;
    }

    public static void main(String[] args) {
        String[] palavra = new String[1000];

        int x = 0;

        do {
            palavra[x] = MyIO.readLine();

        } while (eFim(palavra[x++]) == false);

        for (int i = 0; i < x; i++) {

            if(ePalindromo(palavra[i], 0, palavra[i].length() - 1) == true)
                MyIO.println("SIM");
            else
                MyIO.println("NAO");    


            

        }

    }

}