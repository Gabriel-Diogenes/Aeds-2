import java.io.*;
import java.text.*;
import java.util.*;

class ListaShows {
    private Shows[] array;
    private int n;

    public ListaShows(int capacidade) {
        array = new Shows[capacidade];
        n = 0;
    }

    public void inserirInicio(Shows s) throws Exception {
        if (n >= array.length) throw new Exception("Lista cheia");
        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = s;
        n++;
    }

    public void inserirFim(Shows s) throws Exception {
        if (n >= array.length) throw new Exception("Lista cheia");
        array[n++] = s;
    }

    public void inserir(Shows s, int pos) throws Exception {
        if (n >= array.length || pos < 0 || pos > n) throw new Exception("Posição inválida");
        for (int i = n; i > pos; i--) {
            array[i] = array[i - 1];
        }
        array[pos] = s;
        n++;
    }

    public Shows removerInicio() throws Exception {
        if (n == 0) throw new Exception("Lista vazia");
        Shows resp = array[0];
        for (int i = 0; i < n - 1; i++) {
            array[i] = array[i + 1];
        }
        n--;
        return resp;
    }

    public Shows removerFim() throws Exception {
        if (n == 0) throw new Exception("Lista vazia");
        return array[--n];
    }

    public Shows remover(int pos) throws Exception {
        if (n == 0 || pos < 0 || pos >= n) throw new Exception("Posição inválida");
        Shows resp = array[pos];
        for (int i = pos; i < n - 1; i++) {
            array[i] = array[i + 1];
        }
        n--;
        return resp;
    }

    public void mostrar() {
        for (int i = 0; i < n; i++) {
            array[i].imprimir();
        }
    }
}

public class Lista implements Cloneable {
    private String show_id;
    private String type;
    private String title;
    private String director;
    private String[] cast;
    private String country;
    private Date date_added;
    private int release_year;
    private String rating;
    private String duration;
    private String[] listed_in;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

    // Construtor vazio
    public Lista() {
        this.show_id = "NaN";
        this.type = "NaN";
        this.title = "NaN";
        this.director = "NaN";
        this.cast = new String[]{"NaN"};
        this.country = "NaN";
        this.date_added = null;
        this.release_year = 0;
        this.rating = "NaN";
        this.duration = "NaN";
        this.listed_in = new String[]{"NaN"};
    }

    // Construtor com parâmetros
    public Lista(String show_id, String type, String title, String director, String[] cast,
                 String country, Date date_added, int release_year, String rating,
                 String duration, String[] listed_in) {
        this.show_id = show_id;
        this.type = type;
        this.title = title;
        this.director = director;
        this.cast = cast;
        this.country = country;
        this.date_added = date_added;
        this.release_year = release_year;
        this.rating = rating;
        this.duration = duration;
        this.listed_in = listed_in;
    }

