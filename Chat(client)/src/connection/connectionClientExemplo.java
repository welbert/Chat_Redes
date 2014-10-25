/*package connection;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import app.Message;

public class connectionClientExemplo {


	public static void main(String[] args) {
		try{
			Socket client = new Socket("127.0.0.1",7272);
			
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			
			int[] tes = new int[2];
			tes[0] = 1;
			tes[1] = 2;
			String test1 = "teste";
			int test2 = 4;
			Message obj = new Message("Roberto");
			out.writeObject(obj);
			obj = new Message(test1);
			out.writeObject(obj);
			Scanner sc = new Scanner(System.in);
			while(true){
				obj = new Message(sc.nextLine());
				out.writeObject(obj);
				
			}
			//Thread.sleep(3000);
		}catch(Exception e){
			System.out.println("Erro ao conectar");
		}
	}

}*/
