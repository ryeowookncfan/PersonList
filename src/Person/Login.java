package Person;

import java.io.*;
import java.net.*;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

import Tool.CreateMD5;

public class Login {

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;

	/**
	 * ����
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * ����
	 */
	public Login() {
		initialize();
	}

	/**
	 * ��ʼ���������
	 */
	private void initialize() {
		// ��ȡ��Ļ�Ŀ�ȡ��߶ȣ��Ծ��д���
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screen = kit.getScreenSize();
		int screenWidth = screen.width;
		int screenHeight = screen.height;
		int frameWidth = 518;
		int frameHeight = 345;

		frame = new JFrame();
		frame.setTitle("����ͨѶ¼����");
		frame.setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(518, 345);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		// ���ر���ͼƬ 
        ImageIcon bg = new ImageIcon("iomg.jpg"); // �ѱ���ͼƬ��ʾ��һ����ǩ�� 
        JLabel label = new JLabel(bg); //�ѱ�ǩ�Ĵ�Сλ������ΪͼƬ�պ����������
        label.setBounds(0,0,bg.getIconWidth(),bg.getIconHeight()); //���ͼƬ��frame�ĵڶ��� 
        frame.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE)); //��ȡframe�����ϲ����Ϊ�������䱳����ɫ��JPanel������͸���ķ����� 
        JPanel jp=(JPanel)frame.getContentPane(); 
        jp.setOpaque(false);
        /*
		JPanel titelPanel = new JPanel();
		titelPanel.setBounds(0, 0, 512, 178);
		frame.getContentPane().add(titelPanel);
		titelPanel.setBackground(new Color(68, 119, 136));
		titelPanel.setLayout(null);

		//JLabel label = new JLabel("�� �� ͨ Ѷ ¼ �� ��");
		label.setFont(new Font("΢���ź�", Font.PLAIN, 38));
		label.setBounds((512 - 332) / 2, (178 - 42) / 2, 332, 42);
		label.setForeground(Color.WHITE);
		titelPanel.add(label);
*/
		JPanel loginPanel = new JPanel();
		loginPanel.setBounds(0, 177, 512, 139);
		frame.getContentPane().add(loginPanel);
		loginPanel.setLayout(null);

		usernameField = new JTextField();
		usernameField.setBounds(178, 32, 150, 21);
		loginPanel.add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(178, 63, 150, 21);
		loginPanel.add(passwordField);

		JLabel usernameLabel = new JLabel("�û�����");
		usernameLabel.setFont(new Font("΢���ź� Light", Font.PLAIN, 14));
		usernameLabel.setBounds(110, 34, 60, 15);
		loginPanel.add(usernameLabel);

		JLabel passwordLabel = new JLabel("��    �룺");
		passwordLabel.setFont(new Font("΢���ź� Light", Font.PLAIN, 14));
		passwordLabel.setBounds(110, 66, 60, 15);
		loginPanel.add(passwordLabel);

		JButton submitButton = new JButton("��½");
		submitButton.setFont(new Font("΢���ź�", Font.PLAIN, 16));
		submitButton.addActionListener(new LoginController());
		submitButton.setBounds(345, 31, 70, 52);
		submitButton.setContentAreaFilled(false);
		submitButton.setForeground(Color.BLACK);
		loginPanel.add(submitButton);

		JPanel submitButtonBackground = new JPanel();
		submitButtonBackground.setBounds(345, 31, 70, 52);
		submitButtonBackground.setBackground(new Color(130, 192, 255));
		loginPanel.add(submitButtonBackground);
		
		JLabel regBtn = new JLabel("ע��");
		regBtn.setFont(new Font("����", Font.PLAIN, 12));
		regBtn.setBounds(460, 114, 34, 15);
		regBtn.addMouseListener(new RegBtnController());
		loginPanel.add(regBtn);
		
	}
	
	/**
	 * �ж��ַ����Ƿ�Ϊ���� 
	 * @param str�ַ���
	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length();--i>=0;) {  
			 if (!Character.isDigit(str.charAt(i))) {
			    return false;
			 }
		}
		return true;
	}
	
	//�����½�¼�
	class LoginController implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		
			String user = usernameField.getText();
	        String pass = CreateMD5.getMd5(passwordField.getText(), 16);
			try {
				Socket s = new Socket("127.0.0.1",8001);
				OutputStream os = s.getOutputStream();
		        OutputStreamWriter osw = new OutputStreamWriter(os);
		        PrintWriter pw = new PrintWriter(osw , true);
							
		        pw.println(user+"%"+pass+"%l");
		        
		        InputStream is = s.getInputStream();
		        InputStreamReader isr = new InputStreamReader(is);
		        BufferedReader br = new BufferedReader(isr);
		        
		        String flag = br.readLine();
		        //System.out.println("�û�idΪ��"+flag);isNumeric(flag)
		        if(flag.equals("ok")) { 
		        	new Person(pass, s);
		        	frame.setVisible(false);
		        }
		        else JOptionPane.showMessageDialog(frame, "�û�����������������ԡ�");
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	class RegBtnController implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			new Register(frame);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
		}
	}
}
