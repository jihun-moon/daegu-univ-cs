#include <GL/glut.h>

#include <stdio.h>

 

GLfloat Delta = 0.0;       // ������ �̵� �Ÿ�

GLfloat Scale = 1.0;       // ������ ũ�� ��ȭ

GLboolean IsSphere = true; // ������ �ﰢ������ �׸����� ����

GLboolean IsSmall = true;  // ���� ũ�� (���� ����, ū ����)

GLfloat Speed = 0.01;      // �⺻ �̵� �ӵ�

 

// ������ �׸��� �Լ�

void drawShape() {

    if (IsSphere && IsSmall) {

        glColor3f(1.0, 0.0, 0.0); // ������

        glBegin(GL_TRIANGLES);

        glVertex3f(0.0, 0.2, 0.0);

        glVertex3f(-0.2, -0.2, 0.0);

        glVertex3f(0.2, -0.2, 0.0);

        glEnd();

    }

    else if (IsSphere && !IsSmall) {

        glColor3f(0.0, 1.0, 0.0); // �ʷϻ�

        glBegin(GL_TRIANGLES);

        glVertex3f(0.0, 0.5, 0.0);

        glVertex3f(-0.5, -0.5, 0.0);

        glVertex3f(0.5, -0.5, 0.0);

        glEnd();

    }

    else if (!IsSphere && IsSmall) {

        glColor3f(0.0, 0.5, 1.0); // �Ķ���

        glBegin(GL_POLYGON);

        glVertex3f(-0.2, -0.2, 0.0);

        glVertex3f(0.2, -0.2, 0.0);

        glVertex3f(0.2, 0.2, 0.0);

        glVertex3f(-0.2, 0.2, 0.0);

        glEnd();

    }

    else {

        glColor3f(1.0, 1.0, 0.0); // �����

        glBegin(GL_POLYGON);

        glVertex3f(-0.5, -0.5, 0.0);

        glVertex3f(0.5, -0.5, 0.0);

        glVertex3f(0.5, 0.5, 0.0);

        glVertex3f(-0.5, 0.5, 0.0);

        glEnd();

    }

}

 

// ���÷��� �Լ�: ������ �׸���

void mydisplay() {

    glClear(GL_COLOR_BUFFER_BIT);

 

    glPushMatrix();

    glTranslatef(Delta, 0.0, 0.0);  // �̵�

    glScalef(Scale, Scale, 1.0);    // ũ�� ����

    drawShape();

    glPopMatrix();

 

    glFlush();

    glutSwapBuffers(); // ���� ���۸�

}

 

// â ũ�� ���� �� ������ ��׷����� �ʵ��� ����

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

 

// Ű���� �Է¿� ���� �ӵ� ����

void keyboard(unsigned char key, int x, int y) {

    if (key == 'f') {

        Speed += 0.01;  // �ӵ� ����

        if (Speed > 0.2) Speed = 0.2;

    }

    else if (key == 's') {

        Speed -= 0.01;  // �ӵ� ����

        if (Speed < 0.01) Speed = 0.01;

    }

}

 

// �ִϸ��̼� Ÿ�̸� �Լ�

void MyTimer(int Value) {

    Delta += Speed;  // ���� �̵�

    if (Delta > 1.0) Delta = -1.0;  // ȭ�� ������ ������ �ٽ� ����

 

    Scale += 0.01;  // ���� ũ�� ��ȭ

    if (Scale > 2.0) Scale = 1.0;

 

    glutPostRedisplay();

    glutTimerFunc(40, MyTimer, 1);

}

 

// �޴� ���� �Լ�

void MyMainMenu(int entryID) {

    if (entryID == 1) IsSphere = false;  // �׸�

    else if (entryID == 2) IsSphere = true;   // �ﰢ��

    else if (entryID == 3) exit(0);  // ���α׷� ����

    glutPostRedisplay();

}

 

// ����޴� ���� �Լ�

void MySubMenu(int entryID) {

    if (entryID == 1) IsSmall = true;  // ���� ����

    else if (entryID == 2) IsSmall = false;  // ū ����

    glutPostRedisplay();

}

 

int main(int argc, char* argv[]) {

    glutInit(&argc, argv);

    glutInitDisplayMode(GLUT_RGB | GLUT_DOUBLE);

    glutInitWindowPosition(100, 100);  // â ��ġ

    glutInitWindowSize(800, 400);      // â ũ��

    glutCreateWindow("OpenGL Drawing Example");

 

    GLint MySubMenuID = glutCreateMenu(MySubMenu);

    glutAddMenuEntry("Small One", 1);

    glutAddMenuEntry("Big One", 2);

 

    GLint MyMainMenuID = glutCreateMenu(MyMainMenu);

    glutAddMenuEntry("Draw Square", 1);

    glutAddMenuEntry("Draw Triangle", 2);

    glutAddSubMenu("Change Size", MySubMenuID);

    glutAddMenuEntry("Exit", 3);

    glutAttachMenu(GLUT_RIGHT_BUTTON);  // ������ Ŭ�� �޴�

 

    glClearColor(1.0, 1.0, 1.0, 1.0);  // ��� ���

    glMatrixMode(GL_PROJECTION);

    glLoadIdentity();

    glOrtho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);  // ��ǥ��

 

    glutDisplayFunc(mydisplay);

    glutReshapeFunc(MyReshape);

    glutKeyboardFunc(keyboard);

    glutTimerFunc(40, MyTimer, 1);

 

    glutMainLoop();  // �̺�Ʈ ó�� ����

 

    return 0;

}