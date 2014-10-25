package Janela;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import Utilidade.Message;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel Pane_principal;
	private JTextField text_login;
	private JPasswordField text_password;
	private JLabel lblServidor = null;
	private ControleJanela controle = null;

	public void refresh(){
		Pane_principal.setVisible(false);
		Pane_principal.setVisible(true);
	}
	
	public void fecharJanela(){
		this.dispose();
	}
	
	public void status(boolean e){
		if(e){
			lblServidor = new JLabel("online");
			lblServidor.setForeground(new Color(0,255,0));
			
		}else{
			lblServidor = new JLabel("offline");
			lblServidor.setForeground(new Color(255,0,0));
			
		}
		lblServidor.setBounds(43, 0, 46, 14);
		Pane_principal.add(lblServidor);
		refresh();
	}
	
	/**
	 * Create the frame.
	 */
	public Login(final ControleJanela controle) {
		this.controle = controle;
		//controle.setJanelaLogin(this);
		
		setTitle("Chat - Login");
		setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		Pane_principal = new JPanel();
		Pane_principal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(Pane_principal);
		Pane_principal.setLayout(null);
		
		
		JLabel lbl_Logo = new JLabel("");
		lbl_Logo.setIcon(new ImageIcon("C:\\Users\\login dell\\workspace\\Chat(client)\\Logo.png"));
		lbl_Logo.setBounds(136, 11, 148, 133);
		Pane_principal.add(lbl_Logo);
		
		JLabel lblLogin = new JLabel("Login: ");
		lblLogin.setBounds(136, 174, 46, 14);
		Pane_principal.add(lblLogin);
		
		JLabel lblSenha = new JLabel("Senha: ");
		lblSenha.setBounds(136, 203, 46, 14);
		Pane_principal.add(lblSenha);
		
		text_login = new JTextField();
		text_login.setBounds(176, 171, 108, 20);
		Pane_principal.add(text_login);
		text_login.setColumns(10);
		
		text_password = new JPasswordField();
		text_password.setBounds(176, 200, 108, 20);
		text_password.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Message login = new Message('l', text_login.getText(), text_password.getText());
				controle.enviarMsgAoServidor(login);
			}
		});
		Pane_principal.add(text_password);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Message login = new Message('l', text_login.getText(), text_password.getText());
				controle.enviarMsgAoServidor(login);
			}
		});
		btnEntrar.setBounds(176, 231, 89, 23);
		Pane_principal.add(btnEntrar);
		
		JLabel lblNoPossuiCadastro = new JLabel("N\u00E3o possui cadastro?");
		lblNoPossuiCadastro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount()==1)
				controle.setJanelaCadastro();
				controle.fecharLogin();
			}
		});
		lblNoPossuiCadastro.setForeground(new Color(0, 0, 255));
		lblNoPossuiCadastro.setBounds(136, 155, 148, 14);
		Pane_principal.add(lblNoPossuiCadastro);
		
		JLabel lblStatus = new JLabel("Status: ");
		lblStatus.setBounds(0, 0, 46, 14);
		Pane_principal.add(lblStatus);
		
		
	}
}
