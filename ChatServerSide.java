import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ChatServerSide {

	private JFrame frame;
	private JTextField textField;
	private JButton btnNewButton;
	 static ServerSocket server;
	 static Socket con;
	 static JTextArea textArea;
	 private JScrollPane scrollPane;
	 private JLabel lblNewLabel;
	 private static JLabel lblNewLabel_1;
	 private static JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatServerSide window = new ChatServerSide();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//définit le socket serveur et ecouter 
		serverConnection();
	}
	//la méthode serveur connection pour ouvri le port et etablic la connection 
	//sur le port 5029
	//et attendre les message du client pour echanger les messages

	private static void serverConnection() throws IOException {
		server = new ServerSocket(5029);
		con = server.accept();
		lblNewLabel_2.setText("client trouvé");
		lblNewLabel_2.setForeground(Color.GREEN);
		while (true) {
			try {
				DataInputStream input = new DataInputStream(con.getInputStream());
				String string = input.readUTF();
				textArea.setText(textArea.getText() + "\n" + "Message Client: " + string);
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
	 * @throws IOException 
	 */
	public ChatServerSide() throws IOException {
		initialize();
	
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		//la frame principale du programme
		frame = new JFrame();
		frame.setTitle("Coté serveur");
		frame.setBackground(Color.YELLOW);
		frame.setBounds(100, 100, 461, 422);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//le champ texte pour ecrir le message
		textField = new JTextField();
		textField.setBounds(10, 11, 286, 51);
		textField.setBackground(Color.RED);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		//bouton envoyer
		btnNewButton = new JButton("Envoyer");
		//ajouter une action pour le bouton pour gérer les evenements
		
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//si le champ message est vide
				if ((textField.getText()).equals("")) {
					JOptionPane.showMessageDialog(null, "ecrit un message");
				}
				else {
					//remplir le chaps texte par le message provenant de client
					textArea.setText(textArea.getText() + "\n" + "Server : " + textField.getText());
					
					try {
						DataOutputStream output = new DataOutputStream(con.getOutputStream());
						output.writeUTF(textField.getText());

					} catch (IOException e1) {
						textArea.setText(textArea.getText() + "\n"+"probléme réseau");
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
		btnNewButton.setForeground(Color.BLUE);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton.setBounds(311, 11, 125, 51);
		frame.getContentPane().add(btnNewButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 71, 276, 151);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		lblNewLabel = new JLabel("statue de serveur");
		lblNewLabel.setBounds(24, 289, 117, 43);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel_1 = new JLabel();
		lblNewLabel_1.setBounds(206, 289, 194, 43);
		frame.getContentPane().add(lblNewLabel_1);
		lblNewLabel_2 = new JLabel();
		lblNewLabel_2.setBounds(206, 320, 194, 43);
		frame.getContentPane().add(lblNewLabel_2);

		if (server.isClosed()) {
			//état de serveur
			lblNewLabel_1 .setText("serveur fermé");
			lblNewLabel_1.setForeground(Color.BLACK);
		}
		else {
			//chager le texte en vert pour la connection établie
			lblNewLabel_1.setText("connection etablie");
			lblNewLabel_1.setForeground(Color.GREEN);
		}
		
		
		
	}
}
