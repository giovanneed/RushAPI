/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Filipe Lomelino Cardoso
 */
public class Resposta implements java.io.Serializable {

    private String indiceCadastral;
    private String lotectm;
    private double areaConstruida;
    private double areaTerreno;
    private double percentual;

    /**
     * 
     * @param indiceCadastral - Índice cadastral da tabela iptu
     * @param lotectm - Número ctm do lote
     * @param areaConstruida - Área construida do lote
     * @param areaTerreno - Área do lote
     * @param percentual - Percentual de diferenca entre a area construida anterior, para a atual
     */
    public Resposta(String indiceCadastral, String lotectm, double areaConstruida, double areaTerreno, double percentual) {
        super();
        this.indiceCadastral = indiceCadastral;
        this.lotectm = lotectm;
        this.areaConstruida = areaConstruida;
        this.areaTerreno = areaTerreno;
        this.percentual = percentual;
    }

    public double getAreaConstruida() {
        return areaConstruida;
    }

    public void setAreaConstruida(double areaConstruida) {
        this.areaConstruida = areaConstruida;
    }

    public double getAreaTerreno() {
        return areaTerreno;
    }

    public void setAreaTerreno(double areaTerreno) {
        this.areaTerreno = areaTerreno;
    }

    public String getIndiceCadastral() {
        return indiceCadastral;
    }

    public void setIndiceCadastral(String indiceCadastral) {
        this.indiceCadastral = indiceCadastral;
    }

    public String getLotectm() {
        return lotectm;
    }

    public void setLotectm(String lotectm) {
        this.lotectm = lotectm;
    }

    public double getPercentual() {
        return percentual;
    }

    public void setPercentual(double percentual) {
        this.percentual = percentual;
    }
}
