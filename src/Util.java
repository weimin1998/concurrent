import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static String now(){
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())+"=====>";
    }
}