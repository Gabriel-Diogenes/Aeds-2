public class TP1Q06 {

    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M'); 
                                                                                                    
                                                                                                  
    }

    public static boolean Consoante(String palavra) {       
        boolean resposta = true;

        String s = palavra.toLowerCase();             

        for (int x = 0; x < palavra.length(); x++) {      
            if (s.charAt(x) == 'a' || s.charAt(x) == 'e' || s.charAt(x) == 'i' || s.charAt(x) == 'o' || s.charAt(x) == 'u') {
                resposta = false;           
                break;
            }
        }

        return resposta;
    }

    public static boolean Vogal(String palavra) {          
        boolean resposta = true;

        String s = palavra.toLowerCase();                  

        for (int x = 0; x < palavra.length(); x++) {          
            if (s.charAt(x) != 'a' || s.charAt(x) != 'e' || s.charAt(x) != 'i' || s.charAt(x) != 'o' || s.charAt(x) != 'u') {
                resposta = false;                              
                break;
            }
        }

        return resposta;
    }

    public static boolean Int(String palavra) {            
        boolean resposta = true;

        for (int x = 0; x < palavra.length(); x++) {         
            int ASCII = (int) palavra.charAt(x);         

            if (ASCII < 48 || palavra.charAt(x) > 57) {
                resposta = false;                           

            }

        }

        return resposta;
    }

    public static boolean Real(String palavra) {           
        boolean resposta = true;

        int V = 0;                                  
        int P = 0;

        if (Int(palavra) == true)                          
            resposta = true;

        else {
            for (int x = 0; x < palavra.length(); x++) {      
                if (palavra.charAt(x) == '.')
                    P++;

                else if (palavra.charAt(x) == ',') {
                    V++;

                }
            }

            if (P != 1)                         
                resposta = false;
            else if (V != 1)
                resposta = false;

        }

        return resposta;
    }

    public static void main(String[] args) {

        String[] palavra = new String[1000];
        int x = 0;

      
        do {
            palavra[x] = MyIO.readLine();
        } while (isFim(palavra[x++]) == false);
        x--; 

        for (int i = 0; i < x; i++) {             

            if (Consoante(palavra[i]) == true)
                MyIO.print("SIM ");
            else
                MyIO.print("NAO ");

            if (Vogal(palavra[i]))
                MyIO.print("SIM ");
            else
                MyIO.print("NAO ");

            if (Int(palavra[i]))
                MyIO.print("SIM ");
            else
                MyIO.print("NAO ");

            if (Real(palavra[i]))
                MyIO.print("SIM \n");
            else
                MyIO.print("NAO \n");

        }

    }
}
