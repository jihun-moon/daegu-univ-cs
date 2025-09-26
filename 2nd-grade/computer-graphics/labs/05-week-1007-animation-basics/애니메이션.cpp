#include <GL/glut.h>
#include <stdio.h>

GLfloat Delta = 0.0;       // 도형의 이동 거리
GLfloat Scale = 1.0;       // 도형의 크기 변화
GLboolean IsSphere = true; // 도형이 삼각형인지 네모인지 구분
GLboolean IsSmall = true;  // 도형 크기 (작은 도형, 큰 도형)
GLfloat Speed = 0.01;      // 기본 이동 속도

// 도형을 그리는 함수
void drawShape() {
    if (IsSphere && IsSmall) {
        glColor3f(1.0, 0.0, 0.0); // 빨간색
        glBegin(GL_TRIANGLES);
        glVertex3f(0.0, 0.2, 0.0);
        glVertex3f(-0.2, -0.2, 0.0);
        glVertex3f(0.2, -0.2, 0.0);
        glEnd();
    }
    else if (IsSphere && !IsSmall) {
        glColor3f(0.0, 1.0, 0.0); // 초록색
        glBegin(GL_TRIANGLES);
        glVertex3f(0.0, 0.5, 0.0);
        glVertex3f(-0.5, -0.5, 0.0);
        glVertex3f(0.5, -0.5, 0.0);
        glEnd();
    }
    else if (!IsSphere && IsSmall) {
        glColor3f(0.0, 0.5, 1.0); // 파란색
        glBegin(GL_POLYGON);
        glVertex3f(-0.2, -0.2, 0.0);
        glVertex3f(0.2, -0.2, 0.0);
        glVertex3f(0.2, 0.2, 0.0);
        glVertex3f(-0.2, 0.2, 0.0);
        glEnd();
    }
    else {
        glColor3f(1.0, 1.0, 0.0); // 노란색
        glBegin(GL_POLYGON);
        glVertex3f(-0.5, -0.5, 0.0);
        glVertex3f(0.5, -0.5, 0.0);
        glVertex3f(0.5, 0.5, 0.0);
        glVertex3f(-0.5, 0.5, 0.0);
        glEnd();
    }
}

// 디스플레이 함수: 도형을 그린다
void mydisplay() {
    glClear(GL_COLOR_BUFFER_BIT);

    glPushMatrix();
    glTranslatef(Delta, 0.0, 0.0);  // 이동
    glScalef(Scale, Scale, 1.0);    // 크기 조정
    drawShape();
    glPopMatrix();

    glFlush();
    glutSwapBuffers(); // 더블 버퍼링
}

// 창 크기 변경 시 도형이 찌그러지지 않도록 설정
void MyReshape(int NewWidth, int NewHeight) {
    glViewport(0, 0, NewWidth, NewHeight);

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();

    GLfloat aspectRatio = (GLfloat)NewWidth / (GLfloat)NewHeight;

    if (NewWidth >= NewHeight) {
        glOrtho(-1.0 * aspectRatio, 1.0 * aspectRatio, -1.0, 1.0, -1.0, 1.0);
    }
    else {
        glOrtho(-1.0, 1.0, -1.0 / aspectRatio, 1.0 / aspectRatio, -1.0, 1.0);
    }
}

// 키보드 입력에 따라 속도 조절 및 애니메이션 일시정지
void keyboard(unsigned char key, int x, int y) {
    if (key == 'f') {
        Speed += 0.01;  // 속도 증가
        if (Speed > 0.2) Speed = 0.2;
    }
    else if (key == 's') {
        Speed -= 0.01;  // 속도 감소
        if (Speed < 0.01) Speed = 0.01;
    }
}

// 애니메이션 타이머 함수
void MyTimer(int Value) {
    Delta += Speed;  // 도형 이동
    if (Delta > 1.0) Delta = -1.0;  // 화면 밖으로 나가면 다시 시작

    Scale += 0.01;  // 도형 크기 변화
    if (Scale > 2.0) Scale = 1.0;

    glutPostRedisplay();
    glutTimerFunc(40, MyTimer, 1);
}

// 메뉴 설정 함수
void MyMainMenu(int entryID) {
    if (entryID == 1) IsSphere = false;  // 네모
    else if (entryID == 2) IsSphere = true;   // 삼각형
    else if (entryID == 3) exit(0);  // 프로그램 종료
    glutPostRedisplay();
}

// 서브메뉴 설정 함수
void MySubMenu(int entryID) {
    if (entryID == 1) IsSmall = true;  // 작은 도형
    else if (entryID == 2) IsSmall = false;  // 큰 도형
    glutPostRedisplay();
}

int main(int argc, char* argv[]) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGB | GLUT_DOUBLE);
    glutInitWindowPosition(100, 100);  // 창 위치
    glutInitWindowSize(800, 400);      // 창 크기
    glutCreateWindow("OpenGL Drawing Example");

    GLint MySubMenuID = glutCreateMenu(MySubMenu);
    glutAddMenuEntry("Small One", 1);
    glutAddMenuEntry("Big One", 2);

    GLint MyMainMenuID = glutCreateMenu(MyMainMenu);
    glutAddMenuEntry("Draw Square", 1);
    glutAddMenuEntry("Draw Triangle", 2);
    glutAddSubMenu("Change Size", MySubMenuID);
    glutAddMenuEntry("Exit", 3);
    glutAttachMenu(GLUT_RIGHT_BUTTON);  // 오른쪽 클릭 메뉴

    glClearColor(1.0, 1.0, 1.0, 1.0);  // 흰색 배경
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);  // 좌표계

    glutDisplayFunc(mydisplay);
    glutReshapeFunc(MyReshape);
    glutKeyboardFunc(keyboard);
    glutTimerFunc(40, MyTimer, 1);

    glutMainLoop();  // 이벤트 처리 루프

    return 0;
}