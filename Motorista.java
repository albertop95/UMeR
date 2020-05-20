
/**
 * Escreva a descrição da classe Motorista aqui.
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

public class Motorista extends Utilizador implements Serializable
{
     /** variáveis de instância*/
     private int cumprimentoH; //terá de estar entre 0 e 100
     private double classificacao; //terá de estar entre 0 e 100
     private double kms; //total dos kms ja realizados
     private boolean disponivel; //se o motorista se encontra a trabalhar
     private Set<Viagem> viagensEfetuadas;
    
     //Comparator<Viagem> comData = new ComparaData();
     //Falta historico de viagens
     
     public Motorista(){
         super();
         this.cumprimentoH=0;
         this.classificacao = 0;
         this.kms = 0;
         this.disponivel = false;
         this.viagensEfetuadas = new TreeSet<Viagem>();
     }
     
     public Motorista(String nemail, String nnome, String npassword, String nmorada, GregorianCalendar ndata){
         super(nemail,nnome,npassword,nmorada,ndata);
         this.cumprimentoH =0;
         this.classificacao = 0;
         this.kms = 0;
         this.disponivel = false;
         this.viagensEfetuadas = new TreeSet<Viagem>();
     }
     
     public Motorista(Motorista m){
         super(m.getEmail(),m.getNome(),m.getPassword(),m.getMorada(),m.getDataNasc());
         this.cumprimentoH = m.getCumprimentoH();
         this.classificacao = m.getClassificacao();
         this.kms = m.getKms();
         this.disponivel = m.getDisponivel();
         this.viagensEfetuadas=m.getViagens();
     }
     
     public int getCumprimentoH(){
         return this.cumprimentoH;
     }
     
     public double getClassificacao(){
         return this.classificacao;
     }
     
     public double getKms(){
         return this.kms;
     }
     
     public boolean getDisponivel(){
         return this.disponivel;
     }
     
     
     
     public TreeSet<Viagem> getViagens(){
        TreeSet<Viagem> viagens =new TreeSet<Viagem>();
        viagens.addAll(this.viagensEfetuadas);
        return viagens;
    }
    
     
    
    public void adicionaClassificacao(int c){
        this.classificacao=((this.classificacao+c)/2);
    }
    
    
    public TreeSet<Viagem> getViagensEfetuadas(GregorianCalendar dataI, GregorianCalendar dataF){
        TreeSet<Viagem> viagens =new TreeSet<Viagem>();
        for(Viagem v : this.viagensEfetuadas){
            if(((v.getData().compareTo(dataI)==0)||(v.getData().compareTo(dataI)>0)) && ((v.getData().compareTo(dataF)==0)||(v.getData().compareTo(dataF)<0))){
                viagens.add(v);
            }
        }
        return viagens;
    }
    
    /** Adiciona uma nova viagem à lista de viagens Efetuadas*/
    
    public void adicionaViagem(Viagem v){
        this.viagensEfetuadas.add(v.clone());
    }
    
     public void setCumprimentoH(int cumprimentoH){
         this.cumprimentoH = cumprimentoH;
     }
     
     public void setClassificacao(int clasificacao){
          this.classificacao = classificacao;
     }
        
     public void setKms(double kms){
         this.kms = kms;
     }
     
     public void setDisponivel(boolean disponivel){
         this.disponivel = disponivel;
     }
     
     public String toString(){
         StringBuilder txt = new StringBuilder();
         txt.append("Utilizador: " + super.toString());
         txt.append("Cumprimento horario: " + this.cumprimentoH + "\n");
         txt.append("Classificacao: " + this.classificacao + "\n");
         txt.append("Kms: " + this.kms + "\n");
         txt.append("disponibidade: " + this.disponivel + "\n");
         return txt.toString();
     }
     
     public Motorista clone(){
         return new Motorista(this);
     }
     
     public boolean equals(Motorista m){
         if (m != null)
            return((super.equals(m)) &&
                   (cumprimentoH == m.getCumprimentoH()) &&
                   (classificacao == m.getClassificacao()) &&
                   (kms == m.getKms()) &&
                   (disponivel == m.getDisponivel()));
         else
            return false;
     }
}
