package ia.isola.agents;

import ia.isola.game.*;

/**
 * Interface to be implemented by the game Agents.
 * This is the base class for the agents. All agents should implement this interface.
 * @author  Alair Dias Júnior
 * @version 1.0
 */
public interface BasicAgent
{
   /** 
    * Called by the server for the player to execute its move.
    * @param game An object of type {@link Isola}.
    * @return Should return false when the player give up the game. True otherwise.
    */
    public boolean move(final Isola game);
}

