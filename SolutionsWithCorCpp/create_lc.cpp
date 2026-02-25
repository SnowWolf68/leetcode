#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

// 辅助函数：判断文件是否存在
bool fileExists(const string& name) {
    ifstream f(name.c_str());
    return f.good();
}

// 辅助函数：复制文件
bool copyFile(const string& srcPath, const string& dstPath) {
    ifstream src(srcPath.c_str(), ios::binary);
    if (!src.is_open()) return false;
    
    ofstream dst(dstPath.c_str(), ios::binary);
    if (!dst.is_open()) return false;

    dst << src.rdbuf();
    return true;
}

int main() {
    // 1. 配置路径 (注意：在字符串拼接时，手动加上路径分隔符)
    // 根目录下的模板文件
    string templatePath = "template.cpp";
    
    // 目标文件夹路径 problem_list_0x3f -> sliding_window -> solution
    // Windows 下虽然是用 \, 但 C++ fstream 通常也支持 /
    string targetDir = "problem_list_0x3f/sliding_window/solution/";

    // 2. 检查模板是否存在
    if (!fileExists(templatePath)) {
        cerr << "错误: 根目录下找不到 template.cpp 文件！" << endl;
        return 1;
    }

    // 3. 获取用户输入
    string problemNumber;
    cout << "请输入题号 (例如 643): ";
    cin >> problemNumber;

    if (problemNumber.empty()) {
        cout << "输入无效。" << endl;
        return 0;
    }

    // 4. 构建目标文件名: targetDir + "LC" + problemNumber + ".cpp"
    string fileName = "LC" + problemNumber + ".cpp";
    string targetFilePath = targetDir + fileName;

    // 5. 检查文件是否已存在 (防止误覆盖)
    if (fileExists(targetFilePath)) {
        char choice;
        cout << "警告: 文件 " << fileName << " 已经在目标目录中存在！" << endl;
        cout << "是否覆盖? (y/n): ";
        cin >> choice;
        if (choice != 'y' && choice != 'Y') {
            cout << "操作已取消。" << endl;
            return 0;
        }
    }

    // 6. 执行拷贝
    if (copyFile(templatePath, targetFilePath)) {
        cout << "------------------------------------------------" << endl;
        cout << "成功创建文件: " << fileName << endl;
        cout << "文件全路径: " << targetFilePath << endl;
        cout << "------------------------------------------------" << endl;
    } else {
        cerr << "错误: 文件复制失败。" << endl;
        cerr << "请检查目标文件夹 'problem_list_0x3f/sliding_window/solution/' 是否存在。" << endl;
        return 1;
    }

    return 0;
}

// g++ .\create_lc.cpp