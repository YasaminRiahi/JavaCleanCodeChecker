package ir.ac.kntu;

public class SwitchCaseChecking {

    private String switchLine;

    public void checkSwitchLine(String switchLine, int whichLine) {
        this.switchLine = switchLine;
        if (!switchRegex()) {
            System.out.println("The location of " + findSwitchProblem() + "in switch in line " + whichLine
                    + " is incorrect! You have to write switch exactly in this form: switch(some characters){");
            System.out.println("_____________________________________________________________________");
        }
    }

    public void checkCase(String caseLine, int whichLine) {
        if (caseLine.charAt(caseLine.length() - 1) != ':') {
            System.out.println("You have to write : exactly at the end of line " + whichLine);
            System.out.println("_____________________________________________________________________");
        }
    }

    public void printDefaultError(int whichLine) {
        System.out.println("There is no default for your previous switch in " + whichLine + "!Please add a default");
        System.out.println("_____________________________________________________________________");
    }

    public boolean switchRegex() {
        return this.switchLine.matches("switch\\S(.+)\\{");
    }

    public String findSwitchProblem() {
        String problems = "";
        if (switchLine.charAt(6) != '(') {
            problems += "( ";
        }
        if (switchLine.charAt(switchLine.length() - 2) != ')' && switchLine.charAt(switchLine.length() - 1) == '{') {
            problems += "{ ";
        }
        if (switchLine.charAt(switchLine.length() - 1) != '{') {
            problems += "{ ";
        }
        return problems;
    }
}
