/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vendabanco;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JTable;
import vendacontrole.Cliente;
import vendacontrole.Produto;
import vendacontrole.Venda;
import vendacontrole.Vendedor;

/**
 * Servidor : server1b
   Local : D:\Program Files\Firebird\Ltp4
   Nome : BDVENDAS.GDB

 *
 *
 *
 * @author Giovanne
 */
public class BDVenda {

     private static final String DB_USERNAME = "SYSDBA";
     private static final String DB_PASSWORD  = "masterkey";

     //private static String DB_URL = "jdbc:firebirdsql:localhost/3050:";
     //private static String DB_NAME = "C:\\NetBeans Projects\\SisVenPrj\\BDVENDAS.GDB";

     private final String DB_URL = "jdbc:firebirdsql:server1b/3050:";  // FUMEC
     private final String DB_NAME = "D:\\PROGRAM FILES\\FIREBIRD\\LTP4\\BDVENDAS.GDB";  //FUMEC

     // variaveis utilizadas pelos metodos para a consulta ao banco
     private static Connection con;
     private static PreparedStatement pstm;


      //conexao com banco de dados

         public Connection conectar () throws SQLException	{
		DriverManager.registerDriver(new org.firebirdsql.jdbc.FBDriver());
		con = DriverManager.getConnection(DB_URL+DB_NAME, DB_USERNAME, DB_PASSWORD);
		return con;
	}

        //finalizar conexao a banco de dados
        public void finalizaConexao() throws SQLException {
		con.close();
	}
        public Connection getConexao() {
            return con;
         }

        public int cadastraCliente (Cliente objCliente) throws SQLException{

            String consultaSQL = "Select * from CLIENTES Where NOME = ? ";
            pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            pstm.setString(1,objCliente.getNome());
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                throw new SQLException("Já existe cliente com o nome: " + rs.getString("Nome"));
            }

            String atualizacaoSQL = "Insert INTO CLIENTES (NOME ,ENDERECO ,BAIRRO ,CIDADE ,UF ,CEP ,Telefone ,E_MAIL ,Data_Cad_Cliente ) VALUES (?,?,?,?,?,?,?,?,?)";
            pstm = con.prepareStatement(atualizacaoSQL);
            pstm.setString(1,objCliente.getNome());
            pstm.setString(2,objCliente.getEndereco());
            pstm.setString(3,objCliente.getBairro());
            pstm.setString(4,objCliente.getCidade());
            pstm.setString(5,objCliente.getUf());
            pstm.setString(6,objCliente.getCep());
            pstm.setString(7,objCliente.getTelefone());
            pstm.setString(8,objCliente.getE_mail());
            pstm.setDate(9,new Date(objCliente.getData_cad_cliente().getTimeInMillis()));
            pstm.executeUpdate();

            pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		pstm.setString(1,objCliente.getNome());
		rs = pstm.executeQuery();
		rs.next();
            return rs.getInt("codCliente");

        }

        public int cadastraProduto (Produto objProduto ) throws SQLException{


            String consultaSQL = "Select * from TABPRODUTOS Where PRODUTO = ? ";
            pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            pstm.setString(1,objProduto.getProduto());
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                throw new SQLException("Já existe um produto cadastrado: " + rs.getString("Produto"));
            }

            String atualizacaoSQL ="Insert INTO TABPRODUTOS (PRODUTO,CODUNIDADE ,PRECO, DATAPRECO) VALUES (?,?,?,?)" ;
            //String atualizacaoSQL = "Insert INTO TABPRODUTOS (PRODUTO ,COD_UNIDADE ,PRECO_UNIDADE ,DATA_PRECO ) VALUES (?,?,?,?)";
            pstm = con.prepareStatement(atualizacaoSQL);
            pstm.setString(1,objProduto.getProduto());
            pstm.setInt(2,objProduto.getCodUnidade());
            pstm.setDouble(3,objProduto.getPreco());
            pstm.setDate(4,new Date(objProduto.getDataPreco().getTimeInMillis()));
            pstm.executeUpdate();

            pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            pstm.setString(1,objProduto.getProduto());
            rs = pstm.executeQuery();
            rs.next();
            return rs.getInt("codProduto");



        }

        public int CadastraVenda (Venda objVenda) throws SQLException{

            String atualizacaoSQL = "Insert INTO VENDAS (COD_VENDEDOR ,CODCLIENTE ,DATA_VENDA) VALUES (?,?,?) ";
            pstm = con.prepareStatement(atualizacaoSQL);
            pstm.setInt(1,objVenda.getCodVendedor());
            pstm.setInt(2,objVenda.getCodCliente());
            pstm.setDate(3, new Date(objVenda.getData_venda().getTimeInMillis()));
            pstm.executeUpdate();


            return 1;

            
        }

        public int CadastraVendedor (Vendedor objVendedor) throws SQLException{
            String consultaSQL = "Select * from VENDEDORES Where NOME_VENDEDOR = ?";
            pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            pstm.setString(1,objVendedor.getNome());
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                throw new SQLException("Já existe um vendedor cadastrado: " + rs.getString("Nome_Vendedor"));
            }


            String atualizacaoSQL = "Insert INTO VENDEDORES (NOME_VENDEDOR ,DATA_CAD_VENDEDOR  ) VALUES (?,?)";
            pstm = con.prepareStatement(atualizacaoSQL);
            pstm.setString(1,objVendedor.getNome());
            pstm.setDate(2,new Date(objVendedor.getData_cad_vendedor().getTimeInMillis()));
            pstm.executeUpdate();


            pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            pstm.setString(1,objVendedor.getNome());
	    rs = pstm.executeQuery();
	    rs.next();
	    return rs.getInt("Cod_Vendedor");



        }

        public ResultSet consultaClienteCodigo (int codigo) throws SQLException {
		String consultaSQL = "Select * from CLIENTES Where CODCLIENTE = ? ";
		pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		pstm.setInt(1,codigo);
		ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("Não existe Cliente para o código informado.");
		}
		return rs;
	}


        public ResultSet ConsultarClientePrimeiro() throws SQLException{
             String consultaSQL = "SELECT * FROM CLIENTES WHERE CODCLIENTE = (SELECT MIN(CODCLIENTE) FROM CLIENTES)";
             
             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             
             ResultSet rs = pstm.executeQuery(consultaSQL);
		if (!rs.next()) {
			throw new SQLException("Não existe Cliente Cadastrado.");
		}
		return rs;
         }

        public ResultSet ConsultarVendedorPrimeiro() throws SQLException{
             String consultaSQL = "SELECT * FROM VENDEDORES WHERE COD_VENDEDOR = (SELECT MIN(COD_VENDEDOR) FROM VENDEDORES)";
             
             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

             ResultSet rs = pstm.executeQuery(consultaSQL);
		if (!rs.next()) {
			throw new SQLException("Não existe Vendedor Cadastrado.");
		}
		return rs;
         }

        public ResultSet ConsultarProdutoPrimeiro() throws SQLException{
             String consultaSQL = "SELECT * FROM TABPRODUTOS WHERE CODPRODUTO = (SELECT MIN(CODPRODUTO) FROM TABPRODUTOS)";

             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

             ResultSet rs = pstm.executeQuery(consultaSQL);
		if (!rs.next()) {
			throw new SQLException("Não existe produto Cadastrado.");
		}
		return rs;
         }

        public ResultSet ConsultarVendaPrimeiro() throws SQLException{
             String consultaSQL = "SELECT * FROM VENDAS WHERE CODVENDA = (SELECT MIN(CODVENDA) FROM VENDAS)";

             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

             ResultSet rs = pstm.executeQuery(consultaSQL);
		if (!rs.next()) {
			throw new SQLException("Não existe venda Cadastrado.");
		}
             
             
		return rs;
         }


        public ResultSet ConsultarClienteUltimo() throws SQLException{
             String consultaSQL = "SELECT * FROM CLIENTES WHERE CODCLIENTE = (SELECT MAX(CODCLIENTE) FROM CLIENTES)";
             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("Não existe Cliente Cadastrado.");
		}
		return rs;
         }

        public ResultSet ConsultarVendaUltimo() throws SQLException{
             String consultaSQL = "SELECT * FROM VENDAS WHERE CODVENDA = (SELECT MAX(CODVENDA) FROM VENDAS)";
             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("Não existe venda Cadastrado.");
		}
		return rs;
         }


        public ResultSet ConsultarVendedorUltimo() throws SQLException{
             String consultaSQL = "SELECT * FROM VENDEDORES WHERE COD_VENDEDOR = (SELECT MAX(COD_VENDEDOR) FROM VENDEDORES)";
             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("Não existe Vendedor Cadastrado.");
		}
		return rs;
         }

        public ResultSet ConsultarProdutoUltimo() throws SQLException{
             String consultaSQL = "SELECT * FROM TABPRODUTOS WHERE CODPRODUTO = (SELECT MAX(CODPRODUTO) FROM TABPRODUTOS)";
             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("Não existe produto Cadastrado.");
		}
		return rs;
         }

        public ResultSet consultarClienteNome(String nome) throws SQLException{
            String consultaSQL = "SELECT *  FROM CLIENTES WHERE UPPER(NOME) LIKE ? ORDER BY NOME";
            pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            pstm.setString(1, "%" + nome.toUpperCase() + "%");
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
            throw  new SQLException("Não existe cliente para o nome informado.");
            }
            rs.beforeFirst();
            return rs;


        }
        public ResultSet consultarCliente(String nome) throws SQLException{
            
            


String consultaSQL = "SELECT "

     + "CLIENTES.\"CODCLIENTE\" AS CLIENTES_CODCLIENTE,"
     + " CLIENTES.\"NOME\" AS CLIENTES_NOME,"
     + " CLIENTES.\"TELEFONE\" AS CLIENTES_TELEFONE,"
     + " CLIENTES.\"E_MAIL\" AS CLIENTES_E_MAIL,"
     + " TABPRODUTOS.\"PRODUTO\" AS TABPRODUTOS_PRODUTO,"
     + " TABPRODUTOS.\"PRECO\" AS TABPRODUTOS_PRECO,"

     + " VENDAS.\"DATA_VENDA\" AS VENDAS_DATA_VENDA"
+ " FROM"
     + " \"CLIENTES\" CLIENTES INNER JOIN \"VENDAS\" VENDAS ON CLIENTES.\"CODCLIENTE\" = VENDAS.\"CODCLIENTE\" "
     + " INNER JOIN \"ITENS\" ITENS ON VENDAS.\"CODVENDA\" = ITENS.\"CODVENDA\" "
     + " INNER JOIN \"TABPRODUTOS\" TABPRODUTOS ON ITENS.\"CODPRODUTO\" = TABPRODUTOS.\"CODPRODUTO\""
+ " WHERE"
    + " UPPER(NOME) LIKE ? "
    + "AND"
    +     " DATA_VENDA = (SELECT MAX(DATA_VENDA) FROM VENDAS V INNER JOIN CLIENTES C ON V.CODCLIENTE = C.CODCLIENTE"
    +	                 " WHERE C.NOME LIKE UPPER(?))"


+ " ORDER BY"
   + " NOME";








             
            pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            pstm.setString(1, "%" + nome.toUpperCase() + "%");
            pstm.setString(2, "%" + nome.toUpperCase() + "%");
            ResultSet rs = pstm.executeQuery();
            
            if (!rs.next()) {
            throw  new SQLException("Não existe cliente para o nome informado.");
            }
            rs.beforeFirst();
            
            return rs;


        }

        public ResultSet consultarVendedorNome(String nome) throws SQLException{
            String consultaSQL = "SELECT * FROM VENDEDORES WHERE UPPER(NOME_VENDEDOR) LIKE ? ORDER BY NOME_VENDEDOR";
            pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            pstm.setString(1, "%" + nome.toUpperCase() + "%");
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
            throw  new SQLException("Não existe vendedor para o nome informado.");
            }
            rs.beforeFirst();
            return rs;


        }

        public ResultSet consultarProdutoNome(String nome) throws SQLException{
            
            String consultaSQL ="Select * from TABPRODUTOS where upper(produto) like ? order by produto" ;
            //String consultaSQL = "SELECT * FROM TABPRODUTOS WHERE UPPER(PRODUTO) LIKE ? ORDER BY PRODUTO";
            pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            pstm.setString(1, "%" + nome.toUpperCase() + "%");
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
            throw  new SQLException("Não existe produto para o nome informado.");
            }
            rs.beforeFirst();
            return rs;


        }

        public ResultSet consultarTodosProdutos() throws SQLException{

      		String consultaSQL ="Select * from TABPRODUTOS order by codigo" ;
		pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = pstm.executeQuery();
       		if (!rs.next()) {
			throw new SQLException("Não existe nenhum produto cadastrado.");
		}

                rs.beforeFirst();
                return rs;

        }





        public ResultSet ConsultarClienteProximo(int codigo) throws SQLException{
             String consultaSQL = "SELECT * FROM CLIENTES WHERE CODCLIENTE = (SELECT MIN(CODCLIENTE) FROM CLIENTES WHERE CODCLIENTE > ?)";
             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             pstm.setInt(1,codigo);
             ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("O cliente da tela já é o ultimo.");
		}
		return rs;
         }

        public ResultSet ConsultarVendedorProximo(int codigo) throws SQLException{
             String consultaSQL = "SELECT * FROM VENDEDORES WHERE COD_VENDEDOR = (SELECT MIN(COD_VENDEDOR) FROM VENDEDORES WHERE COD_VENDEDOR > ?)";
             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             pstm.setInt(1,codigo);
             ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("O produto da tela já é o ultimo.");
		}
		return rs;
         }

        public ResultSet ConsultarProdutoProximo(int codigo) throws SQLException{
             String consultaSQL = "SELECT * FROM TABPRODUTOS WHERE CODPRODUTO = (SELECT MIN(CODPRODUTO) FROM TABPRODUTOS WHERE CODPRODUTO > ?)";
             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             pstm.setInt(1,codigo);
             ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("O produto da tela já é o ultimo.");
		}
		return rs;
         }

        public ResultSet ConsultarVendaProximo(int codigo) throws SQLException{
             String consultaSQL = "SELECT * FROM VENDAS WHERE CODVENDA = (SELECT MIN(CODVENDA) FROM VENDAS WHERE CODVENDA > ?)";
             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             pstm.setInt(1,codigo);
             ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("A venda da tela já é o ultimo.");
		}
		return rs;
         }

        public ResultSet ConsultarClienteAnterior(int codigo) throws SQLException{
             String consultaSQL = "SELECT * FROM CLIENTES WHERE CODCLIENTE= (SELECT MAX(CODCLIENTE) FROM CLIENTES WHERE CODCLIENTE < ?)";
             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             pstm.setInt(1,codigo);
             ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("O cliente da tela já é o anterior.");
		}
		return rs;
         }

        public ResultSet ConsultarProdutoAnterior(int codigo) throws SQLException{
             String consultaSQL = "SELECT * FROM TABPRODUTOS WHERE CODPRODUTO= (SELECT MAX(CODPRODUTO) FROM TABPRODUTOS WHERE CODPRODUTO < ?)";
             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             pstm.setInt(1,codigo);
             ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("O cliente da tela já é o anterior.");
		}
		return rs;
         }

        public ResultSet ConsultarVendaAnterior(int codigo) throws SQLException{
             String consultaSQL = "SELECT * FROM VENDAS WHERE CODVENDA= (SELECT MAX(CODVENDA) FROM VENDAS WHERE CODVENDA < ?)";
             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             pstm.setInt(1,codigo);
             ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("A venda da tela já é a anterior.");
		}
		return rs;
         }



        public ResultSet ConsultarVendedorAnterior(int codigo) throws SQLException{
             String consultaSQL = "SELECT * FROM VENDEDORES WHERE COD_VENDEDOR= (SELECT MAX(COD_VENDEDOR) FROM VENDEDORES WHERE COD_VENDEDOR < ?)";
             pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
             pstm.setInt(1,codigo);
             ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("O vendedor da tela já é o anterior.");
		}
		return rs;
         }


        public ResultSet consultaVendedorCodigo (int codigo) throws SQLException {
		String consultaSQL = "Select * from VENDEDORES Where COD_VENDEDOR = ? ";
		pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		pstm.setInt(1,codigo);
		ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("Não existe Vendedor para o código informado.");
		}
		return rs;
	}

        public ResultSet consultaProdutoCodigo (int codigo) throws SQLException {
		String consultaSQL = "Select * from TABPRODUTOS Where CODPRODUTO = ? ";
		pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		pstm.setInt(1,codigo);
		ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("Não existe Produto para o código informado.");
		}
		return rs;
	}

        public ResultSet consultaVendaCodigo (int codigo) throws SQLException {
		String consultaSQL = "Select * from VENDAS Where CODVENDA = ? ";
		pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		pstm.setInt(1,codigo);
		ResultSet rs = pstm.executeQuery();
		if (!rs.next()) {
			throw new SQLException("Não existe Venda para o código informado.");
		}
		return rs;
	}

         public void excluirCliente(int codigo) throws SQLException {

             String consultaSQL = "Select Count(*) as CODVENDA from VENDAS WHERE CODVENDA = ?";
            pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            pstm.setInt(1, codigo);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            if (rs.getInt("codvenda") > 0 )
                throw new SQLException("O Cliente nao pode ser excluido , existe venda alocado para o cliente.");




		//ResultSet rs = consultaClienteCodigo(codigo);
		String atualizacaoSQL = "DELETE FROM CLIENTES WHERE CODCLIENTE = ?";
		pstm = con.prepareStatement(atualizacaoSQL);
		pstm.setInt(1,codigo);
		pstm.executeUpdate();
	}

         public void excluirProduto(int codigo) throws SQLException {

		ResultSet rs = consultaClienteCodigo(codigo);
		String atualizacaoSQL = "DELETE FROM TABPRODUTOS WHERE CODPRODUTO = ?";
		pstm = con.prepareStatement(atualizacaoSQL);
		pstm.setInt(1,codigo);
		pstm.executeUpdate();
	}

         public void excluirVendedor(int codigo) throws SQLException {

            String consultaSQL = "Select Count(*) as CODVENDA from VENDAS WHERE CODVENDA = ?";
            pstm = con.prepareStatement(consultaSQL,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY,ResultSet.HOLD_CURSORS_OVER_COMMIT);
            pstm.setInt(1, codigo);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            if (rs.getInt("codvenda") > 0 )
                throw new SQLException("O Vendedor nao pode ser excluido , existe venda alocado para o vendedor.");




		//ResultSet rs = consultaClienteCodigo(codigo);
		String atualizacaoSQL = "DELETE FROM VENDEDORES WHERE COD_VENDEDOR = ?";
		pstm = con.prepareStatement(atualizacaoSQL);
		pstm.setInt(1,codigo);
		pstm.executeUpdate();
	}

         public void excluirVenda(int codigo) throws SQLException {

		ResultSet rs = consultaClienteCodigo(codigo);
		String atualizacaoSQL = "DELETE FROM VENDAS WHERE CODVENDA = ?";
		pstm = con.prepareStatement(atualizacaoSQL);
		pstm.setInt(1,codigo);
		pstm.executeUpdate();
	}

         public void alterarCliente(Cliente objCliente) throws SQLException {
		String atualizacaoSQL = "UPDATE CLIENTES SET NOME=? ,ENDERECO=? ,BAIRRO=? ,CIDADE=? ,UF=? ,CEP=? ,Telefone=? ,E_MAIL=? ,Data_Cad_Cliente=? WHERE CODCLIENTE = ?";
            pstm = con.prepareStatement(atualizacaoSQL);
            pstm.setString(1,objCliente.getNome());
            pstm.setString(2,objCliente.getEndereco());
            pstm.setString(3,objCliente.getBairro());
            pstm.setString(4,objCliente.getCidade());
            pstm.setString(5,objCliente.getUf());
            pstm.setString(6,objCliente.getCep());
            pstm.setString(7,objCliente.getTelefone());
            pstm.setString(8,objCliente.getE_mail());
            pstm.setDate(9,new Date(objCliente.getData_cad_cliente().getTimeInMillis()));
            pstm.setInt(10,objCliente.getCodCliente());
            pstm.executeUpdate();

	}

         public void alterarVendedor(Vendedor objVendedor) throws SQLException {
	    String atualizacaoSQL = "UPDATE VENDEDORES SET  NOME_VENDEDOR=? ,DATA_CAD_VENDEDOR=?  WHERE COD_VENDEDOR=? ";
            pstm = con.prepareStatement(atualizacaoSQL);
            pstm.setString(1,objVendedor.getNome());
            pstm.setDate(2,new Date(objVendedor.getData_cad_vendedor().getTimeInMillis()));
            pstm.setInt(3, objVendedor.getCodVendedor());
            pstm.executeUpdate();

	}

         public void alterarProduto(Produto objProduto) throws SQLException {
            String atualizacaoSQL = " UPDATE TABPRODUTOS SET PRODUTO  = ? , PRECO  = ? , DATAPRECO = ? WHERE CODPRODUTO = ?" ;
//	    String atualizacaoSQL = "UPDATE TABPRODUTOS SET PRODUTO=? , COD_UNIDADE=? , PRECO_UNIDADE=?,DATAPRECO=?  WHERE CODPRODUTO=? ";
            pstm = con.prepareStatement(atualizacaoSQL);
            
            pstm.setString(1,objProduto.getProduto());
            pstm.setDouble(2,objProduto.getPreco());
            pstm.setDate(3,new Date(objProduto.getDataPreco().getTimeInMillis()));
            pstm.setInt(4, objProduto.getCodProduto());
            pstm.executeUpdate();

	}

         public void alterarVenda(Venda objVenda) throws SQLException {
	    String atualizacaoSQL = "UPDATE VENDAS SET  COD_VENDEDOR=? ,CODCLIENTE=? ,DATA_VENDA=?  WHERE CODVENDA=? ";
            pstm = con.prepareStatement(atualizacaoSQL);
            pstm.setInt(1,objVenda.getCodVendedor());
            pstm.setInt(2,objVenda.getCodCliente());
            pstm.setDate(3,new Date(objVenda.getData_venda().getTimeInMillis()));
            pstm.executeUpdate();

	}




}
