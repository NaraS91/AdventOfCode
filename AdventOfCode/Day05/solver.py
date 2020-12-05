def boarding_pass_to_seat(boarding_pass):
  row = 0
  powOf2 = 64

  for i in range(0, 7):
    if boarding_pass[i] == 'B':
      row += powOf2
    powOf2 /= 2

  column = 0
  powOf2 = 4
  for i in range(7, 10):
    if boarding_pass[i] == 'R':
      column += powOf2
    powOf2 /= 2

  return (row, column)

def row_column_to_id(row_column):
  return row_column[0] * 8 + row_column[1]

def input_to_list_of_boarding_passes(file_address):
  f = open(file_address, "r")
  return f.readlines()

def solve1(input_file):
  boarding_passes = input_to_list_of_boarding_passes(input_file)
  seats = map(boarding_pass_to_seat, boarding_passes)
  ids = map(row_column_to_id, seats)
  return max(ids)

def solve2(input_file):
  boarding_passes = input_to_list_of_boarding_passes(input_file)
  seats = map(boarding_pass_to_seat, boarding_passes)
  ids = list(map(row_column_to_id, seats))
  ids_set = set()
  for id in ids:
    ids_set.add(id)
  
  for id in ids:
    if id + 1 not in ids_set and id + 2 in ids_set:
      return id + 1



print(solve1("input"))
print(solve2("input"))
