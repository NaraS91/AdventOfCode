import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution1 {

  public static void main(String[] args) {

    System.out.println(solve(Utils.parseInput("src\\input")));
  }


  private static int solve(List<Integer> input){
    Set<Integer> values = new HashSet<>();

    for(int v : input){
      if(values.contains(2020 - v)){
        return v * (2020 - v);
      } else {
        values.add(v);
      }
    }

    return - 1;
  }

}
