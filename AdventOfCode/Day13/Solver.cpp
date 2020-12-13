#include <iostream>
#include <fstream>
#include <vector>
using namespace std;

unsigned long long gcd(unsigned long long a, unsigned long long b){
	unsigned long long c;
	unsigned long long temp = b;
	if(b > a){
		b = a;
		a = temp;
	}
	
	while(b != 0){
		temp = a % b;
		a = b;
		b = temp;
	}
	
	return a;
}

int solve1(int timestamp, vector<int> ids){
	int result_id = 0;
	int best_diff = INT_MAX;
	
	for(int i = 0; i < ids.size(); i++){
		if(ids[i] == -1)
			continue;
		if(timestamp % ids[i] == 0){
			result_id = i;
			best_diff = 0;
			break;
		}
		int diff = (timestamp / ids[i] + 1) * ids[i] - timestamp;
		if(diff < best_diff){
			result_id = i;
			best_diff = diff;
		}
	}
	return ids[result_id] * best_diff;
}

unsigned long long solve2(vector<int> ids){
	unsigned long long lcm = ids[0];
	unsigned long long result = ids[0];
	for(int i = 1; i < ids.size(); i++){
		if(ids[i] == -1)
			continue;
		while((result + i) % ids[i] != 0){
			result += lcm;
		}
		lcm = lcm * (ids[i] / gcd(lcm,ids[i]));
	}
	
	return result;
}

int main(){
	int timestamp;
	
	ifstream file("input");
	
	string line;
	getline(file, line);
	timestamp = stoi(line);
	
	getline(file, line);
	vector<int> ids;
	int i = 0;
	int num = 0;
	while(i < line.size()){
		if(line[i] == ',' && num > 0){
			ids.push_back(num);
			num = 0;
		} else {
			if(line[i] == 'x')
				ids.push_back(-1);
			else if(line[i] != ',')
				num = 10*num + (line[i] - '0');
		}
		i++;	
	}
	
	if(num > 0)
		ids.push_back(num);
		
	int solution1 = solve1(timestamp, ids);
	cout << solution1 << endl;
	unsigned long long solution2 = solve2(ids);
	cout << solution2;
}
