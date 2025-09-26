/*
#include <GL/glut.h>
#include <GL/gl.h>
#include <GL/glu.h>

GLboolean IsSphere = true; // 변수 만들기
GLboolean IsSmall = true;

void mydisplay() {
	glClear(GL_COLOR_BUFFER_BIT);
	
	//작은원구
	if ((IsSphere==true) && (IsSmall==true))
		glutWireSphere(0.2, 15, 15);
	//큰 원구
	else if ((IsSphere == true) && (IsSmall == false))
		glutWireSphere(0.8, 15, 15);
	//작은 원환체
	else if ((IsSphere == false) && (IsSmall == true))
		glutWireTorus(0.1, 0.3, 40, 20);
	//큰 원환체
	else if ((IsSphere == false) && (IsSmall == false))
		glutWireTorus(0.4, 0.8, 40, 20);
	glFlush();
}

void MyMainMenu(int entryID) {
	if (entryID == 1)
		IsSphere = true;
	else if (entryID == 2)
		IsSphere = false;
	else if (entryID == 3)
		exit(0);
	glutPostRedisplay();
}

void MySubMenu(int entryID) {
	if (entryID == 1)
		IsSmall = true;
	else if (entryID == 2)
		IsSmall = false;
	glutPostRedisplay();
}

// 메뉴 콜백
int main(int argc, char* argv[]) {
	glutInit(&argc, argv);
	glutCreateWindow("OpenGL Menu Example");
	
	GLint MySubMenuID = glutCreateMenu(MySubMenu);
	glutAddMenuEntry("Small One", 1);
	glutAddMenuEntry("Big One", 2);

	GLint MyMainMenuID = glutCreateMenu(MyMainMenu);
	glutAddMenuEntry("Draw Sphere", 1);
	glutAddMenuEntry("Draw Torus", 2);
	glutAddSubMenu("Change Size", MySubMenuID); // 하위메뉴
	glutAddMenuEntry("Exit", 3);

	glutAttachMenu(GLUT_RIGHT_BUTTON);

	glutDisplayFunc(mydisplay);
	glutMainLoop();

	return 0;

}
*/

// 타이머 콜백

#include <GL/glut.h>

GLfloat Delta = 0.0;

void mydisplay() {
	glClear(GL_COLOR_BUFFER_BIT);
	glBegin(GL_POLYGON);
	glColor3f(0.0, 0.5, 0.8);
	glVertex3f(-0.5 + Delta, -0.5, 0.0);
	glVertex3f(0.0 + Delta, -0.5, 0.0);
	glVertex3f(0.0 + Delta, 0.5, 0.0);
	glVertex3f(-0.5 + Delta, 0.5, 0.0);
	glEnd();
	glFlush();


	glutSwapBuffers(); // 더블 버퍼링
}

void MyTimer(int Value)
{
	Delta = Delta + 0.01;
	glutPostRedisplay();
	glutTimerFunc(40, MyTimer, 1);
}

int main(int argc, char* argv[]) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_RGB | GLUT_DOUBLE);
	glutInitWindowPosition(0, 0);
	glutCreateWindow("OpenGL Drawing Example");

	glClearColor(1.0, 1.0, 1.0, 1.0);
	glMatrixMode(GL_PROJECTION); glLoadIdentity();
	glOrtho(0.0, 1.0, -1.0, 1.0, 1.0, -1.0);
	glutDisplayFunc(mydisplay);

	glutTimerFunc(40, MyTimer, 1);

	glutMainLoop();

	return 0;

}