package ir.ac.kntu;

public class ForWhileIf {

    private String whileLine;

    private String switchLine;

    public ForWhileIf() {

    }

    public void setWhileLine(String whileLine) {
        this.whileLine = whileLine;
    }

    public void setSwitchLine(String switchLine) {
        this.switchLine = switchLine;
    }

    public boolean whileRegex() {
        return this.whileLine.matches("while\\S(.+)\\{");
    }

    public String findWhileProblem() {
        String problems = "";
        if (whileLine.charAt(5) != '(') {
            problems += "( ";
        }
        if ((whileLine.charAt(whileLine.length() - 2) != ')') && (whileLine.charAt(whileLine.length() - 1) == '{')) {
            problems += "{ ";
        }
        if (whileLine.charAt(whileLine.length() - 1) != '{') {
            problems += "{ ";
        }
        return problems;
    }

    public boolean switchRegex(){
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

