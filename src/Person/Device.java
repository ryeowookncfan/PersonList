package Person;
public class Device {
	private String datas;//�豸/�洢���е�����
	private int dataLength;
	public Device()
	{}
	public Device(String datas, ChannalManager cm)
	{
		this.setDatas(datas);
		this.dataLength = datas.length();
		if(this.dataLength > cm.getMaxDevCap())
			cm.setMaxDevCap(this.dataLength);
	}
	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	public int getDataLength() {
		return dataLength;
	}
	public void setDatas(String datas) {
		this.datas = datas;
	}
	public String getDatas() {
		return datas;
	}
}
