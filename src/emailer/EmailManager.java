/**
* This class is responsible for offering a GUI to the user allowing easy referencing
* of client profiles with email addresses and activation of system.
* 
* @author  Thomas Power
* @version 1.0
* @since   2018-03-19 
*/

package emailer;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmailManager {
	public File file = new File(".\\code\\Clients");
	public String[] accountStrings = file.list();
	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmailManager window = new EmailManager();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmailManager() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblAccount = new JLabel("Account");
		
		JComboBox comboBox = new JComboBox(accountStrings);
		
		JLabel lblEmail = new JLabel("Email");
		
		textField = new JTextField();
		textField.setColumns(10);
		JLabel lblBlank = new JLabel("");
		
		JButton btnSetEmail = new JButton("Set Email");
		btnSetEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ClientName = comboBox.getSelectedItem().toString();
				String email = textField.getText();
				localData.loadData();
				localData.addClient(ClientName, email);
				localData.saveData();
				lblBlank.setText("Email set");
			}
		});
		
		JButton btnSendAll = new JButton("Send All");
		btnSendAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emailer.emailAll();
			}
		});
		
		JButton btnViewEmail = new JButton("View Email");
		btnViewEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ClientName = comboBox.getSelectedItem().toString();
				localData.loadData();
				String email = localData.getClient(ClientName).email;
				localData.saveData();
				if(email != null) {
					lblBlank.setText("Current Email is:" + email);
				}
				else {
					lblBlank.setText("No current email");
				}
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblAccount)
							.addGap(116)
							.addComponent(lblEmail))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblBlank))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnSendAll, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSetEmail)
								.addComponent(btnViewEmail))))
					.addContainerGap(14, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAccount)
						.addComponent(lblEmail))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSetEmail))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBlank)
						.addComponent(btnViewEmail))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btnSendAll)
					.addContainerGap())
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
