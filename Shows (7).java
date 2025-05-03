import java.io.*;
import java.text.*;
import java.util.*;

public class Shows implements Cloneable {
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
    public Shows() {
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
    public Shows(String show_id, String type, String title, String director, String[] cast,
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

    public static void quicksortParcial(List<Shows> lista, int esq, int dir, int k,
                                    int[] comparacoes, int[] movimentacoes) {
    if (esq >= dir) return;

    int i = esq, j = dir;
    Shows pivo = lista.get((esq + dir) / 2);

    while (i <= j) {
        while (menor(lista.get(i), pivo, comparacoes)) i++;
        while (menor(pivo, lista.get(j), comparacoes)) j--;

        if (i <= j) {
            Collections.swap(lista, i, j);
            movimentacoes[0] += 3;
            i++;
            j--;
        }
    }

    if (esq < j) quicksortParcial(lista, esq, j, k, comparacoes, movimentacoes);
    if (i < k && i < dir) quicksortParcial(lista, i, dir, k, comparacoes, movimentacoes);
}

private static boolean menor(Shows a, Shows b, int[] comparacoes) {
    comparacoes[0]++;
    if (a.getDate_added() == null && b.getDate_added() != null) return true;
    if (a.getDate_added() != null && b.getDate_added() == null) return false;

    if (a.getDate_added() == null && b.getDate_added() == null) {
        return a.getTitle().compareTo(b.getTitle()) < 0;
    }

    int cmp = a.getDate_added().compareTo(b.getDate_added());
    if (cmp != 0) return cmp < 0;

    return a.getTitle().compareTo(b.getTitle()) < 0;
}

public static void executarQuicksortParcial(List<Shows> lista, int k) throws IOException {
    int[] comparacoes = {0};
    int[] movimentacoes = {0};

    long inicio = System.nanoTime();
    quicksortParcial(lista, 0, lista.size() - 1, k, comparacoes, movimentacoes);
    long fim = System.nanoTime();
    long tempo = (fim - inicio) / 1000000;

    BufferedWriter log = new BufferedWriter(new FileWriter("692997_quicksort.txt"));
    log.write("692997\t" + comparacoes[0] + "\t" + movimentacoes[0] + "\t" + tempo + "\n");
    log.close();
}

    

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Map<String, String> mapaCsv = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv"));
        String linha = br.readLine(); 
        while ((linha = br.readLine()) != null) {
            String id = linha.split(",", 2)[0];
            mapaCsv.put(id, linha);
        }
        br.close();

        List<Shows> lista = new ArrayList<>();
        while (sc.hasNext()) {
            String entrada = sc.nextLine();
            if (entrada.equals("FIM")) break;

            if (mapaCsv.containsKey(entrada)) {
                Shows show = Shows.ler(mapaCsv.get(entrada));
                lista.add(show);
            }
        }

        FileWriter writer = new FileWriter("692997_quicksort.txt");
        BufferedWriter log = new BufferedWriter(writer);
        executarQuicksortParcial(lista, 10);
      
        log.close();

       
        for (int i = 0; i < Math.min(10, lista.size()); i++) {
            lista.get(i).imprimir();
        }

        sc.close();
    }

    
}
