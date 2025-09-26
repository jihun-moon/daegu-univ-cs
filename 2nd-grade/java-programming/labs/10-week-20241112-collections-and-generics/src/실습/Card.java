package 실습;

class Card {
	private String suit;
	private String number;
	
	public Card(String suit, String number) {
		this.suit = suit;
		this.number = number;
	}
	
	@Override
	public String toString() {
		return suit + " " + number;
	}
}
