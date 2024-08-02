package ir.ac.kntu;

public class ForWhileIf {

    private String forLine;

    private String forVariable;

    private String whileLine;

    private String switchLine;

    public ForWhileIf() {

    }

    public void setForLine(String forLine) {
        this.forLine = forLine;
    }


    public void setForVariable(String forVariable) {
        this.forVariable = forVariable;
    }

    public void setWhileLine(String whileLine) {
        this.whileLine = whileLine;
    }

    public void setSwitchLine(String switchLine) {
        this.switchLine = switchLine;
    }

    public boolean forRegex() {
        return this.forLine.matches("for\\S(.+)\\{");
    }

    public boolean forVariableRegex() {
        return this.forVariable.matches("(([a-z]+)|[a-z]+[A-Z]{1}[a-z]*)|" +
                "([a-z]+[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*)|([a-z]+[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*)");
    }

    public String findForProblem() {
        String problems = "";
        if (forLine.charAt(3) != '(') {
            problems += "( ";
        }
        if ((forLine.charAt(forLine.length() - 2) != ')') && (forLine.charAt(forLine.length() - 1) == '{')) {
            problems += "{ ";
        }
        if (forLine.charAt(forLine.length() - 1) != '{') {
            problems += "{ ";
        }
        return problems;
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

