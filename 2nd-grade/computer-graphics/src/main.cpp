// O키 바로 시작 (나중에 삭제예정)
// P키 지진 (나중에 삭제 예정)
// 바로 시작할 예정 파일 저장하는거는 놔두겠음.

// 조작키 A, S ,D, W, C(앉기), X(내려가기), SpaceBar(올라가기)


#include <GL/glut.h>
#include <GL/glu.h>
#include <vector>
#include <fstream>
#include <sstream>
#include <iostream>

std::vector<int> frameIndices; // 각 경로의 현재 프레임을 관리

struct CameraState {
    float cameraX, cameraY, cameraZ;
    float lookX, lookY, lookZ;
};

std::vector<CameraState> lastCameraPositions; // 각 경로의 마지막 위치를 저장할 벡터
std::vector<std::vector<CameraState>> cameraPaths;
int currentPathIndex = 0;
int currentFrame = 0;

// 그냥 시작 버튼
bool startButtonPressed = false;

// 떨림 관련 변수들
bool isEarthquake = false;         // 지진 효과가 활성화되었는지 여부
float shakeIntensity = 0.1f;       // 떨림의 강도 (값이 커지면 떨림이 심해짐)
float shakeDuration = 0.0f;       // 떨림의 지속 시간
float shakeTime = 6.0f;           // 떨림의 지속 시간 (초)
bool earthquakeFinished = false;   // 지진 효과가 끝났는지 여부

// 1인칭 시점 활성화 여부
bool isFirstPersonActive = false; // 1인칭 시점이 활성화 되었는지 여부

// 카메라 변수
float cameraX = -5.0f, cameraY = 1.1f, cameraZ = -5.3f;
float yaw = 0.0f, pitch = 0.0f;
float moveSpeed = 0.05f, mouseSensitivity = 0.2f;
float lookX = 0.0f, lookY = 0.0f, lookZ = 1.0f; // 초기화한 lookZ
float rightX = 1.0f, rightZ = 0.0f; // 초기화한 rightX

// 땅 및 중력 관련 변수
const float groundHeight = 0.8f;
float cameraVelocityY = 0.0f;
bool isFalling = false;

// 점프와 움크리기 관련 변수
bool isJumping = false;
bool isCrouching = false;
const float jumpHeight = 1.0f;
const float crouchHeight = 0.6f;

// 윈도우 크기
int windowWidth = 1280, windowHeight = 720;
bool isWarping = false; // 마우스 강제 이동 여부 플래그

// 키 상태 배열
bool keys[256] = { false };

// 마우스 마지막 위치
int lastX = windowWidth / 2, lastY = windowHeight / 2;

// 카메라 상태 저장 함수
void saveCameraPath(float cameraX, float cameraY, float cameraZ,
    float lookX, float lookY, float lookZ) {
    std::ofstream outFile("camera_path.txt", std::ios::app); // append 모드로 열기
    if (outFile.is_open()) {
        // 카메라 위치와 방향을 한 줄에 기록
        outFile << cameraX << " " << cameraY << " " << cameraZ << " "
            << lookX << " " << lookY << " " << lookZ << std::endl;
        outFile.close();
    }
    else {
        std::cout << "파일 열기 실패!" << std::endl;
    }
}

// 카메라 위치와 방향을 실시간으로 기록하는 함수
void recordCameraPath() {
    saveCameraPath(cameraX, cameraY, cameraZ, lookX, lookY, lookZ); // 카메라 상태 저장
}

// 카메라 경로를 따라가는 함수
void followCameraPath(const std::vector<CameraState>& cameraPath) {
    if (currentFrame < cameraPath.size()) {
        CameraState state = cameraPath[currentFrame];
        cameraX = state.cameraX;
        cameraY = state.cameraY;
        cameraZ = state.cameraZ;
        lookX = state.lookX;
        lookY = state.lookY;
        lookZ = state.lookZ;
        currentFrame++;
    }
    else {
        currentFrame = cameraPath.size();  // 경로 끝에 도달하면 더 이상 이동하지 않음
    }
}


// 카메라 방향 업데이트
void updateCameraDirection() {
    lookX = cosf(yaw) * cosf(pitch);
    lookY = sinf(pitch);
    lookZ = sinf(yaw) * cosf(pitch);
    rightX = cosf(yaw + 3.14159f / 2.0f);
    rightZ = sinf(yaw + 3.14159f / 2.0f);
}

float defaultCameraY = 1.1f; // 기본 카메라 높이
float cameraYOriginal = defaultCameraY;  // 초기 카메라 Y 높이 저장 (기본값)

// X 키로 카메라 이동 (아래로 내려가기)
void processInput() {
    float forwardX = lookX * moveSpeed;
    float forwardZ = lookZ * moveSpeed;

    if (keys['w']) { cameraX += forwardX; cameraZ += forwardZ; }
    if (keys['s']) { cameraX -= forwardX; cameraZ -= forwardZ; }
    if (keys['a']) { cameraX -= rightX * moveSpeed; cameraZ -= rightZ * moveSpeed; }
    if (keys['d']) { cameraX += rightX * moveSpeed; cameraZ += rightZ * moveSpeed; }

    if (keys[' ']) { // 스페이스바 (위로 올라가기)
        cameraY += moveSpeed; // 카메라를 위로 이동
    }

    if (keys['x']) { // X 키 (아래로 내려가기)
        cameraY -= moveSpeed; // 카메라를 아래로 이동
    }

    if (keys['c']) { // Crouch (엎드리기)
        if (!isJumping && !isCrouching) {
            isCrouching = true;
            cameraYOriginal = cameraY; // 현재 Y값을 기준으로 저장
            cameraY -= (defaultCameraY - crouchHeight); // 현재 Y값에서 차이를 빼기 (엎드리기)
        }
    }
    else if (isCrouching) { // Stand up (일어남)
        isCrouching = false;
        cameraY = cameraYOriginal; // 엎드리기 전에 있던 Y값으로 돌아가기
    }
}

// 키보드 입력 처리
void keyboardDown(unsigned char key, int x, int y) {
    if (key == 27) exit(0);  // Escape키 종료
    if (key == 'p' && !earthquakeFinished) {  // p키를 눌렀을 때 지진 시작
        isEarthquake = true; // 지진 시작
        shakeDuration = shakeTime; // 떨림의 지속 시간 설정
    }
    if (key == 'o') {  // o키를 눌렀을 때 시작 버튼
        startButtonPressed = true; // 시작 버튼 활성화
    }
    keys[key] = true;
}

void keyboardUp(unsigned char key, int x, int y) {
    keys[key] = false;
}

// 마우스 입력 처리
void mouseMotion(int x, int y) {
    if (isWarping) return;

    int deltaX = x - lastX;
    int deltaY = y - lastY;

    yaw += deltaX * mouseSensitivity * 0.01f;
    pitch -= deltaY * mouseSensitivity * 0.01f;

    if (pitch > 1.5f) pitch = 1.5f;
    if (pitch < -1.5f) pitch = -1.5f;

    if (yaw > 3.14159f * 2) yaw -= 3.14159f * 2;
    if (yaw < -3.14159f * 2) yaw += 3.14159f * 2;

    updateCameraDirection();

    isWarping = true;
    glutWarpPointer(windowWidth / 2, windowHeight / 2);
    lastX = windowWidth / 2;
    lastY = windowHeight / 2;
    isWarping = false;
}

// 지진 효과 적용 함수
void applyEarthquakeEffect() {
    if (isEarthquake) {
        // 랜덤한 위치 이동 (x, y, z 방향)
        float randomOffsetX = (rand() % 1000 - 500) / 1000.0f * shakeIntensity;
        float randomOffsetY = (rand() % 1000 - 500) / 1000.0f * (shakeIntensity / 2); // Y축 변위 감소
        float randomOffsetZ = (rand() % 1000 - 500) / 1000.0f * shakeIntensity;

        // 기존 카메라 위치에 변위 추가
        cameraX += randomOffsetX;
        cameraY += randomOffsetY;
        cameraZ += randomOffsetZ;

        // 지진 지속 시간 감소
        shakeDuration -= 0.01f; // 지속 시간을 감소시킴
        if (shakeDuration <= 0) {
            isEarthquake = false;      // 떨림 효과 종료
            earthquakeFinished = true; // 지진 종료
        }
    }
}


// 크로스헤어 그리기
void drawCrosshair() {
    glMatrixMode(GL_PROJECTION);
    glPushMatrix();
    glLoadIdentity();
    glOrtho(0, windowWidth, windowHeight, 0, -1, 1);

    glMatrixMode(GL_MODELVIEW);
    glPushMatrix();
    glLoadIdentity();

    glColor3f(1.0f, 1.0f, 1.0f);
    glLineWidth(2.0f);

    glBegin(GL_LINES);
    glVertex2f((GLfloat)windowWidth / 2 - 10, (GLfloat)windowHeight / 2);
    glVertex2f((GLfloat)windowWidth / 2 + 10, (GLfloat)windowHeight / 2);
    glVertex2f((GLfloat)windowWidth / 2, (GLfloat)windowHeight / 2 - 10);
    glVertex2f((GLfloat)windowWidth / 2, (GLfloat)windowHeight / 2 + 10);
    glEnd();

    glMatrixMode(GL_PROJECTION);
    glPopMatrix();
    glMatrixMode(GL_MODELVIEW);
    glPopMatrix();
}

// 물리 업데이트 및 화면 갱신
void idle() {
    processInput();
    glutPostRedisplay();
}

// 윈도우 크기 변경 처리
void reshape(int w, int h) {
    if (h == 0) h = 1;
    float aspect = (float)w / (float)h;
    windowWidth = w;
    windowHeight = h;

    glViewport(0, 0, w, h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45.0f, aspect, 0.1f, 100.0f);
    glMatrixMode(GL_MODELVIEW);
}

