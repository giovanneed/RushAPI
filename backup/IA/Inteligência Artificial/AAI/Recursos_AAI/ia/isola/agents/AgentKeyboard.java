package ia.isola.agents;

import java.io.*;
import ia.isola.game.*;
import ia.isola.agents.BasicAgent;

/**
 * Keyboard Agent.
 * This class uses the keyboard as input for the player's moves. 
 * @author  Alair Dias Júnior
 * @version 1.0
 */
public class AgentKeyboard implements BasicAgent
{
   /** 
    * Called by the server for the player to execute its move.
    * @param game An object of type {@link Isola}.
    * @return Should return false when the player give up the game. True otherwise.
    */
    public boolean move(final Isola game)
    {

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("");
            System.out.println("Player " + (game.getPlayerNumber()+1) + " turn.");
            game.printBoard();

            while(true)
            {
                System.out.print("Enter New Column for the piece: ");
                int i = Integer.parseInt(input.readLine());
                System.out.print("Enter New line for the piece: ");
                int j = Integer.parseInt(input.readLine());
                if (game.move(i,j)) break;
                else System.out.println("\tInvalid Position ("+i+", "+j+")");
            }
            while(true)
            {
                System.out.print("Enter the column number of the square to remove: ");
                int i = Integer.parseInt(input.readLine());
                System.out.print("Enter the line number of the square to remove: ");
                int j = Integer.parseInt(input.readLine());
                if (game.remove(i,j)) break;
                else System.out.println("\tInvalid Square ("+i+", "+j+")");
            }
            System.out.println("");
        }
        catch (NumberFormatException e) {
            System.out.println("Not a valid move!");
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
