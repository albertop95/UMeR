
/**
 * Escreva a descrição da classe Viagem aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */

import java.util.GregorianCalendar;
import java.io.Serializable;


public class Viagem implements Comparable<Viagem>,Serializable
{
    /** Variaveis de instancia */
    private Cliente cliente;
    private Veiculo taxi;
    private Localizacao destino;
    private GregorianCalendar data;

    
    
    /**Construtores Usuais */
    
    private Viagem(){
        this.cliente = new Cliente();
        this.taxi = new Veiculo();
        this.destino = new Localizacao();
        this.data = new GregorianCalendar();
    }
    
    public Viagem(Cliente c,  Veiculo v, Localizacao l, GregorianCalendar d){
        this.cliente=new Cliente(c.clone());
        this.taxi=new Veiculo(v.clone());;
        this.destino=new Localizacao(l.clone());
        this.data= d;
    }
    
    public Viagem(Viagem v){
        this.cliente = new Cliente(v.getCliente());
        this.taxi = new Veiculo(v.getTaxi());
        this.destino = new Localizacao(v.getDestino());
        this.data=v.getData();
    }
    
    
    //GET's
     public Cliente getCliente(){
        return this.cliente.clone();
    }
    
   public Veiculo getTaxi(){
        return this.taxi.clone();
    }
    
    public Localizacao getDestino(){
        return this.destino.clone();
    }
    
    public GregorianCalendar getData(){
        return this.data;
    }
    

   
    
    //SET's
    public void setCliente(Cliente c){
        this.cliente=c.clone();
    }
    
    public void setTaxi(Veiculo v){
        this.taxi=v.clone();
    }
    
    public void setDestino(Localizacao l){
        this.destino=l.clone();
    }
    
    public void setData(GregorianCalendar d){
        this.data=d;
    }
    
    
    public Viagem clone(){
        return new Viagem(this);
    }
    
     public String toString(){
        StringBuilder txt = new StringBuilder();
        txt.append("Cliente: " + this.cliente + "\n");
        txt.append("Veiculo: " + this.taxi + "\n");
        txt.append("Destino: \n" + this.destino + "\n");
        txt.append("Data: "+ this.dataToString(data) +"\n\n");
        return txt.toString();
    }
    
    
    public boolean equals(Viagem v){
        if (v != null)
            return ((cliente == v.getCliente()) &&
                    (taxi == v.getTaxi()) &&
                    (destino == v.getDestino()) &&
                    (data == v.getData()));
        else
            return false;
    }
    
    
    
     private String dataToString(GregorianCalendar data){
        int ano=data.get(GregorianCalendar.YEAR);
        int mes= data.get(GregorianCalendar.MONTH);
        int dia= data.get(GregorianCalendar.DAY_OF_MONTH);
        return "" + dia + "-" + mes + "-" + ano;
    }
    
    /** Devolve a distancia entre o taxi e o cliente 
     */
    
    public double distTaxiCliente(){
        return this.taxi.getLocalizacao().distancia(this.cliente.getLocalizacao());
    }
    
    /** Devolve a distancia entre o Cliente e o Destino 
    */
    
    public double distClienteDestino(){
        return this.cliente.getLocalizacao().distancia(this.destino);
    }
    
    /** Retorna a distancia total efetuadana viagem
     */
    
    public double distTotal(){
       return distTaxiCliente()+distClienteDestino();
    }
    
    /** Devolve o tempo esperado para chegar ao cliente */
   
    public double tempoTaxiClienteE(){
        double tempo = distTaxiCliente()/this.taxi.getVelocidadeMed();
        return tempo;
    }

    /** Devolve o tempo esperado para chegar do cliente 
     * ao destino pretendido pelo cliente */
    
     public double tempoClienteDestinoE(){
        double tempo = distClienteDestino()/this.taxi.getVelocidadeMed();
        return tempo;
    }
    
    /**Tempo total esperado da viagem*/ 
    public double tempoTotalEsperado(){
        return tempoTaxiClienteE()+tempoClienteDestinoE();
    }
    
    public double tempoTotalReal(){
        return tempoTotalEsperado()+(tempoTotalEsperado()*(this.taxi.getAtraso()*0.01));
    }
        
     
    
    /** Custo estimado da viagem, tendo em conta o deslocamento que é necessario
     * efetuar, e o tempo total da viagem
     * 
     *
     */
   
    public double custoViagem(){
        double custoPegarCliente = this.taxi.getPrecoKm() * distTaxiCliente();
        double custoChegarDestino = this.taxi.getPrecoKm() * distClienteDestino();
        return custoPegarCliente + custoChegarDestino+tempoTotalEsperado();
    }
    
    
    /** Custo real da Viagem
     * 
     */
    
    public double custoRealViagem(){
        return distTotal()*this.taxi.getPrecoKm()+tempoTotalReal();
    };
    
    
    /** Comparador Natural*/
    
    public int compareTo(Viagem v){
      return data.compareTo(v.getData());
      
    }
    

}
 