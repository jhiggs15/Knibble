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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

/**
 * Test cases for the KnibbleGameManager.
 */
class KnibbleGameManagerTest
{
    @Test
    void createNewGame()
    {
        assertNotNull(new KnibbleGameManager(makePlayers("A", "B")));
    }
    
    @Test
    void enterHoldingsFor2Players()
    {
        KnibbleGameManager manager = new KnibbleGameManager(makePlayers("A", "B"));
        manager.enterHoldings(makeHoldings(2, 0));
        assertTrue(true);
    }
    
    @Test
    void enterFirstRoundInfoFor2Players()
    {
        KnibbleGameManager manager = new KnibbleGameManager(makePlayers("A", "B"));
        manager.enterHoldings(makeHoldings(2, 0));
        manager.enterGuesses(makeGuesses(3, 2));
        assertTrue(true);
    }

    @ParameterizedTest
    @MethodSource("gameProvider")
    void validateRounds(List<String>players, List<String> expected, List<RoundInput> rounds)
    {
        KnibbleGameManager manager = new KnibbleGameManager(players);
        String result = null;
        int index = 0;
        for (RoundInput ri : rounds) {
            manager.enterHoldings(ri.holdings);
            manager.enterGuesses(ri.guesses);
            result = manager.playRound();
            assertEquals(expected.get(index++), result);
        }
    }
    
    @ParameterizedTest
    @MethodSource("gameProvider")
    void validateEndGame(List<String>players, List<String> expected, List<RoundInput> rounds)
    {
        KnibbleGameManager manager = new KnibbleGameManager(players);
        String result = null;
        for (RoundInput ri : rounds) {
            manager.enterHoldings(ri.holdings);
            manager.enterGuesses(ri.guesses);
            result = manager.playRound();
        }
        assertEquals(expected.get(expected.size() - 1), result);
    }

    @ParameterizedTest
    @MethodSource("getResultProvider")
    void guessIsCorrect(List<String>players, String result, int playerIndex)
    {
        KnibbleGameManager manager = new KnibbleGameManager(players);
        assertEquals(manager.getResult(playerIndex), result);
    }
    
    /**************************** Providers ****************************/
    static Stream<Arguments> gameProvider()
    {
        return Stream.of(
            arguments(makePlayers("A", "B"), makeRoundResults("A loses"), makeRoundInputs(
                new RoundInput(makeHoldings(2, 0), makeGuesses(3, 2))
                )),
            arguments(makePlayers("A", "B"), makeRoundResults("B loses"), makeRoundInputs(
                new RoundInput(makeHoldings(2, 0), makeGuesses(2, 3))
                )),
            arguments(makePlayers("A", "B"), makeRoundResults("", "B loses"), makeRoundInputs(
                    new RoundInput(makeHoldings(1, 0), makeGuesses(2, 3)),
                    new RoundInput(makeHoldings(2, 2), makeGuesses(4,1))
            )),
            arguments(makePlayers("A", "B", "C"), makeRoundResults("A wins", "C loses"), makeRoundInputs(
                    new RoundInput(makeHoldings(2, 1, 0), makeGuesses(3, 1, 2)),
                    new RoundInput(makeHoldings(3, 3), makeGuesses(6, 3))
            )),
            arguments(makePlayers("A", "B", "C"), makeRoundResults("", "", "", "", "A wins", "", "", "B loses"), makeRoundInputs(
                    new RoundInput(makeHoldings(1, 2, 1), makeGuesses(2, 3, 1)),
                    new RoundInput(makeHoldings(2, 1, 2), makeGuesses(2, 3, 4)),
                    new RoundInput(makeHoldings(1, 2, 1), makeGuesses(2, 3, 1)),
                    new RoundInput(makeHoldings(2, 1, 2), makeGuesses(2, 3, 4)),
                    new RoundInput(makeHoldings(1, 0, 0), makeGuesses(1, 0, 2)),
                    new RoundInput(makeHoldings(2, 1), makeGuesses(2, 1)),
                    new RoundInput(makeHoldings(2, 2), makeGuesses(2, 2)),
                    new RoundInput(makeHoldings(0, 3), makeGuesses(0, 3))
            ))

        );
    }

    static Stream<Arguments> getResultProvider()
    {
        return Stream.of(
                arguments(makePlayers("A", "B"), makeResult("B", false), 0),
                arguments(makePlayers("A", "B", "C", "D"), makeResult("A", true), 0)

        );
    }
    
    /**************************** Helper Methods ****************************/
    static List<Integer> makeHoldings(Integer... holdings)
    {
        return new ArrayList<>(Arrays.asList(holdings));
    }
    
    static List<Integer> makeGuesses(Integer... guesses)
    {
        return new ArrayList<>(Arrays.asList(guesses));
    }
    
    static List<String> makePlayers(String... players)
    {
        return new ArrayList<>(Arrays.asList(players));
    }
    
    static List<RoundInput> makeRoundInputs(RoundInput... inputs)
    {
        return new ArrayList<>(Arrays.asList(inputs));
    }

    static List<String> makeRoundResults(String... roundsResults) { return new ArrayList<>(Arrays.asList(roundsResults)); }

    /**
     *
     * @param player The index that corresponds to the player in the players arraylist
     * @param hasWon True if the player has won and False if they have lost
     * @return the string representing the result of a round
     */
    static String makeResult(String player, boolean hasWon)
    {
        if(hasWon) return player + " wins";
        else return player + " loses";
    }

}

class RoundInput
{
    final List<Integer> holdings;
    final List<Integer> guesses;
    
    public RoundInput(List<Integer> holdings, List<Integer> guesses)
    {
        this.holdings = holdings;
        this.guesses = guesses;
    }
}
