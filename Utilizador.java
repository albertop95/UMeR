
/**
 * Escreva a descrição da classe Utilizador aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */

import java.util.GregorianCalendar;
import java.io.Serializable;

public class Utilizador implements Serializable
{
   /** variáveis de instância*/
    private String email; //identifica o utilizador
    private String nome;
    private String password;
    private String morada;
    private GregorianCalendar dataNascimento;
    
    /**Construtores Usuais*/
    public Utilizador(){
        this.email="";
        this.nome="";
        this.password="";
        this.morada="";
    }
    
    public Utilizador(String nemail, String nnome, String npassword, String nmorada, GregorianCalendar ndata){
        this.email=nemail;
        this.nome=nnome;
        this.password=npassword;
        this.morada=nmorada;
        this.dataNascimento=ndata;
    }
    
    public Utilizador(Utilizador u){
        this.email=u.getEmail();
        this.nome=u.getNome();
        this.password=u.getPassword();
        this.morada=u.getMorada();
        this.dataNascimento=u.getDataNasc();
    }
    
        
   /**Métodos de instancia*/
   public String getEmail(){return this.email;}
   public String getNome(){return this.nome;}
   public String getPassword(){return this.password;}
   public String getMorada(){return this.morada;}
   public GregorianCalendar getDataNasc(){return this.dataNascimento;}
   
   public void setEmail(String nemail){
       this.email=nemail;
    }
    
   public void setNome(String nnome){
        this.nome=nnome;
   }
   
   public void setPassword(String npass){
       this.password=npass;
    }
    
    public void setMorada(String nmorada){
        this.morada=nmorada;
    }
    
    public void setData(GregorianCalendar ndata){
        this.dataNascimento=ndata;
    }
    
    public String toString(){
        StringBuilder txt = new StringBuilder();
        txt.append("Email :" +this.email+ "\n");
        txt.append("Nome :" +this.nome+ "\n");
        txt.append("Password :" +this.password+ "\n");
        txt.append("Morada :" +this.morada+ "\n");
        txt.append("Data de Nascimento: " +this.dataToString(dataNascimento)+ "\n");
        return txt.toString();
    }
    
    public Utilizador clone(){
        return new Utilizador(this);
    }
    
     public boolean equals(Utilizador u){
        if (u != null)
            return ((email == u.getEmail()) &&
                    (nome == u.getNome()) &&
                    (password == u.getPassword()) &&
                    (morada == u.getMorada()) &&
                    (dataNascimento == u.getDataNasc()));
        else
            return false;
    }
    

     private String dataToString(GregorianCalendar data){
        int ano=data.get(GregorianCalendar.YEAR);
        int mes= data.get(GregorianCalendar.MONTH);
        int dia= data.get(GregorianCalendar.DAY_OF_MONTH);
        return "" + dia + "-" + mes + "-" + ano;
    }
}
