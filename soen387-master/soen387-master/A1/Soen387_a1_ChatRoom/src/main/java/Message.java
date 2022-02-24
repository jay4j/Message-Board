import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Message implements Comparable{
    private String content;
    private String user;
    private String date;

    public Message() {
    }

    public Message(String content, String user) {
        this.content = content;
        this.user = user;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = formatter.format(now);
        this.date = date;
    }

    public Message(String content, String user, String date) {
        this.content = content;
        this.user = user;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", user='" + user + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        String str1 = this.date;
        String str2 = (String) o;
        int[] origin = strToInt(str1);
        int[] opponent = strToInt(str2);
        for(int i = 0; i < origin.length; i++){
            if(origin[i] > opponent[i]) return 1;
            else if(origin[i] < opponent[i]) return -1;
        }
        return 0;
    }
    public int[] strToInt(String date){
        int[] ans = new int[6];
        Date current = new Date();
        try {
            ans[0] = Integer.valueOf(date.substring(0, 4));
            ans[1] = Integer.valueOf(date.substring(5, 7));
            ans[2] = Integer.valueOf(date.substring(8, 10));
            ans[3] = Integer.valueOf(date.substring(11, 13));
            ans[4] = Integer.valueOf(date.substring(14, 16));
            ans[5] = Integer.valueOf(date.substring(17, 19));
        }catch(NumberFormatException e){
            System.out.println("Your date input format is invalid, please follow the way like: ");
            System.out.println("yyyy/MM/dd HH:mm:ss");
        }
        Calendar calender = Calendar.getInstance();
        int year = calender.getWeekYear();
        if(ans[0] > year || ans[1] < 1 || ans[1] > 12 || ans[2] < 0 || ans[2] > 31 ||
                ans[3] < 0 || ans[3] > 24 || ans[4] < 0 || ans[4] > 59 ||ans[5] < 0 || ans[5] >59){
            System.out.println("Your date input format is invalid, please follow the way like: ");
            System.out.println("yyyy/MM/dd HH:mm:ss");
            System.out.println("Your year, month, day, hour, minute and second must follow the real time");
            System.exit(1);
        }

        return ans;
    }
}