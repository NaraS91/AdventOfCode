import System.IO
data Queue a = Empty |  Queue [a] [a] Int deriving (Show)

size :: Queue a -> Int
size Empty = 0
size (Queue _ _ size) = size

enqueue :: Queue a -> a -> Queue a
enqueue Empty x = Queue [x] [] 1
enqueue (Queue xs ys s) y = Queue xs (y:ys) (s + 1)

queueCheck :: Queue a -> Queue a
queueCheck (Queue [] [] _) = Empty
queueCheck q = q

dequeue :: Queue a -> (a, Queue a)
dequeue Empty = error "queue is empty!"
dequeue (Queue [] ys s) = (y', queueCheck (Queue ys' [] (s - 1)))
  where
    (y' : ys') = reverse ys
dequeue (Queue (x:xs) ys s) = (x, queueCheck (Queue xs ys (s - 1)))

contains :: (Ord a) => Queue a -> a -> Bool
contains Empty _ = False
contains q elem
  | h == elem = True
  | otherwise = contains q' elem
  where
    (h, q') = dequeue q

-------

addsTo :: Queue Int -> Int -> Bool
addsTo Empty sum = False
addsTo q sum
  | contains q (sum - h) = True
  | otherwise            = addsTo q' sum
  where
    (h, q') = dequeue q

solve1 :: Queue Int -> [Int] -> Int
solve1 q [] = error "no solution"
solve1 q (i : is)
  | size q < 25 = solve1 (enqueue q i) is
  | addsTo q i  = solve1 (enqueue q' i) is
  | otherwise   = i
  where
    (_, q') = dequeue q

solve2 :: Int -> [Int] -> Int
solve target [] = target == 0
solve2 target is
  = solve2' Empty is 0
  where
    solve2' :: Queue Int -> [Int] -> Int -> Int
    solve2' q [] subSum = if target == subSum then calculateResult q maxBound minBound else error "no solution"
    solve2' Empty (i : is) _ = solve2' (enqueue Empty i) is i
    solve2' q (i : is) subSum
      | subSum > target  = solve2' q' (i : is) (subSum - h)
      | subSum == target = result q
      | otherwise        = solve2' (enqueue q i) is (subSum + i) 
      where
        result q = calculateResult q maxBound minBound
        (h, q') = dequeue q
    
    calculateResult :: Queue Int -> Int -> Int -> Int
    calculateResult Empty min max = min + max
    calculateResult q min max 
      | h < min  && h > max = calculateResult q' h h
      | h < min             = calculateResult q' h max
      | h > max             = calculateResult q' min h
      | otherwise           = calculateResult q' min max
      where
        (h, q') = dequeue q

main = do
    handle <- openFile "input" ReadMode
    contents <- hGetContents handle
    let
      values :: [Int]
      values = map read (words contents)
      result1 = solve1 Empty values
      result2 = solve2 result1 values
    putStr "Solution 1: "
    putStr (show result1)
    putStr "\nSolution 2: "
    putStr (show result2) 
