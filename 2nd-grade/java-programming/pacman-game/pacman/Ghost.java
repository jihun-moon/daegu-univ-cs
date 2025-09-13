package pacman;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public abstract class Ghost {
    protected int x, y;               // 현재 위치
    protected int initialX, initialY; // 초기 위치
    protected String name;            // 유령 이름
    protected Image image;            // 현재 이미지
    protected Image scaredImage;      // 공포 상태 이미지
    protected Image imageLeft, imageRight; // 좌우 방향 이미지
    protected boolean isScared = false;    // 공포 상태 여부
    protected boolean isConfused = false;  // 혼란 상태인지 여부
    protected Random random;

    public Ghost(int startX, int startY, String normalImagePathLeft, String normalImagePathRight, String scaredImagePath, String name) {
        this.x = startX;
        this.y = startY;
        this.initialX = startX;
        this.initialY = startY;
        this.name = name;

        // 방향별 이미지 로드
        this.imageLeft = loadImage(normalImagePathLeft);
        this.imageRight = loadImage(normalImagePathRight);
        this.scaredImage = loadImage(scaredImagePath);
        
        // 초기 이미지는 오른쪽으로 설정
        this.image = imageRight;
        this.random = new Random();
    }

    // 이미지 로드 메서드
    private Image loadImage(String imagePath) {
        java.net.URL imgURL = getClass().getResource(imagePath);
        return imgURL != null ? new ImageIcon(imgURL).getImage() : null;
    }

    // 위치 설정
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 초기 위치로 리셋
    public void resetPosition() {
        this.x = initialX;
        this.y = initialY;
        this.isScared = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    
    // 공포 상태 설정
    public void setScared(boolean scared) {
        isScared = scared;
        if (isScared) {
            // 스케얼드 이미지 즉시 업데이트
            if (scaredImage != null) {
                image = scaredImage;
            }
        } else {
            // 공포 상태 해제 시 방향에 따라 기본 이미지 업데이트
            updateImageDirection(0); // 방향 없음 -> 기본 이미지
        }
    }

    
    public void setConfused(boolean confused) {
        isConfused = confused;

        // 혼란 상태에서도 스케얼드 상태라면 이미지 즉시 업데이트
        if (isScared && scaredImage != null) {
            image = scaredImage;
        } else if (!isScared) {
            // 스케얼드 상태가 아닐 경우 기본 방향 이미지 유지
            updateImageDirection(0);
        }
    }

    
    public boolean isScared() { return isScared; }

    // 유령의 이동 메서드: 공포 상태에 따라 행동이 달라짐
    public void move(int[][] board, int pacmanX, int pacmanY, int pacmanDirectionX, int pacmanDirectionY, List<Ghost> otherGhosts, int pacmanScore) {
        
    	if (isConfused) { // 혼란 상태 확인
            return; // 혼란 상태일 때 완전히 움직임 생략 (멈춤)
        } else if (isScared) { // 공포 상태
            if (random.nextInt(2) == 0) { // 50% 확률로 이동 생략, 느려지도록 설정
                moveAwayFromPacman(board, pacmanX, pacmanY, otherGhosts);
            }
        } else { // 기본 상태
            if (random.nextBoolean()) {
                moveTowardsPacman(board, pacmanX, pacmanY, otherGhosts, 1); // Pac-Man을 추적
            } else {
                randomMove(board, otherGhosts); // 무작위 이동
            }
        }
    }


    // Pac-Man에서 멀어지도록 이동
    protected void moveAwayFromPacman(int[][] board, int pacmanX, int pacmanY, List<Ghost> otherGhosts) {
        int desiredDistance = 5; // 유지하려는 최소 거리
        int distance = Math.abs(pacmanX - x) + Math.abs(pacmanY - y);

        if (distance < desiredDistance) { // 거리가 가까울 때 Pac-Man에서 멀어지도록 설정
            int dx = pacmanX < x ? 1 : (pacmanX > x ? -1 : 0);
            int dy = pacmanY < y ? 1 : (pacmanY > y ? -1 : 0);

            int newX = x + dx;
            int newY = y + dy;
            if (canMove(newX, newY, board, otherGhosts)) {
                x = newX;
                y = newY;
                updateImageDirection(dx); // 이동 방향에 맞게 이미지 업데이트
            } else {
                randomMove(board, otherGhosts); // 이동 불가 시 무작위로 이동
            }
        } else {
            randomMove(board, otherGhosts); // 멀리 있을 때는 무작위로 이동
        }
    }

    protected void moveTowardsPacman(int[][] board, int pacmanX, int pacmanY, List<Ghost> otherGhosts, int speedFactor) {
        for (int i = 0; i < speedFactor; i++) { // speedFactor에 따라 여러 번 이동
            int dx = Integer.compare(pacmanX, x); // x축 방향
            int dy = Integer.compare(pacmanY, y); // y축 방향

            // 우선적으로 x축 방향으로 이동을 시도하고, 이동 불가능하면 y축 이동을 시도
            if (canMove(x + dx, y, board, otherGhosts)) {
                x += dx;
                updateImageDirection(dx);  // x 방향에 따른 이미지 업데이트
            } else if (canMove(x, y + dy, board, otherGhosts)) {
                y += dy;
                updateImageDirection(0);  // y 방향일 경우 이미지 변경 없음
            }
        }
    }

    // 무작위 이동 메서드
    protected void randomMove(int[][] board, List<Ghost> otherGhosts) {
        int[] dxOptions = {-1, 0, 1, 0};
        int[] dyOptions = {0, -1, 0, 1};
        List<int[]> possibleMoves = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int newX = x + dxOptions[i];
            int newY = y + dyOptions[i];
            if (canMove(newX, newY, board, otherGhosts)) {
                possibleMoves.add(new int[]{dxOptions[i], dyOptions[i]});
            }
        }

        if (!possibleMoves.isEmpty()) {
            int[] move;
            if (isConfused && random.nextInt(4) != 0) { // 75% 확률로 이동 생략
                move = new int[]{0, 0}; // 이동하지 않음
            } else {
                move = possibleMoves.get(random.nextInt(possibleMoves.size()));
            }

            x += move[0];
            y += move[1];
            updateImageDirection(move[0]); // 실제 이동 방향에 맞게 이미지 업데이트
        }
    }


    // 방향에 따라 이미지 업데이트
    protected void updateImageDirection(int dx) {
        if (isScared && scaredImage != null) {
            // 스케얼드 상태 우선 적용
            image = scaredImage;
        } else if (isConfused) {
            // 혼란 상태일 때는 이동 방향에 관계없이 기본 이미지 유지
            image = dx < 0 ? imageLeft : imageRight;
        } else {
            // 일반 상태에서 이동 방향에 따라 이미지 설정
            image = dx < 0 ? imageLeft : imageRight;
        }
    }


    // 유령 이동 가능 여부 확인 메서드
    protected boolean canMove(int newX, int newY, int[][] board, List<Ghost> otherGhosts) {
        // 보드 내에 있으며 벽이 아닌 경우에만 이동 가능
        if (newX < 0 || newX >= board[0].length || newY < 0 || newY >= board.length || board[newY][newX] == 1) {
            return false;  // 벽이거나 보드 범위를 벗어난 경우 이동 불가
        }
        // 다른 유령이 있는 위치로 이동하지 않음
        for (Ghost ghost : otherGhosts) {
            if (ghost != this && ghost.getX() == newX && ghost.getY() == newY) {
                return false;  // 다른 유령과의 충돌 방지
            }
        }
        return true;  // 이동 가능
    }

    // 유령을 화면에 그리는 메서드
    public void draw(Graphics g) {
        g.drawImage(image, x * 24, y * 24, 24, 24, null);
    }
}
