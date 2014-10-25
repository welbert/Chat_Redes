/*package connection;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import app.Message;

public class connectionServerExemplo {

	public static void main(String[] args) {
		try{
			ServerSocket welcomeSocket = new ServerSocket(7272);

			while(true){
				try{
				Socket client = welcomeSocket.accept();

				ObjectInputStream in = new ObjectInputStream(client.getInputStream());
				Message obj = (Message) in.readObject();
				//int[] tes = (int[]) obj.content;
				System.out.println(obj.content);
				//System.out.println(tes[1]+" ; "+tes[0]);
				obj = (Message) in.readObject();
				//int[] tes = (int[]) obj.content;
				System.out.println(obj.content);
				while(true){
					obj = (Message) in.readObject();
					System.out.println(obj.content);
				}
				}catch(Exception e){
					System.out.println("Erro no while");
					e.printStackTrace();
				}
			}


		}catch(Exception e){
			System.out.println("Porta usada");
		}
	}

}
*/