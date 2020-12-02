using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Day02
{
  class PasswordPolicy
  {
    public int Min { get; private set; }
    public int Max { get; private set; }
    public char Character { get; private set; }

    public PasswordPolicy(int min, int max, char c)
    {
      this.Min = min;
      this.Max = max;
      this.Character = c;
    }
  }
}
