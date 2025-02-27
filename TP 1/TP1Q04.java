import java.util.Random;

public class TP1Q04 {

        // Verificacao se o programa chegou ao fim
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M'); 
                                                                                                                                                                                             
    }

    // metodo que realiza a troca de letras
    public static String trocarLetra(String palavra, Random gerador) { 

        char[] stringChar = palavra.toCharArray();
        String saida = ""; 

        char letra1 = (char) ('a' + Math.abs(gerador.nextInt()) % 26); 
        char letra2 = (char) ('a' + Math.abs(gerador.nextInt()) % 26);

        for (int x = 0; x < palavra.length(); x++) { 
                                               
            if (stringChar[x] == letra1) 

                saida += letra2;

            else
                saida += palavra.charAt(x);

        }

        return saida; 
    }

    public static void main(String[] args) {

        Random gerador = new Random();
        gerador.setSeed(4);

        String[] palavra = new String[1000];
        int x = 0;

        // Leitura dos dados
        do {
            palavra[x] = MyIO.readLine();
        } while (isFim(palavra[x++]) == false);
        x--; 

        // Realizar a troca de letras
        for (int y = 0; y < x; y++) 
        {
            MyIO.println(trocarLetra(palavra[y], gerador));
        }

    }
}
