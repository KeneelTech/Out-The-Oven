import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileToList {
    public static void main(String[] args) {
        String filePath = "yourfile.txt";
        List<String> lines = new ArrayList<>();
        
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());  // Add each line to the list
            }
            
            // Print the list (or process it as needed)
            for (String lineInList : lines) {
                System.out.println(lineInList);
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
