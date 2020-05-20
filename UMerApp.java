

/**
 * Escreva a descrição da classe UMerApp aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */

import java.util.Date;
import java.util.GregorianCalendar;
import static java.lang.System.out;
import java.util.Scanner;
import java.util.Set;
import java.util.List;
import java.io.*;
import java.util.*;

import java.io.Serializable;


public class UMerApp implements Serializable
{
    private static UMer u = new UMer();
    private static Menu menuMain, menuRegistar, menuRegistadosCliente;
    private static Menu menuRegistadosMotorista,menuViagem, menuRegiVeiculo;
    private static Menu menuMotoristaIndisp, menuConsultar;
    
   
    
    
    
    
    public static void clearScreen(){
            System.out.print('\u000C');
        }
        
   
        
        
   public static void carregarMenus(){
       String []  opsmain = {"Iniciar Sessão",
                              "Registar",
                              "Consulta"
                              };
       String [] opsregistar = {"Adicionar motorista",
                                 "Adicionar cliente"
                                 };
       String [] opsCliente = {"Fazer Viagem",
                                 "Histórico de Viagens"
                                 };
      
       String [] opsViagem = {"Escolher taxi",
                                 "Taxi mais perto"
                                 };
       
       String [] opsMotorista = {"Entrar ao Serviço",
                                 "Terminar Serviço",
                                 "Despachar Fila de espera",
                                 "Histórico de Viagens"
                                };
        String [] opsVeiculo = {"Moto",
                                "Carro",
                                "Carrinha",
                                };
                                
         String [] opsIndisp = {"Escolher novo taxi",
                                 "Adicionar a fila de espera"
                                 };
         
        String [] opsConsultar = {"10 Clientes que mais gastam",
                               "Motoristas com mais desvios"
                               };
     
       menuMain=new Menu(opsmain);
       menuRegistar = new Menu(opsregistar);
       menuRegistadosCliente = new Menu(opsCliente);
       menuViagem = new Menu(opsViagem);
       menuRegistadosMotorista = new Menu(opsMotorista);
       menuRegiVeiculo = new Menu(opsVeiculo);
       menuMotoristaIndisp = new Menu(opsIndisp);
       menuConsultar = new Menu(opsConsultar);
    }
    
    public static void main(String[] args){
        clearScreen();
        carregarMenus();
        initUMeRApp();
        Scanner ler = new Scanner(System.in);
        String opcao;
        do{
            menuMain.executa();
            switch (menuMain.getOpcao()){
                case 1: iniciaSessao();
                        break;
                case 2: registar();
                        break;
                case 3: consultar();
                        break;
              
                
            }
            
        }while(menuMain.getOpcao()!=0);
        try {
            u.gravaObj("UMeR.txt");
            
        }
        catch (IOException e) {
            System.out.println("Não consegui gravar os dados!");
        }
       
    }
    
    /**Registar um novo utilizador como:
     * 1-Motorista
     * 2-Cliente
     */
    
    public static void registar(){
        clearScreen();
        String nome,email,password,morada;
        
        GregorianCalendar data;
       
    
        int dia,mes,ano,opcao;
        Scanner ler = new Scanner(System.in);
        Utilizador utilizador;
        menuRegistar.executa();
        
            if(menuRegistar.getOpcao() != 0){
                clearScreen();
            
            out.println("Inserir Email:");
            email=ler.nextLine();
            
            out.println("Inserir Password:");
            password=ler.nextLine();
            
            out.println("Inserir Nome:");
            nome=ler.nextLine();
            
            out.println("Inserir Morada:");
            morada=ler.nextLine();
            out.println("Inserir Data de Nascimento");
            System.out.println("Insira o dia (1-31):");
            dia = ler.nextInt();
            while(dia<1 || dia>31){
            out.println("Dia inválido");
                        dia = ler.nextInt();
                    }
            out.println("Insira o mes (1-12):");
            mes = ler.nextInt();
            ler.nextLine();
            while(mes<1 || mes>12){
               System.out.println("Mês inválido");
                mes = ler.nextInt();
            }
            out.println("Insira o ano (1940-2017):");
            ano = ler.nextInt();
            while(ano<1940 || ano>2017){
              out.println("Ano Inválido");
              ano = ler.nextInt();
             }
            data= new GregorianCalendar(ano,mes,dia);
            switch(menuRegistar.getOpcao()){
                case 1 : utilizador = new Motorista(email,nome,password,morada,data);
                         registaVeiculo(email);
                         break;
                case 2 : utilizador = new Cliente(email,nome,password,morada,data);
                         clearScreen();
                         break;
                default: utilizador = new Utilizador(email, nome,password,morada,data);
                         break;
            }
            try{
                u.registarUtilizador(utilizador);
               
                out.println("Utilizador criado com sucesso");
            } 
            catch(UtilizadorExistenteException e){
                out.println("");
                out.println(e.getMessage());
                out.println("");
                ler.nextLine();
            }
        
        
            
        }else{
            clearScreen();
            out.println("Registo Cancelado");
            
        }
        ler.close();
    }
    
    
    /**Se for escolhida a opcao no menuMain de iniciar Sessao
     * 
     */
    
