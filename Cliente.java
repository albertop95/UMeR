

/**
 * Escreva a descrição da classe Cliente aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */

import java.util.GregorianCalendar;
import java.util.Set;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;


public class Cliente extends Utilizador implements Comparable<Cliente>, Serializable
{
    /** variáveis de instância*/
    private Localizacao localCliente;
    private Set<Viagem> viagensEfetuadas;
    private double totalGasto;
    
    
    
    public Cliente(){
        super();
        this.totalGasto=0;
        this.localCliente = new Localizacao();
        this.viagensEfetuadas = new TreeSet<Viagem>();
    }
    
    public Cliente(String nemail, String nnome, String npassword, String nmorada, GregorianCalendar ndata){
        super(nemail,nnome,npassword,nmorada,ndata);
        this.totalGasto=0;
        this.localCliente = new Localizacao();
        this.viagensEfetuadas = new TreeSet<Viagem>();
    }
    
    public Cliente(Cliente c){
        super(c.getEmail(),c.getNome(),c.getPassword(),c.getMorada(),c.getDataNasc());
        this.totalGasto=c.getTotalGasto();
        this.localCliente = new Localizacao(c.localCliente);
        this.viagensEfetuadas=c.getViagens();      
    }
    
    
   public Localizacao getLocalizacao(){
       return this.localCliente;
    }
    
    public double getTotalGasto(){
        return this.totalGasto;
    }
    
        public void setLocalizacao(Localizacao l){
        this.localCliente=l.clone();
    }
    
    public void setTotalGasto(double gasto){
        this.totalGasto=gasto;
    }
    
    public void adicionaGasto(double gasto){
        this.totalGasto=this.totalGasto+gasto;
    }
    
    public TreeSet<Viagem> getViagens(){
        TreeSet<Viagem> viagens =new TreeSet<Viagem>();
        viagens.addAll(this.viagensEfetuadas);
        return viagens;
    }
    
    public TreeSet<Viagem> getViagensEfetuadas(GregorianCalendar dataI, GregorianCalendar dataF){
        TreeSet<Viagem> viagens =new TreeSet<Viagem>();
        for(Viagem v : this.viagensEfetuadas){
            if((v.getData().compareTo(dataI)>=0) && ((v.getData().compareTo(dataF)<=0))){
                viagens.add(v);
            }
        }
        return viagens;
    }
    
    /** Adiciona uma nova viagem à lista de viagens Efetuadas*/
   
    public void adicionaViagem(Viagem v){
        this.viagensEfetuadas.add(v.clone());
    }
    
    public int compareTo(Cliente c){
        if(this.totalGasto==c.getTotalGasto()) return 0;
        if(this.totalGasto>c.getTotalGasto())  return -1;
        return 1;
    }
    
    public String toString(){
         StringBuilder txt = new StringBuilder();
         txt.append("Utilizador: " + super.toString());
         
         return txt.toString();
     }
    
    public Cliente clone(){
        return new Cliente(this);
    }
    
    
    
   

    
}