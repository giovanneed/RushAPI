/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vendacontrole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JTable;
import utilitarios.LtpUtil;
import vendabanco.BDVenda;


/**
 *
 * @author Giovanne
 */
public class Principal {
    private BDVenda bdvenda = new BDVenda();

    public Connection conectar() throws SQLException {
		return bdvenda.conectar();
	}
    public void finalizaConexao() throws SQLException {
		bdvenda.finalizaConexao();
	}

    public void geraRelatorioCliente() throws SQLException, Exception {
                HashMap<String,Object> parametros = new HashMap<String, Object>();
		LtpUtil.gerarRelatorio(
                    LtpUtil.RELATORIO_VISUALIZADOR_JASPER,

                    bdvenda.getConexao(),
                    parametros,
                    "./relatorio/Relatorio_Cliente02.jasper",
                    "Relação de Clientes");
	}

     public void geraRelatorioProduto() throws SQLException, Exception {
                HashMap<String,Object> parametros = new HashMap<String, Object>();
		LtpUtil.gerarRelatorio(
                    LtpUtil.RELATORIO_VISUALIZADOR_JASPER,

                    bdvenda.getConexao(),
                    parametros,
                    "./relatorio/Relatorio_Produto.jasper",
                    "Relação de Clientes");
	}




      public void geraRelatorioVendedor(String ano ) throws SQLException, Exception {
                HashMap<String,Object> parametros = new HashMap<String, Object>();
                parametros.put("ano", ano);
		LtpUtil.gerarRelatorio(
                    LtpUtil.RELATORIO_VISUALIZADOR_JASPER,

                    bdvenda.getConexao(),
                    parametros,
                    "./relatorio/Relatorio_Vendedor.jasper",
                    "Relação de Vendedores");
	}


    public int incluirCliente(Cliente cliente) throws SQLException {
                return bdvenda.cadastraCliente(cliente);

		
	}

    public int incluirProduto(Produto produto) throws SQLException {
		return bdvenda.cadastraProduto(produto);

	}

    public int incluirVenda(Venda venda) throws SQLException {
		return bdvenda.CadastraVenda(venda);

	}

    public int incluirVendedor(Vendedor vendedor) throws SQLException {
		return bdvenda.CadastraVendedor(vendedor);

	}

    public Cliente consultarClienteCodigo( int codigo ) throws SQLException {
                
		ResultSet rs = bdvenda.consultaClienteCodigo(codigo);
                
		return conRowRsCliente(rs);
	}

     public Vendedor consultarVendedorCodigo( int codigo ) throws SQLException {

		ResultSet rs = bdvenda.consultaVendedorCodigo(codigo);

		return conRowRsVendedor(rs);
	}

     public Cliente ConsultarClientePrimeiro() throws SQLException{
        	
                ResultSet rs = bdvenda.ConsultarClientePrimeiro();
                return conRowRsCliente(rs);

    }

     public Vendedor ConsultarVendedorPrimeiro() throws SQLException{

                ResultSet rs = bdvenda.ConsultarVendedorPrimeiro();
                return conRowRsVendedor(rs);

    }
     public Produto ConsultarProdutoPrimeiro() throws SQLException{

                ResultSet rs = bdvenda.ConsultarProdutoPrimeiro();
                return conRowRsProduto(rs);

    }

     public Venda ConsultarVendaPrimeiro() throws SQLException{

                ResultSet rs = bdvenda.ConsultarVendaPrimeiro();
                return conRowRsVenda(rs);

    }

    public Cliente ConsultarClienteUltimo() throws SQLException{
                
        	ResultSet rs = bdvenda.ConsultarClienteUltimo();
		return conRowRsCliente(rs);

    }

    public Vendedor ConsultarVendedorUltimo() throws SQLException{

        	ResultSet rs = bdvenda.ConsultarVendedorUltimo();
		return conRowRsVendedor(rs);

    }
    public Produto ConsultarProdutoUltimo() throws SQLException{

        	ResultSet rs = bdvenda.ConsultarProdutoUltimo();
		return conRowRsProduto(rs);

    }
    public Venda ConsultarVendaUltimo() throws SQLException{

        	ResultSet rs = bdvenda.ConsultarVendaUltimo();
		return conRowRsVenda(rs);

    }
    public Cliente ConsultarClienteProximo(int codigo) throws SQLException{

        	ResultSet rs = bdvenda.ConsultarClienteProximo(codigo);
		return conRowRsCliente(rs);

    }
     public Vendedor ConsultarVendedorProximo(int codigo) throws SQLException{

        	ResultSet rs = bdvenda.ConsultarVendedorProximo(codigo);
		return conRowRsVendedor(rs);

    }
     public Produto ConsultarProdutoProximo(int codigo) throws SQLException{

        	ResultSet rs = bdvenda.ConsultarProdutoProximo(codigo);
		return conRowRsProduto(rs);

    }

