/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ExibirCliente.java
 *
 * Created on 29/10/2010, 13:59:32
 */

package vendainterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utilitarios.LtpUtil;
import vendabanco.BDVenda;
import vendacontrole.Cliente;
import vendacontrole.Principal;

/**
 *
 * @author Giovanne
 */
public class ExibirCliente extends javax.swing.JFrame {
    private static Connection con;
    private static Principal portControl = new Principal();
    public static BDVenda objBanco = new BDVenda();

    /** Creates new form ExibirCliente */
    public ExibirCliente() {
        initComponents();
        limpaform();
    }
  
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelClientePrincipal = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldCodigo = new javax.swing.JTextField();
        jTextNome = new javax.swing.JTextField();
        jTextFieldEndereco = new javax.swing.JTextField();
        jTextFieldBairro = new javax.swing.JTextField();
        jTextFieldCidade = new javax.swing.JTextField();
        jTextFieldUF = new javax.swing.JTextField();
        jTextFieldCEP = new javax.swing.JTextField();
        jTextFieldTelefone = new javax.swing.JTextField();
        jTextFieldEmail = new javax.swing.JTextField();
        jButtonIncluir = new javax.swing.JButton();
        jButtonAlterar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jFormattedTextFieldData = new javax.swing.JFormattedTextField();
        jButtonBuscarCodigo = new javax.swing.JButton();
        jButtonPrimeiro = new javax.swing.JButton();
        jButtonUltimo = new javax.swing.JButton();
        jButtonAnterior = new javax.swing.JButton();
        jButtonProximo = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemConsultaNome = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setText("Codigo:");

        jLabel2.setText("Nome:");

        jLabel3.setText("Endereco:");

        jLabel4.setText("Bairro:");

        jLabel5.setText("Cidade:");

        jLabel6.setText("UF:");

        jLabel7.setText("CEP:");

        jLabel8.setText("Telefone:");

        jLabel9.setText("Email:");

        jLabel10.setText("Data:");

        jTextFieldCodigo.setEditable(false);

