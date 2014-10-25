package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import Utilidade.Arquivo;
import Utilidade.Client;
import Utilidade.Message;

public class connectionServer {
	private static final int porta = 7272;
	private HashMap<String, Client> listaUser = new HashMap<String,Client>();
	private HashMap<String, String> contas = new HashMap<String,String>();
	private Arquivo bancodecontas = null;
	private Arquivo log = null;
	

	public Object[] listaOnline(){
		ArrayList<String> auxiliar= new ArrayList<String>();
		for(Client aux : listaUser.values()){
			auxiliar.add(aux.getName());
		}
	
		return auxiliar.toArray();
	}
	
	
	
	public void addlog(String msg){
		try {
			Date data = new Date(System.currentTimeMillis()); 
			log.salvar("("+data.toString()+")"+msg);
		} catch (IOException e) {			
			e.printStackTrace();
			System.out.println("Erro ao salvar no log - Erro 103");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Erro ao salvar a mensagem no log - Erro 106");
		}
	}
	
	public boolean verificarSenha(String nome,String senha){
		if(contas.get(nome)!=null){
			if(contas.get(nome).equals(senha)){
				return true;
			}
		}
		return false;
	}
	
	public boolean nomedisponivel(String nome){
		if(contas.containsKey(nome)){
			return false;
		}else{
			return true;
		}
	}
	
	public void salvarCadastro(String nome,String senha){
		contas.put(nome, senha);
		try{
		bancodecontas.salvar(nome);
		bancodecontas.salvar(senha);
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Erro ao salvar a conta no arquivo - Erro 104");
		}
	}
	
	public void carregarContas(){
		try{
		while(bancodecontas.NEOF()){
			String nome,senha;
			nome = bancodecontas.carregar();
			senha = bancodecontas.carregar();
			contas.put(nome, senha);
		}
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Erro ao carregar o arquivo de contas - Erro 105");
		}
	}
	
	public void addcliente(Client cliente){
		listaUser.put(cliente.getName(),cliente);
	}
	
	public void removecliente(String name){
		listaUser.remove(name);
	}
	
	public Client getCliente(String nome){
		return listaUser.get(nome);
	}
	
	
	
	public void distribuimensagem(Message obj){
		Set<String> keys = listaUser.keySet();
		for(String key : keys){
			if(key!=null){
				listaUser.get(key).enviarMessageaoCliente(obj);//Observar se o cliente não eh nulo(Possivel nullexception
			}
		}
	}
	
	public void enviarpm(Message obj){
		obj.type = 'b';
		listaUser.get(obj.sender).enviarMessageaoCliente(obj);
	}
	
	public void enviarMenssagemIndividual(Message obj, String nomedequemenvia){
		Message novoobj = new Message(obj.type,obj.content,obj.arg1,obj.arg2);
		novoobj.sender = nomedequemenvia;
		listaUser.get(obj.sender).enviarMessageaoCliente(novoobj);
	}
	
	public void enviarMenssagemIndividual(String nomeparaquemenviar, Message obj){
		
		try{
		listaUser.get(nomeparaquemenviar).enviarMessageaoCliente(obj);
		}catch(Exception e){
			
		}
	}
	
	public void executa(){
		try{
		bancodecontas = new Arquivo("Contas");
		carregarContas();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Erro ao carregar o banco de dados, reinicie o programa - Erro 101");
			System.exit(-1);
		}
		try{
		log = new Arquivo("log");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Erro ao gerar o arquivo de logs - Erro 102");
		}
		
		
		try{
			
			ServerSocket welcomeSocket = new ServerSocket(porta);
			
			while(true){
			TrataCliente tc = new TrataCliente(welcomeSocket.accept(), this);
			new Thread(tc).start();
			}
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Porta ja usada - Erro 100");
			}
	}
	
	public static void main(String[] args) {
		connectionServer servidor = new connectionServer();
		servidor.executa();
	}

}