     public Venda ConsultarVendaProximo(int codigo) throws SQLException{

        	ResultSet rs = bdvenda.ConsultarVendaProximo(codigo);
		return conRowRsVenda(rs);

    }



    public Object[][] consultarClienteNome(String nome) throws SQLException{
        ResultSet objRs = bdvenda.consultarClienteNome(nome);
        int totLinhas=0;
        while (objRs.next()) totLinhas++;

        Object[][] listaResposta = new Object[totLinhas][10];
        objRs.beforeFirst();
        int i = 0;
         while (objRs.next()){
             listaResposta[i][0] = objRs.getInt("CodCliente");
             listaResposta[i][1] = objRs.getString("Nome");
             listaResposta[i][2] = objRs.getString("Endereco");
             listaResposta[i][3] = objRs.getString("Bairro");
             listaResposta[i][4] = objRs.getString("Cidade");
             listaResposta[i][5] = objRs.getString("UF");
             listaResposta[i][6] = objRs.getString("CEP");
             listaResposta[i][7] = objRs.getString("Telefone");
             listaResposta[i][8] = objRs.getString("E_mail");
             listaResposta[i][9] = LtpUtil.formatarData( convDataSQL2(objRs.getDate("data_cad_cliente")), "dd/MM/yyyy");
             i++;

         }

        return listaResposta;

    }


    public Object[][] consultarCliente(String nome) throws SQLException{
        ResultSet objRs = bdvenda.consultarCliente(nome);
int totLinhas=0;
        while (objRs.next()) totLinhas++;

        Object[][] listaResposta = new Object[totLinhas][7];
        objRs.beforeFirst();
        int i = 0;
         while (objRs.next()){

         
             listaResposta[i][0] = objRs.getInt("CodCliente");
             listaResposta[i][1] = objRs.getString("Nome");
             listaResposta[i][2] = objRs.getString("Telefone");
             listaResposta[i][3] = objRs.getString("E_MAIL");
             listaResposta[i][4] = objRs.getString("Produto");
             listaResposta[i][5] = objRs.getString("Preco");
             listaResposta[i][6] = LtpUtil.formatarData( objRs.getDate("Data_Venda") , "dd/MM/yyyy");
             //listaResposta[i][6] = convDataSQL2(objRs.getDate("Data_Venda"));
             
             

             System.out.println("teste: " + objRs.getDate("Data_Venda"));
          i++;
        }

        return listaResposta;

    }



    public Object[][] consultarVendedorNome(String nome) throws SQLException{
        ResultSet objRs = bdvenda.consultarVendedorNome(nome);
        int totLinhas=0;
        while (objRs.next()) totLinhas++;

        Object[][] listaResposta = new Object[totLinhas][10];
        objRs.beforeFirst();
        int i = 0;
         while (objRs.next()){
             listaResposta[i][0] = objRs.getInt("Cod_Vendedor");
             listaResposta[i][1] = objRs.getString("Nome_Vendedor");
             listaResposta[i][2] = convDataSQL2(objRs.getDate("data_cad_vendedor"));
             i++;

         }

        return listaResposta;

    }

    public Object[][] consultarProdutoNome(String nome) throws SQLException{
        ResultSet objRs = bdvenda.consultarProdutoNome(nome);
        int totLinhas=0;
        while (objRs.next()) totLinhas++;

        Object[][] listaResposta = new Object[totLinhas][5];
        objRs.beforeFirst();
        int i = 0;
         while (objRs.next()){
             listaResposta[i][0] = objRs.getInt("CodProduto");
             listaResposta[i][1] = objRs.getString("Produto");
             listaResposta[i][2] = objRs.getString("CodUnidade");
             listaResposta[i][3] = objRs.getString("preco");
             //listaResposta[i][4] = convDataSQL2(objRs.getDate("dataPreco"));

             listaResposta[i][4] = LtpUtil.formatarData( objRs.getDate("dataPreco") , "dd/MM/yyyy");
             i++;

         }

        return listaResposta;

    }

    public Vector<Produto> buscarTodosProdutos () throws SQLException {
                ResultSet objRs = bdvenda.consultarTodosProdutos();
                objRs.beforeFirst();
		Vector produtos = new Vector(5);
		while (objRs.next()) {
			produtos.add(conRowRsProduto(objRs));
		}
		return produtos;

	}




    public Cliente ConsultarClienteAnterior(int codigo) throws SQLException{

        	ResultSet rs = bdvenda.ConsultarClienteAnterior(codigo);
		return conRowRsCliente(rs);

    }

