package pacman;

import java.awt.*;
import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import javax.swing.ImageIcon;

public class BossGhost extends Ghost {
    protected int teleportCooldown = 25; // 텔레포트 쿨다운 (틱 단위, 약 1.6초)
    protected int currentCooldown = teleportCooldown;
    protected boolean isFastMoving = true; // 초기 빠른 이동 여부
    protected int speedDecayTimer = 30; // 빠른 이동 지속 시간 초기화
    private Random random = new Random();

    private Image bossImage; // 보스 유령 이미지
    boolean isVisible = true; // 보스 유령의 가시성
    protected double speedMultiplier = 1.5; // 초기 속도 배율
    private final int maxMovesPerTurn = 2; // 보스 유령이 한 번에 최대 이동할 횟수

    public BossGhost(int startX, int startY, String imagePath) {
        super(startX, startY, "BossGhost", "BossGhost", "BossGhost", "BossGhost");
        this.bossImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
    }
    
    @Override
    public void move(int[][] board, int pacmanX, int pacmanY, int pacmanDirectionX, int pacmanDirectionY, List<Ghost> otherGhosts, int pacmanScore) {
        if (!isVisible) return; // 보스가 보이지 않으면 이동하지 않음

        if (isScared) {
            moveAwayFromPacman(board, pacmanX, pacmanY, otherGhosts);
            return; // 공포 상태에서는 텔레포트나 추가 이동을 하지 않음
        }

        // 텔레포트 쿨다운 감소 및 텔레포트 실행
        if (currentCooldown <= 0) {
            teleportRandomly(board); // 순간 이동
            currentCooldown = teleportCooldown; // 쿨다운 초기화
          //System.out.println("Boss teleported! Cooldown reset to: " + currentCooldown);
            return; // 텔레포트 후 이동 동작 스킵
        } else {
            currentCooldown--; // 쿨다운 감소
        }

        // 빠른 이동 지속 시간 동안 속도 점진적 감소
        if (isFastMoving) {
            speedDecayTimer--;
            if (speedDecayTimer <= 0) {
                isFastMoving = false;
                speedMultiplier = 1.0; // 정상 속도로 변경
              //System.out.println("Boss speed returned to normal at tick: " + (30 - speedDecayTimer)); // 빠른 이동 종료 출력
            } else {
                speedMultiplier = 1.0 + (0.5 * (speedDecayTimer / 30.0)); // 점진적 감소
              //System.out.println("Boss speed multiplier: " + speedMultiplier + ", remaining fast ticks: " + speedDecayTimer);
            }
        }

        // 속도에 따른 이동
        int moves = Math.min((int) speedMultiplier, maxMovesPerTurn);
        for (int i = 0; i < moves; i++) {
            List<Node> path = aStarSearch(board, pacmanX, pacmanY);
            if (path != null && path.size() > 1) {
                Node nextStep = path.get(1);
                x = nextStep.x;
                y = nextStep.y;
                updateImageDirection(nextStep.x - path.get(0).x);
            }
        }
    }

    private void teleportRandomly(int[][] board) {
        if (board.length == 0 || board[0].length == 0) {
            return; // 보드가 유효하지 않으면 텔레포트 하지 않음
        }

        int newX, newY;
        boolean teleported = false;

        for (int attempts = 0; attempts < 50; attempts++) {
            newX = random.nextInt(board[0].length);
            newY = random.nextInt(board.length);

            // 유효한 위치를 확인 (벽이 아니고 현재 위치가 아님)
            if (board[newY][newX] != 1 && (newX != x || newY != y)) {
                setPosition(newX, newY);
                teleported = true;
              //System.out.println("Boss teleported to: (" + newX + ", " + newY + ")");
                break;
            }
        }

        if (!teleported) {
          //System.out.println("Teleport failed after 50 attempts, boss remains at: (" + x + ", " + y + ")");
        }
    }

    public void activateFastMovement() {
        isFastMoving = true;
        speedDecayTimer = 30; // 빠른 이동 지속 시간 초기화 (틱 단위)
        speedMultiplier = 1.5; // 초기 속도 배율 설정
    }

    void disappear() {
        isVisible = false; // 보스가 화면에 보이지 않도록 설정
        isScared = false;
        isFastMoving = false;
        currentCooldown = teleportCooldown;
    }

    @Override
    public void draw(Graphics g) {
        if (!isVisible) return;
        if (bossImage != null) {
            g.drawImage(bossImage, x * 24, y * 24, 24, 24, null);
        }
    }

    private List<Node> aStarSearch(int[][] board, int targetX, int targetY) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        boolean[][] closedList = new boolean[board.length][board[0].length];
        openList.add(new Node(x, y, null, 0, Math.abs(targetX - x) + Math.abs(targetY - y)));

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            if (current.x == targetX && current.y == targetY) {
                return constructPath(current);
            }

            closedList[current.y][current.x] = true;
            for (int[] dir : new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}}) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                if (isWithinBounds(newX, newY, board) && !closedList[newY][newX] && board[newY][newX] != 1) {
                    int gCost = current.g + 1;
                    int hCost = Math.abs(newX - targetX) + Math.abs(targetY - newY);
                    openList.add(new Node(newX, newY, current, gCost, hCost));
                }
            }
        }
        return null; // 경로를 찾지 못한 경우 null 반환
    }

    private List<Node> constructPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node);
            node = node.parent;
        }
        return path;
    }

    private boolean isWithinBounds(int x, int y, int[][] board) {
        return x >= 0 && y >= 0 && x < board[0].length && y < board.length;
    }

    private static class Node {
        int x, y;
        Node parent;
        @SuppressWarnings("unused")
        int g, h, f;

        Node(int x, int y, Node parent, int g, int h) {
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.g = g;
            this.h = h;
            this.f = g + h;
        }
    }
}
