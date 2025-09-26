// Pinky는 짖궂고 고집스러운 성격을 가짐
// 특징: Pac-Man의 예상 경로 4칸 앞을 목표로 이동
// 공포 상태일 때는 Pac-Man으로부터 멀어지고, 그 외에는 예측 가능한 경로로 이동
// 무작위 이동을 일부 포함시켜, 예측을 어렵게 만듦

package pacman;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;

public class Pinky extends Ghost {
    private double randomMoveChance = 0.2; // 20% 확률로 무작위 이동

    public Pinky(int startX, int startY, String leftImagePath, String rightImagePath, String scaredImagePath) {
        super(startX, startY, leftImagePath, rightImagePath, scaredImagePath, "Pinky");
    }

    /**
     * Pinky의 이동 메서드
     * 공포 상태에서는 Pac-Man으로부터 멀어지고, 그렇지 않으면 Pac-Man의 앞쪽을 목표로 이동
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
    	if (isConfused) { 
            return; // 혼란 상태일 때 이동하지 않음
        }
    	
    	if (isScared) {
            // 공포 상태일 때는 Pac-Man으로부터 멀어짐
            moveAwayFromPacman(board, pacmanX, pacmanY, otherGhosts);
        } else if (Math.random() < randomMoveChance) {
            // 무작위 이동 확률을 적용하여 예측 불가한 움직임 강화
            randomMove(board, otherGhosts);
        } else {
            // Pac-Man의 4칸 앞을 목표로 설정
            int targetX = pacmanX + pacmanDirectionX * 4;
            int targetY = pacmanY + pacmanDirectionY * 4;

            List<Node> path = aStarSearch(board, targetX, targetY);
            if (path != null && path.size() > 1) {
                Node nextStep = path.get(1); // 첫 번째 이동 위치로 이동
                x = nextStep.x;
                y = nextStep.y;
            } else {
                // 경로가 없거나 목표에 접근할 수 없는 경우 무작위 이동
                randomMove(board, otherGhosts);
            }
        }
    }

    /**
     * A* 알고리즘을 사용해 Pinky가 목표 위치로 가는 최단 경로를 찾음
     *
     * @param board 게임 보드 배열
     * @param targetX 목표 X 좌표 (Pac-Man의 예상 위치)
     * @param targetY 목표 Y 좌표 (Pac-Man의 예상 위치)
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
