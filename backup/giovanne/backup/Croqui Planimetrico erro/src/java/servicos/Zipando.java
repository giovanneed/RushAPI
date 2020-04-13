/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicos;

/**
 *
 * @author felipe.cardoso
 */
/*
Zipando.java
Zipa um arquivo ou pasta gerando um arquivo .zip
Data:11/01/2005
Autor: Glauber Antonio Garcia Brilhante
 */
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.*;

public class Zipando {

// tamanho de leitura maximo
    static final int TAM_BUFFER = 1024;
    public static String saida = "";

    public static void zipa(String outFileZip, String path) {
        int i, cont;

        outFileZip = outFileZip.replace('/', File.separatorChar);

        try {
            File f = null;
            FileInputStream fileinput = null;
            FileOutputStream fileoutput = null;
            BufferedInputStream buffer = null;
            ZipOutputStream zipar = null;
            ZipEntry entry = null;
            byte[] dados = new byte[TAM_BUFFER];

            fileoutput = new FileOutputStream(outFileZip);

            zipar = new ZipOutputStream(new BufferedOutputStream(fileoutput));

            String[] arquivos;

            f = new File(path); // todos os arquivos da pasta atual
            arquivos = f.list();

            for (i = 0; i < (arquivos.length); i++) {
                File arquivo = new File(path + arquivos[i]);
                if (arquivo.isFile() && !(arquivo.getAbsolutePath()).equals(outFileZip)) {
                    fileinput = new FileInputStream(arquivo.getAbsolutePath());
                    buffer = new BufferedInputStream(fileinput, TAM_BUFFER);
                    entry = new ZipEntry(arquivos[i]);
                    zipar.putNextEntry(entry);

                    while ((cont = buffer.read(dados, 0, TAM_BUFFER)) != -1) {
                        zipar.write(dados, 0, cont);
                    }
                    buffer.close();

                    //arquivo.delete();
                }
            }
            zipar.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Zipando.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Zipando.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// metodo para zipar
}//classe
