package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.graphstream.graph.implementations.*;

public class GraphTest {
    

    public void importGraph(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if(!file.isFile()) { // Si fichier n'est pas un fichier
            throw new FileNotFoundException();
        }
        Scanner lineScanner = null, dataScanner = null;
        String line = "", data = "";

        lineScanner = new Scanner(filePath);
        while(lineScanner.hasNextLine()) {
            line = lineScanner.nextLine(); // .nextLine() Récupère la ligne suivante. Avance automatiquement
            System.out.println("Ligne récupérée : |" + line + "|");
            dataScanner = new Scanner(line); // En théorie jamais vide puisque .hasNextLine() vérifié plus haut
            while(dataScanner.hasNext()) {
                data = dataScanner.next(); // .next() lit le "mot" suivant. space est le séparateur par défaut. Avance automatiquement
                System.out.println("Donnée : |" + data + "|");
            }
            dataScanner.close();
        }
        lineScanner.close();
    }
}
