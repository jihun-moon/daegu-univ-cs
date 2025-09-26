package 실습;

import java.util.Scanner;

public class WordGameApp {
    private Scanner scan;
    private String startWord = "아버지";  // 시작 단어
    private Player[] players;  // 플레이어 배열

    // 생성자
    public WordGameApp() {
        scan = new Scanner(System.in);
    }

    // 참가자 수와 이름을 입력받아 플레이어 생성
    public void createPlayers() {
        System.out.print("참가자 수를 입력하세요: ");
        int numPlayers = scan.nextInt();
        players = new Player[numPlayers];
        
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("참가자의 이름을 입력하세요: ");
            String name = scan.next();
            players[i] = new Player(name);
        }
    }

    // 게임 실행
    public void run() {
        createPlayers();  // 참가자 생성
        String lastWord = startWord;
        System.out.println("첫 단어는 '" + lastWord + "'입니다.");

        int playerIndex = 0;  // 첫 번째 플레이어부터 시작
        while (true) {
            Player currentPlayer = players[playerIndex];
            String newWord = currentPlayer.sayWord();

            // 끝말잇기 성공 여부 확인
            if (!currentPlayer.succeed(lastWord)) {
                System.out.println(currentPlayer.getName() + "님이 패배하였습니다!");
                break;
            }

            // 마지막 단어 갱신
            lastWord = newWord;

            // 다음 플레이어로 순서 이동
            playerIndex = (playerIndex + 1) % players.length; 
            // 초기값 0 -> e.g: 1 % 4 = 1 -> 2 % 4 -> 3 % 4 -> 4 % 4 = 0 :: 총 5명
        }
    }

    // main 메서드
    public static void main(String[] args) {
        WordGameApp game = new WordGameApp();
        game.run();  // 게임 시작
    }
}
