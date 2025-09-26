/**
 * GameBoard.java - 게임 보드 클래스
 * 
 * - 게임의 보드 상태와 렌더링을 관리합니다.
 * - Pac-Man, 유령, 아이템, 점수 등을 처리하며, 게임 로직과 상호작용합니다.
 */

package pacman;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard extends JPanel {

    private static final long serialVersionUID = 4282586493151763905L;
    private final int cellSize = 24; // 셀 크기 (픽셀 단위)
    private int[][] board = { // 게임 보드 배열
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 2, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1},
            {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

    private PacMan pacMan; // Pac-Man 객체
    private List<Ghost> ghosts; // 유령 객체 리스트
    private BossGhost bossGhost; // 보스 유령 객체
    private boolean bossSpawned = false; // 보스 스폰 여부
    private boolean powerUpActive = false; // 파워업 활성 상태
    private int powerUpTimer = 0; // 파워업 지속 시간
    private int ghostEatCombo = 0; // 유령 연속 먹기 콤보
    private boolean gameEnded = false; // 게임 종료 여부
    private boolean levelCleared = false; // 레벨 클리어 여부
    private Random random = new Random(); // 랜덤 객체
    private Image cherryImage, shieldItemImage, speedItemImage, confusionItemImage, powerUpImage; // 아이템 이미지
    private final int maxCherries = 3; // 최대 체리 개수
    private Timer scareTimer; // 공포 상태 타이머
    private String countdownText = ""; // 카운트다운 텍스트
    private MainGame mainGame; // 메인 게임 객체
    private int lastBossSpawnScore = 0; // 마지막 보스 스폰 점수
    private String bossMessage = ""; // 보스 등장 메시지
    private Timer bossMessageTimer;  // 보스 메시지 타이머


    /**
     * 생성자: 게임 보드를 초기화합니다.
     * 
     * @param pacMan Pac-Man 객체
     * @param ghosts 유령 리스트
     * @param mainGame 메인 게임 객체
     */
    public GameBoard(PacMan pacMan, List<Ghost> ghosts, MainGame mainGame) {
        setPreferredSize(new Dimension(500, 500));
        this.pacMan = pacMan;
        this.ghosts = ghosts;
        this.mainGame = mainGame;

        // 리소스 로드
        this.cherryImage = new ImageIcon(getClass().getResource("/resources/cherry.png")).getImage();
        this.shieldItemImage = new ImageIcon(getClass().getResource("/resources/shield.png")).getImage();
        this.speedItemImage = new ImageIcon(getClass().getResource("/resources/speed.png")).getImage();
        this.confusionItemImage = new ImageIcon(getClass().getResource("/resources/confusion.png")).getImage();
        this.powerUpImage = new ImageIcon(getClass().getResource("/resources/powerup.png")).getImage();

        bossGhost = new BossGhost(10, 10, "/resources/boss_ghost.png"); // 보스 유령 초기화
        
        placeRandomItems(); // 아이템 배치
        resetBoard(); // 보드 초기화
    }
    
    /**
     * 무작위로 서로 다른 아이템을 배치합니다.
     */
    private void placeRandomItems() {
        @SuppressWarnings("unused")
		int[] items = {4, 5, 6}; // 4: 방어, 5: 속도, 6: 혼란
        List<Integer> availableItems = new ArrayList<>(List.of(4, 5, 6));

        for (int i = 0; i < 2 && !availableItems.isEmpty(); i++) {
            int x, y;
            do {
                x = random.nextInt(board[0].length);
                y = random.nextInt(board.length);
            } while (board[y][x] != 0); // 빈 공간에만 배치

            // 아이템을 랜덤하게 하나 선택하여 보드에 배치
            int itemIndex = random.nextInt(availableItems.size());
            board[y][x] = availableItems.get(itemIndex);
            availableItems.remove(itemIndex);
        }
    }


    /**
     * 파워업(큰 노란 점)을 활성화합니다.
     */
    private void activatePowerUp1() {
        powerUpActive = true;
        ghostEatCombo = 0; // 유령 콤보 초기화

        int randomTime = 10000 + random.nextInt(5000); // 10~15초 지속
        powerUpTimer = randomTime / 30;

        for (Ghost ghost : ghosts) {
            if (!(ghost instanceof BossGhost)) {
                ghost.setScared(false); // 이전 공포 상태 초기화
                ghost.setScared(true);  // 공포 상태 적용
            }
        }

        // 공포 상태 타이머 설정
        if (scareTimer != null && scareTimer.isRunning()) {
            scareTimer.stop();
        }
        scareTimer = new Timer(randomTime, e -> {
            powerUpActive = false;
            for (Ghost ghost : ghosts) {
                ghost.setScared(false);
            }
        });
        scareTimer.setRepeats(false);
        scareTimer.start();
    }

    /**
     * 보스 유령을 소환합니다.
     * 
     * @param currentScore 현재 점수
     */
    public void spawnBossGhost(int currentScore) {
        int spawnInterval = 500; // 보스 소환 간격

        // 점수 조건이 충족되었으며, 보스가 아직 스폰되지 않은 경우
        if (!bossSpawned && currentScore >= lastBossSpawnScore + spawnInterval) {
            lastBossSpawnScore = currentScore;
            bossSpawned = true;
            
            // 보스 메시지 설정
            bossMessage = "WARNING: BOSS INCOMING!";
            repaint(); // 메시지를 화면에 즉시 반영
            
            if (bossMessageTimer != null && bossMessageTimer.isRunning()) {
                bossMessageTimer.stop();
            }

         // 3초 후 메시지 제거
            bossMessageTimer = new Timer(2000, e -> {
                bossMessage = "";
                repaint();
            });
            bossMessageTimer.setRepeats(false);
            bossMessageTimer.start();

            // 보스를 랜덤한 빈 공간에 스폰
            int[] spawnLocation = findRandomEmptyLocation();
            if (spawnLocation != null) {
                bossGhost.setPosition(spawnLocation[0], spawnLocation[1]);
            } else {
                bossGhost.setPosition(10, 10); // 기본 위치
            }

            bossGhost.isVisible = true;
            
            bossGhost.isFastMoving = true; // 속도 초기화
            bossGhost.speedDecayTimer = 30; // 빠른 이동 지속 시간 초기화
            bossGhost.speedMultiplier = 1.5; // 초기 속도 배율
            
            ghosts.add(bossGhost);

            // 보스 유령 제거 타이머 설정 (10초 후 제거)
            Timer bossDisappearTimer = new Timer(10000, e -> removeBossGhost());
            bossDisappearTimer.setRepeats(false);
            bossDisappearTimer.start();
        }
    }

    /**
     * 보스 유령을 제거합니다.
     */
    private void removeBossGhost() {
        if (bossSpawned) {
            bossGhost.disappear(); // 보스 가시성 해제
            ghosts.remove(bossGhost); // 유령 리스트에서 제거
            bossSpawned = false;
            
            // 보스 사라짐 메시지 설정
            bossMessage = "The Boss vanished!";
            repaint(); // 메시지를 화면에 즉시 반영

            if (bossMessageTimer != null && bossMessageTimer.isRunning()) {
                bossMessageTimer.stop();
            }

            // 3초 후 메시지 제거
            bossMessageTimer = new Timer(2000, e -> {
                bossMessage = "";
                repaint();
            });
            bossMessageTimer.setRepeats(false);
            bossMessageTimer.start();
        }
    }

    /**
     * 보드에 빈 공간을 찾아 반환합니다.
     * 
     * @return 빈 공간의 좌표 (x, y), 없으면 null
     */
    private int[] findRandomEmptyLocation() {
        List<int[]> emptySpaces = new ArrayList<>();
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                if (board[y][x] == 0) { // 빈 공간 확인
                    emptySpaces.add(new int[]{x, y});
                }
            }
        }

        if (!emptySpaces.isEmpty()) {
            return emptySpaces.get(random.nextInt(emptySpaces.size())); // 랜덤 선택
        }
        return null;
    }

    /**
     * Pac-Man과 유령 충돌을 처리합니다.
     */
    private void handleGhostCollision() {
        for (Ghost ghost : ghosts) {
            if (pacMan.getX() == ghost.getX() && pacMan.getY() == ghost.getY()) { // 같은 좌표에 있는 경우
                if (ghost.isScared()) {
                    pacManAteGhost(ghost); // Pac-Man이 공포 상태의 유령을 먹음
                } else if (!pacMan.isInvincible()) {
                    pacMan.loseLife(); // Pac-Man 생명 감소
                    if (pacMan.getLives() <= 0 && !gameEnded) {
                        gameEnded = true;
                        JOptionPane.showMessageDialog(this, "Game Over! Score: " + pacMan.getScore(), "제목 변경함.", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0); // 게임 종료
                    } else if (!gameEnded) {
                        resetPositions(); // 게임 위치 초기화
                    }
                }
            }
        }
    }
    
    /**
     * Pac-Man이 공포 상태의 유령을 먹었을 때 호출됩니다.
     * 
     * @param ghost 먹힌 유령
     */
    public void pacManAteGhost(Ghost ghost) {
        if (ghost.isScared()) {
            ghost.setScared(false); // 공포 상태 해제
            ghostEatCombo++; // 콤보 증가

            int points = Math.min(50, 20 + (ghostEatCombo - 1) * 10); // 점수 계산
            pacMan.addScore(points);

            // 유령을 랜덤한 빈 공간으로 이동
            int[] spawnLocation = findRandomDistantEmptyLocation(pacMan.getX(), pacMan.getY(), 3);
            if (spawnLocation != null) {
                ghost.setPosition(spawnLocation[0], spawnLocation[1]);
            } else {
                ghost.setPosition(5, 5); // 기본 위치
            }
        }
    }


    /**
     * Pac-Man과 일정 거리 이상 떨어진 빈 공간을 반환합니다.
     * 
     * @param pacmanX Pac-Man의 x 좌표
     * @param pacmanY Pac-Man의 y 좌표
     * @param minDistance 최소 거리
     * @return 적절한 빈 공간의 좌표 (x, y), 없으면 null
     */
    private int[] findRandomDistantEmptyLocation(int pacmanX, int pacmanY, int minDistance) {
        List<int[]> possibleLocations = new ArrayList<>();
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                if (isEmptySpace(x, y) && calculateDistance(pacmanX, pacmanY, x, y) >= minDistance) {
                    possibleLocations.add(new int[]{x, y});
                }
            }
        }
        if (!possibleLocations.isEmpty()) {
            return possibleLocations.get(random.nextInt(possibleLocations.size()));
        }
        return null;
    }

    /**
     * 주어진 좌표가 보드의 빈 공간인지 확인합니다.
     * 
     * @param x X 좌표
     * @param y Y 좌표
     * @return 해당 위치가 빈 공간이면 true, 그렇지 않으면 false
     */
    private boolean isEmptySpace(int x, int y) {
        return x >= 0 && y >= 0 && x < board[0].length && y < board.length && board[y][x] == 0;
    }

    /**
     * 두 좌표 사이의 맨해튼 거리 계산.
     */
    private int calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    /**
     * Pac-Man의 위치를 초기화합니다.
     */
    private void resetPositions() {
        pacMan.setPosition(19, 1); // Pac-Man 초기 위치 설정
        for (Ghost ghost : ghosts) {
            ghost.setScared(false); // 유령의 공포 상태 해제
            ghost.resetPosition(); // 유령 초기 위치로 리셋
        }
        powerUpActive = false; // 파워업 상태 해제
        powerUpTimer = 0; // 파워업 타이머 초기화
        ghostEatCombo = 0; // 유령 콤보 초기화
    }

    /**
     * 카운트다운 텍스트를 설정합니다.
     * 
     * @param text 표시할 텍스트
     */
    public void setCountdownText(String text) {
        this.countdownText = text;
    }

    /**
     * 보드의 내용을 그립니다.
     * 
     * @param g 그래픽 객체
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 보스 유령을 렌더링
        if (bossSpawned && bossGhost.isVisible) {
            bossGhost.draw(g);
        }

        // 보드를 그리기
        drawBoard(g);

        // Pac-Man과 일반 유령들을 그리기
        pacMan.draw(g);
        for (Ghost ghost : ghosts) {
            ghost.draw(g);
        }    

        // 보스 등장 메시지 출력
        if (!bossMessage.isEmpty()) {
            Graphics2D g2d = (Graphics2D) g;

            // 글씨 크기를 조정 (36으로 줄임)
            g2d.setFont(new Font("Impact", Font.BOLD, 36));
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(bossMessage);
            int textHeight = fm.getAscent();

            // 외곽선 효과
            g2d.setColor(Color.BLACK);
            for (int offset = 1; offset <= 2; offset++) {
                g2d.drawString(bossMessage, (getWidth() - textWidth) / 2 - offset, (getHeight() + textHeight) / 2 - offset);
                g2d.drawString(bossMessage, (getWidth() - textWidth) / 2 + offset, (getHeight() + textHeight) / 2 + offset);
                g2d.drawString(bossMessage, (getWidth() - textWidth) / 2 - offset, (getHeight() + textHeight) / 2 + offset);
                g2d.drawString(bossMessage, (getWidth() - textWidth) / 2 + offset, (getHeight() + textHeight) / 2 - offset);
            }

            // 텍스트 본체
            g2d.setColor(Color.RED);
            g2d.drawString(bossMessage, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2);
        }

        // 카운트다운 텍스트 표시
        if (!countdownText.isEmpty()) {
            drawCountdownText(g);
        }

        // 게임 상태 업데이트
        checkLevelCleared(); // 레벨 클리어 상태 확인
        handlePacmanPosition(); // Pac-Man의 위치와 아이템 처리
        spawnCherry(); // 체리 생성

        // 파워업 상태 관리
        if (powerUpActive) {
            updatePowerUpState();
        }

        // 점수와 생명 렌더링
        drawScoreAndLives(g);

        // Pac-Man과 유령 충돌 처리
        handleGhostCollision();
    }


    /**
     * 카운트다운 텍스트를 화면에 그립니다.
     * 
     * @param g 그래픽 객체
     */
    private void drawCountdownText(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(countdownText);
        int textHeight = fm.getAscent();
        g.drawString(countdownText, (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2);
    }

    /**
     * 파워업 상태를 업데이트합니다.
     */
    private void updatePowerUpState() {
        powerUpTimer--;
        if (powerUpTimer <= 0) {
            powerUpActive = false;
            for (Ghost ghost : ghosts) {
                ghost.setScared(false);
            }
        }
    }

    /**
     * 레벨 클리어 상태를 확인합니다.
     */
    public void checkLevelCleared() {
        if (allPointsCollected() && !levelCleared) {
            levelCleared = true;
            resetBoard(); // 보드를 리셋
            mainGame.resetPositions(); // 캐릭터 위치 초기화
        }
    }

    /**
     * Pac-Man의 위치를 처리합니다.
     */
    private void handlePacmanPosition() {
        int pacX = pacMan.getX();
        int pacY = pacMan.getY();

        switch (board[pacY][pacX]) {
            case 2 -> { // 파워업 (큰 점)
                activatePowerUp1();
                board[pacY][pacX] = -1; // 점을 없앰
            }
            case 0 -> { // 점수 포인트
                board[pacY][pacX] = -1;
                pacMan.addScore(1);
            }
            case 3 -> { // 체리
                board[pacY][pacX] = -1;
                pacMan.addScore(5);
            }
            case 4 -> { // 방어 아이템
                pacMan.activateInvincibility(5000);
                board[pacY][pacX] = -1;
            }
            case 5 -> { // 속도 아이템
                pacMan.activateSpeedChange(1, 5000); // 속도 증가
                mainGame.setPacManSpeed(true); // Pac-Man 속도 변경
                new Timer(5000, e -> mainGame.setPacManSpeed(false)).start();
                board[pacY][pacX] = -1;
            }
            case 6 -> { // 유령 혼란 아이템
                pacMan.activateConfusion(5000);
                board[pacY][pacX] = -1;

                // 모든 유령 혼란 상태 설정
                for (Ghost ghost : ghosts) {
                    if (!(ghost instanceof BossGhost)) {
                        ghost.setConfused(true);
                    }
                }

                // 5초 후 혼란 상태 해제
                Timer confusionTimer = new Timer(5000, e -> {
                    for (Ghost ghost : ghosts) {
                        if (!(ghost instanceof BossGhost)) {
                            ghost.setConfused(false);
                        }
                    }
                });
                confusionTimer.setRepeats(false);
                confusionTimer.start();
            }
        }
    }

    /**
     * 체리를 생성합니다.
     */
    private void spawnCherry() {
        if (countCherries() < maxCherries && random.nextInt(100) < 5) {
            int x, y;
            do {
                x = random.nextInt(board[0].length);
                y = random.nextInt(board.length);
            } while (board[y][x] != 0); // 빈 공간에만 생성
            board[y][x] = 3; // 체리 배치
        }
    }

    /**
     * 현재 보드에 체리의 개수를 반환합니다.
     * 
     * @return 체리의 개수
     */
    private int countCherries() {
        int count = 0;
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 3) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 보드를 초기화합니다.
     */
    private void resetBoard() {
        board = new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 2, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1},
            {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
            {1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        pacMan.setPosition(19, 1); // Pac-Man 초기 위치
        pacMan.setLives(3); // 초기 생명 설정
        for (Ghost ghost : ghosts) {
            ghost.resetPosition(); // 유령 초기 위치로 리셋
            ghost.setScared(false); // 공포 상태 해제
        }
        powerUpActive = false;
        powerUpTimer = 0;
        ghostEatCombo = 0;
        gameEnded = false;
        levelCleared = false;
        bossSpawned = false;
        placeRandomItems(); // 아이템 배치
    }

    /**
     * 보드에 모든 점수를 수집했는지 확인합니다.
     * 
     * @return 모든 점수를 수집했는지 여부
     */
    private boolean allPointsCollected() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0 || cell == 2) {
                    return false; // 수집하지 않은 점 또는 파워업이 남아 있음
                }
            }
        }
        return true;
    }

    /**
     * 게임 보드의 현재 상태를 그리는 메서드입니다.
     * 각 셀에 따라 다양한 요소(벽, 점수, 파워업, 체리 등)를 화면에 렌더링합니다.
     * 
     * @param g Graphics 객체로 화면에 그리기를 수행합니다.
     */
    private void drawBoard(Graphics g) {
        for (int y = 0; y < board.length; y++) { // 보드의 행 반복
            for (int x = 0; x < board[y].length; x++) { // 보드의 열 반복
                if (board[y][x] == 1) { // 벽
                    g.setColor(Color.BLUE); // 벽 색상 설정
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize); // 벽 렌더링
                } else if (board[y][x] == 0) { // 점 (일반 포인트)
                    g.setColor(Color.BLACK); // 배경 색상 설정
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize); // 배경 렌더링
                    g.setColor(Color.WHITE); // 점 색상 설정
                    g.fillOval(x * cellSize + cellSize / 3, y * cellSize + cellSize / 3, cellSize / 3, cellSize / 3); // 점 렌더링
                } else if (board[y][x] == 2) { // 파워업
                    g.setColor(Color.BLACK); // 배경 색상 설정
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize); // 배경 렌더링
                    g.drawImage(powerUpImage, x * cellSize, y * cellSize, cellSize, cellSize, null); // 파워업 이미지 렌더링
                } else if (board[y][x] == 3) { // 체리
                    g.setColor(Color.BLACK); // 배경 색상 설정
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize); // 배경 렌더링
                    g.drawImage(cherryImage, x * cellSize, y * cellSize, cellSize, cellSize, null); // 체리 이미지 렌더링
                } else if (board[y][x] == 4) { // 방어 아이템
                    g.setColor(Color.BLACK); // 배경 색상 설정
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize); // 배경 렌더링
                    g.drawImage(shieldItemImage, x * cellSize, y * cellSize, cellSize, cellSize, null); // 방어 아이템 이미지 렌더링
                } else if (board[y][x] == 5) { // 속도 아이템
                    g.setColor(Color.BLACK); // 배경 색상 설정
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize); // 배경 렌더링
                    g.drawImage(speedItemImage, x * cellSize, y * cellSize, cellSize, cellSize, null); // 속도 아이템 이미지 렌더링
                } else if (board[y][x] == 6) { // 유령 혼란 아이템
                    g.setColor(Color.BLACK); // 배경 색상 설정
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize); // 배경 렌더링
                    g.drawImage(confusionItemImage, x * cellSize, y * cellSize, cellSize, cellSize, null); // 혼란 아이템 이미지 렌더링
                } else if (board[y][x] == -1) { // 빈 공간
                    g.setColor(Color.BLACK); // 빈 공간 배경 색상 설정
                    g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize); // 빈 공간 렌더링
                }
            }
        }
    }


    /**
     * 현재 점수와 생명을 화면에 표시하는 메서드입니다.
     * 
     * @param g Graphics 객체로 점수와 생명을 화면에 렌더링합니다.
     */
    private void drawScoreAndLives(Graphics g) {
        g.setColor(Color.WHITE); // 텍스트 색상 설정
        g.setFont(new Font("Arial", Font.BOLD, 16)); // 텍스트 폰트 설정
        g.drawString("Score: " + pacMan.getScore(), 10, 20); // 점수 표시
        g.drawString("Lives: " + pacMan.getLives(), 10, 40); // 생명 수 표시
    }
    
    /**
     * 현재 카운트다운 텍스트를 반환합니다.
     * 
     * @return 카운트다운 텍스트
     */
    public String getCountdownText() {
        return countdownText;
    }

    /**
     * 현재 보드 상태를 반환합니다.
     * 
     * @return 게임 보드의 2차원 배열
     */
    public int[][] getBoard() {
        return board;
    }
    
    /**
     * 현재 보스 유령이 스폰되었는지 확인합니다.
     * 
     * @return 보스 유령이 스폰되었으면 true, 아니면 false
     */
    public boolean isBossSpawned() {
        return bossSpawned;
    }

    /**
     * 현재 보드에서의 보스 유령 객체를 반환합니다.
     * 
     * @return 보스 유령 객체
     */
    public BossGhost getBossGhost() {
        return bossGhost;
    }
}
