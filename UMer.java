
/**
 * Escreva a descrição da classe UMer aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */


import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;
import java.util.*;
import java.util.Comparator;
import java.io.IOException;
import java.io.Serializable;
import java.io.*;


public class UMer implements Serializable
{
    /** Variáveis de instacia */
    private Map<String, Utilizador> utilizadores_base; //Map de email--utilizador
    private Map<String, Veiculo> veiculos_base;  //Map de Matricula--Veiculo
    private Utilizador login;
    
    
    
   /**Construtores para objetos da classe UMer*/
    
   public UMer(){
        this.utilizadores_base = new TreeMap<String,Utilizador>();
        this.veiculos_base = new TreeMap<String,Veiculo>();
    }
    
    
    public UMer(Map<String,Utilizador> utilizadores_base, Map<String,Veiculo> veiculos_base){
        this.utilizadores_base = new TreeMap<String,Utilizador>(utilizadores_base);
        this.veiculos_base = new TreeMap<String,Veiculo>(veiculos_base);
    }
    
    public ArrayList<Veiculo> getTodosVeiculos(){
        ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();
        for(Veiculo v : this.veiculos_base.values()){
            veiculos.add(v.clone());
        }
        return veiculos;
    }
    
    public Utilizador getUtilizador(String email){
        return this.utilizadores_base.get(email);
    }
   
     public Veiculo getVeiculo(String matricula){
        return this.veiculos_base.get(matricula);
    }   
        
        
   /** Registar um novo utilizador na base de utilizadores*/
    
   public void registarUtilizador(Utilizador utilizador) throws UtilizadorExistenteException{
        if(utilizadores_base.containsKey(utilizador.getEmail())){
            throw new UtilizadorExistenteException("Email " +utilizador.getEmail()+ "já existe");
        }
        utilizadores_base.put(utilizador.getEmail(),utilizador.clone());
   }
   
   
   
   /** Iniciar sessao na aplicacao com as credenciais email de utilizador
    * e password
    */
   
   public boolean iniciaSessao(String email,String password) throws SemAutorizacaoException{
        if(!(utilizadores_base.get(email).getPassword().equals(password))){
            throw new SemAutorizacaoException("Email ou Password incorrectos");
        }
       
       if(utilizadores_base.get(email).getPassword().equals(password)){
           login = utilizadores_base.get(email);
           return true;
        }
        return false;
    }
    
    /** Registar um novo veiculo na base de Veiculos*/
    
    public void registaVeiculo(Veiculo v) throws VeiculoExistenteException{
         if(veiculos_base.containsKey(v.getMatricula())){  //se um veiculo ja estiver registado na base com a mesma matricula
            throw new VeiculoExistenteException("Veiculo já existe");
        }
        veiculos_base.put(v.getMatricula(),v.clone());
    }
    
    /** Fechar a sessao atual*/
    
    public void fechaSessao(){
        login=null;
    }
    
    /** Implementa tudo o que é necessário ao fazer a viagem 
     * 1) Criar o objeto viagem 
     * 2) Colocar o taxi no destino da viagem a espera de novas solicitacoes 
     * 3) Adicionar os km's fetios no perfil do motorista 
     * 4) Adicionar a viagem à lista de viagens do cliente e do motorista
     * 5) Adiciona o custo da viagem no perfil do cliente
     * 6) Nota dada pelo cliente no perfil do motorista -----> EM FALTA
     * 
     */
    
    public Viagem fazViagem(String email, String matricula,Localizacao destino) throws VeiculoInexistenteException,UtilizadorIndisponivelException{
        if(!veiculos_base.containsKey(matricula)){
            throw new VeiculoInexistenteException("Veiculo não existe");
        }
        Motorista m = null;
        m= (Motorista) utilizadores_base.get(veiculos_base.get(matricula).getEmailMotorista());
        if(m.getDisponivel()== false){
            throw new UtilizadorIndisponivelException("Utilizador Indisponivel");
        }
        Cliente c = null;
        c=(Cliente) utilizadores_base.get(email);
        Veiculo v = null;
        v=veiculos_base.get(matricula);
        GregorianCalendar data = new GregorianCalendar();
        
        //1)
        Viagem novaViagem = new Viagem(c,v,destino,data);
        //2)
        v.setLocalizacao(destino);
        //3)
        //m= (Motorista)  utilizadores_base.get(v.getEmailMotorista());
        double distEfetuada=novaViagem.distTotal();
        double kmsMotorista= m.getKms()+distEfetuada;
        m.setKms(kmsMotorista);

        //4)
        c.adicionaViagem(novaViagem);
        m.adicionaViagem(novaViagem);
        
        //5)
        c.adicionaGasto(novaViagem.custoRealViagem());
       return novaViagem; 
    }
    
    

    public void adicionaNota(String matricula, int nota){
        Motorista m = (Motorista) utilizadores_base.get(veiculos_base.get(matricula).getEmailMotorista());
        m.adicionaClassificacao(nota);
    }
    
    public boolean estaDisponivel(String email, String matricula){
        Motorista m = null;
        m= (Motorista) utilizadores_base.get(veiculos_base.get(matricula).getEmailMotorista());
        if(m.getDisponivel()== false){
            return false;
        }
        return true;
    }
    
    
    
    /** Coloca o motorista como disponivel */
    
    public void entraServico(String email){
        Motorista m = null;
        m = (Motorista) utilizadores_base.get(email);
        m.setDisponivel(true);
    }
    
    
    
    /** Coloca o motorista como indisponivel */
    
    public void terminaServico(String email){
         Motorista m = null;
        m = (Motorista) utilizadores_base.get(email);
        m.setDisponivel(false);
    }
    
    
    /** Devolve a matricula do veiculo que esta mais perto
     * do Cliente(c)
     */
    
    public String maisPerto(Cliente c){
        String matricula="";
        double distanciaF=100000000;
        double distancia;
        for (String mat : veiculos_base.keySet()) {
            distancia=veiculos_base.get(mat).getLocalizacao().distancia(c.getLocalizacao());
            if(distancia<distanciaF){
                distanciaF=veiculos_base.get(mat).getLocalizacao().distancia(c.getLocalizacao());
                matricula=veiculos_base.get(mat).getMatricula();
                
            }
        }
        return matricula;
        
    }
    
    
    public ArrayList<Cliente> clientesMaisGastam(){
        TreeSet<Cliente> clientes = new TreeSet<Cliente>();
        Utilizador u = null;
        Cliente c = null;
        for(String email : utilizadores_base.keySet()){
            u=utilizadores_base.get(email);
            if(u instanceof Cliente){
               c=(Cliente) u;
               clientes.add(c);
            }
        }
        Iterator<Cliente> it =  clientes.iterator();
        ArrayList<Cliente> cGastam = new ArrayList<Cliente>();
        int i=1;
        while(it.hasNext()&&i<=10){
            cGastam.add(it.next());
            i+=1;
        }
        return cGastam;
    }
    
    
    public static UMer leObj(String fich) throws IOException,ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fich));
        UMer u =  (UMer) ois.readObject();
        ois.close();
        return u;
    }
   
        
        
   public void gravaObj(String fich) throws IOException{
       ObjectOutputStream oos =new ObjectOutputStream( new FileOutputStream(fich));
       oos.writeObject(this);
       oos.flush();
       oos.close();
   }
        
        
            
    
    

  
}
