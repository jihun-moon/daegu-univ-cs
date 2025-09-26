/**
 * MainGame.java - 게임의 메인 클래스
 * 
 * - 게임의 메인 프레임과 루프를 관리합니다.
 * - Pac-Man과 유령의 초기 설정, 키보드 입력 처리, 타이머 기반 게임 루프 등을 구현합니다.
 * 
 * 게임 규칙:
 * 
 * 1. 체리 (Cherry):
 *    - 먹으면 5점
 * 
 * 2. 스케얼드 유령 잡기 (Scared Ghost Eating):
 *    - 스케얼드 상태인 유령을 잡으면 점수가 증가합니다.
 *    - 첫 번째 유령: 20점
 *    - 두 번째 유령: 30점
 *    - 세 번째 유령: 40점
 *    - 네 번째 유령: 50점
 * 
 * 3. 일반 포인트 (Normal Points):
 *    - 일반 포인트 먹을 때마다 1점
 * 
 * 4. 유령의 속도 배수 증가 시점 (Blinky 속도 배수):
 *    - 1.0배: 0점 ~ 999점까지
 *    - 1.1배: 1,000점 ~ 1,099점까지
 *    - 1.2배: 1,100점 ~ 1,199점까지
 *    - 1.3배: 1,200점 ~ 1,299점까지
 *    - 1.4배: 1,300점 ~ 1,399점까지
 *    - 1.5배: 1,400점 ~ 1,499점까지
 * 
 * 5. 각 유령의 특징:
 *    - Blinky (빨간색 유령): 항상 Pac-Man을 추적하는 그림자. 속도가 점수에 따라 증가.
 *    - Clyde (주황색 유령): 일정 거리 이상 떨어지면 임의로 떠돌며 느린 속도를 유지.
 *    - Inky (하늘색 유령): 예측 불가능하게 Blinky와 Pac-Man을 기준으로 움직임.
 *    - Pinky (핑크색 유령): Pac-Man의 앞을 추적하여 공격. 예측 가능한 경로로 움직임.
 * 
 * 6. 보스 유령 (Boss Ghost):
 *    - 일정 점수마다 나타나는 특별한 유령으로, 일반 유령과 다른 행동을 보임.
 *    - 주요 능력:
 *        - 순간이동: 일정 시간마다 보드의 빈 공간 중 하나로 텔레포트.
 *        - 빠른 이동: 등장 후 일정 시간 동안 일반 유령보다 빠르게 움직임.
 *        - 공포 상태에서 제외: 파워업을 먹어도 보스 유령은 공포 상태로 변하지 않음.
 *    - 보스 유령은 일정 시간이 지나면 사라지며, 사라지기 전에 Pac-Man을 공격하거나 혼란에 빠뜨릴 수 있음.
 * 
 * 주요 기능:
 * 1. 게임 보드와 캐릭터 초기화
 * 2. 키보드 입력 처리
 * 3. 타이머를 이용한 Pac-Man과 유령의 이동
 * 4. 카운트다운 시작과 게임 루프 실행
 */



