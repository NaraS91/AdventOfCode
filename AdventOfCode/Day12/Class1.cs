using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Day12
{
  enum Direction
  {
    N = 0,
    E = 1,
    S = 2,
    W = 3
  }

  class Solver
  {

    public static int Solve1(List<Tuple<char, int>> commands)
    {
      Direction dir = Direction.E;
      Dictionary<char, int> location = new Dictionary<char, int>();
      location.Add('N', 0);
      location.Add('S', 0);
      location.Add('W', 0);
      location.Add('E', 0);

      foreach (Tuple<char, int> command in commands)
      {
        switch (command.Item1)
        {
          case 'L':
            dir = nextDirection(dir, 4 - command.Item2 / 90);
            break;
          case 'R':
            dir = nextDirection(dir, command.Item2 / 90);
            break;
          case 'F':
            location[dir.ToString()[0]] += command.Item2;
            break;
          default:
            location[command.Item1] += command.Item2;
            break;
        }
      }

      int we = Math.Abs(location['W'] - location['E']);
      int ns = Math.Abs(location['N'] - location['S']);
      return we + ns;
    }

    public static int Solve2(List<Tuple<char, int>> commands)
    {
      Direction dir = Direction.E;
      Dictionary<char, int> location = new Dictionary<char, int>();
      Dictionary<char, int> waypoint = new Dictionary<char, int>();
      location.Add('N', 0);
      location.Add('S', 0);
      location.Add('W', 0);
      location.Add('E', 0);

      waypoint.Add('N', 1);
      waypoint.Add('S', 0);
      waypoint.Add('W', 0);
      waypoint.Add('E', 10);



      foreach (Tuple<char, int> command in commands)
      {
        switch (command.Item1)
        {
          case 'L':
            rotateWaypoint(waypoint, 4 - command.Item2 / 90);
            break;
          case 'R':
            rotateWaypoint(waypoint, command.Item2 / 90);
            break;
          case 'F':
            location['N'] += waypoint['N'] * command.Item2;
            location['S'] += waypoint['S'] * command.Item2;
            location['W'] += waypoint['W'] * command.Item2;
            location['E'] += waypoint['E'] * command.Item2;
            break;
          default:
            waypoint[command.Item1] += command.Item2;
            break;
        }
      }

      int we = Math.Abs(location['W'] - location['E']);
      int ns = Math.Abs(location['N'] - location['S']);
      return we + ns;
    }

    private static Direction nextDirection(Direction dir, int i)
    {
      if (i == 0)
        return dir;

      switch (dir) {
        case Direction.N:
          return nextDirection(Direction.E, i - 1);
        case Direction.E:
          return nextDirection(Direction.S, i - 1);
        case Direction.S:
          return nextDirection(Direction.W, i - 1);
        case Direction.W:
          return nextDirection(Direction.N, i - 1);
        default:
          throw new Exception("hmmmm");
      }

    }

    private static void rotateWaypoint(Dictionary<char, int> waypoint, int i)
    {
      if (i == 0)
        return;

      var temp = waypoint['N'];
      waypoint['N'] = waypoint['W'];
      waypoint['W'] = waypoint['S'];
      waypoint['S'] = waypoint['E'];
      waypoint['E'] = temp;

      rotateWaypoint(waypoint, i - 1);
    }


    private static List<Tuple<char, int>> ParseInput(string filePath)
    {
      string[] lines = System.IO.File.ReadAllLines(filePath);
      var result = new List<Tuple<char, int>>();

      foreach (string line in lines)
      {
        result.Add(new Tuple<char, int>(line[0], int.Parse(line.Substring(1))));
      }

      return result;
    }

    static void Main(string[] args)
    {
      int solution1 = Solve1(ParseInput("input"));
      int solution2 = Solve2(ParseInput("input"));
      Console.WriteLine(solution1);
      Console.WriteLine(solution2);
    }
  }
}
