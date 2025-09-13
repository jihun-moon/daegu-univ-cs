// Blinky는 그림자(Shadow) 성격을 가짐
// 특징: 가장 공격적인 유령, Pac-Man을 최단 경로로 추적
// Pac-Man의 점수에 따라 속도가 증가하며, 최대 1.5배까지 속도 증가
// 주로 Pac-Man을 추적하는데 집중하며, 공포 상태일 때는 Pac-Man과 멀어짐

package pacman;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;

public class Blinky extends Ghost {

    public Blinky(int startX, int startY, String leftImagePath, String rightImagePath, String scaredImagePath) {
        super(startX, startY, leftImagePath, rightImagePath, scaredImagePath, "Blinky");
    }

    /**
     * Blinky를 움직이는 메서드, Pac-Man의 위치에 따라 최단 경로를 계산해 이동
     * 
     * @param board 게임 보드 배열 (0: 길, 1: 벽)
     * @param pacmanX Pac-Man의 X 좌표
     * @param pacmanY Pac-Man의 Y 좌표
     * @param pacmanDirectionX Pac-Man의 이동 방향 X (현재는 사용 안 함)
     * @param pacmanDirectionY Pac-Man의 이동 방향 Y (현재는 사용 안 함)
     * @param otherGhosts 다른 고스트 목록 (겹치지 않도록 하기 위함)
     * @param pacmanScore Pac-Man의 점수 (Blinky의 속도에 영향을 줌)
     */
    @Override
    public void move(int[][] board, int pacmanX, int pacmanY, int pacmanDirectionX, int pacmanDirectionY, List<Ghost> otherGhosts, int pacmanScore) {
        double maxSpeedFactor = 1.5;  // 최대 속도 증가 1.5배
        @SuppressWarnings("unused")
		int speedFactor = Math.min(pacmanScore / 1000, (int)(maxSpeedFactor * 10)); // 속도 증가 계산
        
        if (isConfused) { 
            return; // 혼란 상태일 때 이동하지 않음
        }
        
        if (isScared) {
            moveAwayFromPacman(board, pacmanX, pacmanY, otherGhosts); // 공포 상태일 때 멀어짐
        } else {
            // A* 알고리즘을 한 번 호출하여 Pac-Man으로 가는 최단 경로를 찾음
            List<Node> path = aStarSearch(board, pacmanX, pacmanY);

            // 경로가 있다면 첫 번째 위치로 이동
            if (path != null && path.size() > 1) {
                // 경로 상 첫 번째 칸으로 이동
                Node nextStep = path.get(1);
                x = nextStep.x;
                y = nextStep.y;

                // 이동 방향에 맞게 이미지 갱신
                updateImageDirection(nextStep.x - path.get(0).x);
            } else {
                // 경로가 없으면 무작위 이동
                randomMove(board, otherGhosts);
            }
        }
    }


    /**
     * A* 알고리즘을 사용해 Blinky가 Pac-Man으로 가는 최단 경로를 찾음
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