/**
 * PacMan.java - Pac-Man 캐릭터 클래스
 * 
 * - Pac-Man의 위치, 이동, 상태 및 아이템 효과를 관리합니다.
 * - 게임 보드에서 Pac-Man의 이동과 관련된 로직을 처리합니다.
 * - 점수와 생명 상태를 추적하며, 다양한 아이템 효과를 반영합니다.
 */

package pacman;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class PacMan {
    // 필드 선언
    private int x, y;           // Pac-Man의 현재 위치
    private int dx, dy;         // Pac-Man의 이동 방향
    private int directionX = 0; // 현재 X 방향 (-1, 0, 1)
    private int directionY = 0; // 현재 Y 방향 (-1, 0, 1)
    private Image image;        // 현재 이동 방향에 따라 표시할 이미지
    private int score = 0;      // 현재 점수
    private int lives = 3;      // 남은 생명 수

    // 아이템 효과 상태 변수
    private boolean isInvincible = false; // 방어 아이템 효과 활성 상태 여부
    private int invincibleTimer = 0;      // 방어 효과 지속 시간 (틱 단위)
    private int speedModifier = 1;        // 속도 변화 아이템 효과 (1: 기본 속도)
    private int confusionTimer = 0;       // 유령 혼란 아이템 지속 시간 (틱 단위)

    // 방향별 이미지
    private Image imageUp;
    private Image imageDown;
    private Image imageLeft;
    private Image imageRight;

    /**
     * 생성자: Pac-Man의 초기 위치와 이미지를 설정합니다.
     * 
     * @param startX Pac-Man의 초기 X 위치
     * @param startY Pac-Man의 초기 Y 위치
     */
    public PacMan(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        
        // 각 이동 방향에 따른 이미지를 로드
        this.imageUp = new ImageIcon(getClass().getResource("/resources/pacman_up.png")).getImage();
        this.imageDown = new ImageIcon(getClass().getResource("/resources/pacman_down.png")).getImage();
        this.imageLeft = new ImageIcon(getClass().getResource("/resources/pacman_left.png")).getImage();
        this.imageRight = new ImageIcon(getClass().getResource("/resources/pacman_right.png")).getImage();

        // 기본 이미지를 오른쪽 방향으로 설정
        this.image = imageRight;
    }

    /**
     * Pac-Man의 이동 방향을 설정합니다.
     * 
     * @param dx X 방향 (1: 오른쪽, -1: 왼쪽, 0: 정지)
     * @param dy Y 방향 (1: 아래, -1: 위, 0: 정지)
     */
    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
        this.directionX = dx;
        this.directionY = dy;

        // 이동 방향에 따라 이미지를 업데이트
        if (dx == 1) {
            image = imageRight;
        } else if (dx == -1) {
            image = imageLeft;
        } else if (dy == 1) {
            image = imageDown;
        } else if (dy == -1) {
            image = imageUp;
        }
    }

    /**
     * Pac-Man을 이동시킵니다.
     * 
     * @param board 게임 보드 배열 (벽 및 빈 공간 정보)
     */
    public void move(int[][] board) {
        // 속도 변경 효과 적용 시, 한 번에 speedModifier 칸씩 이동
        for (int i = 0; i < speedModifier; i++) {
            int newX = x + dx; // 다음 위치의 X 좌표
            int newY = y + dy; // 다음 위치의 Y 좌표

            // 이동 가능 여부 확인 (보드 경계를 넘지 않고 벽이 아닌 경우)
            if (newX >= 0 && newY >= 0 && newY < board.length && newX < board[0].length && board[newY][newX] != 1) {
                x = newX;
                y = newY;
            } else {
                // 이동 불가 (벽 또는 경계), 이동 중단
                break;
            }
        }

        // 아이템 효과 지속 시간 감소
        updateItemEffects();
    }

    /**
     * 현재 적용 중인 아이템 효과를 업데이트합니다.
     */
    private void updateItemEffects() {
        // 방어 효과 시간이 끝나면 해제
        if (isInvincible) {
            invincibleTimer--;
            if (invincibleTimer <= 0) {
                isInvincible = false;
            }
        }

        // 혼란 효과 시간이 끝나면 해제
        if (confusionTimer > 0) {
            confusionTimer--;
        }
    }

    /**
     * Pac-Man을 화면에 렌더링합니다.
     * 
     * @param g 그래픽 컨텍스트
     */
    public void draw(Graphics g) {
        g.drawImage(image, x * 24, y * 24, 24, 24, null);
    }

    // === 점수 및 생명 관련 메서드 ===

    /**
     * 점수를 추가합니다.
     * 
     * @param points 추가할 점수
     */
    public void addScore(int points) {
        score += points;
    }

    /**
     * 생명을 하나 잃습니다.
     */
    public void loseLife() {
        if (!isInvincible) { // 방어 효과 중에는 생명 감소 없음
            lives--;
        }
    }

    // === 상태 반환 메서드 ===

    public int getX() { return x; }
    public int getY() { return y; }
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public int getDirectionX() { return directionX; }
    public int getDirectionY() { return directionY; }

    // === 상태 설정 메서드 ===

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    // === 아이템 효과 관련 메서드 ===

    /**
     * 방어 아이템 효과를 활성화합니다.
     * 
     * @param duration 효과 지속 시간 (밀리초)
     */
    public void activateInvincibility(int duration) {
        isInvincible = true;
        invincibleTimer = duration / 100; // 밀리초를 틱 단위로 변환
    }

    /**
     * 속도 변화 아이템 효과를 활성화합니다.
     * 
     * @param modifier 속도 변경 값 (1: 기본, >1: 더 빠름)
     * @param duration 효과 지속 시간 (밀리초)
     */
    public void activateSpeedChange(int modifier, int duration) {
        speedModifier = modifier;
        new Timer(duration, e -> speedModifier = 1).start(); // 지속 시간 이후 속도 초기화
    }

    /**
     * 유령 혼란 아이템 효과를 활성화합니다.
     * 
     * @param duration 효과 지속 시간 (밀리초)
     */
    public void activateConfusion(int duration) {
        confusionTimer = duration / 100; // 밀리초를 틱 단위로 변환
    }

    /**
     * Pac-Man이 혼란 상태인지 확인합니다.
     * 
     * @return 혼란 상태 여부
     */
    public boolean isConfused() {
        return confusionTimer > 0;
    }

    /**
     * Pac-Man이 방어 상태인지 확인합니다.
     * 
     * @return 방어 상태 여부
     */
    public boolean isInvincible() {
        return isInvincible;
    }
}
