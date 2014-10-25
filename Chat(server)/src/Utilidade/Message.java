package Utilidade;

import java.io.Serializable;

public class Message implements Serializable {
	public static final long serialVersionUID = 751637115122276187L;
	
	public char type = 0;
	public String sender = "";
	public Object arg1 = null;
	public Object arg2 = null;	
	public Object content = null;
	
	public Message(Object content){
		this.content = content;
	}
	public Message(char type, Object content){
		this.content = content;
		this.type = type;
	}
	public Message(char type, Object content, Object arg1){
		this.content = content;
		this.type = type;
		this.arg1 = arg1;
	}
	public Message(char type, Object content, Object arg1, Object arg2){
		this.content = content;
		this.type = type;
		this.arg1 = arg1;
		this.arg2 = arg2;
	}
	
	
	
}
