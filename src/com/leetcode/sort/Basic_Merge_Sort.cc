#include "../common.h"
#include <vector>
#include <iostream>
#include <climits>
using namespace std;

// Segment [left, mid] and [mid + 1, right] are guaranteed to be in order.
// Need extra space to merge and then copy back to the segment [left, right].
void merge(vector<int>& a, int left, int mid, int right) {
  vector<int> temp(right - left + 1);
  int i = left;
  int j = mid + 1;
  int c = 0;
  while (i <= mid || j <= right) {
    int x = (i <= mid) ? a[i] : INT_MAX;
    int y = (j <= right) ? a[j] : INT_MAX;
    if (x <= y) {
      temp[c++] = x;
      i++;
    }
    else {
      temp[c++] = y;
      j++;
    }
  }
  std::copy(temp.begin(), temp.end(), a.begin() + left);
}

// ptr are inclusive
void sort(vector<int>& a, int left, int right) {
  if (left >= right) return;    // segment has only one element or no element.
  int mid = left + (right - left) / 2;
  sort(a, left, mid);
  sort(a, mid + 1, right);
  merge(a, left, mid, right);
}

void sort(vector<int>& a) {
  sort(a, 0, a.size() - 1);
}

int main() {
  vector<int> a = {4, 5, 1, 3, 2};
  vector<int> b(10, 0);
  copy(a.begin() + 1, a.end(), b.begin() + 5);
  print(b);
  print(a);
  sort(a);
  print(a);
}

