package 실습;

import java.util.*;

public class Player {
    private String name;
    private String word;
    private Scanner scan;

    public Player(String name) {
        this.name = name;
        scan = new Scanner(System.in);
    }

    // 플레이어 이름 반환
    public String getName() {
        return name;
    }

    // 단어 입력
    public String sayWord() {
        System.out.print(name + ">> ");
        word = scan.next();
        return word;
    }

    // 끝말잇기 성공 여부를 확인하는 메서드
    public boolean succeed(String lastWord) {
        int lastIndex = lastWord.length() - 1;
        if (lastWord.charAt(lastIndex) == word.charAt(0)) return true; 
        else 											 return false;
    }
}