    public static void iniciaSessao(){
        clearScreen();
        Scanner ler= new Scanner(System.in);
        int opcao;
        String email;
        String password;
        out.println("######### Iniciar Sessão #########");
        out.println("Inserir email:");
        email=ler.nextLine();
        out.println("Inserir password:");
        password=ler.nextLine();
        try{
            boolean x = u.iniciaSessao(email,password);
            if(u.getUtilizador(email) instanceof Cliente){
                menuRegistadoCliente(email);
            }
            if(u.getUtilizador(email) instanceof Motorista){
                menuRegistadoMotorista(email);
            }
        }
        
        catch(SemAutorizacaoException e){
            out.println(e.getMessage());
        }
        catch(NullPointerException e){
            out.println();
            out.println("Utilizador não existe");
        
        }
    }
    
    
    
    /** Onde poderão ser feitas várias consultas relativamente a: 
     * 1-Clientes
     * 2-Motoristas
     */
    
    public static void consultar(){
        clearScreen();
        int n;
        Scanner ler = new Scanner(System.in);
        List<Cliente> clientes = null;
        do{
            menuConsultar.executa();
            switch(menuConsultar.getOpcao()){
                case 1: clearScreen();
                        clientes=u.clientesMaisGastam();
                        int i=1;
                        out.println("---------Lista---------");
                        for(Cliente c : clientes){
                            
                            out.println("##### "+i+" #####\nCliente: " +c.getNome());
                            out.println("Email: " +c.getEmail());
                            out.println("Total Gasto: " +c.getTotalGasto());
                            i++;
                            
                        }
                            break;
                case 2: clearScreen();
                        break;
                    }
             }while(menuConsultar.getOpcao()!=0);
    }
                        
                        
    
    /** Se tiver sido iniciada sessao como Cliente */
    
