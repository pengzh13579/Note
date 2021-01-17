import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/***
 * 指定开始时间是2019/08/29 14:10:00。
 * schedule和scheduleAtFixedRate的区别在于：
 * scheduleAtFixedRate会把已经过去时间也作为周期执行。
 * 		如果在14:17:00分执行这个程序，那么会立刻打印3次。
 * schedule不会把过去时间算上。
 * 		如果在14:17:00分执行这个程序，只打印1次。
 * 
 * @author peng
 */
public class TimerDemo {

	public static void main(String[] args) throws ParseException {
		// 3* 60* 1000 代表3分钟
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
