package connection;

import java.net.Socket;

import Utilidade.Client;
import Utilidade.Message;

public class TrataCliente implements Runnable{

	Client cliente = null;
	connectionServer servidor = null;
	private static int id = 0;

	public TrataCliente(Socket user,connectionServer servidor){
		cliente = new Client(user, "");
		this.servidor = servidor;
	}

	public void run(){
		boolean dadoscorreto = false;
		while(!dadoscorreto){
			Message obj = null;

			obj = cliente.receberMessagedoCliente();

			if(obj!=null){
				switch(obj.type){
				case 'l':
					String nome = (String) obj.content;
					if(servidor.verificarSenha(nome, (String)obj.arg1)){
						cliente.enviarMessageaoCliente(new Message('l', nome, true));
						cliente.setNome(nome);
						servidor.addcliente(cliente);
						dadoscorreto = true;
						servidor.distribuimensagem(new Message('t', servidor.listaOnline()));
						servidor.distribuimensagem(new Message('b', "O Usuario "+cliente.getName()+" entrou no chat!"));
						servidor.addlog("O Usuario "+cliente.getName()+" entrou no chat!");
					}else{
						cliente.enviarMessageaoCliente(new Message('l', null, false));
					}
					break;
				case 'c':
					String name = (String) obj.content;
					if(servidor.nomedisponivel(name)){
						servidor.salvarCadastro(name, (String)obj.arg1);
						cliente.enviarMessageaoCliente(new Message('c',true));
					}else{
						cliente.enviarMessageaoCliente(new Message('c',false));
					}
					break;
				default:
					System.out.println("Erro - 200 ; Um arquivo chegou ao usuario não logado");
					break;
				}
			}else{
				break;
			}
		}



		while(true){
			Message obj = cliente.receberMessagedoCliente();
			if(obj!=null){
				switch(obj.type){
				case'b':
					servidor.distribuimensagem(obj);
					try{
						servidor.addlog((String)obj.content);
					}catch(Exception e){}
					break;
				case 'p':
					servidor.enviarpm(obj);
					try{
						servidor.addlog((String)obj.content);
					}catch(Exception e){}
					break;
				case 'z':
					if(!(boolean)obj.arg2){
						String aux = Integer.toString(id);
						Message msg = new Message('u',obj.arg1,aux,false);
						msg.sender = obj.sender;
						servidor.enviarMenssagemIndividual(cliente.getName(),msg);												
						obj.arg1 = aux;						
						servidor.enviarMenssagemIndividual(obj, cliente.getName());
						id++;
					}else{						
						servidor.enviarMenssagemIndividual(obj.sender,obj);
					}
					break;
				case 'd':
					servidor.enviarMenssagemIndividual(obj.sender,obj);
					break;
				}
			}else{
				System.out.println("Erro ao receber mensagem do cliente - Erro 201");
				servidor.removecliente(cliente.getName());
				servidor.distribuimensagem(new Message('t', servidor.listaOnline()));
				if((!cliente.getName().equals(""))&&(cliente.getName()!=null)){
					servidor.distribuimensagem(new Message('b', "O Usuario "+cliente.getName()+" saiu do chat!" ));
					servidor.addlog("O Usuario "+cliente.getName()+" saiu do chat!");
				}
				break;
			}
		}

	}

}
