public class TP1Q12 {
    private static int chave = 3;
    
  
       //Verificar se o programa chegou ao fim
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M'); 
    }
    //metodod para realizarr o ciframento de maneira recursiva 
    public static String Ciframento(String palavra, int i){
        String cif = new String();
        
        if(i < palavra.length())
            cif = (char)(palavra.charAt(i) + chave) + Ciframento(palavra, i + 1);

        return cif;
    }

    public static void main(String[] args){
        String palavra = new String();
        boolean stop;
    // Leitura de entrada de dados
        do{
            palavra = MyIO.readLine();
            
            stop = isFim(palavra);
            //Realizar o ciframento
            if(!stop){
                String cif = Ciframento(palavra, 0);
                MyIO.println(cif);
            }
        }while(!stop);
    }    
}