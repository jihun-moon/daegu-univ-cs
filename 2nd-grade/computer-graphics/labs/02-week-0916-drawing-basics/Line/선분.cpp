#include <GL/glut.h> //GLUT 라이브러리 함수를 사용하기 위한 것

void mydisplay() {
    glClear(GL_COLOR_BUFFER_BIT); // 화면을 지우는 명령

    // 선(Line) 그리기 시작
    glBegin(GL_LINES); // GL_LINES는 선을 그릴 때 사용

    // 첫 번째 선
    glVertex3f(-0.4f, 0.0f, 0.0f); // 왼쪽 시작점
    glVertex3f(0.0f, -0.5f, 0.0f);  // 오른쪽 끝점

    // 두 번째 선
    glVertex3f(0.0f, 0.0f, 0.0f);  // 왼쪽 시작점
    glVertex3f(0.4f, -0.5f, 0.0f);  // 오른쪽 끝점

    // 세 번째 선
    glVertex3f(0.4f, 0.0f, 0.0f);  // 왼쪽 시작점
    glVertex3f(0.8f, -0.5f, 0.0f);   // 오른쪽 끝점

    glEnd();

    glFlush(); //현재까지 쌓인 명령어 모두를 프로세서에 전달하도록 강제명령
}

int main(int argc, char* argv[]) //메인함수는 모두 GLUT로 구성 
{
    glutInit(&argc, argv);
    glutCreateWindow("Hello, OpenGL!");//GLUT에게 새로운 윈도우 생성 명령
    //"Hello, OpenGL!" 파라미터가 윈도우 상단 타이틀 바에 표시

    glutDisplayFunc(mydisplay);//"mydisplay" 함수를
    //디스플레이 이벤트(DIsplayFunc)에 대한 콜백 함수로 등록

    glutMainLoop();//메인함수는 항상 glutMainLoop()로 끝
    //이벤트 루프를 돌려야 하기 때문

    return 0;//메인 함수의 리턴 타입을 정수로 선언
}