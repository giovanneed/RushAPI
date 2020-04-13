package dao;

/**
 *
 * @author henriquevm
 */
public class Iptu {
    private String indice_cadastral;
    private String nulotctm;
    private double area_construída;

    public Iptu(String indice_cadastral,String nulotctm,double area_construída){
        this.indice_cadastral = indice_cadastral;
        this.nulotctm = nulotctm;
        this.area_construída = area_construída;
    }
    
    public String getIndice_cadastral() {
        return indice_cadastral;
    }
 
    public String getNulotctm() {
        return nulotctm;
    }

    public double getArea_construída() {
        return area_construída;
    }
    
}
