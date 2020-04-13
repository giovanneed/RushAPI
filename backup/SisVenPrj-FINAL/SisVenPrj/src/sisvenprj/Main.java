/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sisvenprj;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vendacontrole.Principal;
import vendainterface.MenuPrincipal;

/**
 *
 * @author Giovanne
 */
public class Main {
      private static Connection con;
    private static Principal portControl = new Principal();


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         try {
                    // TODO add your handling code here:
                     System.out.println("conectar");
                    con = portControl.conectar();
                } catch (SQLException ex) {
                    
                }

         MenuPrincipal menu = new vendainterface.MenuPrincipal();
        menu.setVisible(true);
    }
}
