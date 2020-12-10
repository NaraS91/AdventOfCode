def input_to_list_of_ints(file_path):
  f = open(file_path, "r")
  return f.readlines()

def solve1(input_file):
  adapter_values = input_to_list_of_ints(input_file)
  adapters = list(map(int, adapter_values))
  adapters.sort()
  lastAdapter = 0
  diff1 = 0
  diff3 = 0
  for adapter in adapters:
    if adapter - lastAdapter == 1:
      diff1 += 1
    elif adapter - lastAdapter == 3:
      diff3 += 1
    lastAdapter = adapter

  return diff1 * (diff3 + 1)

def solve2(input_file):
  adapter_values = input_to_list_of_ints(input_file)
  adapters = list(map(int, adapter_values))
  adapters.sort()
  
  arrangements = [0] * len(adapters)
  arrangements[0] = 1
  arrangements[1] = 1 if adapters[1] > 3 else 2
  arrangements[2] = arrangements[1]
  if adapters[2] - adapters[0] <= 3:
    arrangements[2] += arrangements[0]
    if adapters[2] <= 3:
      arrangements[2] +=1

  for i in range(3, len(adapters)):
    j = i - 1
    while j >= 0 and adapters[i] - adapters[j] <= 3:
      arrangements[i] += arrangements[j]
      j -= 1
    if adapters[i] == 3:
      arrangements[i] += 1

  return arrangements[len(adapters) - 1]

print(solve1("input"))
print(solve2("input"))
