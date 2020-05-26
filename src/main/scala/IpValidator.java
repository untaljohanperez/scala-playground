public class IpValidator {

    public static void main(String[] args) {
        IpValidator s = new IpValidator();
        System.out.println(s.solution("4216712120"));
        System.out.println(s.solution("255255255255"));
        System.out.println(s.solution("188212"));
    }

    public int solution(String s) {
        if (s.length() > 12 || s.length() < 4)
            return 0;

        int size = s.length();
        int count = 0;

        for (int i = 1; i < size - 2; i++) {
            for (int j = i + 1; j < size - 1; j++) {
                for (int k = j + 1; k < size; k++) {
                    String ip = getIp(s, i, j ,k);
                    count += countValidIp(ip);
                }
            }
        }

        return count;
    }

    private String getIp(String ip, int i, int j, int k) {
        String newIp = ip;
        newIp = newIp.substring(0, k) + "." + newIp.substring(k);
        newIp = newIp.substring(0, j) + "." + newIp.substring(j);
        newIp = newIp.substring(0, i) + "." + newIp.substring(i);
        return newIp;
    }

    private boolean isValidIp(String ip) {
        String parts[] = ip.split("[.]");
        for (String part : parts) {
            int i = Integer.parseInt(part);
            if (part.length() > 3 || i < 0 || i > 255)
                return false;
            if (part.length() > 1 && i == 0)
                return false;
            if (part.length() > 1 && i != 0 && part.charAt(0) == '0')
                return false;
        }
        return true;
    }

    private int countValidIp(String ip) {
        return isValidIp(ip) ? 1 : 0;
    }
}
