package ir.ac.kntu;

public class ForWhileIf {

    private String forLine;

    private String forVariable;

    private String whileLine;

    private String ifLine;

    private String elseLine;

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

    public void setIfLine(String ifLine) {
        this.ifLine = ifLine;
    }

    public void setElseLine(String elseLine) {
        this.elseLine = elseLine;
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

    public boolean ifRegex() {
        return this.ifLine.matches("if(.+)\\{");
    }

    public String findIfProblem() {
        String problems = "";
        if (ifLine.charAt(2) != '(') {
            problems += "( ";
        }
        if (ifLine.charAt(ifLine.length() - 2) != ')' && ifLine.charAt(ifLine.length() - 1) == '{') {
            problems += "{ ";
        }
        if (ifLine.charAt(ifLine.length() - 1) != '{') {
            problems += "{ ";
        }
        return problems;
    }

    public boolean elseRegex() {
        return this.elseLine.matches("(else\\S\\{)|(else if\\S(.+)\\{)");
    }

    public String findElseProblem(int elseOrElseIf) {
        String problems = "";
        if (elseOrElseIf == 1) {
            if (elseLine.charAt(7) != '(') {
                problems += "( ";
            }
            if (elseLine.charAt(elseLine.length() - 2) != ')' && elseLine.charAt(elseLine.length() - 1) == '{') {
                problems += "{ ";
            }
            if (elseLine.charAt(elseLine.length() - 1) != '{') {
                problems += "{ ";
            }
        } else if (elseLine.charAt(4) != '{') {
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

