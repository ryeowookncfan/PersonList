package Person;
import java.util.ArrayList;
import java.util.List;

public class ChannalManager implements Runnable {
private String interrupt;//��ʾ�ж��ź�
		private int maxDevCap=0;//�����豸����������ݳ���
		private List<Device> Devices;//������Χ�豸
		private List<Device> memory;//�洢��
		
		public ChannalManager()
		{
			this.interrupt="none";
			this.Devices = new ArrayList<Device>();
			this.Devices.add(new Device());
			this.Devices.add(new Device());
			this.Devices.add(new Device());
			this.memory = new ArrayList<Device>();
			this.memory.add(new Device("love",this));
			this.memory.add(new Device("channal",this));
			this.memory.add(new Device("architecture",this));
		}
		public void setInterrupt(String interrupt) {
			this.interrupt = interrupt;
		}

		public String getInterrupt() {
			return interrupt;
		}

		public void setMaxDevCap(int maxDevCap) {
			this.maxDevCap = maxDevCap;
		}

		public int getMaxDevCap() {
			return maxDevCap;
		}

		public void setDevices(List<Device> devices) {
			Devices = devices;
		}

		public List<Device> getDevices() {
			return Devices;
		}
		public void printDevice()
		{
			int i=0;
			for(Device d : this.Devices)
			{
				System.out.print("�豸"+i+"����:");
				System.out.println(d.getDatas());
				i++;
			}
		}
		
		public void run() {//ͨ���������������ݴ���
			// TODO Auto-generated method stub
			for(int fence = 0 ;fence < this.maxDevCap; fence++) 
			    for(int i = 0;i < this.Devices.size() ; i++) 
			  { 
			   if(fence < this.memory.get(i).getDataLength()) 
			   {  
				   this.Devices.get(i).setDatas(this.memory.get(i).getDatas().substring(0, fence+1));
			   } 
			   printDevice(); 
			  } 
			  printDevice(); 
			  this.interrupt="finish";//��������ж�
		}
	}
