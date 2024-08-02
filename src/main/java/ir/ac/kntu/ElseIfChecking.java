package ir.ac.kntu;

public class ElseIfChecking {


    private String ifLine;

    private String elseLine;


    public void checkIfLine(String ifLine, int whichLine) {
        this.ifLine = ifLine;
        if (!ifRegex()) {
            System.out.println("The location of " + findIfProblem() + "in the if condition in line " + whichLine
                    + " is incorrect! You have to write if condition exactly in this form: if(some characters){");
            System.out.println("_____________________________________________________________________");
        }
    }

    public void checkElseLine(String elseLine, int whichLine, int elseOrElseIf) {
        ForWhileIf line = new ForWhileIf();
        this.elseLine = elseLine;
        if (!elseRegex()) {
            System.out.println("The location of " + findElseProblem(elseOrElseIf) + "in the else condition in line "
                    + whichLine + " is incorrect!");
            if (elseOrElseIf == 1) {
                System.out.println("You have to write else if condition exactly in this form:else if(some characters){");
            } else if (elseOrElseIf == 2) {
                System.out.println("You have to write else condition exactly in this form: else{");
            }
            System.out.println("_____________________________________________________________________");
        }
    }


    public void moveElse(int whichLine) {
        System.out.println("Move your else statement in line " + whichLine + " exactly after the } of previous statement");
        System.out.println("_____________________________________________________________________");
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
}