    public static void menuRegistadoCliente(String email){
        clearScreen();
        Set<Viagem> sViagens = null;
        Cliente c= null;
        int n;
        Scanner ler = new Scanner(System.in);
        do{
            menuRegistadosCliente.executa();
            switch (menuRegistadosCliente.getOpcao()){
                case 1: menuFazerViagem(email);
                        break;
                
                case 2: 
                        clearScreen();
                        out.println("##### Histórico de Viagens #####");
                        int diaI,mesI,anoI,diaF,mesF,anoF;
                        GregorianCalendar dataI,dataF;
                        out.println("Inserir Data Inicial");
                        System.out.println("Insira o dia (1-31):");
                        diaI = ler.nextInt();
                        while(diaI<1 || diaI>31){
                        out.println("Dia inválido");
                            diaI = ler.nextInt();
                        }
                        out.println("Insira o mes (1-12):");
                        mesI = ler.nextInt();
                        ler.nextLine();
                        while(mesI<1 || mesI>12){
                            System.out.println("Mês inválido");
                            mesI = ler.nextInt();
                        }
                        out.println("Insira o ano (>=2000):");
                        anoI = ler.nextInt();
                        while(anoI<2000){
                            out.println("Ano Inválido");
                            anoI = ler.nextInt();
                        }
                        dataI= new GregorianCalendar(anoI,mesI,diaI);
                        out.println("Inserir Data Final");
                        System.out.println("Insira o dia (1-31):");
                        diaF = ler.nextInt();
                        while(diaF<1 || diaF>31){
                        out.println("Dia inválido");
                            diaF = ler.nextInt();
                        }
                        out.println("Insira o mes (1-12):");
                        mesF = ler.nextInt();
                        ler.nextLine();
                        while(mesF<1 || mesF>12){
                            System.out.println("Mês inválido");
                            mesF = ler.nextInt();
                        }
                        out.println("Insira o ano (>=2000):");
                        anoF = ler.nextInt();
                        while(anoF<2000){
                            out.println("Ano Inválido");
                            anoF = ler.nextInt();
                        }
                        dataF= new GregorianCalendar(anoF,mesF,diaF);
                        c=(Cliente) u.getUtilizador(email);
                        sViagens=c.getViagensEfetuadas(dataI,dataF);
                        clearScreen();
                        out.println(sViagens.toString());
                        break;
                    }    
            }while(menuRegistadosCliente.getOpcao()!=0);
            u.fechaSessao();
            clearScreen();
        }
    
    /** Se o cliente quiser fazer uma viagem */
    
    public static void menuFazerViagem(String email){
        clearScreen();
        String matricula;
        double xOrigem, yOrigem, xDestino, yDestino; 
        int classificacao;
        Localizacao lCliente,lDestino;
        Cliente cliente = null;
        int n;
        Scanner ler = new Scanner(System.in);
        out.println("#### Indique as suas coordenadas ####");
        out.print("x : ");
        xOrigem = ler.nextDouble();
        out.print("y : ");
        yOrigem= ler.nextDouble();
        out.println("#### Indique as coordenadas do destino ####");
        out.print("x : ");
        xDestino = ler.nextDouble();
        out.print("y : ");
        yDestino= ler.nextDouble();
        lDestino = new Localizacao(xDestino,yDestino);
        lCliente = new Localizacao(xOrigem,yOrigem);
        cliente=(Cliente) u.getUtilizador(email);
        cliente.setLocalizacao(lCliente);
        menuViagem.executa();
        switch (menuViagem.getOpcao()){
             case 1: clearScreen();
                     out.println("Indique a matricula do veiculo : ");
                     matricula=ler.next();
                     try{
                         Viagem novaViagem=u.fazViagem(email,matricula,lDestino);
                         clearScreen();
                         int nota;
                         out.println("Custo estimado: "+novaViagem.custoViagem());
                         //out.println("Custo real: "+novaViagem.custoRealViagem());
                         out.print("\nClassifique o motorista (0 a 100) de acordo com a viagem efetuada: ");
                         nota=ler.nextInt();
                         
                        }
                     catch(VeiculoInexistenteException e){
                         out.println();
                         out.println(e.getMessage());
                     }
                     catch(UtilizadorIndisponivelException e){
                         out.println();
                         out.println(e.getMessage());
                         menuMotoristaIndisponivel(cliente,matricula,lDestino);
                     }
                     break;
                     
                       
              
              case 2: clearScreen();
                      matricula=u.maisPerto(cliente);
                      out.println(matricula);
                      try{
                         Viagem novaViagem=u.fazViagem(email,matricula,lDestino);
                         clearScreen();
                         //int nota;
                         //out.println("Custo estimado: "+novaViagem.custoViagem());
                         //out.println("Custo real: "+novaViagem.custoRealViagem());
                         //out.print("\nClassifique o motorista (0 a 100) de acordo com a viagem efetuada: ");
                         //nota=ler.nextInt();
                        }
                        catch(VeiculoInexistenteException e){
                            out.println();
                            out.println(e.getMessage());
                        }
                        
                      catch(UtilizadorIndisponivelException e){
                        out.println();
                        out.println(e.getMessage());
                        menuMotoristaIndisponivel(cliente,matricula,lDestino);
                    }
                        
                      break;
                    }
                    
                    
                    
        //u.fechaSessao();
        
        //clearScreen();
    }
    
