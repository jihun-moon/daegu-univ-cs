// 시험에 나온다고 하는데 그냥 이해만 하면 됨. 외우지 말고.

#include <GL/glut.h>
#include <GL/gl.h>

GLfloat rectX = 0.0, rectY = 0.0;  // 사각형의 위치
GLboolean movePolygon = false;        // 사각형 움직임 기본값
GLfloat step = 0.05;                 // 사각형 움직임의 속도

// 초기화 함수
void init() {
    glClearColor(0.0, 0.0, 0.0, 1.0);  // 배경을 검은색으로 설정
}

// 사각형을 그리는 함수
void drawRect() {
    glBegin(GL_POLYGON);
    glVertex2f(-0.4 + rectX, -0.4 + rectY);
    glVertex2f(0.4 + rectX, -0.4 + rectY);
    glVertex2f(0.4 + rectX, 0.4 + rectY);
    glVertex2f(-0.4 + rectX, 0.4 + rectY);
    glEnd();
}

void mydisplay() {
    glClear(GL_COLOR_BUFFER_BIT);
    glColor3f(1.0, 1.0, 1.0);  // 사각형을 하얀색으로 설정
    drawRect();
    glFlush();
}

// 화면 크기 조정 콜백 함수
void reshape(int NewWidth, int NewHeight) {
    glViewport(0, 0, NewWidth, NewHeight);
    GLfloat WidthFactor = (GLfloat)NewWidth / (GLfloat)300;
    GLfloat HeightFactor = (GLfloat)NewHeight / (GLfloat)300;

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();

    glOrtho(-1.0 * WidthFactor, 1.0 * WidthFactor,
        -1.0 * HeightFactor, 1.0 * HeightFactor, -1.0, 1.0);

}


// 마우스 처리 함수 (왼쪽 버튼 클릭 시 종료)
void mouse(int button, int state, int x, int y) {
    if (button == GLUT_LEFT_BUTTON && state == GLUT_DOWN) {
        exit(0);
    }
}

// 키보드 입력 처리 함수
void keyboard(int key, int x, int y) {
    if (movePolygon) {  // movePolygon이 true일 때만 키보드 입력에 반응
        switch (key) {
        case GLUT_KEY_LEFT:
            rectX -= step;
            break;
        case GLUT_KEY_RIGHT:
            rectX += step;
            break;
        case GLUT_KEY_UP:
            rectY += step;
            break;
        case GLUT_KEY_DOWN:
            rectY -= step;
            break;
        }
        glutPostRedisplay();  // 화면 갱신
    }
}
// 메뉴 처리 함수
void mainMenu(int option) {
    if (option == 1) {
        movePolygon = false;  // 사각형 멈추기
    }
    else if (option == 2) {
        movePolygon = true;   // 키보드로 사각형 움직이기 활성화
    }
    else if (option == 3) {
        exit(0);              // 프로그램 종료
    }
}

// 메뉴 콜백
int main(int argc, char* argv[]) {
    glutInit(&argc, argv);
    glutCreateWindow("OpenGL Menu Example");

    // 디스플레이 및 리셰이프 콜백 설정
    glutDisplayFunc(mydisplay);
    glutReshapeFunc(reshape);

    // 키보드 콜백 설정 (특수 키)
    glutSpecialFunc(keyboard);

    // 마우스 콜백 설정
    glutMouseFunc(mouse);

    // 메뉴 생성
    glutCreateMenu(mainMenu);
    glutAddMenuEntry("Stop Polygon", 1);
    glutAddMenuEntry("Keyboard Control", 2);
    glutAddMenuEntry("Exit", 3);
    glutAttachMenu(GLUT_RIGHT_BUTTON);  // 오른쪽 버튼에 메뉴 연결

    glutMainLoop();  // 이벤트 루프 시작
    return 0;
}