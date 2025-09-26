#include <GL/glut.h>
#include <stdio.h>

GLint TopLeftX, TopLeftY, BottomRightX, BottomRightY;

void mydisplay() {
	glClear(GL_COLOR_BUFFER_BIT);
	glViewport(0, 0, 300, 300);
	glBegin(GL_POLYGON);

	glVertex3f(TopLeftX / 300.0, (300 - TopLeftY) / 300.0, 0.0);
	glVertex3f(TopLeftX / 300.0, (300 - BottomRightY) / 300.0, 0.0);
	glVertex3f(BottomRightX / 300.0, (300 - BottomRightY) / 300.0, 0.0);
	glVertex3f(BottomRightX / 300.0, (300 - TopLeftY) / 300.0, 0.0);

	glEnd();
	/*
	GLUT �𵨸�(������ü)
	glutSolidCube(0.5); // ������ü�� �׸��µ� �ȿ��� ä��.
	glutWireCube(1); // ������ü�� ���븸 ������ ǥ��.
	

	GLUT �𵨸�(����)
	glutSolidSphere(������, �漱, ����);
	glutWireSphere(0.5, 10, 10);
	
	
	GLUT �𵨸�(��ȯü)
	glutSolidTorus(���� ������, �ٱ��� ������, Ʃ�� �ܸ� ���а���, Ʃ�� ���� ���а���);
	glutWireTorus(0.2, 0.35, 10, 10);
	

	GLUT �𵨸�(����)
	glutSolidCone(�ظ��� ������, ������ ����, z�� �ѷ� ���а���, z���� ���� �� ����);
	glutWireCone(0.4, 0.1, 10, 10);
	

	GLUT �𵨸�(�� ������)
	glutSolidTeapot(size);
	glutWireTeapot(0.5);
	*/

	//glutWireTorus(0.2, 0.35, 10, 10); // �׽�Ʈ
	glFlush();
}

//�������� �ݹ�(������ ũ�� ������ ���� �ְ������ ���� ���)
void MyReshape(int NewWidth, int NewHeight) {
	glViewport(0, 0, NewWidth, NewHeight);
	GLfloat WidthFactor = (GLfloat)NewWidth / (GLfloat)300;
	GLfloat HeightFactor = (GLfloat)NewHeight / (GLfloat)300;
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(-1.0 * WidthFactor, 1.0 * WidthFactor,
		-1.0 * HeightFactor, 1.0 * HeightFactor, -1.0, 1.0);
}

//Ű���� �ݹ�(���� �Լ� ���� ���� �������.)
void MyKeyboard(unsigned char KeyPressed, int X, int Y) {
	switch (KeyPressed) {
	case 'Q':
		exit(0); break;
	case 'q':
		exit(0); break;
	
	case 27: //ESC
		exit(0); break;
	}
}


//���콺 �ݹ�
void MyMouseClick(GLint button, GLint State, GLint X, GLint Y) {
	if (button == GLUT_LEFT_BUTTON && State == GLUT_DOWN) { // if (button == 0) �̰Ŷ� ����.
		TopLeftX = X;
		TopLeftY = Y;

		printf("TopLeftX=%d, TopLeftY=%d\n", TopLeftX, TopLeftY);
	}

}

void MyMouseMove(GLint X, GLint Y) {
	BottomRightX = X;
	BottomRightY = Y;
	glutPostRedisplay();

	printf("BottomRightX=%d, BottomRightY=%d\n", BottomRightX, BottomRightY);
}

int main(int argc, char* argv[]) {
	glutInit(&argc, argv);

	glutInitWindowSize(300, 300);
	glutInitWindowPosition(0, 0);
	glutCreateWindow("Hello, OpenGL!");

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(0.0, 1.0, 0.0, 1.0, -1.0, 1.0);

	glutDisplayFunc(mydisplay);
	glutKeyboardFunc(MyKeyboard);
	glutMouseFunc(MyMouseClick);
	glutMotionFunc(MyMouseMove);
	//glutReshapeFunc(MyReshape);

	glutMainLoop();

	return 0;

}