#include <algorithm>
#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
using namespace std;

int minimumRefill(vector<int> &plants, int capacityA, int capacityB) {
	int n = plants.size();
	int i = 0, j = n - 1, cnt = 0, leftA = capacityA,
		leftB = capacityB;
    while(i <= j){
        if(i == j){
            if(max(leftA, leftB) < plants[i]) cnt++;
            break;
        }
        if(leftA < plants[i]){
            leftA = capacityA;
            cnt++;
        }
        if(leftB < plants[j]){
            leftB = capacityB;
            cnt++;
        }
        leftA -= plants[i];
        leftB -= plants[j];
        i++;
        j--;
    }
    return cnt;
}