// 사람 모델 그리기 (전체 변환 적용)
void drawPerson(float x, float y, float z, float rx, float ry, float rz) {
    glPushMatrix();

    // 전체 인물 위치 및 회전
    glTranslatef(x, y, z);
    glRotatef(rx, 1.0f, 0.0f, 0.0f);
    glRotatef(ry, 0.0f, 1.0f, 0.0f);
    glRotatef(rz, 0.0f, 0.0f, 1.0f);

    // 머리
    glPushMatrix();
    glColor3f(1.0f, 0.8f, 0.6f); // 피부색
    glTranslatef(0.0f, 0.8f, 0.0f); // 머리 위치
    glutSolidSphere(0.2f, 20, 20); // 머리 크기
    glPopMatrix();

    // 몸통
    glPushMatrix();
    glColor3f(0.0f, 0.0f, 1.0f); // 파란색 몸통
    glTranslatef(0.0f, 0.5f, 0.0f); // 몸통 위치
    glScalef(0.3f, 0.5f, 0.2f); // 몸통 크기
    glutSolidCube(1.0);
    glPopMatrix();

    // 다리 (왼쪽)
    glPushMatrix();
    glColor3f(0.0f, 0.0f, 0.0f); // 검정색 바지
    glTranslatef(-0.1f, 0.1f, 0.0f); // 왼쪽 다리 위치
    glScalef(0.1f, 0.4f, 0.1f); // 왼쪽 다리 크기
    glutSolidCube(1.0);
    glPopMatrix();

    // 다리 (오른쪽)
    glPushMatrix();
    glColor3f(0.0f, 0.0f, 0.0f);
    glTranslatef(0.1f, 0.1f, 0.0f); // 오른쪽 다리 위치
    glScalef(0.1f, 0.4f, 0.1f); // 오른쪽 다리 크기
    glutSolidCube(1.0);
    glPopMatrix();

    // 팔 (왼쪽)
    glPushMatrix();
    glColor3f(0.0f, 0.0f, 1.0f); // 파란색 팔
    glTranslatef(-0.25f, 0.6f, 0.0f); // 왼쪽 팔 위치
    glScalef(0.1f, 0.3f, 0.1f); // 왼쪽 팔 크기
    glutSolidCube(1.0);
    glPopMatrix();

    // 팔 (오른쪽)
    glPushMatrix();
    glColor3f(0.0f, 0.0f, 1.0f);
    glTranslatef(0.25f, 0.6f, 0.0f); // 오른쪽 팔 위치
    glScalef(0.1f, 0.3f, 0.1f); // 오른쪽 팔 크기
    glutSolidCube(1.0);
    glPopMatrix();

    glPopMatrix();
}


void loadCameraPaths() {
    std::vector<std::string> filenames = {
        "camera_path_1.txt",
        "camera_path_2.txt",
        "camera_path_3.txt",
        "camera_admin.txt"
    };

    for (const auto& filename : filenames) {
        std::ifstream file(filename);
        std::vector<CameraState> path;
        float x, y, z, lx, ly, lz;
        while (file >> x >> y >> z >> lx >> ly >> lz) {
            path.push_back({ x, y, z, lx, ly, lz });
        }

        if (!path.empty()) {
            // 디버그: 첫 프레임 출력
            const auto& firstFrame = path.front();
            std::cout << "File: " << filename
                << " First Frame - Position: ("
                << firstFrame.cameraX << ", " << firstFrame.cameraY << ", " << firstFrame.cameraZ << ")"
                << " LookAt: (" << firstFrame.lookX << ", " << firstFrame.lookY << ", " << firstFrame.lookZ << ")"
                << std::endl;

            cameraPaths.push_back(path);
            frameIndices.push_back(0);  // 초기 프레임 설정

            // 경로가 끝날 때 마지막 프레임의 위치를 기억
            CameraState lastFrame = path.back();  // 경로의 마지막 프레임
            lastCameraPositions.push_back(lastFrame); // 마지막 위치를 저장
        }
        else {
            std::cerr << "경로 파일을 불러오지 못했습니다: " << filename << std::endl;
        }
        file.close();
    }
}


//===================================================          =====================================================//
//=================================================== 대피경로 =====================================================//
//===================================================          =====================================================//

void drawArrow2D(float baseX, float baseY, float length, float width) {
    float headLength = length * 0.3f; // 화살표 머리 길이
    float bodyLength = length - headLength; // 화살표 몸체 길이
    float halfWidth = width / 2.0f; // 몸체 폭의 절반

    // 화살표 몸체 (직사각형)
    glBegin(GL_QUADS);
    glColor3f(0.0f, 1.0f, 0.0f); // 초록색
    glVertex3f(baseX, baseY - halfWidth, 0.0f);                // 왼쪽 아래
    glVertex3f(baseX + bodyLength, baseY - halfWidth, 0.0f);   // 오른쪽 아래
    glVertex3f(baseX + bodyLength, baseY + halfWidth, 0.0f);   // 오른쪽 위
    glVertex3f(baseX, baseY + halfWidth, 0.0f);                // 왼쪽 위
    glEnd();

    // 화살표 머리 (삼각형)
    glBegin(GL_TRIANGLES);
    glColor3f(0.0f, 0.8f, 0.0f); // 어두운 초록색

    // 아래쪽 삼각형 면
    glVertex3f(baseX + bodyLength, baseY - width, 0.0f);       // 아래쪽 끝
    glVertex3f(baseX + bodyLength + headLength, baseY, 0.0f);  // 화살표 끝
    glVertex3f(baseX + bodyLength, baseY + width, 0.0f);       // 위쪽 끝

    // 위쪽 삼각형 면 (반대로 뒤집어서 추가)
    glVertex3f(baseX + bodyLength, baseY + width, 0.0f);       // 위쪽 끝
    glVertex3f(baseX + bodyLength + headLength, baseY, 0.0f);  // 화살표 끝
    glVertex3f(baseX + bodyLength, baseY - width, 0.0f);       // 아래쪽 끝

    glEnd();
}


void drawBoldRedX(float centerX, float centerY, float size, float lineWidth) {
    float halfSize = size / 2.0f; // X 표시의 반 크기

    glColor3f(1.0f, 0.0f, 0.0f); // 빨간색
    glLineWidth(lineWidth); // 선의 두께 설정

    glBegin(GL_LINES);
    // 대각선 1
    glVertex3f(centerX - halfSize, centerY - halfSize, 0.0f); // 왼쪽 아래
    glVertex3f(centerX + halfSize, centerY + halfSize, 0.0f); // 오른쪽 위

    // 대각선 2
    glVertex3f(centerX - halfSize, centerY + halfSize, 0.0f); // 왼쪽 위
    glVertex3f(centerX + halfSize, centerY - halfSize, 0.0f); // 오른쪽 아래
    glEnd();

    glLineWidth(1.0f); // 기본 선 두께로 복원
}



//=====================================================      =====================================================//
//===================================================== 교실 =====================================================//
//=====================================================      =====================================================//


void drawPodium() {
    // 교탁 기본 몸체
    glPushMatrix();
    glColor3f(0.6f, 0.4f, 0.2f); // 교탁 색상 (나무 느낌)
    glTranslatef(-2.5f, -0.3f, 4.6f); // 왼쪽에 위치시킴
    glScalef(0.8f, 1.0f, 0.5f); // 교탁 크기 조정
    glutSolidCube(1.0);
    glPopMatrix();

    // 교탁 상단 (책 놓는 부분)
    glPushMatrix();
    glColor3f(0.5f, 0.3f, 0.1f); // 더 짙은 색상
    glTranslatef(-2.5f, 0.3f, 4.6f);
    glScalef(0.9f, 0.1f, 0.6f);
    glutSolidCube(1.0);
    glPopMatrix();
}

void drawChairs() {
    // 의자 색상 설정 (파란색)
    for (float x = -1.0f; x <= 1.0f; x += 1.0f) {
        // 의자 시트 부분
        glPushMatrix();
        glColor3f(0.0f, 0.4f, 0.8f);
        glTranslatef(x, -0.3f, -0.5f); // 의자 위치 조정 (책상 가까이 배치)
        glScalef(0.4f, 0.05f, 0.4f); // 시트 크기 조정
        glutSolidCube(1.0);
        glPopMatrix();

        // 의자 등받이 부분
        glPushMatrix();
        glColor3f(0.0f, 0.4f, 0.8f);
        glTranslatef(x, -0.1f, -0.68f); // 등받이 위치 조정
        glScalef(0.4f, 0.4f, 0.05f); // 등받이 크기 조정
        glutSolidCube(1.0);
        glPopMatrix();

        // 의자 다리
        glColor3f(0.5f, 0.5f, 0.5f); // 다리 색상 (회색)
        for (float dx = -0.15f; dx <= 0.15f; dx += 0.3f) {
            for (float dz = -0.15f; dz <= 0.15f; dz += 0.3f) {
                glPushMatrix();
                glTranslatef(x + dx, -0.55f, -0.5f + dz); // 다리 위치 조정 (시트에 붙도록)
                glScalef(0.05f, 0.5f, 0.05f); // 다리 크기 조정
                glutSolidCube(1.0);
                glPopMatrix();
            }
        }
    }
}


void drawGlassWindow(float posX, float posY, float posZ, float width, float height) {
    glEnable(GL_BLEND);                      // 반투명 효과 활성화
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); // 블렌딩 함수 설정

    glDisable(GL_CULL_FACE);                 // 양면 렌더링 활성화
    glDepthMask(GL_FALSE);                   // 깊이 버퍼 쓰기 비활성화 (투명도 처리)


    glColor4f(0.6f, 0.8f, 1.0f, 0.6f);       // 알파값(0.5)으로 투명도 설정

    // 통유리 판 그리기
    glPushMatrix();
    glTranslatef(posX, posY, posZ);          // 위치 설정
    glScalef(width, height, 0.01f);          // 통유리 크기 설정 (얇게 설정)
    glutSolidCube(1.0f);                     // 유리창
    glPopMatrix();
    glDepthMask(GL_TRUE);                    // 깊이 버퍼 쓰기 재활성화
    glEnable(GL_CULL_FACE);                  // 기본 설정으로 복구
    glDisable(GL_BLEND);                     // 반투명 효과 비활성화
}

