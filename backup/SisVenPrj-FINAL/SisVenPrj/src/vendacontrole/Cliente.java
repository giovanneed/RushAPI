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
public class Cliente {


    private int codCliente;
    private String nome;
    private String endereco;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String telefone;
    private String e_mail;
    private GregorianCalendar data_cad_cliente;

    public Cliente(int codCliente, String nome, String endereco, String bairro, String cidade, String telefone, String uf, String cep, String e_mail, GregorianCalendar data_cad_cliente) {
        this.codCliente = codCliente;
        this.nome = nome;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        this.telefone = telefone;
        this.e_mail = e_mail;
        this.data_cad_cliente = data_cad_cliente;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public GregorianCalendar getData_cad_cliente() {
        return data_cad_cliente;
    }

    public void setData_cad_cliente(GregorianCalendar data_cad_cliente) {
        this.data_cad_cliente = data_cad_cliente;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String toString(){
        return "___________" + "\n" +
                "CodCliente: " + this.codCliente + "\n" +
                "Nome......: " + this.nome + "\n" +
                "Endereco..: " + this.endereco + "\n" +
                "Bairro....: " + this.bairro + "\n" +
                "Cidade....: " + this.cidade + "\n" +
                
                "CEP.......: " + this.cep + "\n" +
                "Estado....: " + this.uf + "\n" +
                "Telefone..: " + this.telefone + "\n" +
                "Email.....: " + this.e_mail + "\n" +
                "Data Cad..: " + LtpUtil.formatarData(this.data_cad_cliente ,"dd/MM/yyyy HH:mm") + "\n" ;





    }


}
