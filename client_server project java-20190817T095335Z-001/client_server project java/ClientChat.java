import MyConection.Conection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import sun.nio.cs.HistoricallyNamedCharset;

public class ClientChat extends JFrame implements ActionListener {
	static Socket conn;
	JPanel panel;
	JTextField NewMsg;
	JTextArea ChatHistory;
	JButton Send;
        JTextField uname;
        JPasswordField upass;
        JLabel user;
        JLabel pass;
        JButton login;

	public ClientChat() throws UnknownHostException, IOException {
		panel = new JPanel();
		NewMsg = new JTextField();
		ChatHistory = new JTextArea();
		Send = new JButton("Send");
                uname = new JTextField();
                upass = new JPasswordField();
                user = new JLabel("Username");
                pass = new JLabel("Password");
                login = new JButton("Login");
		this.setSize(500, 550);
		this.setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel.setLayout(null);
		this.add(panel);
                user.setBounds(20,20,60,30);
                panel.add(user);
                uname.setBounds(85,20,100,30);
                panel.add(uname);
                pass.setBounds(200,20,60,30);
                panel.add(pass);
                upass.setBounds(270,20,100,30);
                panel.add(upass);
                login.setBounds(390,20,80,30);
                panel.add(login);
                login.addActionListener(this);
		ChatHistory.setBounds(20, 60, 450, 360);
		panel.add(ChatHistory);
		NewMsg.setBounds(20, 450, 340, 30);
		panel.add(NewMsg);
		Send.setBounds(375, 450, 95, 30);
		panel.add(Send);
		Send.addActionListener(this);
		conn = new Socket(InetAddress.getLocalHost(), 2000);
                ChatHistory.setEditable(false);
                NewMsg.setEditable(false);
                Send.setEnabled(false);
		/*
		 * for remote pc use ip address of server with function
		 * InetAddress.getByName("Provide ip here")
		 * ChatHistory.setText("Connected to Server");
		 */

		ChatHistory.setText("Connected to Server");
		this.setTitle("Client");
		while (true) {
			try {
				DataInputStream dis = new DataInputStream(conn.getInputStream());
				String string = dis.readUTF();
				ChatHistory.setText(ChatHistory.getText() + 'n' + "Server:"
						+ string);
			} catch (Exception e1) {
				ChatHistory.setText(ChatHistory.getText() + 'n'
						+ "Message sending fail:Network Error");
				try {
					Thread.sleep(3000);
					System.exit(0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ((e.getSource() == Send) && (NewMsg.getText() != "")) {

			ChatHistory.setText(ChatHistory.getText() + 'n' + "Me:"
					+ NewMsg.getText());
			try {
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());
				dos.writeUTF(NewMsg.getText());
			} catch (Exception e1) {
				ChatHistory.setText(ChatHistory.getText() + 'n'
						+ "Message sending fail:Network Error");
				try {
					Thread.sleep(3000);
					System.exit(0);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
			NewMsg.setText("");
		}
                if(e.getSource() ==login)
                {
                        if(uname.getText()=="")
                            JOptionPane.showMessageDialog(this,"Enter Username!!");
                        if(upass.getText()=="")
                            JOptionPane.showMessageDialog(this,"Enter Password!!");
                        if(uname.getText()!="" && upass.getText()!="")
                        {
                            try
                            {
                                System.out.println("name -->>"+uname.getText()+"pass-->"+upass.getText());
                                Connection conn = Conection.myConnection();
                                Statement st = conn.createStatement();
                                ResultSet rs = st.executeQuery("select * from client_registration where username='"+uname.getText()+"' and password='"+upass.getText()+"'");
                                if(rs.next())
                                {
                                    NewMsg.setEditable(true);
                                    ChatHistory.setEditable(true);
                                    Send.setEnabled(true);
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(this,"Enter Valid Username or Password!!");
                                }
                            }catch(Exception ex)
                            {
                                System.out.println("Exception caught in Client chat class-->>"+ex.getMessage());
                            }
                        }
                }
	}

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		ClientChat chatForm = new ClientChat();
	}
}
    /*@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

                new clientChatform().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}*/
