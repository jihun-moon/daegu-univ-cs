#include <GL/glut.h> //GLUT ���̺귯�� �Լ��� ����ϱ� ���� ��

void mydisplay() {
    glClear(GL_COLOR_BUFFER_BIT); // ȭ���� ����� ���

    // �� ��� �׸���
    glBegin(GL_LINE_LOOP); // GL_LINE_LOOP�� ���� ������ �ٰ����� ����

    // �� ��� ������ ����
    glVertex3f(0.0f, 0.4f, 0.0f);   // ��� ������
    glVertex3f(0.1f, 0.1f, 0.0f);   // ������ ��� ���� ������
    glVertex3f(0.4f, 0.1f, 0.0f);   // ������ �� ������
    glVertex3f(0.2f, -0.1f, 0.0f);  // ������ �ϴ� ���� ������
    glVertex3f(0.25f, -0.4f, 0.0f); // ������ �ϴ� ������
    glVertex3f(0.0f, -0.2f, 0.0f);  // �ϴ� �߾� ���� ������
    glVertex3f(-0.25f, -0.4f, 0.0f);// ���� �ϴ� ������
    glVertex3f(-0.2f, -0.1f, 0.0f); // ���� �ϴ� ���� ������
    glVertex3f(-0.4f, 0.1f, 0.0f);  // ���� �� ������
    glVertex3f(-0.1f, 0.1f, 0.0f);  // ���� ��� ���� ������
    glEnd();

    glFlush(); //������� ���� ��ɾ� ��θ� ���μ����� �����ϵ��� �������
}

int main(int argc, char* argv[]) //�����Լ��� ��� GLUT�� ���� 
{
    glutInit(&argc, argv);
    glutCreateWindow("Hello, OpenGL!");//GLUT���� ���ο� ������ ���� ���
    //"Hello, OpenGL!" �Ķ���Ͱ� ������ ��� Ÿ��Ʋ �ٿ� ǥ��

    glutDisplayFunc(mydisplay);//"mydisplay" �Լ���
    //���÷��� �̺�Ʈ(DIsplayFunc)�� ���� �ݹ� �Լ��� ���

    glutMainLoop();//�����Լ��� �׻� glutMainLoop()�� ��
    //�̺�Ʈ ������ ������ �ϱ� ����

    return 0;//���� �Լ��� ���� Ÿ���� ������ ����
}
