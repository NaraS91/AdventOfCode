using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Day02
{
  class Utills
  {
    public static List<Tuple<PasswordPolicy,string>> ParseInput(string filePath)
    {
      string[] lines = System.IO.File.ReadAllLines(filePath);
      List<Tuple<PasswordPolicy, string>> parsedInput = new List<Tuple<PasswordPolicy, string>>();

      foreach(string line in lines)
      {
        //lines are in form '{min}-{max} {char}: password'
        string[] splitedLine = line.Split(new[] { '-', ' ', ':' });
        int min = int.Parse(splitedLine[0]);
        int max = int.Parse(splitedLine[1]);
        char c = splitedLine[2][0];
        parsedInput.Add(new Tuple<PasswordPolicy, string>(new PasswordPolicy(min, max, c), splitedLine[4]));
      }

      return parsedInput;
    }
  }
}
