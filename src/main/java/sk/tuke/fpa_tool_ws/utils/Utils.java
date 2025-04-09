package sk.tuke.fpa_tool_ws.utils;

public class Utils {

    public static boolean isInRange(String range, int number) {
        String[] parts = range.split("-");
        if (parts.length != 2) return false;

        try {
            int start = Integer.parseInt(parts[0]);
            int end = Integer.parseInt(parts[1]);
            return number >= start && number <= end;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
