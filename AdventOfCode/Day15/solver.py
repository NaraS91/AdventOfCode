input = (0, 1, 5, 10, 3, 12, 19)
test_input = (0,3,6)

def find_nth_number(input_values, n):
  if n <= len(input_values):
    return input_values[n - 1]
  
  last_said = dict()

  for i in range(len(input_values) - 1):
    last_said[input_values[i]] = i

  prev = input_values[len(input_values) - 1]

  for i in range(len(input_values) - 1, n - 1):
    if prev in last_said:
      tmp = prev
      prev = i - last_said[tmp]
      last_said[tmp] = i
    else:
      last_said[prev] = i
      prev = 0

  return prev

def solve1(input_values):
  return find_nth_number(input_values, 2020)

def solve2(input_values):
  return find_nth_number(input_values, 30000000)

print(solve1(input))
print(solve2(input))
