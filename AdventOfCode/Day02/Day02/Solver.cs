using System;
using System.Collections.Generic;
using System.Linq;

namespace Day02
{
  class Solver
  {
    static void Main(string[] args)
    {
      int solution1 = solve1(Utills.ParseInput("input"));
      int solution2 = solve2(Utills.ParseInput("input"));

      Console.WriteLine("part 1 = " + solution1);
      Console.WriteLine("part 2 = " + solution2);
      System.Diagnostics.Debug.WriteLine("part 1 = " + solution1);
      System.Diagnostics.Debug.WriteLine("part 2 = " + solution2);
    }

    public static int solve1(List<Tuple<PasswordPolicy, string>> input)
    {
      int numOfCorrectPasswords = 0;

      foreach(Tuple<PasswordPolicy, string> line in input)
      {
        PasswordPolicy policy = line.Item1;
        string password = line.Item2;

        char charToCount = policy.Character;

        int count = password.Count(c => charToCount == c);

        if (count >= policy.Min && count <= policy.Max) 
          numOfCorrectPasswords++;
      }

      return numOfCorrectPasswords;
    }

    public static int solve2(List<Tuple<PasswordPolicy, string>> input)
    {
      int numOfCorrectPasswords = 0;

      foreach (Tuple<PasswordPolicy, string> line in input)
      {
        PasswordPolicy policy = line.Item1;
        string password = line.Item2;

        char charToCount = policy.Character;

        if(safeIsCharAt(charToCount, policy.Min - 1, password) ^
            safeIsCharAt(charToCount, policy.Max - 1, password))
          numOfCorrectPasswords++;
      }

      return numOfCorrectPasswords;
    }

    private static bool safeIsCharAt(char c, int index, string s)
    {
      return s.Length > index && index >= 0 && s[index] == c;
    }
  }
}
