
# 코드 8.1: 이진트리를 위한 노드 클래스
class TNode:
    def __init__ (self, elem, left, right):
        self.data = elem 
        self.left = left
        self.right = right

    def isLeaf(self):
        return self.left is None and self.right is None

# 코드 8.2: 이진트리의 전위순회
def preorder(n) :
    if n is not None :
        print(n.data, end=' ')
        preorder(n.left)
        preorder(n.right)

# 코드 8.3: 이진트리의 중위순회
def inorder(n) :
    if n is not None :
        inorder(n.left)
        print(n.data, end=' ')
        inorder(n.right)

# 코드 8.4: 이진트리의 후위순회
def postorder(n) :
    if n is not None :
        postorder(n.left)
        postorder(n.right)
        print(n.data, end=' ')

# 코드 8.5: 이진트리의 레벨순회
from CircularQueue import CircularQueue

def levelorder(root) :
    queue = CircularQueue()
    queue.enqueue(root)
    while not queue.isEmpty() :
        n = queue.dequeue()
        if n is not None :
            print(n.data, end=' ')
            queue.enqueue(n.left)
            queue.enqueue(n.right)

# 코드 8.6: 이진트리의 노드 수 계산
def count_node(n) :
    if n is None : 
        return 0
    else : 
        return 1 + count_node(n.left) + count_node(n.right)

# 코드 8.7: 이진트리의 단말노드 수 계산      
def count_leaf(n) :
    if n is None : return 0
    elif n.isLeaf() : return 1
    else : return count_leaf(n.left) + count_leaf(n.right)


# 코드 8.8: 이진트리의 트리의 높이 계산
def calc_height(n) :
    if n is None : return 0
    hLeft = calc_height(n.left)
    hRight = calc_height(n.right)
    if (hLeft > hRight) : return hLeft + 1
    else: return hRight + 1

# 기타 연산들
def evaluate(n) :
    if n is None :
       return 0
    elif n.left is None and n.right is None :
       return n.data
    else :
        op1 = evaluate(n.left)
        op2 = evaluate(n.right)
        if n.data == '+' : return op1 + op2
        elif n.data == '-' : return op1 - op2
        elif n.data == '*' : return op1 * op2
        elif n.data == '/' : return op1 / op2

def calc_size(n) :
    if n is None : 
        return 0
    else : 
        return n.data + calc_size(n.left) + calc_size(n.right)

#=========================================================
#   - 이 파일이 직접 실행될 때에는 다음 문장들을 실행함.
#   - 다른 파일에서 모듈로 불려지는 경우는 실행되지 않음.
#=========================================================
# 테스트 프로그램
if __name__ == "__main__":
    print("\n======= 이진트리 테스트 ===================================") 
    d = TNode('D', None, None)
    e = TNode('E', None, None)
    b = TNode('B', d, e)
    f = TNode('F', None, None)
    c = TNode('C', f, None)
    root = TNode('A', b, c)

    print('\n   In-Order : ', end='')
    inorder(root)
    print('\n  Pre-Order : ', end='')
    preorder(root)
    print('\n Post-Order : ', end='')
    postorder(root)
    print('\nLevel-Order : ', end='')
    levelorder(root)
    print()

    print(" 노드의 개수 = %d개" % count_node(root))
    print(" 단말의 개수 = %d개" % count_leaf(root))
    print(" 트리의 높이 = %d" % calc_height(root))

def testExprTree() :
    n1 = TNode(3, None, None)
    n2 = TNode(2, None, None)
    n3 = TNode('*', n1, n2)
    n4 = TNode(5, None, None)
    n5 = TNode(6, None, None)
    n6 = TNode('-', n4, n5)
    root = TNode('+', n3, n6)

    tree = BinaryTree(root)
    tree.printInOrder   ('Evaluate Expression : ')
    print(' ==> ' + str(evaluate(root)))

def testFolderSize() :
    m4 = TNode(200, None, None)
    m5 = TNode(500, None, None)
    m3 = TNode(100, m4, m5)
    m2 = TNode(50, None, None)
    root = TNode(0, m2, m3)
    tree = BinaryTree(root)
    print("Calculate Folder Size = %d KB" % calc_size(root))

#testBinaryTree()
#testExprTree()
#testFolderSize()
