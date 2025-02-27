import java.io.*;
import java.net.*;

class TP1Q13 {
    public static String getHtml(String endereco) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String resp = "", line;

        try {
            url = new URI(endereco).toURL();
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                resp += line + "\n";
            }
        } catch (MalformedURLException | URISyntaxException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
            }
        }

        return resp;
    }

    public static boolean strContains(String source, String target) {
        int sourceLength = source.length();
        int targetLength = target.length();

        for (int i = 0; i <= sourceLength - targetLength; i++) {
            boolean found = true;
            for (int j = 0; j < targetLength; j++) {
                if (source.charAt(i + j) != target.charAt(j)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return true;
            }
        }
        return false;
    }

    public static boolean strEquals(String str1, String str2) {
        int length = str1.length();
        for (int i = 0; i < length; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      String nomePagina;

      try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"))) {
          while (!(nomePagina = reader.readLine()).equals("FIM")) {
              String endereco = reader.readLine();
              String html = getHtml(endereco);
              // Counts and removes br and table
              int brCount = 0;
              int tableCount = 0;
              while (strContains(html, "<br>")) {
                  brCount++;
                  html = html.replaceFirst("<br>", "");
              }
              while (strContains(html, "<table>")) {
                  tableCount++;
                  html = html.replaceFirst("<table>", "");
              }

              // Count characters
              int[] charCount = new int[256];
              countCharacters(html, charCount);

              int consonantCount = countConsonants(charCount);
              // Print from webpage
              writer.print("a(" + charCount['a'] + ") e(" + charCount['e'] + ") i(" + charCount['i'] + ") o(" + charCount['o'] + ") u(" + charCount['u'] + ") ");
              writer.print("\u00E1(" + charCount['\u00E1'] + ") \u00E9(" + charCount['\u00E9'] + ") \u00ED(" + charCount['\u00ED'] + ") \u00F3(" + charCount['\u00F3'] + ") \u00FA(" + charCount['\u00FA'] + ") ");
              writer.print("\u00E0(" + charCount['\u00E0'] + ") \u00E8(" + charCount['\u00E8'] + ") \u00EC(" + charCount['\u00EC'] + ") \u00F2(" + charCount['\u00F2'] + ") \u00F9(" + charCount['\u00F9'] + ") ");
              writer.print("\u00E3(" + charCount['\u00E3'] + ") \u00F5(" + charCount['\u00F5'] + ") \u00E2(" + charCount['\u00E2'] + ") \u00EA(" + charCount['\u00EA'] + ") \u00EE(" + charCount['\u00EE'] + ") \u00F4(" + charCount['\u00F4'] + ") \u00FB(" + charCount['\u00FB'] + ") ");
              writer.print("consoante(" + consonantCount + ") ");
              writer.print("<br>(" + brCount + ") <table>(" + tableCount + ") " + nomePagina + "\n");
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

    public static void countCharacters(String text, int[] charCount) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            // Ignore non-unicode chars
            if (c < 256) {
                charCount[c]++;
            }
        }
    }

    public static int countConsonants(int[] charCount) {
        int consonantCount = 0;
        String consonants = "bcdfghjklmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ";

        for (int i = 0; i < consonants.length(); i++) {
            char c = consonants.charAt(i);
            if (c < 256) {
                consonantCount += charCount[c];
            }
        }

        return consonantCount;
    }
}