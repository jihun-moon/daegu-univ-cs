#include <GL/glut.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <stdlib.h>

GLfloat Angle = 0.0f; // 공전 각도
GLfloat red = 0.0f, green = 0.0f, blue = 1.0f; // 초기 색상 (파란색)

void drawOrbitingMoon() {
    // 공전하는 원 (현재 색상)
    glColor3f(red, green, blue);
    glutSolidSphere(0.1, 50, 50); // 반지름 0.1의 구
}

void mydisplay() {
    glClear(GL_COLOR_BUFFER_BIT);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    // 복합 변환: 먼저 회전한 후 이동
    glPushMatrix();

    glRotatef(Angle, 0.0, 0.0, 1.0); // Z축을 기준으로 회전
    glTranslatef(0.8, 0.0, 0.0); // 회전 후 일정 거리만큼 이동
    drawOrbitingMoon();

    glPopMatrix();

    glutSwapBuffers(); // 더블 버퍼링
}

// 타이머 함수
void MyTimer(int value) {
    Angle += 1.0f; // 원이 회전하는 속도 (각도 증가)

    // 360도 회전할 때마다 색상 변경
    if (Angle >= 360.0f) {
        Angle = 0.0f; // 각도 초기화

        // 무작위 색상 생성
        red = (float)rand() / RAND_MAX;
        green = (float)rand() / RAND_MAX;
        blue = (float)rand() / RAND_MAX;
    }

    glutPostRedisplay(); // 화면 다시 그리기
    glutTimerFunc(16, MyTimer, 0); // 16ms마다 갱신 (약 60fps)
}

int main(int argc, char** argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE); // 더블 버퍼링 활성화
    glutInitWindowSize(400, 400);
    glutCreateWindow("파란색 원구를 360도 회전시키기");

    glClearColor(1.0, 1.0, 1.0, 1.0); // 배경 흰색
    glutDisplayFunc(mydisplay);
    glutTimerFunc(16, MyTimer, 0); // 타이머 함수 시작

    glutMainLoop(); // 이벤트 루프 시작

    return 0;
}
