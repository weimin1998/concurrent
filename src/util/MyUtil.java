package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {
    public static String now(){
        return Thread.currentThread().getName()+":::::"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())+"=====>";
    }
}
