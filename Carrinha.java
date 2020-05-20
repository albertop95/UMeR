
/**
 * Escreva a descrição da classe Carrinha aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */


import java.io.Serializable;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Carrinha extends Veiculo implements Serializable, FilaDeEspera
{
   /** Variaveis de instancia */
   List<Cliente> filaDeEspera;
   List<Localizacao> destinos;
    
    public Carrinha(){
        super();
        filaDeEspera=new ArrayList<Cliente>();
        destinos=new ArrayList<Localizacao>();
    }
    
    public Carrinha(String matricula,double velo, double preco, Localizacao l,String motorista){
        super(matricula,velo,preco,l,motorista);
        filaDeEspera=new ArrayList<Cliente>();
        destinos=new ArrayList<Localizacao>();
        
    }
    
    public Carrinha(Carrinha c){
        super(c.getMatricula(),c.getVelocidadeMed(),c.getPrecoKm(),c.getLocalizacao(),c.getEmailMotorista());
        //filaDeEspera=new ArrayList<Cliente>();
        filaDeEspera=c.getFilaDeEspera();
        destinos=c.getDestinos();
    }
    
    
    public ArrayList<Cliente> getFilaDeEspera(){
        ArrayList<Cliente> fila = new ArrayList<Cliente>();
        for(Cliente c : this.filaDeEspera){
            fila.add(c.clone());
        }
        return fila;
    }
    
    public ArrayList<Localizacao> getDestinos(){
        ArrayList<Localizacao> dest = new ArrayList<Localizacao>();
        for(Localizacao l : this.destinos){
            dest.add(l.clone());
        }
        return dest;
    }
    
    
    public int getAtraso(){
        Random rand = new Random();
        int randomAtraso=rand.nextInt(35-25) + 25;
        return randomAtraso;
    }

    
    
    public Carrinha clone(){
        return new Carrinha(this);
    }
    
    public void adiciona(Cliente c, Localizacao lDestino){
        filaDeEspera.add(c);
        destinos.add(lDestino);
    }
    
    
    public Localizacao getDestino1(){
        return this.destinos.get(0).clone();
    }
    
    
    public Cliente remove(){
        Cliente c;
        Localizacao destino;
        c=filaDeEspera.get(0);
        destino=destinos.get(0);
        filaDeEspera.remove(0);
        destinos.remove(0);
        return c;
        
    }
    
    
}