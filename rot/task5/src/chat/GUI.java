package chat;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtLogin;
	private JPanel pnlLogin;
	private ChatPanel pnlChat;
	private String m_login;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		pnlLogin = new JPanel();
		contentPane.add(pnlLogin, BorderLayout.CENTER);
		
		JLabel lblLogin = new JLabel("Введите логин: ");
		pnlLogin.add(lblLogin);
		
		txtLogin = new JTextField();
		pnlLogin.add(txtLogin);
		txtLogin.setColumns(10);
		
		pnlChat = new ChatPanel();
		
		JButton btnLogin = new JButton("войти");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				m_login = txtLogin.getText();
				//pnlLogin.setVisible(false);
				
				
				contentPane.remove(pnlLogin);
				contentPane.add(pnlChat, BorderLayout.CENTER);
				
				repaint();
				validate();
				
				pnlChat.btn_send.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JMSApplication.sendMessage(m_login + ": " + pnlChat.txt_msg.getText());
						pnlChat.txt_msg.setText("");
					}
				});
			}
		});
		pnlLogin.add(btnLogin);
	}
	
	public void addMessage(String text)
	{
		pnlChat.txtPn_chat.setText(pnlChat.txtPn_chat.getText()+"\n"+text);
	}

}
