import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main {

    private static final String FILENAME = "asciiArt.txt";
    private static int size;
    private static boolean firstLine = false;
    private static ArrayList<String> lines = new ArrayList<>();
    private static char alphabet[];
    private static List<List<String>> asciiArt = new ArrayList<>();
    private static ArrayList<HashMap<Character, String>> toArt = new ArrayList<>();


    public static void main(String[] args) {

        BufferedReader br = null;
        FileReader fr = null;

        try {

            br = new BufferedReader(new FileReader(FILENAME));
            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                if (!firstLine) {
                    size = Integer.parseInt(sCurrentLine);
                    firstLine = true;
                    continue;
                }
                lines.add(sCurrentLine);
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }


        //work out
        alphabet = lines.get(0).toCharArray();

        for (int i = 1; i < lines.size(); i++) {
            if (toArt.size() < lines.size())
                toArt.add(new HashMap<>());

            int index = 0;

            while (index < lines.get(i).length()) {
                String piece = lines.get(i).substring(index, Math.min(index + 9, lines.get(i).length()));

                toArt.get(i-1).put(Character.toUpperCase(alphabet[index / 9]), piece);

                index += 9;
            }
        }

        String toBeConverted = "Hello";

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < toBeConverted.length(); j++)
                System.out.print(toArt.get(i).get(Character.toUpperCase(toBeConverted.charAt(j))));
            System.out.println();
        }
    }
}
