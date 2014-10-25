package Janela;

import javax.swing.JOptionPane;

import Utilidade.Message;

import connection.ConnectionCliente;

public class ControleJanela {//<<'Conecta' as Janelas com a classe que trata mensagem

	private Login janelaLogin = null;
	private Cadastro janelaCadastro = null; 
	private Chat janelaChat = null; // Chat
	ConnectionCliente client = null;//<< Tratamento de Mensagem
	private Transferencia janelaTransferencia = null; //Tratamento de transferencia

	public void setJanelaChat(){
		this.janelaChat = new Chat(this);
		this.janelaChat.setLocationRelativeTo(null);
		this.janelaChat.setVisible(true);
	}
	
	public String getNomeCliente(){
		return client.getNome();
	}
	
	public void setJanelaLogin() {
		this.janelaLogin = new Login(this);
		this.janelaLogin.setVisible(true);
	}

	public void setJanelaCadastro() {
		this.janelaCadastro = new Cadastro(this);
		this.janelaCadastro.setVisible(true);
	}
	
	public void fecharLogin(){
		janelaLogin.fecharJanela();
		janelaLogin = null;
	}
	
	public void fecharCadastro(){
		janelaCadastro.fecharJanela();
		janelaCadastro = null;
	}

	public void enviarMsgAoServidor(Message msg){
		client.enviarMensagem(msg);
	}
	
	
	public void aviso(String msg){
		JOptionPane.showMessageDialog(null, msg);
	}

	public boolean avisoConfirmacao(String msg){
		if(JOptionPane.showConfirmDialog(null, msg)==1){
			return true;
		}
		return false;
	}
	
	public void LoginCorreto(){
		fecharLogin();
		setJanelaChat();
		janelaTransferencia = new Transferencia(this);
		janelaTransferencia.setVisible(false);
		new Thread(janelaTransferencia).start();
	}
	
	public void setTransferencia(boolean t){
		janelaTransferencia.setVisible(t);
	}
	
	public void cadastroCorreto(){
		fecharCadastro();
		setJanelaLogin();
	}
	
	public void Status(boolean e){
		
		if(janelaLogin !=null){			
			janelaLogin.status(e);
		}
	}
	
	public void addTexto(String msg){
		janelaChat.EnviarMensagemparaTela(msg);
	}
	
	public void addListaOnline(Object[] nome){
		janelaChat.addLista(nome);
	}
	
	public String digitarJanela(String msg){
		return JOptionPane.showInputDialog(msg);
	}
	
	public void addDownload(String id,String nomeArquivo){
		janelaTransferencia.addDownload(id, nomeArquivo);
	}
	
	public void addUpload(String id,String caminhoArquivo, String nomeDestino){
		janelaTransferencia.addUpload(id, caminhoArquivo,nomeDestino);
	}
	
	public void iniciarUpload(String id){
		janelaTransferencia.iniciarUpload(id);
	}
	
	public void salvarArquivo(Message obj){
		janelaTransferencia.salvarArquivo(obj);
	}
	
	public Object[] getListaOnline(){
		return janelaChat.getLista();
	}
	
	
	public void executa(){
		janelaLogin = new Login(this);
		janelaLogin.setVisible(true);
		client = new ConnectionCliente(this);
		new Thread(client).start();
		
	}
	
	public static void main(String[] args) {
		ControleJanela janela = new ControleJanela();
		janela.executa();

	}
	
}
