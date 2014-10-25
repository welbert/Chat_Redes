package connection;

import java.net.Socket;
import java.util.Date;

import Janela.ControleJanela;
import Utilidade.Cliente;
import Utilidade.Message;

public class ConnectionCliente implements Runnable{

	private static final String ip = "127.0.0.1";
	private static final int porta = 7272;
	private ControleJanela janela = null;
	private Cliente user = null;

	public ConnectionCliente(ControleJanela janela){
		this.janela = janela;
	}

	public boolean enviarMensagem(Message obj){
		return user.enviarMessageaoServidor(obj);
	}

	public String getNome(){
		return user.getNome();
	}

	public Object trataMensagem(Message mensagem){
		System.out.println(mensagem.type);
		switch (mensagem.type) {
		case 'l'://Login
			if((boolean) mensagem.arg1){
				user.setNome((String)mensagem.content);
				janela.aviso("Bem vindo "+user.getNome());
				janela.LoginCorreto();

			}else{
				janela.aviso("Login/Senha Invalido");
			}
			break;

		case 'c'://cadastro
			if((boolean)mensagem.content){
				janela.aviso("Cadastro efetuado com sucesso");
				janela.cadastroCorreto();
			}else{
				janela.aviso("Nome já usado");
			}
			break;

		case 't'://lista
			janela.addListaOnline((Object[])mensagem.content);
			break;

		case 'b'://Chat
			Date data = new Date();
			janela.addTexto("("+data.getHours()+":"+data.getMinutes()+") "+(String)mensagem.content);
			break;
		case 'u':
			System.out.println(mensagem.sender);
			janela.addUpload((String)mensagem.arg1, (String) mensagem.content, mensagem.sender);
			break;
		case 'z'://Informacoes sobre o download
			
			if(!(boolean)mensagem.arg2){
				janela.addDownload((String)mensagem.arg1, (String)mensagem.content);
				Message obj = new Message('z',null,mensagem.arg1,true);
				obj.sender = mensagem.sender;
				janela.enviarMsgAoServidor(obj);
			}else{
				janela.iniciarUpload((String)mensagem.arg1);
			}
			break;
		case 'd':
			janela.salvarArquivo(mensagem);
			break;
		default:
			System.out.println("Pacote sem tipo"+mensagem.type);
			break;
		}
		return null;
	}

	public void run() {
		try{
			Socket client = new Socket(ip,porta);
			user = new Cliente(client);

			janela.Status(true);
			while(client.isConnected()){
				trataMensagem(user.receberMessagedoServidor());
			}
			janela.Status(false);
		}catch(Exception e){
			janela.Status(false);
			janela.aviso("Servidor fora do ar");
			//e.printStackTrace();

		}
	}

}
