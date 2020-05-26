import java.util.Arrays;
import java.util.List;

public class DaysOfTheWeek {

    public static void main(String[] args) {
        DaysOfTheWeek s = new DaysOfTheWeek();
        System.out.println(s.solution("Wed", 2));
        System.out.println(s.solution("Sat", 23));
    }

    List<String> days = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");

    public String solution(String S, int K) {
        return days.get((days.indexOf(S) + K) % days.size());
    }
}