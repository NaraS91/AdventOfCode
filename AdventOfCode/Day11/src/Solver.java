import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solver {

  public static List<char[]> parseInput(String pathFile){
    Scanner scanner;
    try {
      scanner = new Scanner(new File(pathFile));
    } catch (Exception e){
      throw new IllegalArgumentException("no file at the given path found");
    }

    List<char[]> result = new ArrayList<>();

    while(scanner.hasNextLine()){
      String line = scanner.nextLine();
      char[] arr = new char[line.length()+2];
      for(int i = 1; i <line.length() + 1; i++){
        arr[i] = line.charAt(i - 1);
      }
      result.add(arr);
    }

    result.add(new char[result.get(1).length]);
    result.add(0, new char[result.get(1).length]);

    return result;
  }

  public static int solver1(String pathFile){
    boolean wasChanged = true;
    List<char[]> next = parseInput(pathFile);
    List<char[]> prev = parseInput(pathFile);
    int occupied = 0;

    while(wasChanged) {
      occupied = 0;
      wasChanged = false;

      for (int i = 1; i < prev.size() - 1; i++) {
        for (int j = 1; j < prev.get(0).length - 1; j++) {
          char initChar = prev.get(i)[j];
          char nextChar = calculateNextState(prev, i, j, initChar);
          next.get(i)[j] = nextChar;
          if (initChar != nextChar) {
            wasChanged = true;
          }
          if (nextChar == '#')
            occupied++;
        }
      }

      var temp = prev;
      prev = next;
      next = temp;
    }

    return occupied;
  }

  private static char calculateNextState(List<char[]> seats, int i, int j, char init){
    int occupiedSeats = 0;

    if(seats.get(i - 1)[j + 1] == '#')
      occupiedSeats++;
    if(seats.get(i)[j + 1] == '#')
      occupiedSeats++;
    if(seats.get(i + 1)[j + 1] == '#')
      occupiedSeats++;
    if(seats.get(i - 1)[j] == '#')
      occupiedSeats++;
    if(seats.get(i + 1)[j] == '#')
      occupiedSeats++;
    if(seats.get(i - 1)[j - 1] == '#')
      occupiedSeats++;
    if(seats.get(i)[j - 1] == '#')
      occupiedSeats++;
    if(seats.get(i + 1)[j - 1] == '#')
      occupiedSeats++;

    return decideSeat(occupiedSeats, init);
  }

  public static int solver2(String pathFile){
    boolean wasChanged = true;
    List<char[]> next = parseInput(pathFile);
    List<char[]> prev = parseInput(pathFile);
    int occupied = 0;

    while(wasChanged) {
      occupied = 0;
      wasChanged = false;

      for (int i = 1; i < prev.size() - 1; i++) {
        for (int j = 1; j < prev.get(0).length - 1; j++) {
          char initChar = prev.get(i)[j];
          char nextChar = calculateNextState2(prev, i, j, initChar);
          next.get(i)[j] = nextChar;
          if (initChar != nextChar) {
            wasChanged = true;
          }
          if (nextChar == '#')
            occupied++;
        }
      }

      var temp = prev;
      prev = next;
      next = temp;
    }

    return occupied;
  }

  private static char calculateNextState2(List<char[]> seats, int i, int j, char initChar) {
    if (initChar == '.')
      return '.';
    int occupiedSeats = 0;

    int upright = 1;
    int upleft = 1;
    int downleft = 1;
    int downright = 1;
    while(i - upright > 0 && j + upright < seats.get(0).length - 1 && seats.get(i - upright)[j + upright] != 'L' && seats.get(i - upright)[j + upright] != '#')
      upright++;
    while(i - upleft > 0 && j - upleft > 0 && seats.get(i - upleft)[j - upleft] != 'L' && seats.get(i - upleft)[j - upleft] != '#')
      upleft++;
    while(i + downleft < seats.size()  - 1 && j - downleft > 0 && seats.get(i + downleft)[j - downleft] != 'L' && seats.get(i + downleft)[j - downleft] != '#')
      downleft++;
    while(i + downright < seats.size() - 1 && j + downright < seats.get(0).length - 1 && seats.get(i + downright)[j + downright] != 'L' && seats.get(i + downright)[j + downright] != '#')
      downright++;

    if(seats.get(i - upright)[j + upright] == '#')
      occupiedSeats++;
    if(seats.get(i - upleft)[j - upleft] == '#')
      occupiedSeats++;
    if(seats.get(i + downright)[j + downright] == '#')
      occupiedSeats++;
    if(seats.get(i + downleft)[j - downleft] == '#')
      occupiedSeats++;

    int up = i - 1;
    int down = i + 1;
    int right = j + 1;
    int left = j - 1;
    while(up > 0 && seats.get(up)[j] != 'L' && seats.get(up)[j] != '#')
      up--;
    while(down < seats.size() - 1 && seats.get(down)[j] != 'L' && seats.get(down)[j] != '#')
      down++;
    while(left > 0 && seats.get(i)[left] != 'L' && seats.get(i)[left] != '#')
      left--;
    while(right < seats.get(0).length - 1 && seats.get(i)[right] != 'L' && seats.get(i)[right] != '#')
      right++;

    if(seats.get(up)[j] == '#')
      occupiedSeats++;
    if(seats.get(down)[j] == '#')
      occupiedSeats++;
    if(seats.get(i)[right] == '#')
      occupiedSeats++;
    if(seats.get(i)[left] == '#')
      occupiedSeats++;

    if(occupiedSeats == 0)
      return '#';
    if (occupiedSeats > 4)
      return 'L';
    return initChar;
  }

  private static char decideSeat(int occupiedSeats, char init){
    if (init == '.')
      return '.';
    if(occupiedSeats == 0)
      return '#';
    if (occupiedSeats > 3)
      return 'L';
    return init;
  }

  private static void printSeats(List<char[]> seats){
    for(int i = 0; i < seats.size(); i++){
      for (int j = 0; j < seats.get(0).length; j++){
        System.out.print(seats.get(i)[j]);
      }
      System.out.println();
    }
    System.out.println();
  }

  public static void main(String[] args) {
    System.out.println(solver1("src/input"));
    System.out.println(solver2("src/input"));
  }


}
