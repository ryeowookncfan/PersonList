
public class StreamProcess {
	private static int SPACE = 4;    //���ܲ�����С
	private static int INSTRUCT_LENGTH = 5; //ָ�������
	private static int SLICE_LENGTH = INSTRUCT_LENGTH+SPACE-1; //������ˮ�ȵĳ���
	private String instruct[] = {"ED","DA","MA","NL","BF"};  //ÿһ��ָ�������
	
	public static void main(String[] args){
		StreamProcess sp = new StreamProcess();
		sp.fillStream();
	}
	/**
	 * ��ˮ�ߵ��ȵ���Ҫ�㷨������ˮ�ߵ�ȫ����������һ����ά������
	 */
	public void fillStream(){
		int[][] stream = new int[SPACE][SLICE_LENGTH];  //������ˮ��Ҫ�ߵ�ʱ�շֲ�
		int tempSapce = 1;    //��ˮ�ߵ�ָ���
		int tempTime = 0;  //��ˮ�ߵĹ��ܲ���
		for(int i=SPACE-1;i>=0;i--){  //�ȴ���ײ㿪ʼ��
			tempSapce=1;          //
			for(int j=tempTime;j<tempTime+INSTRUCT_LENGTH;j++){
				stream[i][j] = tempSapce++;  
			}
			tempTime++; //ת�Ƶ�����һ�����ܲ���
		}
		display(stream);
	}
	
	/**
	 * ��ʾ��ˮ�ߵ�ʱ��ͼ����ʾһ����ά����
	 * @param stream ���������ˮ�߹��̵Ķ�ά����
	 */
	public void display(int stream[][]){
		double num1 =0,num2 = 0,num3 = 0;
		for(int i=0;i<stream.length;i++){
			for(int j=0;j<stream[i].length;j++){
				if(stream[i][j]==0){
					System.out.print("\t");
				}else{
					num1++;
					System.out.print(instruct[i]+stream[i][j]+"\t");
				}
			}
			System.out.println();   
		}
		System.out.println("�����ʣ�"+num1/(SPACE*SLICE_LENGTH)+"��t");
		System.out.println("���ٱȣ�"+(double)SPACE*INSTRUCT_LENGTH/SLICE_LENGTH);
		System.out.println("Ч�ʣ�"+num1/(SPACE*SLICE_LENGTH));
	}
}
