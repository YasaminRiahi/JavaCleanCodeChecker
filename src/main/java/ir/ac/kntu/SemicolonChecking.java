package ir.ac.kntu;

public class SemicolonChecking {

    public void checkSemicolon(String line, int whichLine) {
        String withoutStartingSpace = removeStartingSpace(line);
        VariableChecking variableChecking = new VariableChecking();
        if (line.contains("package")) {
            findAndCheckSemicolon(withoutStartingSpace, whichLine);
        } else if (line.contains("import")) {
            findAndCheckSemicolon(withoutStartingSpace, whichLine);
        } else if (variableChecking.canFindVariable(withoutStartingSpace) == true) {
            findAndCheckSemicolon(withoutStartingSpace, whichLine);
        }
    }
    public void findAndCheckSemicolon(String line, int whichLine) {
        moreSemicolon(line, whichLine);
        if (line.charAt(line.length() - 1) != ';') {
            System.out.println("You have to write ; exactly at the end of line " + whichLine);
            System.out.println("_____________________________________________________________________");
        }
    }

    public void moreSemicolon(String line, int whichLine) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ';') {
                count++;
            }
        }
        if (count > 1) {
            System.out.println("You have more than one semicolon in line " + whichLine);
            System.out.println("Please remove one of the semicolons or write each command on a separate line");
            System.out.println("_____________________________________________________________________");
        }
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
