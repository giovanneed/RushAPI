/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author felipe.cardoso
 */
public class CroquiException extends Exception {

    private static final long serialVersionUID = 3870277218575058015L;

    public CroquiException(String mensagem) {
        super(mensagem);
    }

    public class ExtensaoDeImagemNaoAceitaCroquiException extends CroquiException {

        private static final long serialVersionUID = 3030567801130607615L;

        public ExtensaoDeImagemNaoAceitaCroquiException(String mensagem) {
            super(mensagem);
        }
    }
}
