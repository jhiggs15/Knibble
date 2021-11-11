/*******************************************************************************
 * This files was developed for CS4533: Techniques of Programming Language Translation
 * and/or CS544: Compiler Construction
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2020-21 Gary F. Pollice
 *******************************************************************************/

package knibble;

import java.util.List;

/**
 * Main class for managing Knibble games for the
 * TDD programming assignment.
 */
public class KnibbleGameManager
{

    List<String> players;
    Integer coinTotal;
    List<Integer> guesses;

    /**
     * This is the only constructor for the game manager.
     * @param players the names of the players in the order 
     * of the first round of play and for when the holdings
     * and guesses are made.
     */
    public KnibbleGameManager(List<String> players)
    {
        this.players = players;
    }
    
    /**
     * Enter the holdings for the players remaining. The
     * order of the holdings follow the original list of
     * players when the game was created. If a player has
     * won a round, they do not enter a holding.
     * @param coins the number of coins for each player (0-3)
     */
    public void enterHoldings(List<Integer> coins)
    {
        // sums all values in the list
        this.coinTotal = coins.stream().reduce(0, (total, current) -> total + current);
    }
    
    /**
     * Enter the guesses for the players remaining. The
     * order of the guesses follow the original list of
     * players when the game was created. If a player has
     * won a round, they do not enter a guess.
     * @param guesses the guess for each player
     */
    public void enterGuesses(List<Integer> guesses)
    {
        this.guesses = guesses;
    }
    
    /**
     * Play the current round and return the result
     * @return "X wins round" if player X wins a round,
     *  "X loses" if player X is the last player in the
     *  game, and "" if there is no
     */
    public String playRound() {

        int player = 0; // index of player in players
        for(Integer guess : guesses)
        {
            if(guessIsCorrect(guess))
            {
                String result = getResult(player);
                players.remove(player);
                return result;
            }
            player++;
        }

        return "";
    }

    /**
     * Determine if a guess is correct.
     * Makes code more readable
     * @param guess a guess of the number of chips in all players hands
     * @return
     */
    protected boolean guessIsCorrect(int guess){ return guess == coinTotal; }

    /**
     * Gets the result of a game given a guess is correct
     * @param playerThatGuessedCorrectly the index in players of the player that guessed correctly
     * @return
     */
    protected String getResult(int playerThatGuessedCorrectly)
    {
        if(players.size() == 2) // if there are only two players and there has been a correct guess a player loses
            return players.get(1 - playerThatGuessedCorrectly) + " loses";
        else // otherwise a player has won
            return players.get(playerThatGuessedCorrectly) + " wins";
    }

}
