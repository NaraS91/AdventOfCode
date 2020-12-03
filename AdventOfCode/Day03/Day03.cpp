#include <iostream>
#include <fstream>
#include <vector>
using namespace std;

vector<string> parseInput(string filePath){
	vector<string> result;
	
	ifstream file(filePath);
	
	string line;
	while(getline(file, line)){
		result.push_back(line);
	}
	
	file.close();
	
	return result;
}

int solve(vector<string> &trees_map, int right, int down){
	int width = trees_map[0].size();
	int height = trees_map.size();
	
	int i = right % width, j = down;
	
	int result = 0;
	
	while(j < height){
		if(trees_map[j][i] == '#')
			result++;
			
		i = (i + right) % width;
		j += down;
	}
	
	return result;
}

int main(){
	vector<string> trees_map = parseInput("input");
	cout << "Solution 1: " << solve(trees_map, 3, 1) << endl;
	cout << "Solution 2: " << solve(trees_map, 1, 1) * solve(trees_map, 3, 1) * solve(trees_map, 5, 1) * solve(trees_map, 7, 1) * solve(trees_map, 1, 2) << endl;
}






