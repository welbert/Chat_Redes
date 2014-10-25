package Janela;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Utilidade.Download;
import Utilidade.Message;
import Utilidade.Upload;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;

public class Transferencia extends JFrame implements Runnable{

	private JPanel contentPane;
	private JFileChooser FileTrace;
	private ControleJanela controle;
	private HashMap<String, Download> listaDownload = new HashMap<String,Download>();
	private HashMap<String, Upload> listaUpload = new HashMap<String,Upload>();
	private JList list_download;
	private JList list_upload;
	//private static int id = 0;
	// Download content - arquivo , arg1- id , arg2 - fim do arquivo
	//Upload 
	public void run() {
		while(true){
			Message obj=null;
			//if((listaDownload.size()!=0)||(listaUpload.size()!=0)){
				//System.out.println("Download: "+listaDownload.size());
				//System.out.println("Upload: "+listaUpload.size());
			//}
			for(Upload u : listaUpload.values()){
				if(u.getPronto())
					 obj = u.enviarArquivo();
					if(obj!=null)
						controle.enviarMsgAoServidor(obj);
				
				try{
					
				Thread.sleep(500);//limitar o upload
				}catch(Exception e){
					
				}
			}
		}
	}

	public void salvarArquivo(Message obj){
		listaDownload.get(obj.arg1).salvar(obj);
	}
	
	public void iniciarUpload(String id){
		controle.addTexto("(System) O Upload "+listaUpload.get(id).getNome()+" foi iniciado!");
		listaUpload.get(id).setPronto(true);
	}
	
	public void encerrarUpload(String id){
		controle.addTexto("(System) O Upload "+listaUpload.get(id).getNome()+" terminou!");
		listaUpload.remove(id);
		atualizarList();
		
	}
	
	public Object[] getListaDownload(){
		ArrayList<Object> list = new ArrayList<Object>();
		for(Download d:listaDownload.values()){
			list.add(d.getNome());
		}
		return list.toArray();
	}
	public Object[] getListaUpload(){
		ArrayList<Object> list = new ArrayList<Object>();
		for(Upload u:listaUpload.values()){
			list.add(u.getNome());
		}
		return list.toArray();
	}
	
	public void atualizarList(){
		
		list_download.setListData(getListaDownload());
		list_upload.setListData(getListaUpload());
	}
	
	public void addDownload(String id,String nomeArquivo){
		listaDownload.put(id, new Download(id, nomeArquivo, this));
		atualizarList();
		controle.addTexto("(System) O Download "+listaDownload.get(id).getNome()+" foi iniciado!");
	}
	
	public void addUpload(String id,String caminhoArquivo, String nomeDestino){
		listaUpload.put(id, new Upload(id, caminhoArquivo, this,nomeDestino));
		atualizarList();
	}
	
	public void encerrarDownload(String id){
		controle.addTexto("(System) O Download "+listaDownload.get(id).getNome()+" terminou!");
		listaDownload.remove(id);
		atualizarList();
	}
	public void Aviso(String msg){
		controle.aviso(msg);
	}
	
	public Transferencia(final ControleJanela controle) {
		this.controle = controle;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDownload = new JLabel("Download");
		lblDownload.setBounds(33, 11, 99, 14);
		contentPane.add(lblDownload);
		
		JLabel lblUpload = new JLabel("Upload");
		lblUpload.setBounds(298, 11, 46, 14);
		contentPane.add(lblUpload);
		
		list_download = new JList();
		list_download.setBounds(10, 44, 134, 190);
		contentPane.add(list_download);
		
		list_upload = new JList();
		list_upload.setBounds(226, 36, 144, 203);
		contentPane.add(list_upload);
		
		JButton btnEnviarNovoArquivo = new JButton("Enviar Novo Arquivo");
		btnEnviarNovoArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String caminho = "";
				int retorno;
				FileTrace = new JFileChooser();
				FileTrace.setBounds(0, 0, 238, 174);
				contentPane.add(FileTrace);
				retorno = FileTrace.showOpenDialog(null);
				if(retorno == JFileChooser.APPROVE_OPTION){
				caminho= FileTrace.getSelectedFile().getAbsolutePath();
				
				Object[] lista = controle.getListaOnline();
				int i = JOptionPane.showOptionDialog(null,   
				        "Enviar para? ",   
				        "",  
				        0,  
				        JOptionPane.PLAIN_MESSAGE,  
				        null,  
				        lista,  
				        1);
				
				
				Message obj = new Message('z',FileTrace.getSelectedFile().getName(),caminho,false);
				obj.sender = (String) lista[i];
				controle.enviarMsgAoServidor(obj);
				//controle.addUpload(Integer.toString(id), caminho,(String) lista[i]);
				}
 
				
			}
		});
		btnEnviarNovoArquivo.setBounds(117, 4, 144, 29);
		contentPane.add(btnEnviarNovoArquivo);
		

		
	}
}