        jButtonIncluir.setText("Incluir");
        jButtonIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIncluirActionPerformed(evt);
            }
        });

        jButtonAlterar.setText("Alterar");
        jButtonAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarActionPerformed(evt);
            }
        });

        jButtonExcluir.setText("Excluir");
        jButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirActionPerformed(evt);
            }
        });

        jFormattedTextFieldData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter()));

        jButtonBuscarCodigo.setText("Buscar");
        jButtonBuscarCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarCodigoActionPerformed(evt);
            }
        });

        jButtonPrimeiro.setText("Primeiro");
        jButtonPrimeiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrimeiroActionPerformed(evt);
            }
        });

        jButtonUltimo.setText("Ultimo");
        jButtonUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUltimoActionPerformed(evt);
            }
        });

        jButtonAnterior.setText("Anterior");
        jButtonAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAnteriorActionPerformed(evt);
            }
        });

        jButtonProximo.setText("Próximo");
        jButtonProximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProximoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelClientePrincipalLayout = new javax.swing.GroupLayout(jPanelClientePrincipal);
        jPanelClientePrincipal.setLayout(jPanelClientePrincipalLayout);
        jPanelClientePrincipalLayout.setHorizontalGroup(
            jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClientePrincipalLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelClientePrincipalLayout.createSequentialGroup()
                        .addComponent(jButtonAlterar)
                        .addGap(38, 38, 38)
                        .addComponent(jButtonIncluir))
                    .addGroup(jPanelClientePrincipalLayout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(jButtonBuscarCodigo)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonExcluir))
                    .addComponent(jTextFieldUF, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCEP, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextFieldEndereco, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextNome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE))
                    .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextFieldEmail, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextFieldTelefone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                    .addComponent(jFormattedTextFieldData, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelClientePrincipalLayout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addComponent(jButtonPrimeiro)
                .addGap(28, 28, 28)
                .addComponent(jButtonAnterior)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonProximo)
                .addGap(18, 18, 18)
                .addComponent(jButtonUltimo)
                .addGap(35, 35, 35))
        );
        jPanelClientePrincipalLayout.setVerticalGroup(
            jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelClientePrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonIncluir)
                        .addComponent(jButtonBuscarCodigo)
                        .addComponent(jButtonExcluir))
                    .addComponent(jButtonAlterar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldCEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelClientePrincipalLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10))
                    .addGroup(jPanelClientePrincipalLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanelClientePrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonPrimeiro)
                    .addComponent(jButtonProximo)
                    .addComponent(jButtonAnterior)
                    .addComponent(jButtonUltimo))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jMenu1.setText("Arquivo");

        jMenuItemConsultaNome.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemConsultaNome.setText("Consultar cliente pelo nome");
        jMenuItemConsultaNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConsultaNomeActionPerformed1(evt);
            }
        });
        jMenu1.add(jMenuItemConsultaNome);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Consulta cliente pela venda");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Relatórios de Clientes");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jPanelClientePrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(221, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanelClientePrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirActionPerformed
        // TODO add your handling code here:
        try {
            Cliente objCliente = portControl.ConsultarClienteUltimo();
            new IncluirCliente(this);
            exibirCliente(objCliente);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Consulta de Cliente",JOptionPane.WARNING_MESSAGE);
        }




        
    }//GEN-LAST:event_jButtonIncluirActionPerformed

    private void jMenuItemConsultaNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConsultaNomeActionPerformed
        // TODO add your handling code here:
         new PesqClienteNome(this);
         
    }//GEN-LAST:event_jMenuItemConsultaNomeActionPerformed

    private void jMenuItemConsultaNomeActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConsultaNomeActionPerformed1
        // TODO add your handling code here:
        new PesqClienteNome(this);
    }//GEN-LAST:event_jMenuItemConsultaNomeActionPerformed1

    private void jButtonUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUltimoActionPerformed
        // TODO add your handling code here:
        try {
            Cliente objCliente = portControl.ConsultarClienteUltimo();
            exibirCliente(objCliente);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Consulta de Cliente",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonUltimoActionPerformed

    private void jButtonProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProximoActionPerformed
        // TODO add your handling code here:
        try {
            if(jTextFieldCodigo.getText().equals("")){
                Cliente objCliente = portControl.ConsultarClienteUltimo();
                exibirCliente(objCliente);
            }else{
                Cliente objCliente = portControl.ConsultarClienteProximo(Integer.parseInt(jTextFieldCodigo.getText()));
                exibirCliente(objCliente);
            }


        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Consulta de Cliente",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonProximoActionPerformed

    private void jButtonAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnteriorActionPerformed
        // TODO add your handling code here:
        try {
            if(jTextFieldCodigo.getText().equals("")){
                Cliente objCliente = portControl.ConsultarClientePrimeiro();
                exibirCliente(objCliente);
            }else{
                Cliente objCliente = portControl.ConsultarClienteAnterior(Integer.parseInt(jTextFieldCodigo.getText()));
                exibirCliente(objCliente);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Consulta de Cliente",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAnteriorActionPerformed

    private void jButtonPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrimeiroActionPerformed
        // TODO add your handling code here:
        try {
            Cliente objCliente = portControl.ConsultarClientePrimeiro();
            exibirCliente(objCliente);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Consulta de Cliente",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonPrimeiroActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        // TODO add your handling code here:
        if(jTextFieldCodigo.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Defina o Cliente","Defina o Cliente",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if ( JOptionPane.showConfirmDialog(null, "Confirmar a exclusão do Cliente - " +
                jTextNome.getText().trim() + "?" ,
                "Cadastro de Cliente",
                JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION ){
            return;
        }
        try {
            portControl.excluirCliente(Integer.parseInt(jTextFieldCodigo.getText()));
            JOptionPane.showMessageDialog(this,"Cliente Excluído","Cliente Excluído",JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(this, ex.getMessage(),"Consulta de Cliente",JOptionPane.WARNING_MESSAGE);
        }
        limpaform();

}//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jButtonBuscarCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarCodigoActionPerformed

        String codigo = JOptionPane.showInputDialog(
                this,
                "Código do Cliente",
                "Consulta de Cliente",
                JOptionPane.QUESTION_MESSAGE);
        if (codigo == null) return;
        if (codigo.trim().equals("") ||
                !codigo.matches("[0-9]*")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Código Inválido.",
                    "Consulta de Cliente",
                    JOptionPane.WARNING_MESSAGE);
        }

        Cliente objCliente;
        try {
            objCliente = portControl.consultarClienteCodigo(Integer.parseInt(codigo));
            exibirCliente(objCliente);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Consulta de Cliente",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonBuscarCodigoActionPerformed

    private void jButtonAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarActionPerformed

        if (jTextFieldCodigo.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Não Alterado","Consulta de Cliente",JOptionPane.WARNING_MESSAGE);
        }else{


        // TODO add your handling code here:
        Cliente objClienteIncluir =
                new Cliente(Integer.parseInt((jTextFieldCodigo.getText())),
                            jTextNome.getText(),
                            jTextFieldEndereco.getText(),
                            jTextFieldBairro.getText(),
                            jTextFieldCidade.getText(),
                            jTextFieldTelefone.getText(),
                            jTextFieldUF.getText(),
                            jTextFieldCEP.getText(),
                            jTextFieldEmail.getText(),
                            new GregorianCalendar() );
        try {
            portControl.alteraCliente(objClienteIncluir);
            JOptionPane.showMessageDialog(this,"Cliente Atualizado","Cliente Atualizado",JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),"Consulta de Cliente",JOptionPane.WARNING_MESSAGE);
        }
        }

    }//GEN-LAST:event_jButtonAlterarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
  
//        try {
//
//            portControl.geraRelatorioCliente();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(
//                    this,
//                    e.getMessage(),
//                    "Relação de Clientes",
//                    JOptionPane.WARNING_MESSAGE);
//        }



        RelatorioCliente relatorioCliente = new RelatorioCliente();
        relatorioCliente.setVisible(true);


    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        new vendainterface.ConsultaCliente().setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ExibirCliente().setVisible(true);
                try {
                    // TODO add your handling code here:
                    System.out.println("Cliente");
                    con = portControl.conectar();
                } catch (SQLException ex) {
                    Logger.getLogger(ExibirCliente.class.getName()).log(Level.SEVERE, null, ex);
                }





            }
        });
    }
    private void exibirCliente(Cliente objCliente){
             
            // TODO add your handling code here:


            jTextFieldCodigo.setText(""+objCliente.getCodCliente());
            //jTextFieldCodigo.setEditable(false);
            jTextNome.setText(objCliente.getNome());
            jTextFieldEndereco.setText(objCliente.getEndereco());
            jTextFieldBairro.setText(objCliente.getBairro());
            jTextFieldCidade.setText(objCliente.getCidade());
            jTextFieldUF.setText(objCliente.getUf());
            jTextFieldCEP.setText(objCliente.getCep());
            jTextFieldTelefone.setText(objCliente.getTelefone());
            jTextFieldEmail.setText(objCliente.getE_mail());
            jFormattedTextFieldData.setText(LtpUtil.formatarData(objCliente.getData_cad_cliente(), "dd/MM/yyyy"));

            //jFormattedTextFieldData.setText(objCliente.getData_cad_cliente().toString());





    }
    private void limpaform(){
            jTextNome.setText("");
            jTextFieldEndereco.setText("");
            jTextFieldBairro.setText("");
            jTextFieldCidade.setText("");
            jTextFieldUF.setText("");
            jTextFieldCEP.setText("");
            jTextFieldEmail.setText("");
            jTextFieldTelefone.setText("");
            jTextFieldCodigo.setText("");
            jFormattedTextFieldData.setText("");


    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAlterar;
    private javax.swing.JButton jButtonAnterior;
    private javax.swing.JButton jButtonBuscarCodigo;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonIncluir;
    private javax.swing.JButton jButtonPrimeiro;
    private javax.swing.JButton jButtonProximo;
    private javax.swing.JButton jButtonUltimo;
    private javax.swing.JFormattedTextField jFormattedTextFieldData;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItemConsultaNome;
    private javax.swing.JPanel jPanelClientePrincipal;
    private javax.swing.JTextField jTextFieldBairro;
    private javax.swing.JTextField jTextFieldCEP;
    private javax.swing.JTextField jTextFieldCidade;
    private javax.swing.JTextField jTextFieldCodigo;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldEndereco;
    private javax.swing.JTextField jTextFieldTelefone;
    private javax.swing.JTextField jTextFieldUF;
    private javax.swing.JTextField jTextNome;
    // End of variables declaration//GEN-END:variables

}