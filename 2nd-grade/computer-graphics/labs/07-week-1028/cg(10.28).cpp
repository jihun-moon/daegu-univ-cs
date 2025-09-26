#include <GL/glut.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <stdlib.h>

GLfloat EarthOrbitAngle = 0.0f; // ���� ���� ����
GLfloat MoonOrbitAngle = 0.0f; // �� ���� ����
GLfloat MercuryOrbitAngle = 0.0f; // ���� ���� ����
GLfloat VenusOrbitAngle = 0.0f; // �ݼ� ���� ����
GLfloat MarsOrbitAngle = 0.0f; // ȭ�� ���� ����
GLfloat JupiterOrbitAngle = 0.0f; // �� ���� ����
GLfloat SaturnOrbitAngle = 0.0f; // �伺 ���� ����
GLfloat UranusOrbitAngle = 0.0f; // õ�ռ� ���� ����
GLfloat NeptuneOrbitAngle = 0.0f; // �ؿռ� ���� ����

void Draw_Object(float r, float g, float b, float size) {
    glColor3f(r, g, b);
    glutWireSphere(size, 20, 18); // �־��� ����� ũ��� ��ü�� �׸��ϴ�.
}

void display() {
    glClear(GL_COLOR_BUFFER_BIT);

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    // �¾� �׸���
    glPushMatrix();
    Draw_Object(1.0, 1.0, 0.0, 4.0); // ����� �¾�, ũ�⸦ 4�� ����
    glPopMatrix();

    // ���� ���� �� �׸���
    glPushMatrix();
    glRotatef(EarthOrbitAngle, 0.0, 0.0, 1.0); // �¾� �߽����� ���� ����
    glTranslatef(24.0, 0.0, 0.0); // �¾翡�� ���������� �Ÿ�
    Draw_Object(0.0, 0.0, 2.0, 2.0); // �Ķ��� ����

    // �� ���� �� �׸���
    glPushMatrix();
    glRotatef(MoonOrbitAngle, 0.0, 0.0, 1.0); // ���� �߽����� �� ����
    glTranslatef(4.0, 0.0, 0.0); // �������� �ޱ����� �Ÿ�
    Draw_Object(1.0, 1.0, 1.0, 0.6); // ȸ�� ��
    glPopMatrix();

    glPopMatrix(); // ������ �� ��ȯ ����

    // ����
    glPushMatrix();
    glRotatef(MercuryOrbitAngle, 0.0, 0.0, 1.0); // ����
    glTranslatef(12.0, 0.0, 0.0); // �¾翡���� �Ÿ�
    Draw_Object(1.0, 1.0, 1.0, 1.0);
    glPopMatrix();

    // �ݼ�
    glPushMatrix();
    glRotatef(VenusOrbitAngle, 0.0, 0.0, 1.0);
    glTranslatef(17.0, 0.0, 0.0);
    Draw_Object(2.0, 1.0, 0.0, 1.6);
    glPopMatrix();

    // ȭ��
    glPushMatrix();
    glRotatef(MarsOrbitAngle, 0.0, 0.0, 1.0);
    glTranslatef(31.0, 0.0, 0.0);
    Draw_Object(2.0, 0.0, 0.0, 1.2);
    glPopMatrix();

    // ��
    glPushMatrix();
    glRotatef(JupiterOrbitAngle, 0.0, 0.0, 1.0);
    glTranslatef(37.0, 0.0, 0.0);
    Draw_Object(1.8, 0.12, 0.6, 4.0);
    glPopMatrix();

    // �伺
    glPushMatrix();
    glRotatef(SaturnOrbitAngle, 0.0, 0.0, 1.0);
    glTranslatef(47.0, 0.0, 0.0);
    Draw_Object(1.8, 1.6, 1.0, 3.6);
    glPopMatrix();

    // õ�ռ�
    glPushMatrix();
    glRotatef(UranusOrbitAngle, 0.0, 0.0, 1.0);
    glTranslatef(52.0, 0.0, 0.0);
    Draw_Object(1.0, 1.6, 2.0, 2.8);
    glPopMatrix();

    // �ؿռ�
    glPushMatrix();
    glRotatef(NeptuneOrbitAngle, 0.0, 0.0, 1.0);
    glTranslatef(57.0, 0.0, 0.0);
    Draw_Object(0.6, 0.6, 1.6, 2.6);
    glPopMatrix();

    glutSwapBuffers();
}

// Ÿ�̸� �Լ�
void MyTimer(int value) {
    MercuryOrbitAngle += 4.0f; // ���� ���� �ӵ�
    VenusOrbitAngle += 1.6f; // �ݼ� ���� �ӵ�
    EarthOrbitAngle += 1.0f; // ���� ���� �ӵ�
    MoonOrbitAngle += 5.0f; // �� ���� �ӵ�
    MarsOrbitAngle += 0.8f; // ȭ�� ���� �ӵ�
    JupiterOrbitAngle += 0.4f; // �� ���� �ӵ�
    SaturnOrbitAngle += 0.3f; // �伺 ���� �ӵ�
    UranusOrbitAngle += 0.2f; // õ�ռ� ���� �ӵ�
    NeptuneOrbitAngle += 0.1f; // �ؿռ� ���� �ӵ�

    // ���� ����ȭ
    if (MercuryOrbitAngle >= 360.0f) MercuryOrbitAngle -= 360.0f;
    if (VenusOrbitAngle >= 360.0f) VenusOrbitAngle -= 360.0f;
    if (EarthOrbitAngle >= 360.0f) EarthOrbitAngle -= 360.0f;
    if (MoonOrbitAngle >= 360.0f) MoonOrbitAngle -= 360.0f;
    if (MarsOrbitAngle >= 360.0f) MarsOrbitAngle -= 360.0f;
    if (JupiterOrbitAngle >= 360.0f) JupiterOrbitAngle -= 360.0f;
    if (SaturnOrbitAngle >= 360.0f) SaturnOrbitAngle -= 360.0f;
    if (UranusOrbitAngle >= 360.0f) UranusOrbitAngle -= 360.0f;
    if (NeptuneOrbitAngle >= 360.0f) NeptuneOrbitAngle -= 360.0f;

    glutPostRedisplay(); // ȭ�� �ٽ� �׸���
    glutTimerFunc(16, MyTimer, 0); // 16ms���� ���� (�� 60fps)
}

void reshape(int w, int h) {
    glViewport(0, 0, w, h); // ����Ʈ�� ������ ũ�⿡ ����
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
    glClearColor(0.0, 0.0, 0.0, 1.0); // ������ ���������� ���� (���� ���)
}

int main(int argc, char** argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
    glutInitWindowSize(800, 800);
    glutCreateWindow("�¾��");

    init();
    glutDisplayFunc(display);
    glutReshapeFunc(reshape); // â ũ�� ���� �ݹ� ���
    glutTimerFunc(16, MyTimer, 0); // Ÿ�̸� �Լ� ����

    glutMainLoop();

    return 0;
}
