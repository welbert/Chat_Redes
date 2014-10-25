package Utilidade;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import Janela.Transferencia;

public class Download {
	private FileOutputStream file ;
	private Transferencia janelaTransf;
	private String nomeArquivo;
	private String id;
	
	public Download(String id,String file,Transferencia janelaTransf){
		this.id = id;
		this.janelaTransf = janelaTransf;
		nomeArquivo = file;
		try {
			this.file = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			janelaTransf.Aviso("Erro 500 - Erro ao criar o download");
			e.printStackTrace();
			janelaTransf.encerrarDownload(id);
		}
	}

	public String getNome(){
		return nomeArquivo;
	}
	
	public void salvar(Message message){
		byte[] buf = (byte[]) message.content;
		if(message.arg2 == null){
			try {
				file.write(buf);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				file.write(buf, 0, (int)message.arg2);
				janelaTransf.encerrarDownload(id);
			} catch (IOException e) {
				e.printStackTrace();
			}

			
		}
	}

}
