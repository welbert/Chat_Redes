package Janela;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import Utilidade.Message;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Cadastro extends JFrame {

	private JPanel contentPane;
	private JTextField text_Cnome;
	private JPasswordField text_Cpassword;
	private ControleJanela controle = null;


	public void fecharJanela(){
		this.dispose();
	}
	
	public Cadastro(final ControleJanela controle) {
		this.controle = controle;
		setResizable(false);
		setTitle("Chat - Cadastro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(23, 55, 46, 14);
		contentPane.add(lblNome);
		
		JLabel lblSenha = new JLabel("Senha: ");
		lblSenha.setBounds(23, 101, 46, 14);
		contentPane.add(lblSenha);
		
		text_Cnome = new JTextField();
		text_Cnome.setBounds(79, 52, 87, 20);
		contentPane.add(text_Cnome);
		text_Cnome.setColumns(10);
		
		text_Cpassword = new JPasswordField();
		text_Cpassword.setBounds(79, 98, 87, 20);
		contentPane.add(text_Cpassword);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controle.enviarMsgAoServidor(new Message('c', text_Cnome.getText(), text_Cpassword.getText()));
			}
		});
		btnCadastrar.setBounds(60, 146, 106, 23);
		contentPane.add(btnCadastrar);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controle.cadastroCorreto();
			}
		});
		btnVoltar.setBounds(70, 180, 89, 23);
		contentPane.add(btnVoltar);
	}
}
