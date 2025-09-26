// ���迡 ���´ٰ� �ϴµ� �׳� ���ظ� �ϸ� ��. �ܿ��� ����.

#include <GL/glut.h>
#include <GL/gl.h>

GLfloat rectX = 0.0, rectY = 0.0;  // �簢���� ��ġ
GLboolean movePolygon = false;        // �簢�� ������ �⺻��
GLfloat step = 0.05;                 // �簢�� �������� �ӵ�

// �ʱ�ȭ �Լ�
void init() {
    glClearColor(0.0, 0.0, 0.0, 1.0);  // ����� ���������� ����
}

// �簢���� �׸��� �Լ�
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
    glColor3f(1.0, 1.0, 1.0);  // �簢���� �Ͼ������ ����
    drawRect();
    glFlush();
}

// ȭ�� ũ�� ���� �ݹ� �Լ�
void reshape(int NewWidth, int NewHeight) {
    glViewport(0, 0, NewWidth, NewHeight);
    GLfloat WidthFactor = (GLfloat)NewWidth / (GLfloat)300;
    GLfloat HeightFactor = (GLfloat)NewHeight / (GLfloat)300;

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();

    glOrtho(-1.0 * WidthFactor, 1.0 * WidthFactor,
        -1.0 * HeightFactor, 1.0 * HeightFactor, -1.0, 1.0);

}


// ���콺 ó�� �Լ� (���� ��ư Ŭ�� �� ����)
void mouse(int button, int state, int x, int y) {
    if (button == GLUT_LEFT_BUTTON && state == GLUT_DOWN) {
        exit(0);
    }
}

// Ű���� �Է� ó�� �Լ�
void keyboard(int key, int x, int y) {
    if (movePolygon) {  // movePolygon�� true�� ���� Ű���� �Է¿� ����
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
        glutPostRedisplay();  // ȭ�� ����
    }
}
// �޴� ó�� �Լ�
void mainMenu(int option) {
    if (option == 1) {
        movePolygon = false;  // �簢�� ���߱�
    }
    else if (option == 2) {
        movePolygon = true;   // Ű����� �簢�� �����̱� Ȱ��ȭ
    }
    else if (option == 3) {
        exit(0);              // ���α׷� ����
    }
}

// �޴� �ݹ�
int main(int argc, char* argv[]) {
    glutInit(&argc, argv);
    glutCreateWindow("OpenGL Menu Example");

    // ���÷��� �� �������� �ݹ� ����
    glutDisplayFunc(mydisplay);
    glutReshapeFunc(reshape);

    // Ű���� �ݹ� ���� (Ư�� Ű)
    glutSpecialFunc(keyboard);

    // ���콺 �ݹ� ����
    glutMouseFunc(mouse);

    // �޴� ����
    glutCreateMenu(mainMenu);
    glutAddMenuEntry("Stop Polygon", 1);
    glutAddMenuEntry("Keyboard Control", 2);
    glutAddMenuEntry("Exit", 3);
    glutAttachMenu(GLUT_RIGHT_BUTTON);  // ������ ��ư�� �޴� ����

    glutMainLoop();  // �̺�Ʈ ���� ����
    return 0;
}