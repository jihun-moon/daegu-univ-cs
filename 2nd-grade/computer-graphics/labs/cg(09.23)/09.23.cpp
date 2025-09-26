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
	GLUT 모델링(정육면체)
	glutSolidCube(0.5); // 정육면체를 그리는데 안에는 채움.
	glutWireCube(1); // 정육면체의 뼈대만 선으로 표시.
	

	GLUT 모델링(원구)
	glutSolidSphere(반지름, 경선, 위선);
	glutWireSphere(0.5, 10, 10);
	
	
	GLUT 모델링(원환체)
	glutSolidTorus(안쪽 반지름, 바깥쪽 반지름, 튜브 단면 선분갯수, 튜브 윤곽 선분갯수);
	glutWireTorus(0.2, 0.35, 10, 10);
	

	GLUT 모델링(원뿔)
	glutSolidCone(밑면의 반지름, 원뿔의 높이, z축 둘레 선분갯수, z축을 따라서 면 갯수);
	glutWireCone(0.4, 0.1, 10, 10);
	

	GLUT 모델링(차 주전자)
	glutSolidTeapot(size);
	glutWireTeapot(0.5);
	*/

	//glutWireTorus(0.2, 0.35, 10, 10); // 테스트
	glFlush();
}

//리셰이프 콜백(윈도우 크기 조절에 따른 왜곡방지를 위해 사용)
void MyReshape(int NewWidth, int NewHeight) {
	glViewport(0, 0, NewWidth, NewHeight);
	GLfloat WidthFactor = (GLfloat)NewWidth / (GLfloat)300;
	GLfloat HeightFactor = (GLfloat)NewHeight / (GLfloat)300;
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(-1.0 * WidthFactor, 1.0 * WidthFactor,
		-1.0 * HeightFactor, 1.0 * HeightFactor, -1.0, 1.0);
}

//키보드 콜백(메인 함수 전에 정의 해줘야함.)
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


//마우스 콜백
void MyMouseClick(GLint button, GLint State, GLint X, GLint Y) {
	if (button == GLUT_LEFT_BUTTON && State == GLUT_DOWN) { // if (button == 0) 이거랑 같음.
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