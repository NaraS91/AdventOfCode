using System;
using System.Collections.Generic;
using System.Linq;

namespace Day07
{
  class Solver
  {
    static void Main(string[] args)
    {
      Console.WriteLine(Solve1(ParseInput("input")));
      Console.WriteLine(Solve2(ParseInput("input")));
    }

    private static Dictionary<string, Dictionary<string, int>> ParseInput(string filePath)
    {
      Dictionary<string, Dictionary<string, int>> result = new Dictionary<string, Dictionary<string, int>>();
      string[] lines = System.IO.File.ReadAllLines(filePath);

      foreach (string line in lines)
      {
        string[] splitLine = line.Split(new char[] { ' ', ',', '.' });
        string mainBag = splitLine[0] + splitLine[1];
        result.Add(mainBag, new Dictionary<string, int>());
        if (!splitLine[4].Equals("no"))
        {
          for (int i = 4; i < splitLine.Length - 1; i += 5)
          {
            string subBag = splitLine[i + 1] + splitLine[i + 2];
            result[mainBag].Add(subBag, int.Parse(splitLine[i]));
          }
        }
      }

      return result;
    }

    private static int Solve1(Dictionary<string, Dictionary<string, int>> bags)
    {
      int result = 0;

      foreach (string bag in bags.Keys)
      {
        HashSet<string> visited = new HashSet<string>();

        Queue<string> queue = new Queue<string>(bags[bag].Keys);

        while (queue.Count > 0)
        {
          string foundBag = queue.Dequeue();

          if (foundBag.Equals("shinygold"))
          {
            result++;
            break;
          }

          if (!visited.Contains(foundBag))
          {
            visited.Add(foundBag);
            foreach (string bagToAdd in bags[foundBag].Keys)
            {
              queue.Enqueue(bagToAdd);
            }
          }
        }
      }

      return result;
    }

    private static int Solve2(Dictionary<string, Dictionary<string, int>> bags)
    {
      return CountBagsInBag("shinygold", bags);
    }

    private static int CountBagsInBag(string mainBag, Dictionary<string, Dictionary<string, int>> bags)
    {
      int result = 0;
      foreach (KeyValuePair<string, int> bagCount in bags[mainBag])
      {
        result += (CountBagsInBag(bagCount.Key, bags) + 1) * bagCount.Value;
      }

      return result;
    }

  }
}
