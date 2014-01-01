package ui;

import java.util.LinkedList;

import deck.card.Card;

/**
 */
public abstract class Player {
	/**
	 * Holds the cards of this players hand
	 */
	private LinkedList<Card> cards;
	
	/**
	 * 
	 */
	private int handSize;
	
	/**
	 * Holds the max amount of cards a player can have
	 */
	public static final int NUMBER_OF_CARDS = 2;
	
	/**
	 * 
	 */
	public Player() {
		cards = new LinkedList<Card>();
	}
	
	/**
	 * 
	 * @param i
	 * Plays one of the cards on this players hand.
	 * i is the index of the card to play.
	 * If the player has 2 cards, which it should when playing a card
	 * and this method is called with 0 as i, the other card on the player hand
	 * will get a new index of 0.
	 * This is because the list of cards on the players hand is a LinkedList, so when
	 * a card is removed from the players hand, the list "collapses upon itself"
	 * 
	 * @param i The card to play
	 * 
	 * @throws IndexOutOfBoundsException If the card does not exist
	 */
	protected final void playCard(short i) {
		
	}
	
	/**
	 * Check if the player has a specific card
	 * 
	 * @param cardName The name of the card to check if the player has
	 * @return Do you really need an explanation expect boolean????
	 */
	public final boolean hasCard(String cardName) {
		for (Card card : cards) {
			String theCardName = card.getName();
			if (theCardName.compareTo(cardName) == 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if the player has a specific card
	 * 
	 * @param card The card to check if the player has
	 * @return
	 */
	public final boolean hasCard(Card card) {
		return hasCard(card.getName());
	}
	
}
