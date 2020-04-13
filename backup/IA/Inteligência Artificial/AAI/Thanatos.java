package ia.isola.agents;

import java.util.LinkedList;

import ia.isola.game.*;
import ia.isola.agents.BasicAgent;

public class Thanatos implements BasicAgent 
{	
	/**
	 * A simple data class for storing the moves
	 * Used by the successor function
	 * @author  Alair Dias Júnior 			
	 * @version 1.0
	 * 
	 * Modified by Caetano Milorde
	 * 			   Luciano Bernardes
	 * 			   Tanato Cartaxo
	 */
    private class Move
    {
        public Move(int px, int py, int rx, int ry)
        {
            posX = px;
            posY = py;
            remX = rx;
            remY = ry;
        }
        int posX;
        int posY;
        int remX;
        int remY;
    }
    
    /** 
     * Returns the opponent in the current game.
     * @param game A {@link ia_isola} object
     * @return The number of the opponent.
     */
    private int opponent(Isola game)
    {
        return (game.getPlayerNumber() == 0) ? 1 : 0;
    }
	
	/** 
     * List all possible movements from the current state
     * @param game A {@link ia_isola} object
     * @return A LinkedList of move objects
     */
    private LinkedList<Move> successors(Isola game)
    {
        LinkedList<Move> successor = new LinkedList<Move>();
        
        int[] posXY = game.getPlayerPos(game.getPlayerNumber());
        int[] enemyXY = game.getPlayerPos(opponent(game));
        
        for (int i = (posXY[0] - 1); i <= (posXY[0] + 1); ++i)
            for (int j = (posXY[1] - 1); j <= (posXY[1] + 1); ++j)
                if (game.checkLegalMove(i, j))
                {
                	game.move(i, j);
                	for (int k = (enemyXY[0] - 1); k <=  (enemyXY[0] + 1); ++k)
                        for (int l = (enemyXY[1] - 1); l <= (enemyXY[1] + 1); ++l)
                        	if(game.checkLegalRemoval(k, l))
                        	{
                        		successor.add(new Move(i, j, k, l));
                        	}
                    game.undoMove();
                }
        return successor;
    }

    
    /** 
     * Called by the server for the player to execute its move.
     * @param game An object of type {@link ia_isola}.
     * @return Should return false when the player give up the game. True otherwise.
     */
    public boolean move(final Isola game)
    {    	
        Move m = minimax(game);
        
        if (game.move(m.posX, m.posY) && game.remove(m.remX, m.remY))
            return true;

        System.out.println("Impossible to move");
        return false;
    }

    /** 
     * This is a simple implementation of the Minimax Algorithm.
     * The method minimax is the frontend of the algorithm, implementing
     * a version of the Max side and storing the best move so far.
     * @param game An object of type {@link ia_isola}.
     * @return The best move found for the current state.
     */
    private Move minimax(Isola game)
    {
        int bestValue = -10; // Initiate the best value with (-infinite).
        					 
        LinkedList<Move> successor = successors(game); // List the possible moves from the
        
        //System.out.println("size = "+successor.size());
        
        Move bestMove = null;
        while(successor.size() > 0) // While there is a move to test
        {
            Move m = successor.removeFirst(); // Gets the move to test
            game.move(m.posX, m.posY);// executes the move
            game.remove(m.remX, m.remY);// executes the remove
            
            int value = minValue(game.copy(opponent(game))); // calculates the outcomes
            												 // of the move
            
            if (value > bestValue) // if the value of the move is better than the previous
            {					   // best move, save this move and the value
                bestValue = value;
                bestMove = m;
            }
            
            game.undoMove(); // Undo the move to test the next, if available
            game.undoRemoval(); 
            
        }

        return bestMove; // returns the best move found
    }

    /** 
     * This is an implementation of the Minimax Algorithm. The Maxvalue portion.
     * This method maximizes all the minimum values tested from the current state.
     * @param game An object of type {@link ia_isola}.
     * @return The Maximum value achieved from the current state.
     */
    private int maxValue(Isola game)
    {    	
        if (!game.checkCanMove(game.getPlayerNumber())) return -1; 

        int bestValue = -10; // Initiate the best value with (-infinite).

        LinkedList<Move> successor = successors(game);        // List the possible moves from the
        										              // current state
        
        while(successor.size() > 0)                           // While there is a move to test
        {
            Move m = successor.removeFirst();                 // Gets the move to test
            game.move(m.posX, m.posY);// executes the move
            game.remove(m.remX, m.remY);// executes the remove
            int value = minValue(game.copy(opponent(game)));  // calculates the outcomes
            												  // of the move
            if (value > bestValue)						// if the value of the move is better than the previous
            	bestValue = value; 						// best move, save this move and the value
            
            if (bestValue == 1)		//Alpha-Beta pruning
                return bestValue;
            
            game.undoMove();                            // Undo the move to test the next, if available
            game.undoRemoval();
            
            
        }
        return bestValue; // returns the best move found
    }

    /** 
     * This is an implementation of the Minimax Algorithm. The Minvalue portion.
     * This method minimizes all the maximum values tested from the current state.
     * @param game An object of type {@link ia_isola}.
     * @return The Minimum value achieved from the current state.
     */
    private int minValue(Isola game)
    {
        if (!game.checkCanMove(game.getPlayerNumber())) return 1;    // if this is a tie state
        
        int bestValue = 10; // Initiate the best value with (+infinite).

        LinkedList<Move> successor = successors(game); // List the possible moves from the
        											   // current state
        
        while(successor.size() > 0)                    				// While there is a move to test
        {
            Move m = successor.removeFirst();		        		// Gets the move to test
            game.move(m.posX, m.posY);// executes the move
            game.remove(m.remX, m.remY);// executes the remove
            int value = maxValue(game.copy(opponent(game))); 		// calculates the outcomes
            														// of the move
            if (value < bestValue)						// if the value of the move is better than the previous
            	bestValue = value; 						// best move, save this move and the value
            
            if (bestValue == -1)	//Alpha-Beta pruning
                return bestValue;

            game.undoMove();							// Undo the move to test the next, if available
            game.undoRemoval();
            
            
        }
        return bestValue; // returns the best move found
    }
}