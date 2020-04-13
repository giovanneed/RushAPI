/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vendacontrole;

import java.util.GregorianCalendar;
import utilitarios.LtpUtil;

/**
 *
 * @author Giovanne
 */
public class Produto {


    private int codProduto;
    private String produto;
    private int codUnidade;
    private double  preco;
    private GregorianCalendar dataPreco;

    public Produto(int codProduto, String produto, int codUnidade, double preco, GregorianCalendar dataPreco) {
        this.codProduto = codProduto;
        this.produto = produto;
        this.codUnidade = codUnidade;
        this.preco = preco;
        this.dataPreco = dataPreco;
    }

    public int getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(int codProduto) {
        this.codProduto = codProduto;
    }

    public int getCodUnidade() {
        return codUnidade;
    }

    public void setCodUnidade(int codUnidade) {
        this.codUnidade = codUnidade;
    }

    public GregorianCalendar getDataPreco() {
        return dataPreco;
    }

    public void setDataPreco(GregorianCalendar dataPreco) {
        this.dataPreco = dataPreco;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String toString(){
        return "___________" + "\n" +
                "CodProduto......: " + this.codProduto + "\n" +
                "Produto......: " + this.produto + "\n" +
                "CodUnidade...: " + this.codUnidade + "\n" +
                "Preco........: " + this.preco + "\n" +
                "Data.........: " + LtpUtil.formatarData(this.dataPreco ,"dd/MM/yyyy HH:mm") + "\n" ;
    }



}
