#include <iostream>
#include <string>

using namespace std;

int main(){
    string s;
    cin >> s;
    for(char&c : s){
        if(c == '[') c = '{';
        else if(c == ']') c = '}';
    }
    cout << s << endl;
    return 0;
}