    // Getters e setters
    public String getShow_id() { return show_id; }
    public void setShow_id(String show_id) { this.show_id = show_id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String[] getCast() { return cast; }
    public void setCast(String[] cast) { this.cast = cast; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Date getDate_added() { return date_added; }
    public void setDate_added(Date date_added) { this.date_added = date_added; }

    public int getRelease_year() { return release_year; }
    public void setRelease_year(int release_year) { this.release_year = release_year; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String[] getListed_in() { return listed_in; }
    public void setListed_in(String[] listed_in) { this.listed_in = listed_in; }

    // Método clone
    @Override
    public Shows clone() {
        try {
            Shows copy = (Shows) super.clone();
            copy.cast = cast.clone();
            copy.listed_in = listed_in.clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
    // Método imprimir
    public void imprimir() {
        Arrays.sort(cast);
        System.out.print("=> " + show_id + " ## " + title + " ## " + type + " ## " + director + " ## ");
        System.out.print(Arrays.toString(cast) + " ## " + country + " ## ");
        System.out.print((date_added != null ? sdf.format(date_added) : "NaN") + " ## ");
        System.out.print(release_year + " ## " + rating + " ## " + duration + " ## ");
        System.out.println(Arrays.toString(listed_in) + " ##");
    }

    // Método ler
    public static Shows ler(String linha) {
        String[] campos = dividirLinhaCSV(linha);

        Shows show = new Shows();
        show.setShow_id(valorOuNaN(campos, 0));
        show.setType(valorOuNaN(campos, 1));
        show.setTitle(valorOuNaN(campos, 2));
        show.setDirector(valorOuNaN(campos, 3));

        String castStr = valorOuNaN(campos, 4);
        show.setCast(castStr.equals("NaN") ? new String[]{"NaN"} : castStr.split(", ?"));

        show.setCountry(valorOuNaN(campos, 5));

        try {
            show.setDate_added(campos.length > 6 && !campos[6].isEmpty() ? sdf.parse(campos[6]) : null);
        } catch (ParseException e) {
            show.setDate_added(null);
        }

        try {
            show.setRelease_year(Integer.parseInt(valorOuNaN(campos, 7)));
        } catch (NumberFormatException e) {
            show.setRelease_year(0);
        }

        show.setRating(valorOuNaN(campos, 8));
        show.setDuration(valorOuNaN(campos, 9));

        String listedInStr = valorOuNaN(campos, 10);
        show.setListed_in(listedInStr.equals("NaN") ? new String[]{"NaN"} : listedInStr.split(", ?"));

        return show;
    }

    private static String valorOuNaN(String[] campos, int index) {
        return (index < campos.length && !campos[index].isEmpty()) ? campos[index] : "NaN";
    }

    private static String[] dividirLinhaCSV(String linha) {
        List<String> campos = new ArrayList<>();
        StringBuilder campo = new StringBuilder();
        boolean entreAspas = false;

        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);
            if (c == '"') {
                entreAspas = !entreAspas;
            } else if (c == ',' && !entreAspas) {
                campos.add(campo.toString());
                campo.setLength(0);
            } else {
                campo.append(c);
            }
        }
        campos.add(campo.toString());

        return campos.toArray(new String[0]);
    }

    // Método main
   public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
    Map<String, String> mapaCsv = new HashMap<>();

    BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv"));
    String linha = br.readLine(); // cabeçalho
    while ((linha = br.readLine()) != null) {
        String id = linha.split(",", 2)[0];
        mapaCsv.put(id, linha);
    }
    br.close();

    ListaShows lista = new ListaShows(1000);

    // Leitura dos IDs iniciais
    while (sc.hasNext()) {
        String entrada = sc.nextLine();
        if (entrada.equals("FIM")) break;

        if (mapaCsv.containsKey(entrada)) {
            Shows show = Shows.ler(mapaCsv.get(entrada));
            lista.inserirFim(show);
        }
    }

    int numComandos = Integer.parseInt(sc.nextLine());
    for (int i = 0; i < numComandos; i++) {
        String linhaComando = sc.nextLine();
        String[] partes = linhaComando.split(" ");

        switch (partes[0]) {
            case "II": {
                String id = partes[1];
                Shows show = Shows.ler(mapaCsv.get(id));
                lista.inserirInicio(show);
                break;
            }
            case "IF": {
                String id = partes[1];
                Shows show = Shows.ler(mapaCsv.get(id));
                lista.inserirFim(show);
                break;
            }
            case "I*": {
                int pos = Integer.parseInt(partes[1]);
                String id = partes[2];
                Shows show = Shows.ler(mapaCsv.get(id));
                lista.inserir(show, pos);
                break;
            }
            case "RI": {
                Shows removido = lista.removerInicio();
                System.out.println("(R) " + removido.getTitle());
                break;
            }
            case "RF": {
                Shows removido = lista.removerFim();
                System.out.println("(R) " + removido.getTitle());
                break;
            }
            case "R*": {
                int pos = Integer.parseInt(partes[1]);
                Shows removido = lista.remover(pos);
                System.out.println("(R) " + removido.getTitle());
                break;
            }
        }
    }

    lista.mostrar();
    sc.close();
}
}
