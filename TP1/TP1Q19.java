class TP1Q19
// Obs: Nao consegui realizar o exercicio de maneira recursiva, logo estarei entregando ele de maneira semelhante ao exercicio 5
{
         
    public static int primeiro(String palavra, int i) 
    { 
        for(int x = i; x != palavra.length(); x++) if(palavra.charAt(x) >= '0' && palavra.charAt(x) <= '9') return x;
        return -1;
    }

    public static int primeiro(String palavra) { return primeiro(palavra, 0); }

    public static int ultimo(String palavra)
    {
        int e = palavra.lastIndexOf("and"), ou = palavra.lastIndexOf("or"), nao = palavra.lastIndexOf("not");

        if(e > ou && e > nao) return e;
        else if(ou > e && ou > nao) return ou;
        else if(nao > ou && nao > e) return nao;
        return -1;
    }

        
    public static void main(String[] args) 
    {
        while(true)
        {
            int quantidade = MyIO.readInt();

            if(quantidade == 0) break;

            int[] letras = new int[quantidade];

            for(int x = 0; x != quantidade; x++) letras[x] = MyIO.readInt();

           
            
            String linha = MyIO.readLine();

            if(linha.charAt(linha.length() - 1) == ' ') linha = linha.substring(0, linha.length() - 1); 
            for(int x = 0; x != quantidade; x++)
            {
                if(x == 0) 
                {
                    linha = linha.replace("not(A)", letras[0] == 0 ? "1" : "0");
                    linha = linha.replace("A", letras[0] == 0 ? "0" : "1");
                }
                else if(x == 1) 
                {
                    linha = linha.replace("not(B)", letras[1] == 0 ? "1" : "0");
                    linha = linha.replace("B", letras[1] == 0 ? "0" : "1");
                }
                else if(x == 2) 
                {
                    linha = linha.replace("not(C)", letras[2] == 0 ? "1" : "0");
                    linha = linha.replace("C", letras[2] == 0 ? "0" : "1");
                }
            }

            

            while(linha.length() > 1)
            {
                int ultima = ultimo(linha);
    
                String expressao = linha.substring(ultima, linha.indexOf(")", ultima) + 1);

               

                if(expressao.charAt(0) != 'n') 
                {
                    int c = 1;

                    for(int x = 0; x != expressao.length(); x++) if(expressao.charAt(x) == ',') c++;

                    int[] para = new int[c];
                    int p = 0;

                    for(int x = 0; x != c; x++) 
                    {
                        p = primeiro(expressao, p);
                        
                        String string_num = expressao.substring(p, ++p);

                        para[x] = Integer.parseInt(string_num);
                    }

                    

                    if(expressao.charAt(0) == 'a') 
                    {
                        String resposta = "1";

                        if(c == 1) resposta = String.format("%i", para[0]);
                        else
                        {
                            for(int x = 0; x != c; x++)
                            {
                                if(para[x] == 0) 
                                {
                                    resposta = "0";
                                    break;
                                }
                            }
                        }

                        linha = linha.replace(expressao, resposta);
                    }
                    else if(expressao.charAt(0) == 'o') 
                    {
                        String resposta = "0";

                        if(c == 1) resposta = String.format("%i", para[0]);
                        else
                        {
                            for(int x = 0; x != c; x++) 
                            {
                                if(para[x] == 1) 
                                {
                                    resposta = "1";
                                    break;
                                }
                            }
                        }

                        linha = linha.replace(expressao, resposta);
                    }
                }
                else
                {
                    if(expressao.equals("not(0)")) linha = linha.replace("not(0)", "1");
                    else if(expressao.equals("not(1)")) linha = linha.replace("not(1)", "0");
                }
            }

            MyIO.println(linha);
        }
    }
}