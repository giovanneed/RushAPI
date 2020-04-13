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
public class Vendedor {

    private int codVendedor;
    private String nome;
    private GregorianCalendar data_cad_vendedor;

    public Vendedor(int codVendedor, String nome, GregorianCalendar data_cad_vendedor) {
        this.codVendedor = codVendedor;
        this.nome = nome;
        this.data_cad_vendedor = data_cad_vendedor;
    }

    public int getCodVendedor() {
        return codVendedor;
    }

    public void setCodVendedor(int codVendedor) {
        this.codVendedor = codVendedor;
    }

    public GregorianCalendar getData_cad_vendedor() {
        return data_cad_vendedor;
    }

    public void setData_cad_vendedor(GregorianCalendar data_cad_vendedor) {
        this.data_cad_vendedor = data_cad_vendedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString(){
        return "___________" + "\n" +
                "CodVendedor...: " + this.codVendedor + "\n" +
                "Nome..........: " + this.nome + "\n"  ;
                //  + "Data Cad......: " + LtpUtil.formatarData(this.data_cad_vendedor ,"dd/MM/yyyy HH:mm") + "\n" ;
    }


}
