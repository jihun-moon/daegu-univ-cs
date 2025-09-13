# 스택의 구현(클래스 버전)

class ArrayStack:
    def __init__(self, capacity):
        self.capacity = capacity
        self.array = [None]*self.capacity
        self.top = -1

    
    # 스택의 연산들을 멤버 함수로 구현
    def isEmpty(self):
        return self.top == -1
    
    def isFull(self):
        return self.top == self.capacity-1
    
    def push(self, e):
        if not self.isFull():
            self.top += 1
            self.array[self.top] = e
        else: pass
    
    def pop(self):
        if not self.isEmpty():
            self.top -= 1
            return self.array[self.top+1]
        else: pass
    
    def peek(self):
        if not self.isEmpty():
            return self.array[self.top]
        else: pass

    def __str__(self):
        return str(self.array[0:self.top+1])
    # push 5회:  [1, 2, 3, 4, 5, None, None, None, None, None]
    # 해결 하기 위해서 str 연산자 중복과 슬라이싱 기능을 이용해 필요한 부분만 출력하게 함.

    def size(self):
        return self.top + 1

# 괄호 검사 알고리즘

def checkBrackets(statement):
    stack = ArrayStack(100)
    for ch in statement:
        if ch == '(':
            stack.push(ch)
        
        elif ch == ')':
            if stack.isEmpty():
                return False
            
            else:
                left = stack.pop()
                if (ch == ")" and left != "("):
                    return False
                
    return stack.isEmpty()


# 후위 표기 수식 계산 알고리즘

def evalPostfix(tokens):
    s = ArrayStack(100)
    for token in tokens:
        if token.replace('.', '').isdigit():
            s.push(float(token))
        elif token.startswith('-') and token[1:].replace('.', '').isdigit():
            s.push(float(token))
        elif token in "+-*/":
            if s.size() < 2:
                print("오류발생: 올바른 연산을 위한 피연산자가 부족합니다.")
                return None  # 피연산자가 부족한 경우 None을 반환하여 나머지 연산을 수행하지 않음
            operand2 = s.pop()
            operand1 = s.pop()
            if token == '+':
                s.push(operand1 + operand2)
            elif token == '-':
                s.push(operand1 - operand2)
            elif token == '*':
                s.push(operand1 * operand2)
            elif token == '/':
                if operand2 == 0:
                    print("오류발생: 0으로 나눌 수 없습니다.")
                else:
                    s.push(operand1 / operand2)
    if s.size() != 1:
        print("오류발생: 올바른 연산을 위한 피연산자가 부족합니다.")
        return None
    return s.pop()


# 중위 --> 후위 변환 알고리즘

def Infix2Postfix(expr):
    s = ArrayStack(100)
    output = [] # 후위 표기식

    for term in expr:  # expr = ['8', '/', '2', '-', '3', '+', '(', '3', '*', '2', ')']
        if term in '(':
            s.push('(') # 스택에 '('를 넣어라. (가장 우선순위가 낮음.)

        elif term in ')': # 리스트 안에 ')' 있으면
            while not s.isEmpty(): # 스택이 비어있지 않을 때까지 계속 반복.
                op = s.pop() # 맨 위에 있는 스택에서 꺼내온다. 꺼내온 값은 삭제 됨.
                if op == '(': # 스택에서 꺼낸게 '('이면
                    break; # 반복문을 종료.
                else:
                    output.append(op) # 스택에서 꺼낸 값을 output 리스트에 추가해라.

        elif term in "+-*/": # 리스트 안에 "+-*/" 있으면
            while not s.isEmpty(): # 스택이 비어있지 않을 때까지 계속 반복.
                op = s.peek() # 맨 위에 있는 스택에 있는 값을 유지하되 불러온다.
                if(precedence(term) <= precedence(op)): # 리스트에 있는 연산자가 스택의 맨 위 연산자보다 작거나 같으면.
                    output.append(op) # 스택에서 불러온 값을 output 리스트에 추가해라.
                    s.pop() # 그리고 스택에 있는거 꺼내고 삭제.
                else: break # 반복문을 종료.
            s.push(term) # ★ 리스트에 있는 연산자를 스택에 넣습니다. ★

        else: # 숫자일 경우
            output.append(term) # 리스트에서 불러온 값을 output 리스트에 추가해라.

    while not s.isEmpty():  # 스택이 비어있지 않을 때까지 반복합니다.
        output.append(s.pop()) # 스택의 나머지를 꺼내어 output 리스트에 추가합니다.

    return output # 후위 표기식으로 변환된 결과를 반환합니다.
    
def precedence(op): # 연산자 우선순위를 지정.
    if op == '(' or op == ')' : return 0 # 낮은 우선 순위
    elif op == '+' or op == '-' : return 1 # 높은 우선 순위
    elif op == '*' or op == '/' : return 2 # 가장 높은 우선 순위
    else: return -1 # 나머지는 -1을 반환

def has_operator_inside_parentheses(expr): # 제일 힘들었던 부분 제일 밑에 글 주석 처리 보면 각각 조건에 따라서 되나 안되나.. 확인
    operators = {'+', '-', '*', '/'}

    for i in range(len(expr) - 1):
        if expr[i] == '(':
            if i == len(expr) - 1 or expr[i + 1] == ')':  # 괄호 다음에 아무런 내용이 없는 경우
                return True
            elif i > 0 and expr[i - 1] not in operators and expr[i - 1] != '(':  # 괄호 앞에 연산자가 없는 경우
                return True
            elif i < len(expr) - 2 and expr[i + 2] not in operators and expr[i + 2] != ')' and expr[i + 1] != '(':  # 괄호 뒤에 연산자가 없는 경우
                return True
        elif expr[i] == ')':
            if i == 0 or expr[i - 1] == '(':  # 괄호 앞에 아무런 내용이 없는 경우
                return True
            elif i < len(expr) - 1 and expr[i + 1] not in operators and expr[i + 1] != ')':  # 괄호 뒤에 연산자가 없는 경우
                return True
    return False

