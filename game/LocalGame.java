package game;

import java.util.ArrayList;

import player.BotPlayer;
import player.LocalPlayer;
import player.Player;
import view.TerminalView;
import deck.Deck;
import deck.card.Card;

public class LocalGame extends Game {
	
	public String localPlayerName;
	
	public LocalGame() {
		this.init();
		this.start();
	}
	
	@Override
	public void init() {
		view = new TerminalView();
		view.setInformation("Welcome to LoveLetters!");
		
		// Get name for player
		view.setInformation("What is your name, charmer?");
		localPlayerName = view.getInformation();
 	}
	
	@Override
	public void start() {
		gameState = GameState.Menu;
		gameLoop();
	}
	
	@Override
	public void gameLoop() {
		while(true) {
			
			switch(gameState) {
				case Menu:
					int opponents = -1, bNameIndex;
					
					// Announce new game
					view.setInformation("==== NEW GAME ====");
		
					// Get number of wished opponents from view
					while (true) {
						view.setInformation("Please specify wished number of opponents? (1-3) ");
						try {
							opponents = Integer.parseInt(view.getInformation());
							if (opponents > 0 && opponents < 4) break;
		
						} catch (Exception e) {
							view.setInformation("Please specify a number.");
						}
					}
					
					// Clear player list before new game
					players = new ArrayList<Player>();
					
					// Add Local Player in players list
					players.add( new LocalPlayer(localPlayerName, this));
					
					// Add wished number of BOTs to the player list
					for (int c = 0; c < opponents; c++) {
						bNameIndex =  (int) (BotPlayer.NUM_BOT_NAMES * Math.random());
						playerJoin(new BotPlayer( BotPlayer.botNames[bNameIndex], this ));
					}
					
					// Pick randomly first player
					currentPlayerIndex =  (int) (getNumPlayers() * Math.random());
					view.setInformation("Player " + players.get(currentPlayerIndex).getName() + " goes first.");
					
					// Update gamestate variable to Game
					gameState = GameState.Game;
					break; // End of Main case
					
				case Game:
					Player currentPlayer, winner;
					
					// Announce new round!
					view.setInformation("====== NEW ROUND ======");
					
					// Shuffle up deck and get ready to play! 
					deck = new Deck();
					deck.shuffle(10000);
					
					// Remove first card of shuffled deck
					view.setInformation("Setting a card aside.");
					removedAtStart = deck.draw();
					
					// Each player draws a starting card
					view.setInformation("Each player draws their card.");
					for (Player p : players) {
						p.drawCard(deck.draw());
					}
					
					while( getNumPlayers() > 1 && deck.peek() != null ) {
						// Retrieve current player and let the player draw a card.
						currentPlayer = players.get(currentPlayerIndex);
						view.setInformation("Players turn: " + currentPlayer.getName());
						currentPlayer.drawCard(deck.draw());
						
						// Let current player play a card
						currentPlayer.playCard();
						
						view.setInformation("");
						nextPlayer();
					}
					
					// End of round - retrieve winner, update letters delivered 
					// and announce winner of round
					winner = getWinner();
					winner.incrementLettersDelivired();
					view.setInformation("Player " + winner.getName() + " won the round with Card: " 
										+ winner.getCard(0).getName() + " (" + winner.getCard(0).getDistance() + ")");
					
					// Announce end of round
					view.setInformation("===== END OF ROUND =====");
					
					if (winner.getLettersDelivired() >= lettersDeliveredToWin) {
						// Announce end of game
						view.setInformation("Player " + winner.getName() + " won the game.");
						view.setInformation("===== END OF GAME =====");
						
						// Do you want to play again?
						String answer = "";
						while (true) {
							view.setInformation("Do you want to play again? (Y/N)");
							answer = view.getInformation().toLowerCase();
							if (answer.compareTo("y") == 0){
								gameState = GameState.Menu;
								break;
							} else if (answer.compareTo("n") == 0){
								gameState = GameState.Exit;
								break;
							} else {
								view.setInformation("Please specify Y or N");
							}
						}
					}
					
					break; // End of Game case
				case Exit:
					view.setInformation("Thanks for playing!");
					System.exit(0);
					break; // End of Exit case
			} // End of switch
		} // End of main loop
	}

	public Player askPlayerForPlayer(Player player, String message) {
		return null;
	}
	
	public Player askPlayerForPlayer(Player player) {
		return askPlayerForPlayer(player, "Choose a player");
	}

	public Card askPlayerForCard(Player player, String message) {
		return null;
	}
	
	public Card askPlayerForCard(Player player) {
		return askPlayerForCard(player, "Choose a card");
	}
}
