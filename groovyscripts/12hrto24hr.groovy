import java.text.SimpleDateFormat 
import java.util.Date


String time = "2020-08-28 10:10 pm";

SimpleDateFormat date12Format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
SimpleDateFormat date24Format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss"); 

System.out.println(date24Format.format(date12Format.parse(time)));
