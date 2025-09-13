
class CircularQueue :
    def __init__( self, capacity = 8 ) :
        self.capacity = capacity        # 용량(고정)
        self.array = [None] * capacity  # 요소들을 저장할 배열
        self.front = 0                  # 전단의 인덱스
        self.rear = 0                   # 후단의 인덱스

    def isEmpty( self ) :
       return self.front == self.rear

    def isFull( self ) :
       return self.front == (self.rear+1)%self.capacity

    def enqueue( self, item ):
        if not self.isFull():
            self.rear = (self.rear + 1) % self.capacity
            self.array[self.rear] = item

    def dequeue( self ):
        if not self.isEmpty():
            self.front = (self.front + 1) % self.capacity
            return self.array[self.front]

    def peek( self ):
        if not self.isEmpty():
            return self.array[(self.front + 1) % self.capacity]

    def size( self ) :
       return (self.rear - self.front + self.capacity) % self.capacity

    def __str__(self):
        if self.front < self.rear :
            return str(self.array[self.front+1:self.rear+1])
        else :
            return str(self.array[self.front+1:self.capacity] + \
                       self.array[0:self.rear+1] )


#======================================================================
if __name__ == "__main__":
    q = CircularQueue(8)
    q.enqueue('A')
    q.enqueue('B')
    q.enqueue('C')
    q.enqueue('D')
    q.enqueue('E')
    q.enqueue('F')
    print('A B C D E F 삽입: ', q)
    print('삭제 -->', q.dequeue())
    print('삭제 -->', q.dequeue())
    print('삭제 -->', q.dequeue())
    print('      3번의 삭제: ', q)
    q.enqueue('G')
    q.enqueue('H')
    q.enqueue('I')
    print('      G H I 삽입: ', q)
    
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


# 테스트 프로그램(1)
if __name__ == "__main__":
    g = TNode('G', None, None)
    h = TNode('H', None, None)
    d = TNode('D', None, None)
    e = TNode('E', g, h)
    f = TNode('F', None, None)
    b = TNode('B', d, None)
    c = TNode('C', e, f)
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

# 테스트 프로그램(2)
if __name__ == "__main__":
    a = TNode('A', None, None)
    b = TNode('B', None, None)
    node_slash = TNode('/', a, b)
    c = TNode('C', None, None)
    node_star_1 = TNode('*', node_slash, c)
    d = TNode('D', None, None)
    node_star_2 = TNode('*', node_star_1, d)
    e = TNode('E', None, None)
    root = TNode('+', node_star_2, e)

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
