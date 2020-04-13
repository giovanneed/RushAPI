
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import vendacontrole.Cliente;
import vendacontrole.Principal;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Giovanne
 */


public class TesteConexao {
    private static Principal portControl = new Principal();


    private static final String DB_USERNAME = "SYSDBA";
    private static final String DB_PASSWORD  = "masterkey";



    // variaveis utilizadas pelos metodos para a consulta ao banco
	private static Connection con;
	private static PreparedStatement pstm;





       public static void main(String[] args){
        try {
            con = portControl.conectar();
            Cliente teste = portControl.consultarClienteCodigo(3);
            System.out.println(teste.toString());



        } catch (SQLException ex) {
            System.out.println("erro: " + ex.getMessage());
            Logger.getLogger(TesteConexao.class.getName()).log(Level.SEVERE, null, ex);
        }

       }

       }
