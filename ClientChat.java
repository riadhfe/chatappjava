import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.TextArea;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class ClientChat {

	private JFrame frmClient;
	private JTextField textField;
	private static JTextArea textArea;
	private static Socket con;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientChat window = new ClientChat();
					window.frmClient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		con = new Socket("127.0.0.1",5029);
		while (true) {
			try {
				DataInputStream input = new DataInputStream(con.getInputStream());
				String string = input.readUTF();
				textArea.setText(textArea.getText() + "\n" + "Message serveur: " + string);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				textArea.setText(textArea.getText()+ "\n"+"probléme réseau");
				try {
					Thread.sleep(2000);
					System.exit(0);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}
	}

	/**
	 * Create the application.
	 */
	public ClientChat() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClient = new JFrame();
		frmClient.setForeground(Color.ORANGE);
		frmClient.setTitle("Coté client");
		frmClient.setBounds(100, 100, 450, 300);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClient.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(27, 11, 247, 41);
		textField.setBackground(Color.MAGENTA);
		frmClient.getContentPane().add(textField);
		textField.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 63, 399, 188);
		frmClient.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setBackground(Color.BLUE);
		textArea.setForeground(Color.WHITE);
		
		JButton btnNewButton = new JButton("Envoyer");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((textField.getText()).equals("")) {
					JOptionPane.showMessageDialog(null, "ecrit un message");
				}
				else {
					textArea.setText(textArea.getText() + "\n" + "Client : " + textField.getText());
					
					try {
						DataOutputStream output = new DataOutputStream(con.getOutputStream());
						output.writeUTF(textField.getText());

					} catch (IOException e1) {
						textArea.setText(textArea.getText() + "\n" + "probléme réseau");
						try {
							Thread.sleep(2000);
							System.exit(0);
						} catch (InterruptedException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}

					}
					
				}
				textField.setText("");
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton.setBounds(286, 11, 125, 41);
		frmClient.getContentPane().add(btnNewButton);
	}

}
