package Utilidade;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import Janela.Transferencia;

public class Upload {

	private FileInputStream stream;
	private Transferencia janelaTransf;
	private String id;
	private String sender;
	private boolean pronto= false;
	private String nomeArquivo;
	int len = 0;

	public boolean getPronto(){
		return pronto;
	}
	
	public String getNome(){
		return nomeArquivo;
	}

	public void setPronto(boolean e){
		pronto = e;
	}

	public Upload(String id,String caminho,Transferencia janelaTransf,String sender){
		this.id = id;
		this.sender = sender;
		this.janelaTransf = janelaTransf;
		try {
			File file = new File(caminho);
			stream = new FileInputStream(file);
			nomeArquivo = file.getName();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			janelaTransf.Aviso("Erro 401 - Erro ao importar o arquivo");
		}
	}

	public Message enviarArquivo(){

		byte[] buf = new byte[4096];
		try {
			len = stream.read(buf, 0, 4096);

		} catch (IOException e) {
			e.printStackTrace();
			janelaTransf.Aviso("Erro 400 - Erro ao realizar o upload");
		}
		if(len==-1){
			janelaTransf.encerrarUpload(id);			
			return null;
		}

		if(len==4096){
			Message obj = new Message('d', buf,id);
			obj.sender = sender;
			
			return obj;
		}
		if(len<4096){
			Message obj =new Message('d', buf,id, len);
			obj.sender = sender;
			
			return obj;
		}

		
		janelaTransf.encerrarUpload(id);
		return null;

	}
}
