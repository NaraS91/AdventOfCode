import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Solver {

  public static void main(String[] args) {

    List<List<String>> groups = parseInput("input");



    System.out.println("Solution 1: " + solve1(groups));
    System.out.println("Solution 2: " + solve2(groups));
  }

  private static List<List<String>> parseInput(String filePath){
    File inputFile = new File(filePath);
    Scanner scanner;

    try {
      scanner = new Scanner(inputFile);
    } catch (FileNotFoundException e){
      throw new RuntimeException("file not found");
    }

    List<List<String>> result = new LinkedList<>();
    result.add(new LinkedList<>());

    while (scanner.hasNextLine()){
      String line = scanner.nextLine();
      if(line.isBlank()){
        result.add(0, new LinkedList<>());
      } else {
        result.get(0).add(line);
      }
    }

    return result;
  }

  private static int countAllAnswers(List<String> group){
    Set<Character> uniqueAnswers = new HashSet<>();

    for(String answers : group){
      for(Character answer : answers.toCharArray()){
        if(answer >= 'a' && answer <= 'z')
          uniqueAnswers.add(answer);
      }
    }

    return uniqueAnswers.size();
  }

  private static int solve1(List<List<String>> groups){
    int sum = 0;

    for(List<String> group : groups){
      sum += countAllAnswers(group);
    }

    return sum;
  }

  private static Set<Character> stringToSet(String s){
    Set<Character> result = new HashSet<>();

    for(char c : s.toCharArray()){
      result.add(c);
    }

    return result;
  }

  private static int countCommonAnswers(List<String> group){
    if(group.size() == 0)
      return 0;

    Set<Character> commonAnswers = new HashSet<>();

    for(char c = 'a'; c <= 'z'; c++){
      commonAnswers.add(c);
    }

    for(String answers : group){
      commonAnswers.retainAll(stringToSet(answers));
    }

    return commonAnswers.size();
  }

  private static int solve2(List<List<String>> groups){
    int sum = 0;

    for(List<String> group : groups){
      sum += countCommonAnswers(group);
    }

    return sum;
  }

}