package pacman;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MainGame extends JFrame {
    private static final long serialVersionUID = -5290771177593536351L;

    // 게임 보드와 캐릭터 객체
    private GameBoard gameBoard; // 게임 보드 패널
    private PacMan pacMan; // Pac-Man 객체
    private List<Ghost> ghosts; // 유령 객체 리스트

    // 게임 상태 플래그
    private boolean gameRunning = false; // 게임이 실행 중인지 여부

    // 타이머
    private Timer pacManTimer, ghostTimer, roundCheckTimer, bossGhostTimer; // 여러 루프 타이머

    // Pac-Man 속도 관련
    private int pacManSpeed = 150; // 기본 속도(ms)
    private int fastPacManSpeed = 75; // 속도 아이템 효과 적용 시 속도(ms)

    /**
     * MainGame 생성자 - 게임 초기화
     */
    public MainGame() {
        setTitle("Pac-Man"); // 창 제목 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 설정

        // Pac-Man 초기화
        pacMan = new PacMan(19, 1);

        // 유령 리스트 초기화
        ghosts = new ArrayList<>();
        Blinky blinky = new Blinky(10, 9, "/resources/blinky_left.png", "/resources/blinky_right.png", "/resources/scared_ghost.png");
        ghosts.add(blinky); // 빨간 유령 Blinky 추가
        ghosts.add(new Pinky(10, 10, "/resources/pinky_left.png", "/resources/pinky_right.png", "/resources/scared_ghost.png")); // 핑크 유령 Pinky 추가
        ghosts.add(new Inky(11, 9, "/resources/inky_left.png", "/resources/inky_right.png", "/resources/scared_ghost.png", blinky)); // 하늘색 유령 Inky 추가
        ghosts.add(new Clyde(11, 10, "/resources/clyde_left.png", "/resources/clyde_right.png", "/resources/scared_ghost.png")); // 주황색 유령 Clyde 추가

        // 게임 보드 초기화
        gameBoard = new GameBoard(pacMan, ghosts, this);
        add(gameBoard); // 보드를 JFrame에 추가

        pack(); // 프레임 크기 조정
        setResizable(false); // 창 크기 고정
        setVisible(true); // 창 표시

        // 키보드 입력 처리
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameRunning) { // 게임 실행 중일 때만 입력 허용
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP -> pacMan.setDirection(0, -1); // 위로 이동
                        case KeyEvent.VK_DOWN -> pacMan.setDirection(0, 1); // 아래로 이동
                        case KeyEvent.VK_LEFT -> pacMan.setDirection(-1, 0); // 왼쪽으로 이동
                        case KeyEvent.VK_RIGHT -> pacMan.setDirection(1, 0); // 오른쪽으로 이동
                    }
                }
            }
        });

        startCountdown(); // 첫 게임 시작 시 카운트다운 실행
    }

    /**
     * 카운트다운 타이머 시작
     * - 게임 시작 전 딜레이를 추가해 준비 시간을 제공합니다.
     */
    private void startCountdown() {
        gameRunning = false; // 카운트다운 중에는 게임 중지
        gameBoard.setCountdownText("4"); // 초기 카운트다운 값 설정

        Timer countdownTimer = new Timer(1000, e -> {
            int timeLeft;
            try {
                timeLeft = Integer.parseInt(gameBoard.getCountdownText()); // 현재 카운트다운 값 읽기
            } catch (NumberFormatException ex) {
                timeLeft = 4; // 값이 없을 경우 기본값 설정
            }

            if (timeLeft > 1) {
                gameBoard.setCountdownText(String.valueOf(timeLeft - 1)); // 카운트다운 감소
            } else {
                ((Timer) e.getSource()).stop(); // 타이머 중지
                gameBoard.setCountdownText(""); // 카운트다운 텍스트 제거
                gameRunning = true; // 게임 시작
                startGameLoop(); // 게임 루프 실행
            }
            gameBoard.repaint(); // 화면 갱신
        });

        countdownTimer.setInitialDelay(0); // 즉시 시작
        countdownTimer.start(); // 카운트다운 시작
    }

    /**
     * Pac-Man 속도 변경
     * - 속도 아이템 효과 발동 시 호출됩니다.
     */
    public void setPacManSpeed(boolean isFast) {
        if (isFast) {
            pacManTimer.setDelay(fastPacManSpeed); // 빠른 속도 설정
        } else {
            pacManTimer.setDelay(pacManSpeed); // 기본 속도 복귀
        }
    }

    /**
     * 게임 루프 시작
     * - Pac-Man과 유령의 이동, 보스 스폰 등을 타이머로 관리합니다.
     */
    private void startGameLoop() {
        // 이전 타이머가 실행 중이면 중지
        if (pacManTimer != null && pacManTimer.isRunning()) pacManTimer.stop();
        if (ghostTimer != null && ghostTimer.isRunning()) ghostTimer.stop();
        if (roundCheckTimer != null && roundCheckTimer.isRunning()) roundCheckTimer.stop();
        if (bossGhostTimer != null && bossGhostTimer.isRunning()) bossGhostTimer.stop();

        // Pac-Man 이동 타이머
        pacManTimer = new Timer(pacManSpeed, e -> {
            if (gameRunning) {
                pacMan.move(gameBoard.getBoard()); // Pac-Man 이동
                gameBoard.repaint(); // 화면 갱신
            }
        });

        // 유령 이동 타이머
        ghostTimer = new Timer(220, e -> {
            if (gameRunning) {
                for (Ghost ghost : ghosts) {
                    ghost.move(gameBoard.getBoard(), pacMan.getX(), pacMan.getY(), pacMan.getDirectionX(), pacMan.getDirectionY(), ghosts, pacMan.getScore());
                }
            }
        });

        // 보스 유령 이동 타이머
        bossGhostTimer = new Timer(350, e -> {
            if (gameRunning && gameBoard.isBossSpawned()) {
                BossGhost bossGhost = gameBoard.getBossGhost();
                if (bossGhost != null) {
                    bossGhost.move(gameBoard.getBoard(), pacMan.getX(), pacMan.getY(), pacMan.getDirectionX(), pacMan.getDirectionY(), ghosts, pacMan.getScore());
                }
            }
        });

        // 레벨 클리어 및 보스 스폰 체크 타이머
        roundCheckTimer = new Timer(1000, e -> {
            if (gameRunning) {
                gameBoard.checkLevelCleared(); // 레벨 완료 체크
                gameBoard.spawnBossGhost(pacMan.getScore()); // 보스 스폰 체크
            }
        });

        // 타이머 시작
        pacManTimer.start();
        ghostTimer.start();
        bossGhostTimer.start();
        roundCheckTimer.start();
    }

    /**
     * 게임 위치 초기화
     * - Pac-Man과 유령의 위치를 기본값으로 설정합니다.
     */
    public void resetPositions() {
        gameRunning = false; // 초기화 중 게임 중지
        pacMan.setPosition(19, 1); // Pac-Man 초기 위치 설정
        for (Ghost ghost : ghosts) {
            if (ghost instanceof Blinky) ghost.setPosition(10, 9); // Blinky 초기 위치
            else if (ghost instanceof Pinky) ghost.setPosition(10, 10); // Pinky 초기 위치
            else if (ghost instanceof Inky) ghost.setPosition(11, 9); // Inky 초기 위치
            else if (ghost instanceof Clyde) ghost.setPosition(11, 10); // Clyde 초기 위치
        }
        gameBoard.repaint(); // 화면 갱신
        startCountdown(); // 카운트다운 다시 시작
    }

    /**
     * 메인 메서드
     * - 프로그램 실행 시작점.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGame::new); // 메인 게임 실행
    }
}