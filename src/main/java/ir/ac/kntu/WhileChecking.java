package ir.ac.kntu;

public class WhileChecking {

    private String whileLine;


    public void checkWhileLine(String whileLine, int whichLine) {
        this.whileLine = whileLine;
        if (!whileRegex()) {
            System.out.println("The location of " + findWhileProblem() + "in the while loop in line " + whichLine
                    + " is incorrect! You have to write while loop exactly in this form: while(some characters){");
            System.out.println("_____________________________________________________________________");
        }
    }

    public boolean whileRegex() {
        return this.whileLine.matches("while\\S(.+)\\{");
    }

    public String findWhileProblem() {
        String problems = "";
        if (whileLine.length() < 5 || (whileLine.length() > 5 && whileLine.charAt(5) != '(')) {
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