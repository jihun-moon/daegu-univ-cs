def printStep(arr, val):
    print(" Step %2d = " % val, end="")
    print(arr)

# 선택정렬 알고리즘
def selection_sort(A):
    n = len(A)
    for last in range(n - 1, 0, -1):
        k = theLargest(A, last)
        A[k], A[last] = A[last], A[k]
        printStep(A, n - last)

def theLargest(A, last):
    largest = 0
    for i in range(1, last + 1):
        if A[i] > A[largest]:
            largest = i
    return largest

if __name__ == "__main__":
    org = [8, 31, 48, 73, 3, 65, 20, 29]
    data = list(org)
    print("Original: ", org)
    selection_sort(data)
    print("Selection: ", data)
