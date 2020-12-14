import Data.Bits
import Data.Map
import Data.Int
import Data.Char
import System.IO

data Mask = Mask Int64 Int64
data Instruction = M String | Mem Int Int64

encode :: Mask -> Int64 -> Int64
encode (Mask ones zeros) value
  = (value .&. zeros) .|. ones

encode2 :: String -> Int -> [Int]
encode2 mask addr
  = encode2' (reverse mask) addr 0
  where
    encode2' [] addr b = [addr]
    encode2' (c : cs) addr b
      | c == '0' = encode2' cs addr (b + 1)
      | c == '1' = set
      | c == 'X' = cleared ++ set
      where
        cleared = encode2' cs (clearBit addr b) (b + 1)
        set = encode2' cs (setBit addr b) (b + 1)

parseInput :: String -> [Instruction]
parseInput s
  = parseInput' (words s)
  where
    parseInput' [] = []
    parseInput' (w : ws)
      | w == "mask" = (M arg) : (parseInput' ws')
      | otherwise   = (Mem addr (read arg)) : (parseInput' ws')
      where
        (_ : arg : ws') = ws
        addr :: Int
        addr = read (takeWhile isDigit (dropWhile (\c -> not (isDigit c)) w))

stringToMask :: String -> Mask
stringToMask s
  = stringToMask' (reverse s) (Mask 0 0) 0
  where
    stringToMask' :: String -> Mask -> Int -> Mask
    stringToMask' [] mask _ = mask
    stringToMask' (c : cs) (Mask ones zeros) b 
      | c == 'X'  = stringToMask' cs (Mask ones setZeros) (b + 1)
      | c == '1'  = stringToMask' cs (Mask setOnes setZeros) (b + 1)
      | otherwise = stringToMask' cs (Mask ones zeros) (b + 1)
      where
        setOnes  = setBit ones b
        setZeros = setBit zeros b

solve1 instructions
  = solve1' instructions (Mask 0 0) empty
  where
    solve1' :: [Instruction] -> Mask -> Map Int Int64 -> Int64
    solve1' [] _ m = Data.Map.foldr (+) 0 m
    solve1' ((M newMask) : insts) baseMask m
      = solve1' insts (stringToMask newMask) m
    solve1' ((Mem addr value) : insts) mask m
      = solve1' insts mask (insert addr (encode mask value) m)

solve2 instructions
  = solve2' instructions "0" empty
  where
    solve2' :: [Instruction] -> String -> Map Int Int64 -> Int64
    solve2' [] _ m = Data.Map.foldr (+) 0 m
    solve2' ((M newMask) : insts) baseMask m
      = solve2' insts newMask m
    solve2' ((Mem addr value) : insts) mask m
      = solve2' insts mask (Prelude.foldl (\m' addr -> insert addr value m') m (encode2 mask addr))      

main = do
  handle <- openFile "input" ReadMode
  contents <- hGetContents handle
  let instructions = parseInput contents
      solution1 = solve1 instructions
      solution2 = solve2 instructions
  putStr (show solution1)
  putStr "\n"
  putStr (show solution2)
  putStr "\n"