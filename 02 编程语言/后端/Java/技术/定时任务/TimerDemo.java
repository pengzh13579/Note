import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/***
 * ָ����ʼʱ����2019/08/29 14:10:00��
 * schedule��scheduleAtFixedRate���������ڣ�
 * scheduleAtFixedRate����Ѿ���ȥʱ��Ҳ��Ϊ����ִ�С�
 * 		�����14:17:00��ִ�����������ô�����̴�ӡ3�Ρ�
 * schedule����ѹ�ȥʱ�����ϡ�
 * 		�����14:17:00��ִ���������ֻ��ӡ1�Ρ�
 * 
 * @author peng
 */
public class TimerDemo {

	public static void main(String[] args) throws ParseException {
		// 3* 60* 1000 ����3����
		//scheduleAtFixedRateDemo();
		scheduleDemo();
	}

	public static void scheduleAtFixedRateDemo() throws ParseException {
		Timer t = new Timer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = sdf.parse("2019/08/29 14:10:00");
		t.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				System.out.println("this is task you do6");
			}
		}, date, 3 * 60 * 1000);
	}
	
	public static void scheduleDemo() throws ParseException {
		Timer t = new Timer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = sdf.parse("2019/08/29 14:10:00");
		t.schedule(new TimerTask() {
			public void run() {   
				System.out.println("this is task you do6");   
			}
		}, date, 3 * 60 * 1000);
	}
}
