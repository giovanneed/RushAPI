package ia.tictactoe.agents;

import java.util.*;
import ia.tictactoe.game.*;
import ia.tictactoe.agents.BasicAgent;

/**
 * This is the implementation of an Agent for the TicTacToe Game.
 * A Simple Agent using Minimax.
 * @author  Alair Dias Júnior
 * @version 1.0
 */
public class BitHead implements BasicAgent
{

	/**
	 * A simple data class for storing the moves
	 * Used by the successor function
	 * @author  Alair Dias Júnior
	 * @version 1.0
	 */
    private class Move
    {
        public Move(int px, int py)
        {
            posX = px;
            posY = py;
        }
        int posX;
        int posY;
    }

    /** 
     * Returns the opponent in the current game.
     * @param game A {@link TicTacToe} object
     * @return The number of the opponent.
     */
    private int opponent(TicTacToe game)
    {
        return (game.getPlayerNumber() == 0) ? 1 : 0;
    }

    /** 
     * List all possible movements from the current state
     * @param game A {@link TicTacToe} object
     * @return A LinkedList of move objects
     */
    private LinkedList<Move> successors(TicTacToe game)
    {
        LinkedList<Move> successor = new LinkedList<Move>();
        
        for (int i = 0; i < game.getWidth(); ++i)
            for (int j = 0; j < game.getHeight(); ++j)
                if (game.checkLegalMove(i, j))
                    successor.add(new Move(i, j));
        return successor;
    }

    
    /** 
     * Called by the server for the player to execute its move.
     * @param game An object of type {@link TicTacToe}.
     * @return Should return false when the player give up the game. True otherwise.
     */
    public boolean move(final TicTacToe game)
    {
        Move m = minimax(game);

        if (game.move(m.posX, m.posY))
            return true;

        System.out.println("Impossible to move");
        return false;
    }

    /** 
     * This is a simple implementation of the Minimax Algorithm.
     * The method minimax is the frontend of the algorithm, implementing
     * a version of the Max side and storing the best move so far.
     * @param game An object of type {@link TicTacToe}.
     * @return The best move found for the current state.
     */
    private Move minimax(TicTacToe game)
    {
        int bestValue = -10; // Initiate the best value with (-infinite).
        					 
        LinkedList<Move> successor = successors(game); // List the possible moves from the
        											   // current state
        Move bestMove = null;
        while(successor.size() > 0) // While there is a move to test
        {
            Move m = successor.removeFirst(); // Gets the move to test
            game.move(m.posX, m.posY); // executes the move
            int value = minValue(game.copy(opponent(game))); // calculates the outcomes
            												 // of the move
            
            if (value > bestValue) // if the value of the move is better than the previous
            {					   // best move, save this move and the value
                bestValue = value;
                bestMove = m;
            }
            game.undoMove(); // Undo the move to test the next, if available
        }

        return bestMove; // returns the best move found
    }

    /** 
     * This is an implementation of the Minimax Algorithm. The Maxvalue portion.
     * This method maximizes all the minimum values tested from the current state.
     * @param game An object of type {@link TicTacToe}.
     * @return The Maximum value achieved from the current state.
     */
    private int maxValue(TicTacToe game)
    {
        if (game.checkEndState() == game.getPlayerNumber()) return 1; // if this is a win state
        else if (game.checkEndState() == opponent(game)) return -1;   // if this is a lose state
        else if (game.checkEndState() == 3) return 0;                 // if this is a tie state

        int bestValue = -10; // Initiate the best value with (-infinite).

        LinkedList<Move> successor = successors(game);        // List the possible moves from the
        										              // current state
        
        while(successor.size() > 0)                           // While there is a move to test
        {
            Move m = successor.removeFirst();                 // Gets the move to test
            game.move(m.posX, m.posY);                        // executes the move
            int value = minValue(game.copy(opponent(game)));  // calculates the outcomes
            												  // of the move
            if (value > bestValue)						// if the value of the move is better than the previous
            	bestValue = value; 						// best move, save this move and the value
                

            game.undoMove();                            // Undo the move to test the next, if available
        }
        return bestValue; // returns the best move found
    }

    /** 
     * This is an implementation of the Minimax Algorithm. The Minvalue portion.
     * This method minimizes all the maximum values tested from the current state.
     * @param game An object of type {@link TicTacToe}.
     * @return The Minimum value achieved from the current state.
     */
    private int minValue(TicTacToe game)
    {
        if (game.checkEndState() == game.getPlayerNumber()) return -1; // if this is a win state (for the opponent)
        else if (game.checkEndState() == opponent(game)) return +1;    // if this is a lose state (for the opponent)
        else if (game.checkEndState() == 3) return 0;                  // if this is a tie state

        int bestValue = 10; // Initiate the best value with (+infinite).

        LinkedList<Move> successor = successors(game); // List the possible moves from the
        											   // current state
        
        while(successor.size() > 0)                    				// While there is a move to test
        {
            Move m = successor.removeFirst();		        		// Gets the move to test
            game.move(m.posX, m.posY);                      		// executes the move
            int value = maxValue(game.copy(opponent(game))); 		// calculates the outcomes
            														// of the move
            if (value < bestValue)						// if the value of the move is better than the previous
            	bestValue = value; 						// best move, save this move and the value

            game.undoMove();							// Undo the move to test the next, if available
        }
        return bestValue; // returns the best move found
    }

}