    public Vendedor ConsultarVendedorAnterior(int codigo) throws SQLException{

        	ResultSet rs = bdvenda.ConsultarVendedorAnterior(codigo);
		return conRowRsVendedor(rs);

    }
    public Produto ConsultarProdutoAnterior(int codigo) throws SQLException{

        	ResultSet rs = bdvenda.ConsultarProdutoAnterior(codigo);
		return conRowRsProduto(rs);

    }
    public Venda ConsultarVendaAnterior(int codigo) throws SQLException{

        	ResultSet rs = bdvenda.ConsultarVendaAnterior(codigo);
		return conRowRsVenda(rs);

    }


    public Produto consultarProdutoCodigo( int codigo ) throws SQLException {
		ResultSet rs = bdvenda.consultaProdutoCodigo(codigo);
		return conRowRsProduto(rs);
	}
    public Venda consultarVendaCodigo( int codigo ) throws SQLException {
		ResultSet rs = bdvenda.consultaVendaCodigo(codigo);
		return conRowRsVenda(rs);
	}

    public void excluirCliente(int codCliente) throws SQLException {
		bdvenda.excluirCliente(codCliente);
	}
    
    public void excluirProduto(int codProduto) throws SQLException {
		bdvenda.excluirProduto(codProduto);
	}

    public void excluirVendedor(int codVendedor) throws SQLException {
		bdvenda.excluirVendedor(codVendedor);
	}

    public void excluirVenda(int codVenda) throws SQLException {
		bdvenda.excluirVenda(codVenda);
	}

    public void alteraCliente(Cliente cliente) throws SQLException {
        bdvenda.alterarCliente(cliente);
    }
    public void alteraProduto(Produto produto) throws SQLException {
        bdvenda.alterarProduto(produto);
    }
    public void alteraVendedor(Vendedor vendedor) throws SQLException {
        bdvenda.alterarVendedor(vendedor);
    }
    public void alteraVenda(Venda venda) throws SQLException {
        bdvenda.alterarVenda(venda);
    }
   


    private Cliente conRowRsCliente(ResultSet rs) throws SQLException {

                
		return new Cliente (rs.getInt("CodCliente"),
	             rs.getString("Nome"),
                     rs.getString("Endereco"),
                     rs.getString("Bairro"),
                     rs.getString("Cidade"),
                     rs.getString("Telefone"),
	             rs.getString("UF"),
                     rs.getString("CEP"),
	             rs.getString("E_mail"),
                     convDataSQL2(rs.getDate("data_cad_cliente"))

	             );
	}

    private Produto conRowRsProduto(ResultSet rs) throws SQLException {


		return new Produto (rs.getInt("CodProduto"),
	             rs.getString("Produto"),
	             rs.getInt("CodUnidade"),
                     rs.getDouble("Preco"),
                     convDataSQL2(rs.getDate("dataPreco"))
                     //convDataSQL(rs.getDate("dataPreco"),rs.getTime("dataPreco"))

	             );
	}

    private Venda conRowRsVenda(ResultSet rs) throws SQLException {


		return new Venda (rs.getInt("CodVenda"),
	             rs.getInt("Cod_Vendedor"),
	             rs.getInt("CodCliente"),
                     convDataSQL2(rs.getDate("Data_Venda"))
                     //convDataSQL(rs.getDate("Data_Venda"),rs.getTime("Data_Venda"))

	             );
	}

    private Vendedor conRowRsVendedor(ResultSet rs) throws SQLException {


		return new Vendedor (rs.getInt("cod_Vendedor"),
	             rs.getString("Nome_vendedor"),
                     convDataSQL2(rs.getDate("Data_Cad_vendedor"))
	             //convDataSQL(rs.getDate("Data_Cad_vendedor"),rs.getTime("Data_Cad_vendedor"))

	             );
	}

    public GregorianCalendar convDataSQL(Date data, Time hora) {
                
		if ( data==null ) return null;
		String [ ] auxData =  data.toString().split("-");
		String [ ] auxHora = hora.toString().split(":");
        return new GregorianCalendar(Integer.parseInt(auxData[0]),Integer.parseInt(auxData[1])-1,
        		                     Integer.parseInt(auxData[2]),Integer.parseInt(auxHora[0]),
        		                     Integer.parseInt(auxHora[1]),Integer.parseInt(auxHora[2]));
	}

    public GregorianCalendar convDataSQL2(Date data) {
    
		if ( data==null ) return null;
               
		String [ ] auxData =  data.toString().split("-");

        return new GregorianCalendar ( Integer.parseInt(auxData[0]),Integer.parseInt(auxData[1]),
        		                     Integer.parseInt(auxData[2]));
	}

}
