class TP1Q03 {

    // Verificacao se o programa chegou ao fim
    public static boolean isFim(String s) {
        return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M'); 

    } 
    private static int chave = 3;
    //metodo para realizar o ciframento
    public static String Ciframento(String palavra){
        String cif = new String();
        
        for(int i = 0; i < palavra.length(); i++){
            cif += (char)(palavra.charAt(i) + chave);
        }

        return cif;
    }

    public static void main(String[] args){
        String palavra = new String();
        boolean stop;

        // Leitura dos dados
        do{
            palavra= MyIO.readLine();
            
            stop = isFim(palavra
);
            //Realizar o ciframento
            if(!stop){
                String cif = Ciframento(palavra);
                MyIO.println(cif);
            }
        }while(!stop);
    }
}