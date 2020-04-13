/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vendacontrole;

import java.util.GregorianCalendar;

/**
 *
 * @author Giovanne
 */
public class Venda {

    private int codVenda;
    private int codVendedor;
    private int codCliente;
    private GregorianCalendar data_venda;

    public Venda(int codVenda, int codVendedor, int codCliente, GregorianCalendar data_venda) {
        this.codVenda = codVenda;
        this.codVendedor = codVendedor;
        this.codCliente = codCliente;
        this.data_venda = data_venda;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public int getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(int codVenda) {
        this.codVenda = codVenda;
    }

    public int getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(int codVendedor) {
        this.codVendedor = codVendedor;
    }

    public GregorianCalendar getData_venda() {
        return data_venda;
    }

    public void setData_venda(GregorianCalendar data_venda) {
        this.data_venda = data_venda;
    }

    public String toString(){
        return "___________" + "\n" +
                "CodVenda......: " + this.codVenda + "\n" +
                "CodVendedor...: " + this.codVendedor + "\n" +
                "CodCliente....: " + this.codCliente + "\n" ;
                // + "Data Venda....: " + LtpUtil.formatarData(this.data_venda ,"dd/MM/yyyy HH:mm") + "\n" ;
                
    }

}
