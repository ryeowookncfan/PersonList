package Person;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;
import java.sql.*;

import Tool.CreateAES;

public class Person extends JFrame implements ActionListener {

    JPanel Panel, Panel2;
    JButton View, Add, Delete, Update, select, show,Backup;
    JComboBox sort, way;
    JTextField message;
    JScrollPane gdt;
    JLabel tip;
    JTable bg1;
    selectmessage sel;
    Vector ziduan, jilu;
    PreparedStatement ps = null;
    Connection ct = null;
    ResultSet rs = null;
    static String AESkey = "000000";
    private Socket sss;
    private int row;

    public Person(String key,Socket ss) {
    	sss=ss;
    	AESkey = key;
    	//System.out.println(AESkey);
    	//��Ӳ�ѯ���
        Panel2 = new JPanel();
        String[] choice1 = {"��ѯ��ʽ", "����", "�Ա�", "����", "����","�ֻ�", "Email","QQ","��ַ"};

        Panel2.setOpaque(false);//͸��   

        way = new JComboBox(choice1); 
        way.setBackground(new Color(168, 211, 255));
        way.addActionListener(this);
        message = new JTextField(15);
        select = new JButton("��ѯ");
        select.setBackground(new Color(168, 211, 255)); 
        show = new JButton("��ʾȫ��");
        show.setBackground(new Color(168, 211, 255)); 
        show.addActionListener(this);
        select.addActionListener(this);
        
        Panel2.add(way);
        Panel2.add(message);
        Panel2.add(select);
        Panel2.add(show);

        sel = new selectmessage(AESkey, ss);
        row = sel.getRowCount();
        bg1 = new JTable(sel);
        
        setcolimnwiddth(bg1);
        
        gdt = new JScrollPane(bg1);
        gdt.setOpaque(false);

        Panel = new JPanel();
        
        Panel.setOpaque(false);
        
        View = new JButton("�鿴����");
        View.setBackground(new Color(168, 211, 255)); 
        View.addActionListener(this);
        Add = new JButton("���");
        Add.addActionListener(this);
        Add.setBackground(new Color(168, 211, 255)); 
        Delete = new JButton("ɾ��");
        Delete.addActionListener(this);
        Delete.setBackground(new Color(168, 211, 255)); 
        Update = new JButton("����");
        Update.addActionListener(this);
        Update.setBackground(new Color(168, 211, 255)); 
        Backup = new JButton("����");
        Backup.addActionListener(this);
        Backup.setBackground(new Color(168, 211, 255)); 

        Panel.add(View);
        Panel.add(Add);
        Panel.add(Delete);
        Panel.add(Update);
        Panel.add(Backup);
        
        //���ر���ͼƬ 
        ImageIcon bg = new ImageIcon("img.jpg"); // �ѱ���ͼƬ��ʾ��һ����ǩ�� 
        JLabel label = new JLabel(bg); //�ѱ�ǩ�Ĵ�Сλ������ΪͼƬ�պ����������
        label.setBounds(0,0,bg.getIconWidth(),bg.getIconHeight()); //���ͼƬ��frame�ĵڶ��� 
        this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE)); //��ȡframe�����ϲ����Ϊ�������䱳����ɫ��JPanel������͸���ķ����� 
        JPanel jp=(JPanel)this.getContentPane(); 
        jp.setOpaque(false);//����͸�� 


        this.add(gdt);
        this.add(Panel, BorderLayout.SOUTH);
        this.add(Panel2, BorderLayout.NORTH);

        this.setTitle("����ͨѶ¼����");
        this.setSize(675, 650);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setVisible(true);

    }

    /**
     * �����п�
     * @param bg1
     */
    private void setcolimnwiddth(JTable bg1) {
    	bg1.getColumnModel().getColumn(0).setPreferredWidth(20);
        bg1.getColumnModel().getColumn(1).setPreferredWidth(50);
        bg1.getColumnModel().getColumn(2).setPreferredWidth(35);
        bg1.getColumnModel().getColumn(3).setPreferredWidth(35);
        bg1.getColumnModel().getColumn(4).setPreferredWidth(71);
        bg1.getColumnModel().getColumn(5).setPreferredWidth(90);
        bg1.getColumnModel().getColumn(6).setPreferredWidth(130);
        bg1.getColumnModel().getColumn(7).setPreferredWidth(75);
        bg1.getColumnModel().getColumn(8).setPreferredWidth(160);
        bg1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        try {
			OutputStream os = sss.getOutputStream();
	        OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
	        PrintWriter pw = new PrintWriter(osw, true);
	        String selway = (String) way.getSelectedItem();
 
	        if (s.equals("��ѯ")) {
	            String choices = message.getText().trim();
	            String sql;
	            try{
	            	if (selway.equals("����")) {
	            		//sql = "select * from person where name='" + CreateAES.aesEncryptToBytes(choices, AESkey) + "'and uid="+uid;
	            		sql = "select%name%" + CreateAES.aesEncrypt(choices, AESkey);
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else if (selway.equals("�Ա�")) {
	            		//sql = "select * from person where gender ='" + choices + "'and uid="+uid;
	            		//selectmessage sq = new selectmessage(sql,AESkey);
	            		sql = "select%sex%" + choices;
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else if (selway.equals("����")) {
	            		//sql = "select * from person where age=" + choices + " and uid="+uid;
	            		sql = "select%age%" + choices;
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else if (selway.equals("����")) {
	            		//sql = "select * from person where dbo=" + choices + " and uid="+uid;
	            		sql = "select%dbo%" + choices;
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else if (selway.equals("�ֻ�")) {
	            		//sql = "select * from person where phone='" + CreateAES.aesEncryptToBytes(choices, AESkey)+ "'and uid="+uid;
	            		sql = "select%phone%" + CreateAES.aesEncrypt(choices, AESkey);
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else if (selway.equals("Email")) {
	            		//sql = "select * from person where email='" + CreateAES.aesEncryptToBytes(choices, AESkey)+ "'and uid="+uid;
	            		sql = "select%email%" + CreateAES.aesEncrypt(choices, AESkey);
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else if (selway.equals("QQ")) {
	            		//sql = "select * from person where qq='" + CreateAES.aesEncryptToBytes(choices, AESkey)+ "'and uid="+uid;
	            		sql = "select%qq%" + CreateAES.aesEncrypt(choices, AESkey);
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else if (selway.equals("��ַ")) {
	            		//sql = "select * from person where address='" + CreateAES.aesEncryptToBytes(choices, AESkey)+ "'and uid="+uid;
	            		sql = "select%address%" + CreateAES.aesEncrypt(choices, AESkey);
	            		pw.println(sql);
	            		selectmessage sq = new selectmessage(AESkey, sss);
	            		bg1.setModel(sq);
	            		setcolimnwiddth(bg1);
	            	} 
	            	else {
	            		JOptionPane.showMessageDialog(this, "��ѡ����ҷ�ʽ��");
	            		return;
	            	}
	            }catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }    
	        else if (s.equals("�鿴����")) {
	            int iii = this.bg1.getSelectedRow();
	            System.out.println("�鿴��" + iii + "�е���ϵ�ˡ�");
	            if (iii == -1) {
	                JOptionPane.showMessageDialog(this, "��ѡ��Ҫ�鿴���У�");
	                return;
	            }
	            new ViewPerson(this, "�鿴��Ա��Ϣ", true, sel, iii);
	        }    
	        else if (s.equals("���")) {
	            new addPerson(this, "�����Ա��Ϣ",true, AESkey, sss);
	            sel = new selectmessage(AESkey, sss);
	            
	            if(sel.getRowCount() == row){
	            	JOptionPane.showMessageDialog(this, "δ�ɹ���ӣ�");
	            }
	            else row++;
	                
	            bg1.setModel(sel);
	            setcolimnwiddth(bg1);
	        }    
	        else if (s.equals("��ʾȫ��")) {
            	pw.println("select%*");
	            bg1.setModel(sel);
	            setcolimnwiddth(bg1);
	        }    
	        else if (s.equals("����")) {
	            int iii = this.bg1.getSelectedRow();
	            System.out.println("���µ�" + iii + "�е���ϵ�ˡ�");
	            if (iii == -1) {
	                JOptionPane.showMessageDialog(this, "��ѡ��Ҫ���µ��У�");
	                return;
	            }
	            new Update(this, "�޸���Ա��Ϣ", true, sel, iii, AESkey, sss);
	            sel = new selectmessage(AESkey, sss);
	            bg1.setModel(sel);
	            setcolimnwiddth(bg1);
	        }    
	        else if (s.equals("ɾ��")) {
	            int ii = this.bg1.getSelectedRow();
	            if (ii == -1) {
	                JOptionPane.showMessageDialog(this, "��ѡ��ɾ�����У�");
	                return;
	            }
	            String st = (String) sel.getValueAt(ii, 0);
	            System.out.println("ɾ����Ϊ" + st + "�е���ϵ�ˡ�");
	            int n = JOptionPane.showConfirmDialog(this, "ȷ��ɾ����?", "ȷ��", JOptionPane.YES_NO_OPTION);
	            if (n == JOptionPane.YES_OPTION) {
	            	pw.println("delete%"+st);
		            
	            	sel = new selectmessage(AESkey, sss);
		            if(sel.getRowCount() == row){
		            	JOptionPane.showMessageDialog(this,"����δ֪����");
		            }
		            else row--;
		            
		            bg1.setModel(sel);
		            setcolimnwiddth(bg1);
	            } else if (n == JOptionPane.NO_OPTION){} 
	        }    
	        else if (s.equals("����")) {
	        	pw.println("save");
	        	InputStream is =sss.getInputStream();
		        InputStreamReader isr = new InputStreamReader(is);
		        BufferedReader br = new BufferedReader(isr);
	        	String result = br.readLine();
	        	if(result.equals("ok")) JOptionPane.showMessageDialog(this, "�ѳɹ����ݵ���������");
	        }
		} catch (UnknownHostException e1) {

			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
    }
}
