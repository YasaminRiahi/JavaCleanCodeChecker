package ir.ac.kntu;

public class TabChecking {


    public static int findNumberOfTabs(String line, int tab) {
        if (!line.equals("")) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '{') {
                    tab += 4;
                } else if (i + 3 < line.length() && line.substring(i, i + 4).equals("case")) {
                    tab += 4;
                } else if (line.charAt(i) == '}') {
                    tab -= 4;
                } else if (i + 4 < line.length() && line.substring(i, i + 5).equals("break")) {
                    tab -= 4;
                }
            }
        }
        return tab;
    }

    public static void isTabEnough(String line, int tab, int whichLine) {
        int i = 0, count = 0;
        if (!line.equals("")) {
            while (i < line.length() && line.charAt(i) == ' ') {
                count++;
                i++;
            }
            if (count != tab) {
                if (count > tab) {
                    System.out.println("Please remove " + (count - tab) + " space(s) in line " + whichLine);
                } else {
                    System.out.println("Please add " + (tab - count) + " space(s) in line " + whichLine);
                }
                System.out.println("_____________________________________________________________________");
            }
        }
    }
}
