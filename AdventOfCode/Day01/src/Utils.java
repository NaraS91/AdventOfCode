import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
  public static List<Integer> parseInput(String filePath){
    BufferedReader fileToRead;

    try {
      fileToRead = new BufferedReader(new FileReader(filePath));
    } catch (Exception e){
      System.err.println("file not found");
      throw new RuntimeException("no file found");
    }

    return fileToRead.lines().map(Integer::parseInt).collect(Collectors.toList());
  }
}
