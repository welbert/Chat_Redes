package Janela;


import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;

import Utilidade.Cliente;
import Utilidade.Message;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class Chat extends JFrame {

	private JPanel contentPane;
	private JTextField text_msg;
	private ControleJanela controle = null;
	private JTextArea Chat = null;
	private JList list_user = null;
	private Object[] Lista = null;

	public void EnviarMensagemparaTela(String msg){
		Chat.setText(Chat.getText()+"\n"+msg);
	}
	
	public void EnviarMensagemaoServidor(String msg){
		controle.enviarMsgAoServidor(new Message('b', controle.getNomeCliente()+" : "+msg));
	}
	
	public Object[] getLista(){
		return Lista;
	}

	public void atualizarList(){
		list_user.setVisible(false);
		list_user.setVisible(true);
	}
	
	public void addLista(Object[] lista){
		list_user.setListData(lista);
		this.Lista = lista;
		atualizarList();
	}
	
	
	public Chat(final ControleJanela controle) {
		this.controle = controle;
		setTitle("Bem vindo - "+controle.getNomeCliente());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Chat = new JTextArea();
		Chat.setBounds(10, 11, 450, 310);
		Chat.setEditable(false);
		Chat.setLineWrap(true);
		
		JScrollPane scroll = new JScrollPane();
		scroll.getViewport().setView(Chat);
		scroll.setBounds(10, 11, 450, 310);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		contentPane.add(scroll);
		
		list_user = new JList();
		list_user.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if(evt.getClickCount()>2){
					String usuario = (String) list_user.getSelectedValue();
					String text = controle.digitarJanela("Escreva uma mensagem privada para "+ usuario);
					Message obj = new Message('p',"(PM)"+controle.getNomeCliente() + " : "+text);
					obj.sender = usuario;
					controle.enviarMsgAoServidor(obj);
					EnviarMensagemparaTela("(PM)"+controle.getNomeCliente() + " : "+text);
				}
			}
		});
		list_user.setBounds(470, 11, 154, 310);
		contentPane.add(list_user);
		
		text_msg = new JTextField();
		text_msg.setBounds(10, 340, 450, 28);
		contentPane.add(text_msg);
		text_msg.setColumns(10);
		text_msg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!text_msg.getText().trim().equals(""))
				EnviarMensagemaoServidor(text_msg.getText());
				text_msg.setText("");
			}
		});
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!text_msg.getText().trim().equals(""))
				EnviarMensagemaoServidor(text_msg.getText());
				text_msg.setText("");
			}
		});
		btnEnviar.setBounds(493, 343, 89, 23);
		contentPane.add(btnEnviar);
		
		JButton btnArquivo = new JButton("Arquivo");
		btnArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controle.setTransferencia(true);
			}
		});
		btnArquivo.setBounds(535, 377, 89, 23);
		contentPane.add(btnArquivo);
	}
}
