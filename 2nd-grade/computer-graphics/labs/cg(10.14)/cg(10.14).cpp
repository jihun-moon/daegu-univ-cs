#include <GL/glut.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <stdlib.h>

GLfloat Angle = 0.0f; // ���� ����
GLfloat red = 0.0f, green = 0.0f, blue = 1.0f; // �ʱ� ���� (�Ķ���)

void drawOrbitingMoon() {
    // �����ϴ� �� (���� ����)
    glColor3f(red, green, blue);
    glutSolidSphere(0.1, 50, 50); // ������ 0.1�� ��
}

void mydisplay() {
    glClear(GL_COLOR_BUFFER_BIT);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    // ���� ��ȯ: ���� ȸ���� �� �̵�
    glPushMatrix();

    glRotatef(Angle, 0.0, 0.0, 1.0); // Z���� �������� ȸ��
    glTranslatef(0.8, 0.0, 0.0); // ȸ�� �� ���� �Ÿ���ŭ �̵�
    drawOrbitingMoon();

    glPopMatrix();

    glutSwapBuffers(); // ���� ���۸�
}

// Ÿ�̸� �Լ�
void MyTimer(int value) {
    Angle += 1.0f; // ���� ȸ���ϴ� �ӵ� (���� ����)

    // 360�� ȸ���� ������ ���� ����
    if (Angle >= 360.0f) {
        Angle = 0.0f; // ���� �ʱ�ȭ

        // ������ ���� ����
        red = (float)rand() / RAND_MAX;
        green = (float)rand() / RAND_MAX;
        blue = (float)rand() / RAND_MAX;
    }

    glutPostRedisplay(); // ȭ�� �ٽ� �׸���
    glutTimerFunc(16, MyTimer, 0); // 16ms���� ���� (�� 60fps)
}

int main(int argc, char** argv) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE); // ���� ���۸� Ȱ��ȭ
    glutInitWindowSize(400, 400);
    glutCreateWindow("�Ķ��� ������ 360�� ȸ����Ű��");

    glClearColor(1.0, 1.0, 1.0, 1.0); // ��� ���
    glutDisplayFunc(mydisplay);
    glutTimerFunc(16, MyTimer, 0); // Ÿ�̸� �Լ� ����

    glutMainLoop(); // �̺�Ʈ ���� ����

    return 0;
}
