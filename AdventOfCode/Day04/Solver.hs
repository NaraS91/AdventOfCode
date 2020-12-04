import System.IO
import Control.Monad
import Data.List
import Data.Char

data PassportData = Byr Int | Iyr Int | Eyr Int | Hgt String | Hcl String | Ecl String | Pid String | Cid Int
type Passport = [PassportData]

prefixes = ["byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:", "cid:"]
    
main = do
    handle <- openFile "input" ReadMode
    contents <- hGetContents handle
    let passportsStrings = separatePassportsInput contents
        validPassports1 = filter isAValidPassport1 passportsStrings
        result1 = length validPassports1
        validPassports1' = map stringToPassport validPassports1
        validPassports2 = filter isAValidPassport2 validPassports1'
        result2 = length validPassports2
    putStr "solution 1: " 
    putStr (show result1)
    putStr "\nsolution 2: "
    putStr (show result2)

separatePassportsInput :: String -> [String]
separatePassportsInput [] = []
separatePassportsInput (c : c' : cs)
    | c == '\n' && c' == '\n' = [] : separatePassportsInput cs
    | c == '\n'              = separatePassportsInput (' ' : c' : cs) 
    | null passports         = [[c]]
    | otherwise              = (c : p) : ps
        where
            passports = separatePassportsInput (c' : cs)
            (p : ps) = passports
separatePassportsInput s 
    = [s]

isAValidPassport1 :: String -> Bool
isAValidPassport1 s = length (filter (\x -> not (isPrefixOf "cid:" x)) (words s)) == 7


stringToPassport :: String -> Passport 
stringToPassport [] = []
stringToPassport s 
    = foldl (\passport pDataS -> (stringToPassportData pDataS) : passport ) [] (words s)

stringToPassportData :: [Char] -> PassportData
stringToPassportData s
    | isPrefixOf "byr:" s' = Byr (read (drop 4 s'))
    | isPrefixOf "iyr:" s' = Iyr (read (drop 4 s'))
    | isPrefixOf "eyr:" s' = Eyr (read (drop 4 s'))
    | isPrefixOf "hgt:" s' = Hgt (drop 4 s')
    | isPrefixOf "hcl:" s' = Hcl (drop 4 s')
    | isPrefixOf "ecl:" s' = Ecl (drop 4 s')
    | isPrefixOf "pid:" s' = Pid (drop 4 s')
    | isPrefixOf "cid:" s' = Cid (read (drop 4 s'))
    where
        s' = dropWhile isSpace s


isDataValid :: PassportData -> Bool
isDataValid (Byr i) = 1920 <= i && i <= 2002
isDataValid (Iyr i) = 2010 <= i && i <= 2020
isDataValid (Eyr i) = 2020 <= i && i <= 2030
isDataValid (Hgt s) = length s > 1 && if "cm" `elem` (tails s) then validCmHgt else (if "in" `elem` (tails s) then validInHgt else False)
    where
        i = read (takeWhile isDigit s)
        validCmHgt = 150 <= i && i <= 193
        validInHgt = 59 <= i && i <= 76
isDataValid (Hcl s) = length s == 7 && head s == '#' && all (\c -> isDigit c || (c >= 'a' && c < 'g')) (tail s)
isDataValid (Ecl s) = s == "amb" || s == "blu" || s == "brn" || s == "gry" || s == "grn" || s == "hzl" || s == "oth"
isDataValid (Pid s) = length s == 9 && all isDigit s
isDataValid (Cid i) = True;    
    
isAValidPassport2 :: Passport -> Bool
isAValidPassport2 passport = all isDataValid passport