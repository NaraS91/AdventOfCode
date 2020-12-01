import java.util.List;

public class Solution2 {

  public static void main(String[] args) {
    System.out.println(solve(Utils.parseInput("src\\input")));
  }

  private static int solve (List<Integer> input){
    for (int i = 0; i < input.size() - 2; i++){
      for (int j = i + 1; j < input.size() - 1; j++){
        int searchingFor = 2020 - input.get(i) - input.get(j);
        for(int k = j + 1; k < input.size(); k++){
          if(searchingFor == input.get(k)){
            return input.get(i) * input.get(k) * input.get(j);
          }
        }
      }
    }

    return -1;
  }
}
