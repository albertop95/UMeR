
/**
 * Escreva a descrição da classe Veiculo aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */


import java.io.Serializable;



public  class Veiculo implements Serializable
{
    /** variáveis de instância*/
    private String matricula; //Identifica o veiculo
    private double velocidadeMed, precoKm;
    private Localizacao localVeiculo;
    private String emailMotorista;
    
    //private Motorista motorista; //Quem o está a conduzir em cada momento
    
    /** Cosntrutores Usuiais */
    public Veiculo(){
        this.matricula="";
        this.velocidadeMed=0;
        this.precoKm=0;
        this.localVeiculo = new Localizacao();
        this.emailMotorista="";
        //this.motorista = new Motorista();
    }

    public Veiculo(String matricula, double velo, double precoKm,Localizacao l,String emailMotorista){
        this.matricula=matricula;
        this.velocidadeMed=velo;
        this.precoKm=precoKm;
        this.localVeiculo= new Localizacao(l);
        this.emailMotorista= emailMotorista;
        //this.motorista= new Motorista(m);
    }
    
    public Veiculo(Veiculo v){
        this.matricula=v.getMatricula();
        this.velocidadeMed=v.getVelocidadeMed();
        this.precoKm=v.getPrecoKm();
        this.localVeiculo= new Localizacao(v.getLocalizacao());
        this.emailMotorista=v.getEmailMotorista();
        //this.motorista = new Motorista(v.getMotorista());
        
        
    }
    
    /** GET's e SET's */
    public String getMatricula(){
        return this.matricula;
    }
    
    public double getVelocidadeMed(){
        return this.velocidadeMed;
    }
    
    public double getPrecoKm(){
        return this.precoKm;
    }
    
    public Localizacao getLocalizacao(){
        return this.localVeiculo.clone();
    }
    
    public String getEmailMotorista(){
        return this.emailMotorista;
    }
    
    
    public int getAtraso(){
        return 0;
    }
    
    
    public void setMatricula(String matricula){
        this.matricula=matricula;
    }
    
    public void setVelocidadeMed(double velo){
        this.velocidadeMed=velo;
    }
    
    public void setPrecoKm(double precoKm){
        this.precoKm=precoKm;
    }
    
    public void setLocalizacao(Localizacao l){
        this.localVeiculo=l.clone();
    }
    
    public void setEmailMotorista(String emailMotorista){
        this.emailMotorista=emailMotorista;
    }
   
    
    public String toString(){
        StringBuilder txt = new StringBuilder();
        txt.append("Matricula: " +this.matricula+ "\n");
        txt.append("Velocidade Media :" +this.velocidadeMed+ "\n");
        txt.append("Preco por Km :" +this.precoKm+ "\n");
        txt.append("Localizacao :\n" +this.localVeiculo+ "\n");
        txt.append("Motorista :" +this.emailMotorista+ "\n");
        /**txt.append(this.motorista.toString());*/
        return txt.toString();
    }
    
   public Veiculo clone(){
        return new Veiculo(this);
    }
    
   public boolean equals(Veiculo v){
        if (v != null)
            return ((matricula == v.getMatricula())&&
                    (velocidadeMed == v.getVelocidadeMed()) && 
                    (precoKm == v.getPrecoKm()) &&
                    (localVeiculo == v.getLocalizacao()) && 
                    (emailMotorista == v.getEmailMotorista()));
                    //(motorista == v.getMotorista()));
        else
            return false;
    } 
}
    
   
        
