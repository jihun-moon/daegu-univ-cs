package 실습;

import java.util.*;

class Deck{
	private List<Card> deck;
	private String[] suits = {"하트", 
			"다이아몬드", "클로버", "스페이드"};
	private String[] numbers = {"2", "3", "4", "5",
			"6", "7", "8", "9", "10", "J", "Q",
			"K", "A"};
	
	public Deck() {
		deck = new ArrayList<>();
		for (String suit : suits) {
			for (String number : numbers) {
				deck.add(new Card(suit, number));
			}
		}
		shuffle();
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public Card deal() {
		return deck.remove(deck.size() - 1);
	}
		
}


