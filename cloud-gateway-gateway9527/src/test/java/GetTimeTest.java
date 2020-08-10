import java.time.ZonedDateTime;

public class GetTimeTest {
    public static void main(String[] args) {
        ZonedDateTime zbj = ZonedDateTime.now();    //默认时区(2020-08-10T10:02:25.263+08:00[Asia/Shanghai])
        System.out.println(zbj);
    }
}
