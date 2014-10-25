package Utilidade;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Utilidade.Message;

public class Client {

	private Socket client = null;
	private String name = "";
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	
	public Client(Socket client, String name){
		this.client = client;
		this.name = name;
		try{
		out = new ObjectOutputStream(client.getOutputStream());
		in = new ObjectInputStream(client.getInputStream());
		}catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	
	public String getName(){
		return name;
	}
	
	public void setNome(String name){
		this.name = name;
	}
	
	public Message receberMessagedoCliente(){
		Message obj = null;
		try{
			if(client.isConnected()){
			obj = (Message) in.readObject();
			}else{
				System.out.println("TEste");
			}
			return obj;
			
		}catch(Exception e){
			//e.printStackTrace();
			return null;
		}
	}
	
	public boolean enviarMessageaoCliente(Message obj){
		try{
			out.writeObject(obj);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
}
