#include "../common.h"
#include <algorithm>
#include <climits>
using namespace std;

/**
 * Techniques that is Must have / Always ready when comes to Arrays.
 * 
 * 1. Swap Element
 * 2. Reverse Array
 * 3. Find Min / Max
 * 
 */

// Swap
void swap_by_xnor(vector<int>& a, int i, int j) {
    a[i] = a[i] ^ a[j];
    a[j] = a[i] ^ a[j];
    a[i] = a[i] ^ a[j];
}

void swap_by_temp(vector<int>& a, int i, int j) {
    int temp = a[i];
    a[i] = a[j];
    a[j] = temp;
}

void swap_by_api(vector<int>& a, int i, int j) {
    std::swap(a[i], a[j]);
}

// reverse
void reverse_by_for_loop(vector<int>& a) {
    for (int i = 0; i < a.size() / 2; i++) {
        swap_by_xnor(a, i, a.size() - i - 1);
    }
}

void reverse_by_while_loop(vector<int>& a) {
    int i = 0;
    int j = a.size() - 1;
    while (i < j) {
        swap_by_xnor(a, i, j);
        i++;
        j--;
    }
}

void reverse_by_api(vector<int>& a) {
    std::reverse(a.begin(), a.end());
}

// Min: Init as INT_MAX!!!
int find_min_by_comparsion(vector<int>& a) {
    int min = INT_MAX;
    for (auto x : a) min = std::min(min, x);
    return min;
}

int find_min_by_api(vector<int>& a) {
    auto it = std::min_element(a.begin(), a.end());
    return *it;
}



int main() {
    vector<int> a = {1, 2, 3, 4, 5};
    reverse_by_for_loop(a);
    print(a);
    reverse_by_while_loop(a);
    print(a);
    swap_by_api(a, 0, 4);
    print(a);
    cout << find_min_by_api(a) << endl;;
}