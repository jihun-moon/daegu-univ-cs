#include <GL/glut.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <stdlib.h>

GLfloat EarthOrbitAngle = 0.0f; // 지구 공전 각도
GLfloat MoonOrbitAngle = 0.0f; // 달 공전 각도
GLfloat MercuryOrbitAngle = 0.0f; // 수성 공전 각도
GLfloat VenusOrbitAngle = 0.0f; // 금성 공전 각도
GLfloat MarsOrbitAngle = 0.0f; // 화성 공전 각도
GLfloat JupiterOrbitAngle = 0.0f; // 목성 공전 각도
GLfloat SaturnOrbitAngle = 0.0f; // 토성 공전 각도
GLfloat UranusOrbitAngle = 0.0f; // 천왕성 공전 각도
GLfloat NeptuneOrbitAngle = 0.0f; // 해왕성 공전 각도

void Draw_Object(float r, float g, float b, float size) {
    glColor3f(r, g, b);
    glutWireSphere(size, 20, 18); // 주어진 색상과 크기로 객체를 그립니다.
}

void display() {
    glClear(GL_COLOR_BUFFER_BIT);

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    // 태양 그리기
    glPushMatrix();
    Draw_Object(1.0, 1.0, 0.0, 4.0); // 노란색 태양, 크기를 4로 설정
    glPopMatrix();

    // 지구 공전 및 그리기
    glPushMatrix();
    glRotatef(EarthOrbitAngle, 0.0, 0.0, 1.0); // 태양 중심으로 지구 공전
    glTranslatef(24.0, 0.0, 0.0); // 태양에서 지구까지의 거리
    Draw_Object(0.0, 0.0, 2.0, 2.0); // 파란색 지구

    // 달 공전 및 그리기
    glPushMatrix();
    glRotatef(MoonOrbitAngle, 0.0, 0.0, 1.0); // 지구 중심으로 달 공전
    glTranslatef(4.0, 0.0, 0.0); // 지구에서 달까지의 거리
    Draw_Object(1.0, 1.0, 1.0, 0.6); // 회색 달
    glPopMatrix();

    glPopMatrix(); // 지구와 달 변환 복원

    // 수성
    glPushMatrix();
    glRotatef(MercuryOrbitAngle, 0.0, 0.0, 1.0); // 공전
    glTranslatef(12.0, 0.0, 0.0); // 태양에서의 거리
    Draw_Object(1.0, 1.0, 1.0, 1.0);
    glPopMatrix();

    // 금성
    glPushMatrix();
    glRotatef(VenusOrbitAngle, 0.0, 0.0, 1.0);
    glTranslatef(17.0, 0.0, 0.0);
    Draw_Object(2.0, 1.0, 0.0, 1.6);
    glPopMatrix();

    // 화성
    glPushMatrix();
    glRotatef(MarsOrbitAngle, 0.0, 0.0, 1.0);
    glTranslatef(31.0, 0.0, 0.0);
    Draw_Object(2.0, 0.0, 0.0, 1.2);
    glPopMatrix();

    // 목성
    glPushMatrix();
    glRotatef(JupiterOrbitAngle, 0.0, 0.0, 1.0);
    glTranslatef(37.0, 0.0, 0.0);
    Draw_Object(1.8, 0.12, 0.6, 4.0);
    glPopMatrix();

    // 토성
    glPushMatrix();
    glRotatef(SaturnOrbitAngle, 0.0, 0.0, 1.0);
    glTranslatef(47.0, 0.0, 0.0);
    Draw_Object(1.8, 1.6, 1.0, 3.6);
    glPopMatrix();

    // 천왕성
    glPushMatrix();
    glRotatef(UranusOrbitAngle, 0.0, 0.0, 1.0);
    glTranslatef(52.0, 0.0, 0.0);
    Draw_Object(1.0, 1.6, 2.0, 2.8);
    glPopMatrix();

    // 해왕성
    glPushMatrix();
    glRotatef(NeptuneOrbitAngle, 0.0, 0.0, 1.0);
    glTranslatef(57.0, 0.0, 0.0);
    Draw_Object(0.6, 0.6, 1.6, 2.6);
    glPopMatrix();

    glutSwapBuffers();
}

// 타이머 함수
void MyTimer(int value) {
    MercuryOrbitAngle += 4.0f; // 수성 공전 속도
    VenusOrbitAngle += 1.6f; // 금성 공전 속도
    EarthOrbitAngle += 1.0f; // 지구 공전 속도
    MoonOrbitAngle += 5.0f; // 달 공전 속도
    MarsOrbitAngle += 0.8f; // 화성 공전 속도
    JupiterOrbitAngle += 0.4f; // 목성 공전 속도
    SaturnOrbitAngle += 0.3f; // 토성 공전 속도
    UranusOrbitAngle += 0.2f; // 천왕성 공전 속도
    NeptuneOrbitAngle += 0.1f; // 해왕성 공전 속도

    // 각도 정규화
    if (MercuryOrbitAngle >= 360.0f) MercuryOrbitAngle -= 360.0f;
    if (VenusOrbitAngle >= 360.0f) VenusOrbitAngle -= 360.0f;
    if (EarthOrbitAngle >= 360.0f) EarthOrbitAngle -= 360.0f;
    if (MoonOrbitAngle >= 360.0f) MoonOrbitAngle -= 360.0f;
    if (MarsOrbitAngle >= 360.0f) MarsOrbitAngle -= 360.0f;
    if (JupiterOrbitAngle >= 360.0f) JupiterOrbitAngle -= 360.0f;
    if (SaturnOrbitAngle >= 360.0f) SaturnOrbitAngle -= 360.0f;
    if (UranusOrbitAngle >= 360.0f) UranusOrbitAngle -= 360.0f;
    if (NeptuneOrbitAngle >= 360.0f) NeptuneOrbitAngle -= 360.0f;

    glutPostRedisplay(); // 화면 다시 그리기
    glutTimerFunc(16, MyTimer, 0); // 16ms마다 갱신 (약 60fps)
}

void reshape(int w, int h) {
    glViewport(0, 0, w, h); // 뷰포트를 윈도우 크기에 맞춤
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();

    if (w <= h) {
        glOrtho(-60.0, 60.0, -60.0 * (GLfloat)h / (GLfloat)w, 60.0 * (GLfloat)h / (GLfloat)w, -60.0, 60.0);
    }
    else {
        glOrtho(-60.0 * (GLfloat)w / (GLfloat)h, 60.0 * (GLfloat)w / (GLfloat)h, -60.0, 60.0, -60.0, 60.0);
    }

    glMatrixMode(GL_MODELVIEW);
}

void init() {
    glClearColor(0.0, 0.0, 0.0, 1.0); // 배경색을 검은색으로 설정 (우주 배경)
}

int main(int argc, char** argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
    glutInitWindowSize(800, 800);
    glutCreateWindow("태양계");

    init();
    glutDisplayFunc(display);
    glutReshapeFunc(reshape); // 창 크기 변경 콜백 등록
    glutTimerFunc(16, MyTimer, 0); // 타이머 함수 시작

    glutMainLoop();

    return 0;
}