void drawClassRoomGlassWindow(float posX, float posY, float posZ, float totalWidth, float height, float frameThickness) {
    float paneWidth = totalWidth / 3.0f; // 각 창문의 너비 (총 너비를 3등분)

    glEnable(GL_BLEND); // 반투명 효과 활성화
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    // 프레임 색상 (회색)
    glColor3f(0.4f, 0.4f, 0.4f);

    // 상단 프레임
    glPushMatrix();
    glTranslatef(posX, posY + height / 2.0f, posZ);
    glScalef(totalWidth, frameThickness, frameThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 하단 프레임
    glPushMatrix();
    glTranslatef(posX, posY - height / 2.0f, posZ);
    glScalef(totalWidth, frameThickness, frameThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 좌측 프레임
    glPushMatrix();
    glTranslatef(posX - totalWidth / 2.0f, posY, posZ);
    glScalef(frameThickness, height, frameThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 우측 프레임
    glPushMatrix();
    glTranslatef(posX + totalWidth / 2.0f, posY, posZ);
    glScalef(frameThickness, height, frameThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 중간 프레임 1
    glPushMatrix();
    glTranslatef(posX - paneWidth / 2.0f, posY, posZ);
    glScalef(frameThickness, height, frameThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 중간 프레임 2
    glPushMatrix();
    glTranslatef(posX + paneWidth / 2.0f, posY, posZ);
    glScalef(frameThickness, height, frameThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 유리창 (세 개의 유리 패널)
    glDisable(GL_CULL_FACE); // 양면 렌더링 활성화
    glDepthMask(GL_FALSE);  // 깊이 버퍼 쓰기 비활성화

    glColor4f(0.6f, 0.8f, 1.0f, 0.5f); // 유리 색상 설정 (투명)

    for (int i = 0; i < 3; i++) { // 3개의 패널 생성
        glPushMatrix();
        glTranslatef(posX - totalWidth / 2.0f + paneWidth / 2.0f + i * paneWidth, posY, posZ);
        // 위치: 왼쪽 시작점에서 `paneWidth / 2.0f`만큼 이동 후, 각 패널의 위치로 이동
        glScalef(paneWidth - frameThickness, height - frameThickness, frameThickness);
        glutSolidCube(1.0f);
        glPopMatrix();
    }

    glDepthMask(GL_TRUE); // 깊이 버퍼 쓰기 활성화
    glEnable(GL_CULL_FACE); // 기본 설정으로 복구
    glDisable(GL_BLEND); // 반투명 효과 비활성화
}






void drawBlackboard() {
    glColor3f(0.1f, 0.3f, 0.1f); // 짙은 녹색
    glPushMatrix();
    glTranslatef(0.0f, 0.5f, 4.95f); // 칠판 위치 조정
    glScalef(5.0f, 1.0f, 0.05f); // 칠판 크기 조정
    glutSolidCube(1.0);
    glPopMatrix();
}



void drawFloor() {
    glPushMatrix();
    glColor3f(0.8f, 0.8f, 0.8f); // 바닥 색을 더 짙게
    glTranslatef(0.0f, -0.7f, 0.0f);
    glScalef(10.0f, 0.05f, 10.0f);
    glutSolidCube(1.0);
    glPopMatrix();
}

void drawWalls() {
    glPushMatrix();
    glColor3f(0.75f, 0.75f, 0.75f); // 왼쪽 벽
    glTranslatef(-5.0f, 0.75f, 0.0f);
    glScalef(0.05f, 5.0f, 10.0f);
    glutSolidCube(1.0);
    glPopMatrix();

    glPushMatrix();
    glColor3f(0.75f, 0.75f, 0.75f); // 오른쪽 벽
    glTranslatef(5.0f, 0.75f, 0.0f);
    glScalef(0.05f, 5.0f, 10.0f);
    glutSolidCube(1.0);
    glPopMatrix();

    glPushMatrix();
    glColor3f(0.8f, 0.8f, 0.8f); // 앞벽
    glTranslatef(0.0f, 0.25f, 5.0f);
    glScalef(10.0f, 5.0f, 0.05f);
    glutSolidCube(1.0);
    glPopMatrix();

    drawBlackboard();

    // 뒷벽: 창문 윗부분과 아래부분을 막음
    glPushMatrix();
    glColor3f(0.8f, 0.8f, 0.8f);
    glTranslatef(0.0f, 2.5f, -5.0f); // 창문 위쪽 벽
    glScalef(10.0f, 1.5f, 0.05f); // 창문 위쪽 벽 크기
    glutSolidCube(1.0);
    glPopMatrix();

    glPushMatrix();
    glTranslatef(0.0f, -1.5f, -5.0f); // 창문 아래쪽 벽
    glScalef(10.0f, 3.0f, 0.05f); // 창문 아래쪽 벽 크기
    glutSolidCube(1.0);
    glPopMatrix();

    drawClassRoomGlassWindow(0.0f, 1.0f, -5.0f, 10.0f, 1.75f, 0.1f); // 창문 그리기
}

void drawProjector() {
    glColor3f(0.5f, 0.5f, 0.5f); // 짙은 회색
    glPushMatrix();
    glTranslatef(0.0f, 1.35f, 3.0f);
    glScalef(0.5f, 0.2f, 0.5f);
    glutSolidCube(1.0);
    glPopMatrix();

    glPushMatrix();
    glColor3f(0.4f, 0.4f, 0.4f); // 렌즈 색상
    glTranslatef(0.0f, 1.35f, 2.75f);
    glRotatef(-90, 1.0f, 0.0f, 0.0f);
    GLUquadric* quad = gluNewQuadric();
    gluCylinder(quad, 0.1f, 0.1f, 0.25f, 20, 20);
    gluDeleteQuadric(quad);
    glPopMatrix();

    glColor4f(1.0f, 1.0f, 0.8f, 0.3f);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    glPushMatrix();
    glBegin(GL_TRIANGLES);
    glVertex3f(0.0f, 1.3f, 2.75f);
    glVertex3f(-2.5f, 0.5f, 4.95f);
    glVertex3f(2.5f, 0.5f, 4.95f);
    glEnd();
    glPopMatrix();

    glDisable(GL_BLEND);
}

void drawMonitors() {
    // 모니터 화면 색상 (회색)
    glColor3f(0.3f, 0.3f, 0.3f);
    for (float x = -1.0f; x <= 1.0f; x += 1.0f) {
        glPushMatrix();
        glTranslatef(x, 0.2f, 0.25f); // 책상 위에 중앙 쪽으로 더 가까이 놓도록 조정
        glScalef(0.5f, 0.3f, 0.05f); // 모니터 크기 조정
        glutSolidCube(1.0);
        glPopMatrix();
    }

    // 모니터 받침대 (검정색)
    glColor3f(0.0f, 0.0f, 0.0f);
    for (float x = -1.0f; x <= 1.0f; x += 1.0f) {
        glPushMatrix();
        glTranslatef(x, 0.05f, 0.25f); // 받침대 위치 조정
        glScalef(0.1f, 0.05f, 0.1f); // 받침대 크기 조정
        glutSolidCube(1.0);
        glPopMatrix();
    }
}

void drawDoor(float x, float z, bool open = false) {
    glColor3f(0.7f, 0.7f, 0.7f); // 문 색상
    glPushMatrix();

    // 문이 열려 있으면 방향에 맞게 회전
    if (open) {
        glTranslatef(x, -0.3f, z);
        if (x > 0) { // 오른쪽 문일 때
            glRotatef(90.0f, 0.0f, 1.0f, 0.0f); // 오른쪽 문 열림
            glTranslatef(-0.35f, 0.0f, -0.35f); // 회전 후 위치 조정
        }
        else { // 왼쪽 문일 때
            glRotatef(-90.0f, 0.0f, 1.0f, 0.0f); // 왼쪽 문 열림
            glTranslatef(0.35f, 0.0f, -0.35f); // 회전 후 위치 조정
        }
    }
    else {
        glTranslatef(x, -0.3f, z); // 문이 닫힌 경우 기본 위치
    }

    glScalef(0.7f, 1.5f, 0.01f); // 문 크기 조정
    glutSolidCube(1.0);
    glPopMatrix();

    // 손잡이 추가 (원기둥 모양)
    glPushMatrix();
    glColor3f(0.5f, 0.5f, 0.5f); // 손잡이 색상
    glTranslatef(x + 0.25f * (x > 0 ? -1 : 1), -0.3f, z + 0.01f);
    glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
    GLUquadric* quad = gluNewQuadric();
    gluCylinder(quad, 0.05f, 0.05f, 0.1f, 20, 20);
    gluDeleteQuadric(quad);
    glPopMatrix();
}

void drawDesk() {
    // 책상 상판 그리기
    glPushMatrix();
    glColor3f(0.76f, 0.49f, 0.27f); // 갈색 상판 색상 설정 (RGB)
    glTranslatef(0.0f, 0.0f, 0.0f);
    glScalef(3.0f, 0.1f, 0.6f); // 상판 크기 조정
    glutSolidCube(1.0);
    glPopMatrix();

    // 책상 중앙 칸막이 그리기
    glPushMatrix();
    glColor3f(0.6f, 0.4f, 0.3f); // 중앙 칸막이 색상 설정
    glTranslatef(0.0f, -0.4f, 0.25f); // 중앙 칸막이 위치 조정 (상판 바로 아래)
    glScalef(2.9f, 0.7f, 0.1f); // 중앙 칸막이 크기 조정
    glutSolidCube(1.0);
    glPopMatrix();

    // 책상 왼쪽 칸막이 그리기
    glPushMatrix();
    glColor3f(0.6f, 0.4f, 0.3f); // 왼쪽 칸막이 색상 설정
    glTranslatef(-1.4f, -0.4f, 0.0f); // 왼쪽 칸막이 위치 조정
    glScalef(0.1f, 0.7f, 0.6f); // 왼쪽 칸막이 크기 조정
    glutSolidCube(1.0);
    glPopMatrix();

    // 책상 오른쪽 칸막이 그리기
    glPushMatrix();
    glColor3f(0.6f, 0.4f, 0.3f); // 오른쪽 칸막이 색상 설정
    glTranslatef(1.4f, -0.4f, 0.0f); // 오른쪽 칸막이 위치 조정
    glScalef(0.1f, 0.7f, 0.6f); // 오른쪽 칸막이 크기 조정
    glutSolidCube(1.0);
    glPopMatrix();
}

void drawClassroomSetupWithChairs() {
    // 원래의 중앙 책상, 모니터, 의자 (사람 포함)
    drawDesk();
    drawMonitors();
    drawChairs(); // 중앙에는 사람 앉히기

    // 오른쪽 책상과 모니터 (사람 없음)
    glPushMatrix();
    glTranslatef(2.8f, 0.0f, 0.0f);
    drawDesk();
    drawMonitors();
    glPopMatrix();

    // 왼쪽 책상과 모니터 (사람 없음)
    glPushMatrix();
    glTranslatef(-2.8f, 0.0f, 0.0f);
    drawDesk();
    drawMonitors();
    glPopMatrix();

    // 오른쪽 뒤 책상과 모니터 (사람 없음)
    glPushMatrix();
    glTranslatef(2.0f, 0.0f, -2.2f);
    drawDesk();
    drawMonitors();
    glPopMatrix();

    // 왼쪽 뒤 책상과 모니터 (사람 없음)
    glPushMatrix();
    glTranslatef(-2.0f, 0.0f, -2.2f);
    drawDesk();
    drawMonitors();
    glPopMatrix();

    // 뒤쪽 책상과 모니터 (사람 없음)
    glPushMatrix();
    glTranslatef(0.0f, 0.0f, -4.0f);
    drawDesk();
    drawMonitors();
    glPopMatrix();

    // 대각선 뒤 왼쪽 책상과 모니터 (사람 없음)
    glPushMatrix();
    glTranslatef(-2.0f, 0.0f, -4.0f);
    drawDesk();
    drawMonitors();
    glPopMatrix();

    // 대각선 뒤 오른쪽 책상과 모니터 (사람 없음)
    glPushMatrix();
    glTranslatef(2.0f, 0.0f, -4.0f);
    drawDesk();
    drawMonitors();
    glPopMatrix();
}

//=====================================================      =====================================================//
//=====================================================  2F  =====================================================//
//=====================================================      =====================================================//
void drawElevatorButtons(float baseX, float baseY, float baseZ, float width, float height, float depth) {
    float buttonPanelWidth = 0.2f;  // 버튼 패널의 폭
    float buttonPanelHeight = 0.5f; // 버튼 패널의 높이
    float buttonSize = 0.1f;        // 버튼의 크기
    float buttonSpacing = 0.15f;    // 버튼 간 간격

    // 버튼 패널 (엘리베이터 본체에 붙임, 기준 위치에 맞춤)
    glPushMatrix();
    glColor3f(0.4f, 0.4f, 0.4f); // 버튼 패널 색상
    glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
    glTranslatef(baseX + width / 2.0f + 0.05f, baseY, baseZ - depth / 2.0f + 0.1f); // 패널 위치 (오른쪽 앞쪽 모서리에 붙임)
    glScalef(buttonPanelWidth, buttonPanelHeight, 0.05f); // 패널 크기
    glutSolidCube(1.0f);
    glPopMatrix();

    // 버튼 1 (위쪽 버튼)
    glPushMatrix();
    glColor3f(1.0f, 0.0f, 0.0f); // 버튼 색상 (빨간색)
    glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
    glTranslatef(baseX + width / 2.0f + 0.1f, baseY + buttonSpacing / 2.0f, baseZ - depth / 2.0f + 0.15f);
    glScalef(buttonSize, buttonSize, buttonSize);
    glutSolidSphere(1.0f, 20, 20); // 버튼 모양 (구)
    glPopMatrix();

    // 버튼 2 (아래쪽 버튼)
    glPushMatrix();
    glColor3f(0.0f, 1.0f, 0.0f); // 버튼 색상 (녹색)
    glRotatef(270.0f, 0.0f, 1.0f, 0.0f);
    glTranslatef(baseX + width / 2.0f + 0.1f, baseY - buttonSpacing / 2.0f, baseZ - depth / 2.0f + 0.15f);
    glScalef(buttonSize, buttonSize, buttonSize);
    glutSolidSphere(1.0f, 20, 20); // 버튼 모양 (구)
    glPopMatrix();
}


void drawElevatorBox(float width, float height, float depth) {
    // 엘리베이터 본체
    glPushMatrix();
    glColor3f(0.7f, 0.7f, 0.7f); // 금속 회색
    glScalef(width, height, depth);
    glutSolidCube(1.0);
    glPopMatrix();

    // 프레임
    glPushMatrix();
    glColor3f(0.5f, 0.5f, 0.5f); // 더 어두운 프레임
    glScalef(width + 0.1f, height + 0.1f, depth + 0.05f); // 프레임 크기
    glutWireCube(1.0);
    glPopMatrix();
}

void drawElevatorDoors(float width, float height) {
    float doorWidth = width / 2.0f;

    // 왼쪽 문 (오른쪽으로 배치)
    glPushMatrix();
    glColor3f(0.6f, 0.6f, 0.6f); // 밝은 회색
    glTranslatef(doorWidth / 2.0f, 0.0f, 0.51f); // 기존 오른쪽 문 위치
    glScalef(doorWidth, height, 0.05f); // 문 크기
    glutSolidCube(1.0);
    glPopMatrix();

    // 오른쪽 문 (왼쪽으로 배치)
    glPushMatrix();
    glColor3f(0.6f, 0.6f, 0.6f); // 밝은 회색
    glTranslatef(-doorWidth / 2.0f, 0.0f, 0.51f); // 기존 왼쪽 문 위치
    glScalef(doorWidth, height, 0.05f); // 문 크기
    glutSolidCube(1.0);
    glPopMatrix();

    // 문 옆 경계선 (왼쪽 문 옆)
    glPushMatrix();
    glColor3f(0.4f, 0.4f, 0.4f); // 더 어두운 경계선
    glTranslatef(doorWidth, 0.0f, 0.50f); // 왼쪽 문 옆에 경계선 배치
    glScalef(0.02f, height, 0.05f); // 경계선 크기
    glutSolidCube(1.0);
    glPopMatrix();

    // 문 옆 경계선 (오른쪽 문 옆)
    glPushMatrix();
    glColor3f(0.4f, 0.4f, 0.4f); // 더 어두운 경계선
    glTranslatef(-doorWidth, 0.0f, 0.50f); // 오른쪽 문 옆에 경계선 배치
    glScalef(0.02f, height, 0.05f); // 경계선 크기
    glutSolidCube(1.0);
    glPopMatrix();
}

void drawElevator(float x, float y, float z, float width, float height, float depth) {
    glPushMatrix();
    glTranslatef(x, y, z); // 엘리베이터 위치 조정
    glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
    // 엘리베이터 구성 요소
    drawElevatorBox(width, height, depth);
    drawElevatorDoors(width, height);
    drawElevatorButtons(-4.5f, -0.5f, 5.0f, width, height, depth);

    glPopMatrix();
}

void draw2F() {
    glColor3f(0.765f, 0.608f, 0.467f);
    glPushMatrix();
    glTranslatef(-5.0f, -1.700, 5.0f);
    glScalef(20.0f, 0.05f, 20.0f);
    glutSolidCube(1.0);
    glPopMatrix();

    //계단 주변
    glColor3f(0.765f, 0.608f, 0.467f);
    glPushMatrix();
    glTranslatef(-21.0f, -1.7f, 0.0f);
    glScalef(12.0f, 0.05f, 10.0f);
    glutSolidCube(1.0);
    glPopMatrix();

    glColor3f(0.65f, 0.608f, 0.467f);
    glPushMatrix();//계단옆 운동장쪽 벽
    glTranslatef(-21.0f, -3.8f, 5.0f);
    glScalef(12.0f, 4.3f, 0.05f);
    glutSolidCube(1.0);
    glPopMatrix();

    glColor3f(0.65f, 0.608f, 0.467f);
    glPushMatrix();//계단옆 건물뒤쪽 벽
    glTranslatef(-21.0f, -3.8f, 10.0f);
    glScalef(12.0f, 4.3f, 0.05f);
    glutSolidCube(1.0);
    glPopMatrix();

    glColor3f(0.65f, 0.608f, 0.467f);
    glPushMatrix();//계단숨기는벽
    glTranslatef(-15.0f, -3.8f, 7.5f);
    glScalef(0.4f, 4.3f, 5.0f);
    glutSolidCube(1.0);
    glPopMatrix();

    glColor3f(0.765f, 0.608f, 0.467f);
    glPushMatrix();
    glTranslatef(-31.0f, -1.7f, 5.0f);
    glScalef(8.0f, 0.0f, 20.0f);
    glutSolidCube(1.0);
    glPopMatrix();

    glColor3f(0.765f, 0.608f, 0.467f);
    glPushMatrix();
    glTranslatef(-21.0f, -1.7f, 12.5f);
    glScalef(12.0f, 0.0f, 5.0f);
    glutSolidCube(1.0);
    glPopMatrix();

    //계단 앞 벽
    glColor3f(0.7f, 0.7f, 0.7f);
    glPushMatrix();
    glTranslatef(-27.0f, 0.5f, 7.5f);
    glScalef(0.5f, 4.5f, 5.0f);
    glutSolidCube(1.0);
    glPopMatrix();

    //입구 문 비막이
    glColor3f(0.8f, 0.8f, 0.7f);
    glPushMatrix();
    glTranslatef(-31.0f, -1.8f, -6.0f);
    glScalef(8.0f, 0.5f, 2.0f);
    glutSolidCube(1.0);
    glPopMatrix();

}

void draw2FWalls() {
    glPushMatrix();
    glColor3f(0.8f, 0.8f, 0.8); // 앞벽
    glTranslatef(-15.0f, 0.0f, 10.0f);
    glScalef(40.0f, 3.5f, 0.05f);
    glutSolidCube(1.0);
    glPopMatrix();

    glPushMatrix();
    glColor3f(0.8f, 0.8f, 0.8); //복도끝벽
    glTranslatef(5.0f, -2.0f, 10.0f);
    glScalef(0.0f, 8.0f, 10.0f);
    glutSolidCube(1.0);
    glPopMatrix();
}

void drawStairs() {
    glPushMatrix();
    glColor3f(0.6f, 0.6f, 0.6f); // 계단 색상
    glTranslatef(-22.0f, -4.50f, 7.5f); // 계단 위치 지정
    glRotatef(20.0f, .0f, 0.0f, 1.0f); // 계단을 약간 기울여 경사로처럼 보이게 만듦
    glScalef(15.0f, 0.5f, 5.0f); // 계단 크기 지정 (넓고 길쭉한 직사각형)
    glutSolidCube(1.0);
    glPopMatrix();
}

void drawPillar(float x, float y, float z) {
    glPushMatrix();
    glColor3f(0.0, 0.0, 0.0); // 기둥 색상
    glTranslatef(x, y, z); // 기둥 위치
    glScalef(1.0, 10.0, 1.0); // 기둥 크기
    glutSolidCube(1.0f);
    glPopMatrix();
}
void drawPillarInside(float x, float y, float z) {
    glPushMatrix();
    glColor3f(0.0, 0.0, 0.0); // 기둥 색상
    glTranslatef(x, y, z); // 기둥 위치
    glScalef(0.6, 10.0, 0.6); // 기둥 크기
    glutSolidCube(1.0f);
    glPopMatrix();
}
void drawToiletWall(float x, float y, float z, float width, float height, float depth, float r, float g, float b) {
    glPushMatrix();
    glColor3f(r, g, b);
    glTranslatef(x, y, z);
    glScalef(width, height, depth);
    glutSolidCube(1.0f);
    glPopMatrix();
}
void drawBathroomLayout(float x, float y, float z) {
    glPushMatrix();
    float wallHeight = 3.0f;
    float wallThickness = 0.1f;
    float roomWidth = 10.0f;
    float roomDepth = 5.0f;
    glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
    // 외벽
    drawToiletWall(x - roomWidth / 2.0f, y, z, wallThickness, wallHeight, roomDepth, 0.8f, 0.8f, 0.8f); // 왼쪽 벽
    drawToiletWall(x + roomWidth / 2.0f, y, z, wallThickness, wallHeight, roomDepth, 0.8f, 0.8f, 0.8f); // 오른쪽 벽
    drawToiletWall(x, y, z - roomDepth / 2.0f, roomWidth, wallHeight, wallThickness, 0.8f, 0.8f, 0.8f); // 뒤쪽 벽

    // 남녀 구분 벽
    drawToiletWall(x, y, z, wallThickness, wallHeight, roomDepth, 0.6f, 0.6f, 0.6f); // 중앙 벽

    // 남자 화장실
    drawToiletWall(x - 2.5f, y, z + 10.0 / 4.0f, 5.0f, wallHeight, wallThickness, 0.6f, 0.6f, 0.8f);
    drawToiletWall(x - 3.5f, y, z + 2.6f, 1.0f, wallHeight, wallThickness, 0.9f, 0.9f, 0.9f);

    // 여자 화장실
    drawToiletWall(x + 2.5f, y, z + 10.0 / 4.0f, 5.0f, wallHeight, wallThickness, 0.8f, 0.6f, 0.6f);
    drawToiletWall(x + 3.5f, y, z + 2.6f, 1.0f, wallHeight, wallThickness, 0.9f, 0.9f, 0.9f);


    glPopMatrix();

}

//=====================================================      =====================================================//
//=====================================================  1F  =====================================================//
//=====================================================      =====================================================//

void drawGlassDoorLeft(float posX, float posY, float posZ, float width, float height, float frameThickness) {
    glEnable(GL_BLEND); // 반투명 효과 활성화
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    // 문틀 색상 (어두운 회색)
    glColor3f(0.4f, 0.4f, 0.4f);

    // 왼쪽 문틀
    glPushMatrix();
    glTranslatef(posX - width / 2.0f, posY, posZ);
    glScalef(frameThickness, height, frameThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 오른쪽 문틀
    glPushMatrix();
    glTranslatef(posX + width / 2.0f, posY, posZ);
    glScalef(frameThickness, height, frameThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 상단 문틀
    glPushMatrix();
    glTranslatef(posX, posY + height / 2.0f, posZ);
    glScalef(width, frameThickness, frameThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 하단 문틀
    glPushMatrix();
    glTranslatef(posX, posY - height / 2.0f, posZ);
    glScalef(width, frameThickness, frameThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 유리 부분
    glDisable(GL_CULL_FACE); // 양면 렌더링 활성화
    glDepthMask(GL_FALSE);  // 깊이 버퍼 쓰기 비활성화

    glColor4f(0.6f, 0.8f, 1.0f, 0.5f); // 유리 색상 설정 (투명)

    glPushMatrix();
    glTranslatef(posX, posY, posZ);
    glScalef(width - frameThickness * 2, height - frameThickness * 2, frameThickness * 0.5f); // 유리 크기
    glutSolidCube(1.0f);
    glPopMatrix();

    glDepthMask(GL_TRUE); // 깊이 버퍼 쓰기 활성화
    glEnable(GL_CULL_FACE); // 기본 설정으로 복구
    glDisable(GL_BLEND); // 반투명 효과 비활성화

    glColor3f(0.5f, 0.5f, 0.5f); // 손잡이 색상 (회색)
    GLUquadric* quad = gluNewQuadric(); // 손잡이용 원기둥 생성

    // 문 앞쪽 손잡이
    glPushMatrix();
    glTranslatef(posX + 2.0 - 4 + width / 4.0f, posY, posZ + frameThickness * 2.0f); // 손잡이 위치 (x-4 적용)
    glRotatef(90.0f, 1.0f, 0.0f, 0.0f); // 원기둥 회전 (세로 방향)
    gluCylinder(quad, 0.05f, 0.05f, 0.3f, 20, 20); // 손잡이 크기 설정
    glPopMatrix();

    // 문 뒤쪽 손잡이
    glPushMatrix();
    glTranslatef(posX + 2.0 - 4 + width / 4.0f, posY, posZ - frameThickness * 2.0f); // 반대편 손잡이 위치 (x-4 적용)
    glRotatef(90.0f, 1.0f, 0.0f, 0.0f); // 원기둥 회전 (세로 방향)
    gluCylinder(quad, 0.05f, 0.05f, 0.3f, 20, 20); // 손잡이 크기 설정
    glPopMatrix();

    gluDeleteQuadric(quad);

}
void drawGlassDoorRight(float posX, float posY, float posZ, float width, float height, float frameThickness) {
    glEnable(GL_BLEND); // 반투명 효과 활성화
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    // 문틀 색상 (어두운 회색)
    glColor3f(0.4f, 0.4f, 0.4f);

    // 왼쪽 문틀
    glPushMatrix();
    glTranslatef(posX - width / 2.0f, posY, posZ);
    glScalef(frameThickness, height, frameThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 오른쪽 문틀
    glPushMatrix();
    glTranslatef(posX + width / 2.0f, posY, posZ);
    glScalef(frameThickness, height, frameThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 상단 문틀
    glPushMatrix();
    glTranslatef(posX, posY + height / 2.0f, posZ);
    glScalef(width, frameThickness, frameThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 하단 문틀
    glPushMatrix();
    glTranslatef(posX, posY - height / 2.0f, posZ);
    glScalef(width, frameThickness, frameThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 유리 부분
    glDisable(GL_CULL_FACE); // 양면 렌더링 활성화
    glDepthMask(GL_FALSE);  // 깊이 버퍼 쓰기 비활성화

    glColor4f(0.6f, 0.8f, 1.0f, 0.5f); // 유리 색상 설정 (투명)

    glPushMatrix();
    glTranslatef(posX, posY, posZ);
    glScalef(width - frameThickness * 2, height - frameThickness * 2, frameThickness * 0.5f); // 유리 크기
    glutSolidCube(1.0f);
    glPopMatrix();

    glDepthMask(GL_TRUE); // 깊이 버퍼 쓰기 활성화
    glEnable(GL_CULL_FACE); // 기본 설정으로 복구
    glDisable(GL_BLEND); // 반투명 효과 비활성화

    glColor3f(0.5f, 0.5f, 0.5f); // 손잡이 색상 (회색)
    GLUquadric* quad = gluNewQuadric(); // 손잡이용 원기둥 생성

    // 문 앞쪽 손잡이
    glPushMatrix();
    glTranslatef(posX + width / 4.0f, posY, posZ + frameThickness * 2.0f); // 손잡이 위치
    glRotatef(90.0f, 1.0f, 0.0f, 0.0f); // 원기둥 회전 (세로 방향)
    gluCylinder(quad, 0.05f, 0.05f, 0.3f, 20, 20); // 손잡이 크기 설정
    glPopMatrix();

    // 문 뒤쪽 손잡이
    glPushMatrix();
    glTranslatef(posX + width / 4.0f, posY, posZ - frameThickness * 2.0f); // 반대편 손잡이 위치
    glRotatef(90.0f, 1.0f, 0.0f, 0.0f); // 원기둥 회전 (세로 방향)
    gluCylinder(quad, 0.05f, 0.05f, 0.3f, 20, 20); // 손잡이 크기 설정
    glPopMatrix();

    gluDeleteQuadric(quad);
}

void draw1FWalls() {
    // 왼쪽 벽
    glPushMatrix();
    glColor3f(0.7f, 0.7f, 0.7f); // 벽 색상
    glTranslatef(-27.0f, -1.0f, 0.0f); // 왼쪽 벽 위치 지정
    glScalef(0.5f, 10.0f, 10.0f); // 벽 크기 지정 (얇고 긴 벽)
    glutSolidCube(1.0);
    glPopMatrix();

    // 오른쪽 벽
    glPushMatrix();
    glColor3f(0.8f, 0.8f, 0.8f); // 벽 색상
    glTranslatef(-35.0f, -1.0f, 5.0f); // 오른쪽 벽 위치 지정
    glScalef(0.5f, 10.0f, 20.0f); // 벽 크기 지정 (얇고 긴 벽)
    glutSolidCube(1.0);
    glPopMatrix();


    //건물 큰뒤쪽벽
    glPushMatrix();
    glColor3f(0.8f, 0.8f, 0.8f); // 벽 색상
    glTranslatef(-15.0f, -1.0f, 15.0f); // 오른쪽 벽 위치 지정
    glScalef(40.0f, 10.0f, 0.5f); // 벽 크기 지정 (얇고 긴 벽)
    glutSolidCube(1.0);
    glPopMatrix();

    //건물 앞벽
    glPushMatrix();
    glColor3f(0.8f, 0.8f, 0.8f); // 벽 색상
    glTranslatef(-16.0f, -1.0f, -5.0f); // 오른쪽 벽 위치 지정
    glScalef(22.0f, 10.0f, 0.5f); // 벽 크기 지정 (얇고 긴 벽)
    glutSolidCube(1.0);
    glPopMatrix();
}

void drawChairs1F() {
    // 의자 색상 설정 (파란색)
    for (float x = -1.0f; x <= 1.0f; x += 1.0f) {
        // 의자 시트 부분
        glPushMatrix();
        glColor3f(0.0f, 0.4f, 0.8f);
        glTranslatef(x, -0.3f, -0.5f); // 의자 위치 조정 (책상 가까이 배치)
        glScalef(0.4f, 0.05f, 0.4f); // 시트 크기 조정
        glutSolidCube(1.0);
        glPopMatrix();

        // 의자 등받이 부분
        glPushMatrix();
        glColor3f(0.0f, 0.4f, 0.8f);
        glTranslatef(x, -0.1f, -0.68f); // 등받이 위치 조정
        glScalef(0.4f, 0.4f, 0.05f); // 등받이 크기 조정
        glutSolidCube(1.0);
        glPopMatrix();

        // 의자 다리
        glColor3f(0.5f, 0.5f, 0.5f); // 다리 색상 (회색)
        for (float dx = -0.15f; dx <= 0.15f; dx += 0.3f) {
            for (float dz = -0.15f; dz <= 0.15f; dz += 0.3f) {
                glPushMatrix();
                glTranslatef(x + dx, -0.55f, -0.5f + dz); // 다리 위치 조정 (시트에 붙도록)
                glScalef(0.05f, 0.5f, 0.05f); // 다리 크기 조정
                glutSolidCube(1.0);
                glPopMatrix();
            }
        }


    }
}

void drawClassroomSetupWithChairs1F() {
    // 원래의 중앙 책상, 모니터, 의자 (사람 포함)
    drawDesk();
    drawMonitors();
    drawChairs1F(); // 중앙에는 사람 앉히기

    // 오른쪽 책상과 모니터 (사람 없음)
    glPushMatrix();
    glTranslatef(2.8f, 0.0f, 0.0f);
    drawDesk();
    drawMonitors();
    glPopMatrix();

    // 왼쪽 책상과 모니터 (사람 없음)
    glPushMatrix();
    glTranslatef(-2.8f, 0.0f, 0.0f);
    drawDesk();
    drawMonitors();
    glPopMatrix();

    // 오른쪽 뒤 책상과 모니터 (사람 없음)
    glPushMatrix();
    glTranslatef(2.0f, 0.0f, -2.2f);
    drawDesk();
    drawMonitors();
    glPopMatrix();

    // 왼쪽 뒤 책상과 모니터 (사람 없음)
    glPushMatrix();
    glTranslatef(-2.0f, 0.0f, -2.2f);
    drawDesk();
    drawMonitors();
    glPopMatrix();

    // 뒤쪽 책상과 모니터 (사람 없음)
    glPushMatrix();
    glTranslatef(0.0f, 0.0f, -4.0f);
    drawDesk();
    drawMonitors();
    glPopMatrix();

    // 대각선 뒤 왼쪽 책상과 모니터 (사람 없음)
    glPushMatrix();
    glTranslatef(-2.0f, 0.0f, -4.0f);
    drawDesk();
    drawMonitors();
    glPopMatrix();

    // 대각선 뒤 오른쪽 책상과 모니터 (사람 없음)
    glPushMatrix();
    glTranslatef(2.0f, 0.0f, -4.0f);
    drawDesk();
    drawMonitors();
    glPopMatrix();
}

void drawClassroom1F(float x, float y, float z) {

    glPushMatrix();
    glTranslatef(x, y, z);  // 교실 위치 조정

    // 교실 내부 요소 그리기
    drawFloor();
    drawWalls();
    drawDoor(-4.0f, 4.95f, true);  // 왼쪽 문 열림
    drawDoor(4.0f, 4.95f);         // 오른쪽 문 닫힘
    drawBlackboard();
    drawProjector();
    drawDesk();
    drawMonitors();
    drawChairs1F();
    drawPodium();
    drawClassroomSetupWithChairs1F();

    glPopMatrix();

}

void drawGround() {
    glPushMatrix();
    glColor3f(0.765f, 0.608f, 0.467f);
    glTranslatef(-5.0f, -6.0f, 5.0f);
    glScalef(20.0f, 0.05f, 20.0f);
    glutSolidCube(1.0);
    glPopMatrix();

    glPushMatrix();
    glColor3f(0.765f, 0.608f, 0.467f);
    glTranslatef(-25.0f, -6.0f, 5.0f);
    glScalef(20.0f, 0.05f, 20.0f);
    glutSolidCube(1.0);
    glPopMatrix();

    
    //건물 외벽뚜껑
    glColor3f(1.0f, 1.0f, 0.941f);
    glPushMatrix();
    glTranslatef(-15.0f, 2.0f, 5.0f);
    glScalef(40.0f, 0.5f, 20.0f);
    glutSolidCube(1.0);
    glPopMatrix();
     

    glPushMatrix();
    glColor3f(0.34f, 0.41f, 0.11f);
    glTranslatef(-15.0f, -6.0f, -7.0f);
    glScalef(40.0f, 0.05f, 4.0f);
    glutSolidCube(1.0);
    glPopMatrix();

    glPushMatrix();
    glColor3f(0.34f, 0.41f, 0.11f);
    glTranslatef(-40.0f, -6.0f, 3.0f);
    glScalef(10.0f, 0.05f, 24.0f);
    glutSolidCube(1.0);
    glPopMatrix();

}

void draw1Foutside() {
    glPushMatrix();
    glColor3f(0.34f, 0.41f, 0.11f);;   // (초록색)
    glTranslatef(-20.00f, -6.00f, -19.00f);
    glScalef(50.0f, 0.05f, 20.0f);
    glutSolidCube(1.0);
    glPopMatrix();

    // 운동장 경계선 (트랙) 색상 (초록색)
    glPushMatrix();
    glColor3f(0.2f, 0.8f, 0.2f);
    glTranslatef(-18.0f, -5.92f, -18.9f);
    glScalef(-30.00f, 0.1f, 10.00f);
    glutSolidCube(1.0);
    glPopMatrix();
}




//=====================================================            ===============================================//
//=====================================================   운동장   ===============================================//
//=====================================================            ===============================================//


// 초기화 함수
void init() {
    glEnable(GL_DEPTH_TEST); // 깊이 테스트 활성화
    glClearColor(0.3f, 0.7f, 0.2f, 1.0f);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45.0, 1.0, 1.0, 200.0); // 원근 투영 설정
    std::srand(std::time(0)); // 랜덤 초기화
}

// 운동장 흰 선을 점으로 그리기
void drawFieldLines() {
    glColor3f(1.0f, 1.0f, 1.0f); // 흰색

    // 중앙 서클의 중심 좌표
    float centerX = -18.5f;
    float centerY = -5.9f; // 초록색 경계 위에 맞춤
    float centerZ = -18.9f;

    // 중앙선 (중앙 서클 중심을 기준으로 좌우로 그리기)
    glLineWidth(5.0f);
    glBegin(GL_LINES);
    glVertex3f(centerX, centerY, centerZ + 5.3f); // 왼쪽 끝
    glVertex3f(centerX, centerY, centerZ - 5.3f); // 오른쪽 끝
    glEnd();

    // 중앙 서클 (Y축 90도 회전)
    glPushMatrix();
    glTranslatef(centerX, centerY, centerZ); // 중앙 서클의 중심 위치
    glBegin(GL_LINE_LOOP);
    for (int i = 0; i <= 360; i += 10) {
        float angle = i * 3.14159f / 180.0f;
        glVertex3f(2.0f * cos(angle), 0.0f, 2.0f * sin(angle)); // 반지름 2
    }
    glEnd();
    glPopMatrix();
}

void drawTrack(float x, float y, float z, float rotationAngle) {
    glPushMatrix();
    // 트랙의 중심 위치 설정
    glTranslatef(x, y, z);
    // 트랙 회전 적용 (Z축 기준 회전)
    glRotatef(rotationAngle, 0.0f, 1.0f, 0.0f);

    glColor3f(0.8f, 0.1f, 0.1f); // 트랙 색상 (빨간색)

    int numLanes = 4;          // 트랙 레인 수
    float laneWidth = 1.0f;    // 각 레인의 폭
    float trackLength = 25.0f; // 트랙의 길이
    float trackWidth = 20.0f;  // 트랙의 너비
    float laneHeight = 0.10f;   // 모든 레인의 높이 고정
    float Height = 0.15f;
    float gap = 0.2f;          // 레인 사이 간격

    for (int i = 0; i < numLanes; i++) {
        float innerRadius = (trackWidth / 2.0f) - (i * (laneWidth + gap)); // 내부 반지름
        float outerRadius = innerRadius - laneWidth;                      // 외부 반지름

        // 트랙의 윗부분 (반원)
        glPushMatrix();
        glBegin(GL_QUAD_STRIP);
        for (int j = 0; j <= 180; j += 10) { // 0도에서 180도까지
            float angle = j * 3.14159f / 180.0f;
            glVertex3f(innerRadius * cos(angle), laneHeight, trackLength / 2.0f + innerRadius * sin(angle));
            glVertex3f(outerRadius * cos(angle), laneHeight, trackLength / 2.0f + outerRadius * sin(angle));
        }
        glEnd();
        glPopMatrix();

        // 트랙의 아랫부분 (반원)
        glPushMatrix();
        glBegin(GL_QUAD_STRIP);
        for (int j = 180; j <= 360; j += 10) { // 180도에서 360도까지
            float angle = j * 3.14159f / 180.0f;
            glVertex3f(innerRadius * cos(angle), laneHeight, -trackLength / 2.0f + innerRadius * sin(angle));
            glVertex3f(outerRadius * cos(angle), laneHeight, -trackLength / 2.0f + outerRadius * sin(angle));
        }
        glEnd();
        glPopMatrix();

        // 트랙의 직선 부분 (좌우)
        glPushMatrix();
        glBegin(GL_QUADS);

        // 왼쪽 직선
        glVertex3f(-innerRadius, laneHeight, -trackLength / 2.0f);
        glVertex3f(-innerRadius, laneHeight, trackLength / 2.0f);
        glVertex3f(-outerRadius, laneHeight, trackLength / 2.0f);
        glVertex3f(-outerRadius, laneHeight, -trackLength / 2.0f);


        // 오른쪽 직선
        glVertex3f(innerRadius, laneHeight, -trackLength / 2.0f);
        glVertex3f(outerRadius, laneHeight, -trackLength / 2.0f);
        glVertex3f(outerRadius, laneHeight, trackLength / 2.0f);
        glVertex3f(innerRadius, laneHeight, trackLength / 2.0f);

        glEnd();
        glPopMatrix();
    }

    // 결승선 추가
    glColor3f(1.0f, 1.0f, 1.0f); // 흰색
    glPushMatrix();
    glLineWidth(10.0f);
    glBegin(GL_LINES);

    // 결승선을 트랙의 길이와 너비에 맞게 설정
    for (int i = 0; i < numLanes; i++) {
        float innerRadius = (trackWidth / 2.0f) - (i * (laneWidth + gap)); // 내부 반지름
        float outerRadius = innerRadius - laneWidth;                      // 외부 반지름

        // 결승선의 시작과 끝 점을 트랙 영역 내에서 설정
        glVertex3f(-innerRadius, Height, trackLength / 2.0f);
        glVertex3f(-outerRadius, Height, trackLength / 2.0f);

        glVertex3f(innerRadius, Height, trackLength / 2.0f);
        glVertex3f(outerRadius, Height, trackLength / 2.0f);
    }

    glEnd();
    glPopMatrix();


    glPopMatrix(); // 트랙 변환 및 회전 복원
}

void drawGoal(float xPosition, float zPosition, float rotationAngle) {
    glPushMatrix();
    glTranslatef(xPosition, -5.95f, zPosition); // 골대 위치
    glRotatef(rotationAngle, 0.0f, 1.0f, 0.0f); // Y축 기준 회전

    // 골대 기둥 (4개)
    glColor3f(0.5f, 0.5f, 0.5f); // 중간 회색 (더 어두운 색상)

    float poleHeight = 2.0f;
    float poleThickness = 0.1f;

    // 앞쪽 왼쪽 기둥
    glPushMatrix();
    glTranslatef(-2.0f, poleHeight / 2.0f, 0.0f);
    glScalef(poleThickness, poleHeight, poleThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 앞쪽 오른쪽 기둥
    glPushMatrix();
    glTranslatef(2.0f, poleHeight / 2.0f, 0.0f);
    glScalef(poleThickness, poleHeight, poleThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 뒤쪽 왼쪽 기둥
    glPushMatrix();
    glTranslatef(-2.0f, poleHeight / 2.0f, -2.0f);
    glScalef(poleThickness, poleHeight, poleThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 뒤쪽 오른쪽 기둥
    glPushMatrix();
    glTranslatef(2.0f, poleHeight / 2.0f, -2.0f);
    glScalef(poleThickness, poleHeight, poleThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 상단 가로 막대 (앞쪽)
    glPushMatrix();
    glTranslatef(0.0f, poleHeight, 0.0f);
    glScalef(4.0f, poleThickness, poleThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 상단 가로 막대 (뒤쪽)
    glPushMatrix();
    glTranslatef(0.0f, poleHeight, -2.0f);
    glScalef(4.0f, poleThickness, poleThickness);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 상단 세로 막대 (왼쪽)
    glPushMatrix();
    glTranslatef(-2.0f, poleHeight, -1.0f);
    glScalef(poleThickness, poleThickness, 2.0f);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 상단 세로 막대 (오른쪽)
    glPushMatrix();
    glTranslatef(2.0f, poleHeight, -1.0f);
    glScalef(poleThickness, poleThickness, 2.0f);
    glutSolidCube(1.0f);
    glPopMatrix();

    // 그물 (뒤쪽)
    glColor3f(0.7f, 0.7f, 0.7f); // 약간 어두운 회색
    for (float x = -1.8f; x <= 1.8f; x += 0.6f) {
        glPushMatrix();
        glTranslatef(x, 1.0f, -2.0f); // 세로 줄 위치
        glScalef(0.05f, 2.0f, 0.05f);
        glutSolidCube(1.0f);
        glPopMatrix();
    }
    for (float y = 0.0f; y <= 2.0f; y += 0.5f) {
        glPushMatrix();
        glTranslatef(0.0f, y, -2.0f); // 가로 줄 위치
        glScalef(4.0f, 0.05f, 0.05f);
        glutSolidCube(1.0f);
        glPopMatrix();
    }

    // 그물 (왼쪽)
    for (float z = -0.2f; z >= -2.0f; z -= 0.4f) {
        glPushMatrix();
        glTranslatef(-2.0f, 1.0f, z); // 세로 줄 위치
        glScalef(0.05f, 2.0f, 0.05f);
        glutSolidCube(1.0f);
        glPopMatrix();
    }
    for (float y = 0.0f; y <= 2.0f; y += 0.5f) {
        glPushMatrix();
        glTranslatef(-2.0f, y, -1.0f); // 가로 줄 위치
        glScalef(0.05f, 0.05f, 2.0f);
        glutSolidCube(1.0f);
        glPopMatrix();
    }

    // 그물 (오른쪽)
    for (float z = -0.2f; z >= -2.0f; z -= 0.4f) {
        glPushMatrix();
        glTranslatef(2.0f, 1.0f, z); // 세로 줄 위치
        glScalef(0.05f, 2.0f, 0.05f);
        glutSolidCube(1.0f);
        glPopMatrix();
    }
    for (float y = 0.0f; y <= 2.0f; y += 0.5f) {
        glPushMatrix();
        glTranslatef(2.0f, y, -1.0f); // 가로 줄 위치
        glScalef(0.05f, 0.05f, 2.0f);
        glutSolidCube(1.0f);
        glPopMatrix();
    }

    // 그물 (위쪽)
    for (float x = -1.8f; x <= 1.8f; x += 0.6f) {
        glPushMatrix();
        glTranslatef(x, 2.0f, -1.0f); // 세로 줄 위치
        glScalef(0.05f, 0.05f, 2.0f);
        glutSolidCube(1.0f);
        glPopMatrix();
    }
    for (float z = -0.2f; z >= -2.0f; z -= 0.4f) {
        glPushMatrix();
        glTranslatef(0.0f, 2.0f, z); // 가로 줄 위치
        glScalef(4.0f, 0.05f, 0.05f);
        glutSolidCube(1.0f);
        glPopMatrix();
    }

    glPopMatrix(); // 변환 복원
}

void drawStands(float xPosition, float yPosition, float zPosition, bool rotate, float rotationAngle, int rows, int blocksPerRow, float heights[], float depths[], float blockWidths[], float blockHeights[], float blockDepths[]) {
    glPushMatrix();
    // 관중석 위치 설정
    glTranslatef(xPosition, yPosition, zPosition);

    // 관중석 회전 여부 확인
    if (rotate) {
        glRotatef(rotationAngle, 0.0f, 1.0f, 0.0f); // Y축 기준으로 회전
    }

    // 관중석 블록 및 의자 생성
    float chairOffset = 0.25f; // 의자 블록에서의 높이 보정값
    int halfBlocks = blocksPerRow / 2; // 관중석의 절반 블록 수

    for (int row = 0; row < rows; row++) {
        float height = heights[row];  // 각 층의 높이
        float depth = depths[row] - (row * 0.5f); // 각 층의 깊이 (기울기 조정)
        float blockWidth = blockWidths[row];   // 각 층의 블록 너비
        float blockHeight = blockHeights[row]; // 각 층의 블록 높이
        float blockDepth = blockDepths[row];   // 각 층의 블록 깊이

        for (int block = 0; block < blocksPerRow; block++) {
            // 좌우 대칭 위치 계산
            float offset = (block < halfBlocks) ? -1 : 1; // 왼쪽(-1) 또는 오른쪽(1)
            float blockX = offset * (7.5f - (block % halfBlocks) * blockWidth - row * 0.3f);

            // 블록 그리기
            glPushMatrix();
            glColor3f(0.3f, 0.3f, 0.3f); // 관중석 블록 색상 (진한 회색)
            glTranslatef(blockX + blockWidth / 2.0f, height / 2.0f, depth);
            glScalef(blockWidth, blockHeight, blockDepth);
            glutSolidCube(1.0f);
            glPopMatrix();

            // 의자 추가 (관중석에 붙이기)
            float chairX = blockX + blockWidth / 2.0f;
            glPushMatrix();
            glColor3f(0.6f, 0.6f, 0.6f); // 의자 색상 (중간 회색)
            glTranslatef(chairX, height + chairOffset, depth); // 의자 위치 (관중석과 동일한 Z축)
            glScalef(0.8f, 0.2f, 1.0f); // 의자 크기
            glutSolidCube(1.0f); // 의자 좌석
            glPopMatrix();

            // 등받이 추가
            glPushMatrix();
            glColor3f(0.5f, 0.5f, 0.5f); // 등받이 색상 (어두운 회색)
            glTranslatef(chairX, height + chairOffset + 0.15f, depth - 0.5f); // 등받이 위치
            glScalef(0.8f, 0.6f, 0.1f); // 등받이 크기
            glutSolidCube(1.0f); // 의자 등받이
            glPopMatrix();
        }
    }
    glPopMatrix();
}

// 여기에 쓰기

//=====================================================            ===============================================//
//=====================================================  메인메뉴  ===============================================//
//=====================================================            ===============================================//



// 텍스트를 2D 화면에 출력하는 함수 (폰트 추가)
void drawText(const std::string& text, float x, float y, void* font) {
    glRasterPos2f(x, y);
    for (char c : text) {
        glutBitmapCharacter(font, c);
    }
}

// 2D 프로젝션 설정
void setupOrtho() {
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluOrtho2D(0.0, 800.0, 0.0, 600.0);  // 화면 좌표계 설정 (윈도우 크기 기준)
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
}

// 디스플레이 콜백 함수
void display() {
    if (cameraPaths.empty()) {
        std::cerr << "카메라 경로가 없습니다. 디스플레이를 중단합니다." << std::endl;
        return;
    }

    glClearColor(0.53f, 0.81f, 0.98f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glLoadIdentity();

    recordCameraPath(); // 저장 할 때는

    // 지진 효과 적용
    applyEarthquakeEffect(); // 카메라 떨림 효과 적용

    // 카메라 위치 출력
    std::ostringstream oss;
    oss << "Camera Position: (" << cameraX << ", " << cameraY << ", " << cameraZ << ")";
    std::string cameraPositionText = oss.str();

    // 2D 텍스트를 3D 공간에 출력
    glPushMatrix();  // 현재 변환 상태 저장

    // 2D 텍스트를 그리기 위한 설정
    glMatrixMode(GL_PROJECTION);
    glPushMatrix();  // 프로젝션 매트릭스 저장
    glLoadIdentity();
    gluOrtho2D(0, 800, 0, 600);  // 2D 좌표 시스템 설정

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();  // 모델뷰 행렬 초기화

    // 텍스트 출력
    drawText(cameraPositionText, 10, 580, GLUT_BITMAP_HELVETICA_18);

    glMatrixMode(GL_PROJECTION);
    glPopMatrix();  // 프로젝션 매트릭스 복원

    glMatrixMode(GL_MODELVIEW);
    glPopMatrix();  // 모델뷰 행렬 복원


    // 2번 경로 (1인칭 시점) 처리
    if (startButtonPressed) {
        // 지진 끝난 후 또는 시작 버튼을 누른 경우 1인칭 시점 시작
        isFirstPersonActive = true; // 1인칭 시점 활성화
    }

    if (isFirstPersonActive) {  // 1인칭 시점 활성화 후 카메라 이동
        if (currentPathIndex == 3) {  // 2번 시점 = 1, 관리자 시점 = 3
            if (frameIndices[currentPathIndex] < cameraPaths[currentPathIndex].size()) {
                followCameraPath(cameraPaths[currentPathIndex]);

                // 사람 모델 위치 (여기서 머리 위치 기준으로 카메라 이동)
                float headX = cameraX;  // 머리 X 위치
                float headY = cameraY + 0.8f;  // 머리 Y 위치 (몸통과의 차이)
                float headZ = cameraZ;  // 머리 Z 위치

                // 카메라가 머리 위치에서 약간 아래로 설정
                float cameraHeightOffset = -0.2f;  // 카메라가 머리 아래로 내려갈 거리 (1인칭 느낌을 위해)

                // 머리에서 약간 아래로 내려가서 카메라 위치 설정
                gluLookAt(headX, headY + cameraHeightOffset, headZ,  // 카메라 위치
                    headX + lookX, headY + lookY, headZ + lookZ,  // 바라보는 방향
                    0.0f, 1.0f, 0.0f);  // 상향 방향
            }
            else {
                // 2번 경로를 끝까지 따라갔으면, 카메라를 멈추고 사람만 그리도록
                currentPathIndex = -1;  // 경로 끝나면 멈추고 더 이상 이동하지 않도록
            }
        }
    }
    else {
        // 다른 경로에서는 일반 카메라 위치만 따라가고, 사람만 모델링
        gluLookAt(cameraX, cameraY, cameraZ,
            cameraX + lookX, cameraY + lookY, cameraZ + lookZ,
            0.0f, 1.0f, 0.0f);
    }


    // 교실 내부 요소 그리기
    glPushMatrix();
    glTranslatef(-5.0f, 0.7f, -5.0f);  // 장면을 새 중심으로 이동
    drawFloor();
    drawWalls();

    drawDoor(-4.0f, 4.95f, true);  // 왼쪽 문 열림
    drawDoor(4.0f, 4.95f);         // 오른쪽 문 닫힘
    drawBlackboard();
    drawProjector();
    drawDesk();
    drawMonitors();
    drawChairs();
    drawPodium();
    draw1FWalls();
    drawStairs();
    drawGround();
    drawClassroomSetupWithChairs();
    drawElevator(-7.75, 0.75f, -2.5f, 5.0f, 5.0f, 5.0f);
    drawElevator(-7.75, -4.2f, -2.5f, 5.0f, 5.0f, 5.0f);
    draw2F();
    draw2FWalls();
    drawClassroom1F(0.0, -4.3, 0.0);
    drawGlassWindow(-30.5f, 0.5f, -5.0f, 9.0f, 5.0f);
    drawGlassDoorLeft(-29.0f, -4.0f, -5.0f, 4.0f, 4.0f, 0.1f);
    drawGlassDoorRight(-33.0f, -4.0f, -5.0f, 4.0f, 4.0f, 0.1f);
    drawClassRoomGlassWindow(0.0f, 0.9f, -5.0f, 10.0f, 1.75f, 0.1f);
    // OUTSIDE
    // OUTSIDE
    drawPillar(5.0, -1.0, 15.0);
    drawPillar(-35.0, -1.0, 15.0);
    drawPillarInside(-27.0, -1.0, 9.9);
    drawPillarInside(-27.0, -1.0, 4.9);
    drawPillarInside(5.0, -1.0, 10.0);
    drawBathroomLayout(0.0f, -0.3f, -24.0f);
    drawBathroomLayout(0.0f, -4.5f, -24.0f);
    draw1Foutside();


    //여기 부터 쓰기
    drawTrack(-18.0f, -6.04f, -18.9f, 90.0f);
    drawFieldLines();
    drawGoal(-33.0f, -19.0f, 90.0f); // 왼쪽 골대
    drawGoal(-3.0f, -19.0f, -90.0f);  // 오른쪽 골대

    // 관중석 설정
    int rows = 6; // 층 수
    int blocksPerRow = 30; // 한 층당 블록 수

    // 층별 설정 값
    float heights[6] = { 1.0f, 1.5f, 2.0f, 2.5f, 2.7f,2.9f };  // 층별 높이
    float depths[6] = { -5.0f, -6.5f, -8.0f, -9.5f, -11.0f,-12.5f }; // 층별 깊이
    float blockWidths[6] = { 1.5f, 1.6f, 1.7f, 1.8f, 1.9f ,2.0f };  // 층별 블록 너비
    float blockHeights[6] = { 1.0f, 1.5f, 2.0f, 2.5f, 3.0f ,3.5f }; // 층별 블록 높이
    float blockDepths[6] = { 1.5f, 1.6f, 1.7f, 1.8f, 1.9f,2.0f };  // 층별 블록 깊이

    // 정면 관중석 그리기
    drawStands(-21.0f, -6.0f, -24.7f, false, 0.0f, rows, 42, heights, depths, blockWidths, blockHeights, blockDepths);

    // 왼쪽 관중석: 회전 활성화
    drawStands(-40.0f, -6.0f, -15.6f, true, 90.0f, rows, blocksPerRow, heights, depths, blockWidths, blockHeights, blockDepths);

    //오른쪽 관중석: 회전 활성화
    drawStands(0.0f, -6.0f, -20.0f, true, -90.0f, rows, blocksPerRow, heights, depths, blockWidths, blockHeights, blockDepths);



      
    ///// 화살표  ///// 
    ///// 화살표  ///// 

    ///// 2F교실 문 앞에
    glPushMatrix();
    glTranslatef(-4.0f, -0.6f, 4.0f); // 중심 이동
    glRotatef(-90, 0.0f, 1.0f, 0.0f); // y축 기준 회전
    glRotatef(-90, 1.0f, 0.0f, 0.0f); // x축 기준 회전
    drawArrow2D(0.0f, 0.0f, 1.0f, 0.3f);
    glPopMatrix();
    /////

    ///// 2F교실 밖
    glPushMatrix();
    glTranslatef(-4.0f, 0.10f, 9.9f); // 중심 이동
    glRotatef(-180.0f, 0.0f, 1.0f, 0.0f); // y축 기준 회전
    glRotatef(0.0f, 1.0f, 0.0f, 0.0f); // x축 기준 회전
    drawArrow2D(0.0f, 0.0f, 1.0f, 0.3f);
    glPopMatrix();
    /////

    ///// 2F 계단 시작부분
    glPushMatrix();
    glTranslatef(-13.0f, 0.10f, 9.9f); // 중심 이동
    glRotatef(-180.0f, 0.0f, 1.0f, 0.0f); // y축 기준 회전
    glRotatef(0.0f, 1.0f, 0.0f, 0.0f); // x축 기준 회전
    drawArrow2D(0.0f, 0.0f, 1.0f, 0.3f);
    glPopMatrix();

    glPushMatrix();
    glTranslatef(-25.0f, -3.3f, 9.9f); // 중심 이동
    glRotatef(-180.0f, 0.0f, 1.0f, 0.0f); // y축 기준 회전
    glRotatef(-17.0f, 0.0f, 0.0f, 1.0f); // z축 기준 회전
    drawArrow2D(0.0f, 0.0f, 1.0f, 0.3f);
    glPopMatrix();

    glPushMatrix();
    glTranslatef(-25.0f, -3.3f, 5.1f); // 중심 이동
    glRotatef(0.0f, 0.0f, 1.0f, 0.0f); // y축 기준 회전
    glRotatef(-160.0f, 0.0f, 0.0f, 1.0f); // z축 기준 회전
    drawArrow2D(0.0f, 0.0f, 1.0f, 0.3f);
    glPopMatrix();
    /////


    ///// 1F 복도
    glPushMatrix();
    glTranslatef(-34.7f, -4.3f, 7.6f); // 중심 이동
    glRotatef(-270.0f, 0.0f, 1.0f, 0.0f); // y축 기준 회전
    glRotatef(0.0f, 0.0f, 0.0f, 1.0f); // x축 기준 회전
    drawArrow2D(0.0f, 0.0f, 1.0f, 0.3f);
    glPopMatrix();

    glPushMatrix();
    glTranslatef(-34.7f, -4.3f, 0.9f); // 중심 이동
    glRotatef(-270.0f, 0.0f, 1.0f, 0.0f); // y축 기준 회전
    glRotatef(0.0f, 0.0f, 0.0f, 1.0f); // x축 기준 회전
    drawArrow2D(0.0f, 0.0f, 1.0f, 0.3f);
    glPopMatrix();
    /////



    // 엘레베이터 앞
    glPushMatrix();
    glTranslatef(-7.7f, 0.10f, 0.9f); // 중심 이동
    glRotatef(0.0f, 0.0f, 1.0f, 0.0f); // y축 기준 회전
    drawBoldRedX(0.0f, 0.0f, 2.0f, 14.0f); // 중심 (0, 0), 크기 2.0, 두께 5.0
    glPopMatrix();


    glPopMatrix();

    // 경로별 사람 그리기 (현재 보고 있는 사람을 제외)
    for (size_t i = 0; i < cameraPaths.size(); ++i) {
        if (i != currentPathIndex && currentPathIndex != -1) {  // 현재 보고 있는 사람을 제외하고 그리기
            if (frameIndices[i] < cameraPaths[i].size()) {
                const auto& state = cameraPaths[i][frameIndices[i]];

                // 사람 모델 그리기
                glPushMatrix();
                glTranslatef(state.cameraX, state.cameraY, state.cameraZ);
                drawPerson(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
                glPopMatrix();

                // 프레임 업데이트
                frameIndices[i]++;
            }
            else {
                // 마지막 좌표에서 계속 그리기
                const auto& lastState = lastCameraPositions[i];

                glPushMatrix();
                glTranslatef(lastState.cameraX, lastState.cameraY, lastState.cameraZ);
                drawPerson(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
                glPopMatrix();
            }
        }
    }

    glutSwapBuffers();
}

// 타이머 콜백
void update(int value) {
    glutPostRedisplay();
    glutTimerFunc(100, update, 0);
}

// 메인 함수
int main(int argc, char** argv) {
    glutInit(&argc, argv);
    glutInitWindowSize(800, 600);
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    glutCreateWindow("Moving Person");
    glEnable(GL_DEPTH_TEST);

    loadCameraPaths();


    // 2번 경로로 카메라 초기화
    currentPathIndex = 3; // 2번 경로를 사용 (1인칭 시점) 잠시 카메라 모드로 하고 싶으면 주석 처리하셈.
    // 2번 시점 = 1, 관리자 시점 = 3


    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutKeyboardFunc(keyboardDown);
    glutKeyboardUpFunc(keyboardUp);
    glutMotionFunc(mouseMotion);
    glutIdleFunc(idle);
    glutTimerFunc(100, update, 0);
    glutMainLoop();
    return 0;
}