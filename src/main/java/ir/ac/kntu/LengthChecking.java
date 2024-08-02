package ir.ac.kntu;

public class LengthChecking {

    public void checkLineLength(String line, int whichLine) {
        if (line.length() > 80) {
            System.out.println("Line " + whichLine + " is more than 80 characters!");
            giveSuggestion(line);
            System.out.println("_____________________________________________________________________");
        }
    }

    public void giveSuggestion(String line) {
        int howManySpace = 0, i = 0;
        while (line.charAt(i) != '(' && line.charAt(i) != '=' && line.charAt(i) != '"') {
            howManySpace++;
            i++;
        }
        String[] lines = new String[10];
        for (i = 0; i < 10; i++) {
            lines[i] = "";
        }
        lines[0] = line;
        int k = 0;
        while (lines[k].length() > 80) {
            k++;
            String toKnow = breakLines(lines[k - 1], howManySpace);
            if (!toKnow.equals("nothing")) {
                lines[k] = toKnow;
            } else {
                System.out.println("You can break this line anyway you want!");
            }
        }
        for (i = 0; i < lines.length; i++) {
            if (i + 1 < lines.length) {
                if (!lines[i].equals("")) {
                    int length = lines[i].length() - removeStartingSpace(lines[i + 1]).length();
                    lines[i] = lines[i].substring(0, length);
                    System.out.println(lines[i]);
                }
            }
        }
    }

    public String breakLines(String beforeBreaking, int howManySpace) {
        for (int j = beforeBreaking.length() - 1; j >= 0; j--) {
            if (isOkToBreak(beforeBreaking.charAt(j)) && j <= 80) {
                StringBuilder newLine = new StringBuilder();
                while (howManySpace != 0) {
                    newLine.append(" ");
                    howManySpace--;
                }
                if (beforeBreaking.charAt(j) != ',') {
                    System.out.println("You can break this line from character " + (j - 1) + ":" + beforeBreaking.charAt(j - 1));
                    for (int p = j; p < beforeBreaking.length(); p++) {
                        newLine.append(beforeBreaking.charAt(p));
                    }
                } else {
                    System.out.println("You can break this line from character " + j + ":" + beforeBreaking.charAt(j));
                    for (int p = j + 1; p < beforeBreaking.length(); p++) {
                        newLine.append(beforeBreaking.charAt(p));
                    }
                }
                return newLine.toString();
            }
        }
        return "nothing";
    }

    public boolean isOkToBreak(char character) {
        if (character == '+' || character == '-' || character == '*' || character == '/' || character == '%') {
            return true;
        } else return character == '^' || character == '&' || character == '|' || character == ',';
    }

    public String removeStartingSpace(String line) {
        int count = 0;
        int i = 0;
        while (i < line.length() && line.charAt(i) == ' ') {
            count++;
            i++;
        }
        return line.substring(count);
    }
}