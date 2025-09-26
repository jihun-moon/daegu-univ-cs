package 실습;

import java.util.*;

class Player {
	private List<Card> hand;
	
	public Player() {
		hand = new ArrayList<>();
	}
	
	public void getCard(Card card) {
		hand.add(card);
	}
	
	public void showCards() {
		System.out.println("(" + hand + ")");
	}
}
