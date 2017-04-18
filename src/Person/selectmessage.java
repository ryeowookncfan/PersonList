package Person;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import Tool.CreateAES;

final class selectmessage extends AbstractTableModel{
    Vector ziduan, jilu;
    PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;

    /**
     * ���ַ����ԡ�, ��Ϊ�ָ���ת��Ϊ�ַ�������
     * @param source
     * @return �ַ�����
     */
 	public static String[] splitStringByComma(String source) {
		if(source == null || source.trim().equals("")) return null;
		StringTokenizer commaToker=new StringTokenizer(source, ", ");
		String[] result = new String[commaToker.countTokens()];
		int i=0;
		while(commaToker.hasMoreTokens()){
			result[i] = commaToker.nextToken();
			i++;
		}
		return result;
	} 
 	 
    public selectmessage(String key, Socket s) {        
        ziduan = new Vector();
        ziduan.add("ID");
        ziduan.add("����");  
        ziduan.add("�Ա�");
        ziduan.add("����"); 
        ziduan.add("����");
        ziduan.add("�ֻ�");
        ziduan.add("Email");
        ziduan.add("QQ");
        ziduan.add("��ַ");
        
        jilu=new Vector();
		try {
			InputStream is = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(is,"utf-8");
		    BufferedReader br = new BufferedReader(isr);		     
	     	String result = br.readLine();
	     	result = result.substring(1,result.length()-1);
	     	 
	     	if(!result.equals("")) {
	     		String[] tp = splitStringByComma(result);
	     		for(int i = 0; i < tp.length/9; i++) {
	     			Vector<String> temp = new Vector<String>();
	     			for(int j = 0; j < 9; j++){
	     				if(j == 0 || j == 2 || j == 3 || j == 4) temp.add(tp[j+9*i]);
	     				else temp.add(CreateAES.aesDecrypt(tp[j+9*i], key));
	     			}
	     			jilu.add(temp);//System.out.println(jilu);
	     		}
	     	}else ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
     	 
        /*
         * ���ļ���ȡ      
        int i;
        Sheet sheet;
        Workbook book;
        Cell cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8,cell9;
        try { 
            //t.xlsΪҪ��ȡ��excel�ļ���
            book= Workbook.getWorkbook(new File("E:\\test.xls"));         
            sheet=book.getSheet("test"); 
            
            i=0;
            while(true)
            {
                //��ȡÿһ�еĵ�Ԫ�� 
                cell1=sheet.getCell(0,i);//���У��У�
                cell2=sheet.getCell(1,i);
                cell3=sheet.getCell(2,i);
                cell4=sheet.getCell(3,i);
                cell5=sheet.getCell(4,i);
                cell6=sheet.getCell(5,i);
                cell7=sheet.getCell(6,i);
                cell8=sheet.getCell(7,i);
                cell9=sheet.getCell(8,i);
                
                if("".equals(cell1.getContents())==true) break;   //�����ȡ������Ϊ��
                Vector hang= new Vector();
                hang.add(cell1.getContents());
                hang.add(cell2.getContents());
                hang.add(cell3.getContents());
                hang.add(cell4.getContents());
                hang.add(cell5.getContents());
                hang.add(cell6.getContents());
                hang.add(cell7.getContents());
                hang.add(cell8.getContents());
                hang.add(cell9.getContents());
                jilu.add(hang);   
                i++;
            }
            book.close(); 
        }
        catch(Exception e)  { } 
        */
        /*
         * ֱ�Ӵ����ݿ��ȡ
        try{
        	Class.forName("org.gjt.mm.mysql.Driver");	
            ct = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/game?useUnicode=true&characterEncoding=utf8", "root", "ryeo");
            ps = ct.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Vector<String> hang= new Vector<String>();             
                hang.add(rs.getString(1));
                hang.add(CreateAES.aesDecrypt(rs.getString(2), key));
                hang.add(rs.getString(3));
                hang.add(rs.getString(4));
                hang.add(rs.getString(5));
                hang.add(CreateAES.aesDecrypt(rs.getString(6), key));
                hang.add(CreateAES.aesDecrypt(rs.getString(7), key));
                hang.add(CreateAES.aesDecrypt(rs.getString(8), key));
                hang.add(CreateAES.aesDecrypt(rs.getString(9), key));
                jilu.add(hang);
                //System.out.println(hang);                   
            }         
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("Error "+e);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        finally{
            try{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
                if(ct!=null){
                    ct.close();
                }
            }catch(SQLException e){}
        }
        */
    }


     @Override
    public int getRowCount() {//���н��м��������ŵ��м�����һ����Ŀ������ڵģ����޷������
        return this.jilu.size();
    }

    @Override
    public int getColumnCount() {//���н��м��������ص�ֵ��ʲô��˵���м���,���ŵ���Ŀ�м���
    	return this.ziduan.size();
    }

    @Override
    public Object getValueAt(int hang, int lie) {//���ĳ��ĳ�������ֵ
        return ((Vector)this.jilu.get(hang)).get(lie);
    }
     
     @Override
    public String getColumnName(int e){//�õ��������һ�еı�ͷ
    	 return (String)this.ziduan.get(e);
    }


}