#################################################################################################

# 사칙 연산 계산기 구현 (소수점은 안되고 정수만 입력 받는 계산기)

# * 조건
# 1. 사칙연산식 입력 가능해야함
# 2. 사칙연산식에 대해 스택을 이용한 문법 체크 가능해야함 (예: (1+2), 1*2, ((1+2)), (1+2)) 등 식의 정오 체크 가능해야 함)
# 3. 사칙연산의 순서 체크 가능해야 함
# 4. 사칙연산식의 정오에 따라 문법적 오류에 대한 코멘트 출력이 가능해야 하고 문법적 오류가 없을 시 계산에 대한 답을 출력할 것
# 5. 스택 및 문법 체크 알고리즘 체크 직접 구현할 것

# 입력받은 수식을 리스트에 넣기 위한 준비
Calculate_list = []

while True:
    Calculate = input("\n사칙연산을 입력하세요.\n(숫자, 연산자, 괄호를 공백으로 구분하여 입력하세요)\n--> ").split()
    valid_operators = ['+', '-', '*', '/', '(', ')']

    # 입력된 값이 숫자와 소수점, 사칙 연산자로만 이루어져 있는지 확인
    if all((item.replace('.', '').replace('-', '').isdigit() or item in valid_operators) for item in Calculate):
        # Calculate에는 올바른 입력 값만 들어가게 됩니다.
        # 연속된 숫자가 있는지 확인하고 처리
        for i in range(len(Calculate) - 1):
            if Calculate[i].replace('.', '').isdigit() and Calculate[i+1].replace('.', '').isdigit():
                print("오류 발생: 올바른 입력이 아닙니다. 연속된 숫자를 사용할 수 없습니다.")
                break
        else:
            # 음수 값 및 빼기 연산자 확인 및 처리
            for i, item in enumerate(Calculate):
                if item == '-' and (i == 0 or Calculate[i-1] in ['(', '+', '-', '*', '/']):
                    if i < len(Calculate) - 1 and Calculate[i+1].replace('.', '').isdigit():
                        Calculate[i] = '-' + Calculate[i+1]  # 음수 부호로 처리할 다음 숫자에 음수 부호를 붙임
                        Calculate.pop(i+1)  # 음수 부호가 붙은 현재 항목 제거
                    else:
                        # 부호 "-"가 단독으로 나타나거나 다음에 숫자가 아닌 경우, 일반적인 뺄셈 연산으로 처리
                        pass

            Calculate_list = Calculate

            # 유효한 문자 및 연산자가 사용되었는지 확인
            if not Calculate_list:
                print("오류 발생: 수식이 비어 있습니다. 다시 입력해주세요.")
            elif not checkBrackets(Calculate_list):
                print("오류 발생: 괄호가 올바르게 닫혀있지 않거나 괄호의 쌍이 일치하지 않습니다. 올바른 수식을 입력하세요.")
            elif has_operator_inside_parentheses(Calculate_list):
                print("오류 발생: 괄호 외부에 연산자가 생략되었습니다. 다시 입력해주세요.")
            else:
                result = Infix2Postfix(Calculate_list)
                if evalPostfix(result) is not None:
                    print("\n수식에 오류가 없습니다. 계산을 진행합니다.\n")
                   #print("리스트 목록 -->", Calculate_list) # 잘 들어갔나 테스트
                   #print("후위 변환식 --> ", result)  # 잘 들어갔나 테스트
                    print("사칙연산 결과 --> ", evalPostfix(result))
                    break

            # 올바른 입력이 아닌 경우 다시 입력 요청
            continue
    else:
        print("오류발생: 올바른 입력이 아닙니다. 숫자와 사칙 연산자만 입력하세요.\n또는 공백이 잘 구분이 되어있는지 확인이 필요합니다.")




"""
print(has_operator_inside_parentheses(['-3', '+','(', '-2',')']))  # False
print(has_operator_inside_parentheses(['-3', '+', '-2']))  # False
print(has_operator_inside_parentheses(['(', '-3', '+', '-2', ')', '8']))  # True
print(has_operator_inside_parentheses(['(', '-8', '(', '-3', '+', '-2', ')']))  # True
print(has_operator_inside_parentheses(['(', '-8', '*', '-8', ')']))  # False
print(has_operator_inside_parentheses(['(', '(', '-8', '*', '-3', ')', '+', '-2']))  # False
print(has_operator_inside_parentheses(['(', '-8', '*', '(', '-3', '+', '-2', ')',')']))  # False
print(has_operator_inside_parentheses(['(', '(', '-6', '*', '-6', ')', ')']))  # False
print(has_operator_inside_parentheses(['(', '(', '-6', '*', '-6', ')', ')', '+', '-8']))  # False
print(has_operator_inside_parentheses(['(', '(', '-6', '*', '-6', ')', '+', '-8', ')']))  # False  
print(has_operator_inside_parentheses(['(', '-6', '*','(' '-6', '+', '-8',')',')']))  # False
"""