   /** Se o motorista que conduz a viatura pretendida está indisponivel*/ 
    
   public static void menuMotoristaIndisponivel(Cliente c,String matricula,Localizacao lDestino){
       menuMotoristaIndisp.executa();
        switch (menuMotoristaIndisp.getOpcao()){
             case 1: clearScreen();
                     //menuFazerViagem();
                     break;
                     
                       
              
              case 2: clearScreen();
                        if(u.getVeiculo(matricula) instanceof Carrinha){
                            Carrinha v=(Carrinha) u.getVeiculo(matricula);
                            v.adiciona(c,lDestino);
                        }
                         if(u.getVeiculo(matricula) instanceof Carro){
                            Carro v=(Carro) u.getVeiculo(matricula);
                            v.adiciona(c,lDestino);
                        }
                        if(u.getVeiculo(matricula) instanceof Moto){
                            Moto v=(Moto) u.getVeiculo(matricula);
                            v.adiciona(c,lDestino);
                        }
                        out.println("Cliente  " +c.getEmail()+ " adicionado à fila de espera do veiculo " +matricula);
                        break;
                    }
        
    }
       
                        
    
    /** Se tiver sido iniciada sessao como Motorista */
    
    public static void menuRegistadoMotorista(String email){
        clearScreen();
        String matricula;
        Set<Viagem> sViagens = null;
        Motorista m=null;
        int n;
        Scanner ler = new Scanner(System.in);
         do{
            menuRegistadosMotorista.executa();
            switch (menuRegistadosMotorista.getOpcao()){
                case 1: u.entraServico(email);
                        clearScreen();
                        break;
                
                case 2: u.terminaServico(email);
                        clearScreen();
                        break;
                
                case 3: clearScreen();
                        Veiculo veiculo=null;    
                        Cliente c = null;   
                        for(Veiculo v : u.getTodosVeiculos()){
                            if(v.getEmailMotorista().equals(email)){
                                veiculo=v;
                            }
                        }
                        if(veiculo instanceof Carrinha){
                            Carrinha v=(Carrinha) veiculo;
                            Localizacao destino=v.getDestino1();
                            c= v.remove();
                            try{
                                u.fazViagem(c.getEmail(),v.getMatricula(),destino);
                            }
                            catch(VeiculoInexistenteException e){
                                out.println();
                                out.println(e.getMessage());
                            }
                        
                            catch(UtilizadorIndisponivelException e){
                                out.println();
                                out.println(e.getMessage());
                               
                            }
                            
                        }
                         if(veiculo instanceof Carro){
                            Carro v=(Carro) veiculo;
                            Localizacao destino=v.getDestino1();
                            c= v.remove();
                            try{
                                u.fazViagem(c.getEmail(),v.getMatricula(),destino);
                            }
                            catch(VeiculoInexistenteException e){
                                out.println();
                                out.println(e.getMessage());
                            }
                        
                            catch(UtilizadorIndisponivelException e){
                                out.println();
                                out.println(e.getMessage());
                               
                            }
                        }
                        if(veiculo instanceof Moto){
                            Moto  v=(Moto) veiculo;
                            Localizacao destino=v.getDestino1();
                            c= v.remove();
                            try{
                                u.fazViagem(c.getEmail(),v.getMatricula(),destino);
                            }
                            catch(VeiculoInexistenteException e){
                                out.println();
                                out.println(e.getMessage());
                            }
                        
                            catch(UtilizadorIndisponivelException e){
                                out.println();
                                out.println(e.getMessage());
                               
                            }
                        }
                            
                           
                        
                        break;
                
                case 4: clearScreen();
                        out.println("##### Histórico de Viagens #####");
                        m=(Motorista) u.getUtilizador(email);
                        int diaI,mesI,anoI,diaF,mesF,anoF;
                        GregorianCalendar dataI,dataF;
                        out.println("Inserir Data Inicial");
                        System.out.println("Insira o dia (1-31):");
                        diaI = ler.nextInt();
                        while(diaI<1 || diaI>31){
                        out.println("Dia inválido");
                            diaI = ler.nextInt();
                        }
                        out.println("Insira o mes (1-12):");
                        mesI = ler.nextInt();
                        ler.nextLine();
                        while(mesI<1 || mesI>12){
                            System.out.println("Mês inválido");
                            mesI = ler.nextInt();
                        }
                        out.println("Insira o ano (>=2000):");
                        anoI = ler.nextInt();
                        while(anoI<2000){
                            out.println("Ano Inválido");
                            anoI = ler.nextInt();
                        }
                        dataI= new GregorianCalendar(anoI,mesI,diaI);
                        out.println("Inserir Data Final");
                        System.out.println("Insira o dia (1-31):");
                        diaF = ler.nextInt();
                        while(diaF<1 || diaF>31){
                        out.println("Dia inválido");
                            diaF = ler.nextInt();
                        }
                        out.println("Insira o mes (1-12):");
                        mesF = ler.nextInt();
                        ler.nextLine();
                        while(mesF<1 || mesF>12){
                            System.out.println("Mês inválido");
                            mesF = ler.nextInt();
                        }
                        out.println("Insira o ano (>=2000):");
                        anoF = ler.nextInt();
                        while(anoF<2000){
                            out.println("Ano Inválido");
                            anoF = ler.nextInt();
                        }
                        dataF= new GregorianCalendar(anoF,mesF,diaF);
                        sViagens=m.getViagensEfetuadas(dataI,dataF);
                        out.println(sViagens.toString());
                        break;
                    }    
            }while(menuRegistadosMotorista.getOpcao()!=0);
            u.fechaSessao();
            clearScreen();
        
      }
      
      
    /** Funcao chamada a quando o registo de um novo Veiculo*/  
    
