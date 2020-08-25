import java.text.SimpleDateFormat 
import java.util.Date


String time = "15/09/20 10:10 pm";

SimpleDateFormat date12Format = new SimpleDateFormat("dd/MM/yy hh:mm aa");
SimpleDateFormat date24Format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 

String time1 = date24Format.format(date12Format.parse(time));

System.out.println(time1)


System.out.println(date24Format.format(date12Format.parse(time)));
