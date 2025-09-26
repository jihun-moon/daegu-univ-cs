// Clyde는 느리고 무식한 성격을 가짐
// 특징: Pac-Man의 거리가 멀거나 가까울 때 각각 다른 전략을 취함
// 공포 상태일 때는 Pac-Man과 멀어지지만, 가까이 있으면 랜덤으로 이동하거나 초기 위치로 돌아감
// 느린 속도와 예측 불가한 이동으로 게임 내에서 가장 덜 위협적인 유령

package pacman;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;

public class Clyde extends Ghost {
    private int speed = 1; // Clyde는 다른 유령보다 속도가 느림
    private double wanderProbability = 0.6; // 60% 확률로 무작위로 떠돌기

    public Clyde(int startX, int startY, String leftImagePath, String rightImagePath, String scaredImagePath) {
        super(startX, startY, leftImagePath, rightImagePath, scaredImagePath, "Clyde");
    }

    /**
     * Clyde의 이동 메서드
     * 공포 상태일 때는 Pac-Man으로부터 멀어지고, 그렇지 않을 때는 Pac-Man과의 거리와 초기 위치를 고려하여 이동
     *
     * @param board 게임 보드 배열
     * @param pacmanX Pac-Man의 X 좌표
     * @param pacmanY Pac-Man의 Y 좌표
     * @param pacmanDirectionX Pac-Man의 X 방향
     * @param pacmanDirectionY Pac-Man의 Y 방향
     * @param otherGhosts 다른 유령 목록
     * @param pacmanScore Pac-Man의 점수
     */
    @Override
    public void move(int[][] board, int pacmanX, int pacmanY, int pacmanDirectionX, int pacmanDirectionY, List<Ghost> otherGhosts, int pacmanScore) {
        int distance = Math.abs(pacmanX - x) + Math.abs(pacmanY - y);
        
        if (isConfused) { 
            return; // 혼란 상태일 때 이동하지 않음
        }
        
        if (isScared) {
            // 공포 상태일 때는 Pac-Man으로부터 멀어짐
            moveAwayFromPacman(board, pacmanX, pacmanY, otherGhosts);
        } else {
            if (distance > 8 && Math.random() > wanderProbability) {
                // Pac-Man과의 거리가 8 이상일 때, 무작위 이동 확률에 따라 추적
                List<Node> path = aStarSearch(board, pacmanX, pacmanY);
                if (path != null && path.size() > speed) {
                    Node nextStep = path.get(speed); // 속도에 맞춰 한 칸 이동
                    x = nextStep.x;
                    y = nextStep.y;
                }
            } else {
                // 가까이 있거나 떠돌기 확률이 높을 때는 무작위 이동 또는 초기 위치로 돌아감
                moveRandomOrHome(board, otherGhosts);
            }
        }
    }

    /**
     * 초기 위치로 돌아가거나 무작위로 이동하는 메서드
     *
     * @param board 게임 보드 배열
     * @param otherGhosts 다른 고스트 목록
     */
    private void moveRandomOrHome(int[][] board, List<Ghost> otherGhosts) {
        if (random.nextInt(4) == 0) { // 25% 확률로 초기 위치로 이동
            moveTowardsInitialPosition(board, otherGhosts);
        } else {
            randomMove(board, otherGhosts); // 75% 확률로 무작위 이동
        }
    }

    /**
     * 초기 위치로 이동하는 메서드 (한 칸씩 이동)
     *
     * @param board 게임 보드 배열
     * @param otherGhosts 다른 고스트 목록
     */
    protected void moveTowardsInitialPosition(int[][] board, List<Ghost> otherGhosts) {
        int dx = Integer.compare(initialX, x); // 초기 위치로 가기 위한 x 방향 설정
        int dy = Integer.compare(initialY, y); // 초기 위치로 가기 위한 y 방향 설정

        // x축 이동, 불가능할 경우 y축 이동 시도
        if (canMove(x + dx, y, board, otherGhosts)) {
            x += dx;
        } else if (canMove(x, y + dy, board, otherGhosts)) {
            y += dy;
        } else {
            randomMove(board, otherGhosts); // 양쪽 모두 이동 불가 시 무작위 이동
        }
    }

    /**
     * A* 알고리즘을 사용해 Clyde가 목표 위치로 가는 최단 경로를 찾음
     *
     * @param board 게임 보드 배열
     * @param targetX 목표 X 좌표 (Pac-Man의 X 좌표)
     * @param targetY 목표 Y 좌표 (Pac-Man의 Y 좌표)
     * @return 최단 경로 리스트 (목표 위치로 가기 위한 좌표 리스트)
     */
    private List<Node> aStarSearch(int[][] board, int targetX, int targetY) {
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        boolean[][] closedList = new boolean[board.length][board[0].length];

        openList.add(new Node(x, y, null, 0, Math.abs(targetX - x) + Math.abs(targetY - y)));

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.x == targetX && current.y == targetY) {
                return constructPath(current); // 목표 위치에 도달 시 경로 반환
            }

            closedList[current.y][current.x] = true;

            // 상하좌우 이웃 노드 탐색
            int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
            for (int[] dir : directions) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                if (isWithinBounds(newX, newY, board) && !closedList[newY][newX] && board[newY][newX] != 1) {
                    int gCost = current.g + 1;
                    int hCost = Math.abs(newX - targetX) + Math.abs(newY - targetY);
                    Node neighbor = new Node(newX, newY, current, gCost, hCost);
                    openList.add(neighbor);
                }
            }
        }

        return null; // 경로를 찾지 못한 경우 null 반환
    }

    /**
     * 목표 위치에 도달할 때까지의 경로를 리스트로 반환
     *
     * @param node 목표 노드
     * @return 최단 경로 리스트
     */
    private List<Node> constructPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node);
            node = node.parent;
        }
        return path;
    }

    /**
     * 좌표가 보드 범위 내에 있는지 확인
     *
     * @param x 좌표 X
     * @param y 좌표 Y
     * @param board 게임 보드 배열
     * @return 범위 내에 있으면 true, 그렇지 않으면 false
     */
    private boolean isWithinBounds(int x, int y, int[][] board) {
        return x >= 0 && x < board[0].length && y >= 0 && y < board.length;
    }

    /**
     * A* 알고리즘에 필요한 노드 클래스
     */
    private static class Node {
        int x, y;
        Node parent;
        int g; // 시작 노드로부터의 비용
        @SuppressWarnings("unused")
		int h; // 휴리스틱 비용 (목표까지의 추정 거리)
        int f; // 총 비용 (g + h)

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
