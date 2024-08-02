package ir.ac.kntu;

public class ForWhileIf {

    private String whileLine;


    public ForWhileIf() {

    }

    public void setWhileLine(String whileLine) {
        this.whileLine = whileLine;
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
}

