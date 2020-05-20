
/**
 * Escreva a descrição da classe Localizacao aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */


import java.io.Serializable;


public class Localizacao implements Serializable
{
    /** variáveis de instância*/
    private double x,y;
    
    /** Construtores usuais*/
    public Localizacao(double x, double y){this.x=x;this.y=y;}
    public Localizacao(){this(0.0,0.0);}
    public Localizacao(Localizacao l){this.x=l.getX();this.y=l.getY();}
    

   /** Metodos complementares usuais*/
    public boolean equals(Localizacao l){
        if(this.x==l.getX() && this.y==l.getY()) return true;
        else return false;
    }
    
    public String toString(){
        String r;
        r="Cordenada x: " + this.x + "\nCordenada y: "+this.y;
        return r;
    }
    
    public Localizacao clone(){
        return new Localizacao(this);
    } 
    
    /** Metodos de instancia*/
    
    //get's
    public double getX(){return this.x;}
    public double getY(){return this.y;}
    
    //set's
    public void setX(double x){this.x=x;}
    public void setY(double y){this.y=y;}
    
    
    public double distancia(Localizacao l){
        double x2=(java.lang.Math.pow((l.getX()-this.x),2));
        double y2=(java.lang.Math.pow((l.getY()-this.y),2));
        return java.lang.Math.sqrt(x2+y2);
    }

}
