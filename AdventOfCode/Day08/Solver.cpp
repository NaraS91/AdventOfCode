#include <iostream>
#include <fstream>
#include <vector>
#include <set>
using namespace std;

bool found = false;

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

int getInstructionValue(string line){
	string num = line.substr(5, line.length() - 5);
	int result  = stoi(num);
	if(line[4] == '-')
		result = - result;
	return result;
}

int solve1(vector<string> commands){
	int acc = 0;
	int pc = 0;
	set<int> visited;
	
	while(visited.find(pc) == visited.end()){
		if(pc >= commands.size()){
			found = true;
			return acc;
		}
		
		visited.insert(pc);
		
		switch(commands[pc][0]){
			case 'a':
				acc += getInstructionValue(commands[pc]);
				break;
			case 'j':
				pc += getInstructionValue(commands[pc]) - 1;
				break;
			case 'n':
				break;
		}
		
		pc++;
	}
	
	return acc;
}

int solve2(vector<string> commands){
	found = false;
	
	for(int i = 0; i < commands.size(); i++){
		char savedCommand = commands[i][0];
		switch(commands[i][0]){
			case 'j':
				commands[i][0] = 'n';
				break;
			case 'n':
				commands[i][0] = 'j';
				break;
			default:
				break;
		}
		
		int maybeResult = solve1(commands);
		if(found)
			return maybeResult;
		commands[i][0] = savedCommand;
	}
}

int main(){
	vector<string> commands = parseInput("input");
	cout << "Solution 1: " << solve1(commands) << endl;
	cout << "Solution 2: " << solve2(commands) << endl;
}
