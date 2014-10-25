package Utilidade;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {

	private Socket client = null;
	private String nome = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;

	public Cliente(Socket client){
		this.client = client;
		try{
		out = new ObjectOutputStream(client.getOutputStream());
		in = new ObjectInputStream(client.getInputStream());
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Erro ao criar o stream - 001");
		}
	}
	
	public String getNome(){
		return nome;
	}
	
	public void setNome(String nome){
		this.nome = nome;
	}
	
	public Message receberMessagedoServidor(){
		Message obj = null;
		try{
			obj = (Message) in.readObject();
			return obj;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean enviarMessageaoServidor(Message obj){
		try{
			out.writeObject(obj);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
}
