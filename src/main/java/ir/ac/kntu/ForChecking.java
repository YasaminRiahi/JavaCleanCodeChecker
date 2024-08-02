package ir.ac.kntu;

public class ForChecking {

    private String forLine;

    public void checkForLoop(String forLine, int whichLine) {
        this.forLine = forLine;
        if (!forRegex()) {
            System.out.println("The location of " + findForProblem() + "in the for loop in line " + whichLine
                    + " is incorrect! You have to write for loop exactly in this form: for(some characters){");
            System.out.println("_____________________________________________________________________");
        }
    }


    public boolean forRegex() {
        return this.forLine.matches("for\\S(.+)\\{");
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
}
