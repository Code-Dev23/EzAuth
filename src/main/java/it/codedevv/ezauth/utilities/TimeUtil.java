package it.codedevv.ezauth.utilities;

public class TimeUtil {
    public static String formattedTime(long seconds) {
        if (seconds < 0)
            return "###";

        long[] units = {31536000, 2592000, 86400, 3600, 60, 1};
        String[] labels = {"y ", "mo ", "d ", "h ", "m ", "s"};

        StringBuilder formattedTime = new StringBuilder();

        for (int i = 0; i < units.length; i++) {
            if (seconds >= units[i]) {
                formattedTime.append(seconds / units[i]).append(labels[i]);
                seconds %= units[i];
            }
        }

        return formattedTime.toString().trim();
    }
}