    public static void registaVeiculo(String email){
        clearScreen();
        String matricula;
        double x,y,velocidade, preco;
        Veiculo novoVeiculo;
        Localizacao l;
        int opcao;
        Scanner ler = new Scanner(System.in);
        menuRegiVeiculo.executa();
        if(menuRegistar.getOpcao() != 0){
            clearScreen();
            
            out.println("Inserir matricula:");
            matricula=ler.nextLine();
            
            out.println("Inserir velocidade Média:");
            velocidade=ler.nextDouble();
            
            out.println("Inserir preco po km:");
            preco=ler.nextDouble();
            
            out.println("Inserir Localizacao atual do veiculo:");
            out.print("x : ");
            x=ler.nextDouble();
            out.print("y : ");
            y=ler.nextDouble();
            l=new Localizacao(x,y);
            switch(menuRegistar.getOpcao()){
                case 1 :  novoVeiculo = new Moto(matricula,velocidade,preco,l,email);
                         clearScreen();
                         break;
                case 2 : novoVeiculo = new Carro(matricula,velocidade,preco,l,email);
                         clearScreen();
                         break;
                case 3:  novoVeiculo = new Carrinha(matricula,velocidade,preco,l,email);
                         clearScreen();
                         break;
                default: novoVeiculo = new Veiculo(matricula, velocidade,preco,l,email);
                         clearScreen();
                         break;
            }
            try{
                u.registaVeiculo(novoVeiculo);
               
                out.println("Veiculo criado com sucesso");
                
            } 
            catch(VeiculoExistenteException e){
                out.println("");
                out.println(e.getMessage());
                out.println("");
                ler.nextLine();
            }
        
        
            
        }else{
            clearScreen();
            out.println("Registo Cancelado");
            
        }
        ler.close();
    }
    
    
    
    
    public static void initUMeRApp(){
          try{
              u=u.leObj("UMeR.txt");
            }
            catch (IOException e) {
            u = new UMer();
            System.out.println("Não consegui ler os dados!\nErro de leitura.");
        } 
        catch (ClassNotFoundException e) {
            u = new UMer();
            System.out.println("Não consegui ler os dados!\nFicheiro com formato desconhecido.");
        }
        catch (ClassCastException e) {
            u = new UMer();
            System.out.println("Não consegui ler os dados!\nErro de formato.");        
        }
        }
            
             
    
    

}
