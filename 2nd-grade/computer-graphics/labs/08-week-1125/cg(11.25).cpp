#include <GL/glut.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <stdio.h>

GLfloat angle1;
GLfloat angle2;

void PolarView(GLfloat radius, GLfloat elevation, GLfloat azimuth, GLfloat twist) {
    glTranslatef(0.0, 0.0, -radius);
    glRotatef(-twist, 0.0, 0.0, 1.0);
    glRotatef(-elevation, 1.0, 0.0, 0.0);
    glRotatef(-azimuth, 0.0, 1.0, 0.0);
}

void mydisplay() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    //gluLookAt(0.3, 0.3, 1.0, 0.0, 0.0, -1.0, 0.0, 1.0, 0.0);
    PolarView(-1, angle1, angle2, 1);
    glutWireTeapot(0.5);
    glFlush();
}

void MyReshape(int w, int h) {
    glViewport(0, 0, (GLfloat)w, (GLfloat)h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);
}

void MyTimer(int Value) {

    if (angle2 <= 90) {
        angle1 = angle1 - 5;
    }
    else {
        angle2 = angle2 + 5;
    }

    glutPostRedisplay();
    glutTimerFunc(40, MyTimer, 1);
}

int main(int argc, char* argv[]) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_SINGLE | GLUT_RGBA | GLUT_DEPTH);
    glutInitWindowSize(400, 400);
    glutInitWindowPosition(0, 0);
    glutCreateWindow("Hello, OpenGL!");
    glClearColor(0.0, 0.0, 0.0, 0.0);
    glEnable(GL_DEPTH_TEST); // Enable depth test
    glutDisplayFunc(mydisplay);
    glutReshapeFunc(MyReshape);
    glutTimerFunc(40, MyTimer, 1);

    glutMainLoop();
    return 0;
}

/*
PPT( 7 ~ 10분)
1. 프로젝트 개요
2. 프로젝트 주요내용
3. 개인의 역할소개 및 개인역량을 발휘한 기능(개인별 발표 2페이지 넘기지 말것)
4. 구동영상 gif 또는 동영상 삽입 (PPT 내에)
*/