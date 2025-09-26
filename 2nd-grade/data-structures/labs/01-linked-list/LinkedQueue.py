# LinkedQueue.py
class Node:
    def __init__ (self, elem, next=None):
        self.data = elem 
        self.link = next


# 코드 6.6: 원형으로 연결된 큐 클래스
class LinkedQueue:
    def __init__( self ):
        self.tail = None

    def isEmpty( self ): return self.tail == None
    def isFull( self ): return False

    def enqueue( self, item ):
        node = Node(item, None)
        if self.isEmpty() :
           self.tail = node
           node.link = node
        else :
            node.link = self.tail.link
            self.tail.link = node
            self.tail = node

    def dequeue( self ):
        if not self.isEmpty():
            data = self.tail.link.data
            if self.tail.link == self.tail : self.tail = None
            else:
                self.tail.link = self.tail.link.link
            return data

    def peek( self ):
        if not self.isEmpty():
            return self.tail.link.data



    # 코드 6.7: 원형으로 연결된 큐의 요소의 수 계산
    def size( self ):
        if self.isEmpty() : return 0
        else :
            count = 1
            node = self.tail.link
            while not node == self.tail :
                node = node.link
                count += 1
            return count

    # 코드 6.8: 문자열 변환을 위한 str 연산자 중복
    def __str__( self ):
        arr = []
        if not self.isEmpty() :
            node = self.tail.link
            while not node == self.tail :
                arr.append(node.data)
                node = node.link
            arr.append(node.data)
        return str(arr)


#======================================================================
if __name__ == "__main__":
    q = LinkedQueue()
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
