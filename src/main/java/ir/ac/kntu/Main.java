package ir.ac.kntu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static int switchCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("src/main/java/ir/ac/kntu/" + fileName));
            int countLine = 0, countEmptyLine = 0, countForImport = 0, tab = 0;
            String line = reader.readLine();
            while (line != null) {
                if (line.equals("")) {
                    countEmptyLine++;
                    countForImport++;
                } else if (line.contains("package")) {
                    countForImport++;
                } else if (line.contains("import")) {
                    countForImport++;
                }
                countLine++;
                if (removeStartingSpace(line).length() > 0 && removeStartingSpace(line).charAt(0) == '}') {
                    tab -= 4;
                    isTabEnough(line, tab, countLine);
                    tab += 4;
                } else {
                    isTabEnough(line, tab, countLine);
                }
                tab = findNumberOfTabs(line, tab);
                checkLineLength(line, countLine);
                checkCloseBraces(removeStartingSpace(line), countLine);
                checkAll(line, countLine, countEmptyLine, countForImport);
                checkSemicolon(line, countLine);
                line = reader.readLine();
            }
            if (switchCount != 0) {
                System.out.println("There is no default for your last switch!Please add a default");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkLineLength(String line, int whichLine) {
        if (line.length() > 80) {
            System.out.println("Line " + whichLine + " is more than 80 characters!");
            giveSuggestion(line);
            System.out.println("_____________________________________________________________________");
        }
    }

    public static void giveSuggestion(String line) {
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

    public static String breakLines(String beforeBreaking, int howManySpace) {
        for (int j = beforeBreaking.length() - 1; j >= 0; j--) {
            if (isOkToBreak(beforeBreaking.charAt(j)) == true && j <= 80) {
                String newLine = "";
                while (howManySpace != 0) {
                    newLine += " ";
                    howManySpace--;
                }
                if (beforeBreaking.charAt(j) != ',') {
                    System.out.println("You can break this line from character " + (j - 1) + ":" + beforeBreaking.charAt(j - 1));
                    for (int p = j; p < beforeBreaking.length(); p++) {
                        newLine += beforeBreaking.charAt(p);
                    }
                } else {
                    System.out.println("You can break this line from character " + j + ":" + beforeBreaking.charAt(j));
                    for (int p = j + 1; p < beforeBreaking.length(); p++) {
                        newLine += beforeBreaking.charAt(p);
                    }
                }
                return newLine;
            }
        }
        return "nothing";
    }

    public static boolean isOkToBreak(char character) {
        if (character == '+' || character == '-' || character == '*' || character == '/' || character == '%') {
            return true;
        } else if (character == '^' || character == '&' || character == '|' || character == ',') {
            return true;
        }
        return false;
    }

    public static int findNumberOfTabs(String line, int tab) {
        if (!line.equals("")) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '{') {
                    tab += 4;
                } else if (i + 3 < line.length() && line.substring(i, i + 4).equals("case")) {
                    tab += 4;
                } else if (line.charAt(i) == '}') {
                    tab -= 4;
                } else if (i + 4 < line.length() && line.substring(i, i + 5).equals("break")) {
                    tab -= 4;
                }
            }
        }
        return tab;
    }

    public static void isTabEnough(String line, int tab, int whichLine) {
        int i = 0, count = 0;
        if (!line.equals("")) {
            while (i < line.length() && line.charAt(i) == ' ') {
                count++;
                i++;
            }
            if (count != tab) {
                if (count > tab) {
                    System.out.println("Please remove " + (count - tab) + " space(s) in line " + whichLine);
                } else {
                    System.out.println("Please add " + (tab - count) + " space(s) in line " + whichLine);
                }
                System.out.println("_____________________________________________________________________");
            }
        }
    }

    public static void checkAll(String line, int whichLine, int empty, int forImport) {
        String withoutStartingSpace = removeStartingSpace(line);
        if (line.contains("package")) {
            checkPackage(whichLine, empty);
        } else if (line.contains("import") && forImport != whichLine) {
            writeImportError(whichLine);
        } else if (findVariables(withoutStartingSpace) == true) {
            findAndCheckVariableName(withoutStartingSpace, whichLine);
        } else if (line.contains("public")) {
            checkNames(withoutStartingSpace, whichLine);
        } else if (line.contains("for")) {
            checkForLoop(withoutStartingSpace, whichLine);
            checkForVariable(withoutStartingSpace, whichLine);
        } else if (line.contains("while")) {
            checkWhileLine(withoutStartingSpace, whichLine);
        } else if (withoutStartingSpace.length() > 1 && withoutStartingSpace.substring(0, 2).equals("if")) {
            checkIfLine(withoutStartingSpace, whichLine);
        } else if (line.contains("else") && withoutStartingSpace.charAt(0) != '}') {
            moveElse(whichLine);
            if (line.contains("else if")) {
                checkElseLine(withoutStartingSpace, whichLine, 1);
            } else {
                checkElseLine(withoutStartingSpace, whichLine, 2);
            }
        } else if (line.contains("else") && withoutStartingSpace.charAt(0) == '}') {
            if (line.contains("else if")) {
                checkElseLine(withoutStartingSpace.substring(1, withoutStartingSpace.length()), whichLine, 1);
            } else {
                checkElseLine(withoutStartingSpace.substring(1, withoutStartingSpace.length()), whichLine, 2);
            }
        } else if (line.contains("switch")) {
            switchCount++;
            if (switchCount > 1) {
                printDefaultError(whichLine);
            }
            checkSwitchLine(withoutStartingSpace, whichLine);
        } else if (line.contains("default")) {
            switchCount--;
            findAndCheckSemicolon(withoutStartingSpace, whichLine);
        } else if (line.contains("case")) {
            checkCase(withoutStartingSpace, whichLine);
        } else if (!line.equals("") && !withoutStartingSpace.equals("}")) {
            findAndCheckSemicolon(withoutStartingSpace, whichLine);
        }
    }

    public static void checkSemicolon(String line, int whichLine) {
        String withoutStartingSpace = removeStartingSpace(line);
        if (line.contains("package")) {
            findAndCheckSemicolon(withoutStartingSpace, whichLine);
        } else if (line.contains("import")) {
            findAndCheckSemicolon(withoutStartingSpace, whichLine);
        } else if (findVariables(withoutStartingSpace) == true) {
            findAndCheckSemicolon(withoutStartingSpace, whichLine);
        }
    }

    public static String removeStartingSpace(String line) {
        int count = 0;
        int i = 0;
        while (i < line.length() && line.charAt(i) == ' ') {
            count++;
            i++;
        }
        return line.substring(count);
    }

    public static void findAndCheckSemicolon(String line, int whichLine) {
        moreSemicolon(line, whichLine);
        if (line.charAt(line.length() - 1) != ';') {
            System.out.println("You have to write ; exactly at the end of line " + whichLine);
            System.out.println("_____________________________________________________________________");
        }
    }

    public static void moreSemicolon(String line, int whichLine) {
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

    public static void checkPackage(int whichLine, int emptyLine) {
        if (whichLine != 1 && whichLine - emptyLine == 1) {
            System.out.println("It is better to move your package from line " + whichLine + " into first line");
            System.out.println("_____________________________________________________________________");
        } else if (whichLine != 1 && whichLine - emptyLine != 1) {
            System.out.println("Your package location in line " + whichLine + " is not true!move it to the first line!");
            System.out.println("_____________________________________________________________________");
        }
    }

    public static void writeImportError(int whichLine) {
        System.out.println("Your import location in line " + whichLine + " is not true!");
        System.out.println("_____________________________________________________________________");
    }

    public static boolean findVariables(String line) {
        String[] lineToArray = line.split(" ");
        String firstWord = lineToArray[0];
        NamingStyle toFind = new NamingStyle();
        toFind.setIsVariable(firstWord);
        return toFind.canFindVariable();
    }

    public static void findAndCheckVariableName(String line, int whichLine) {
        String[] lineToArray = line.split(" ");
        String includeName = lineToArray[1];
        String name = "";
        int i = 0;
        while (i < includeName.length() && includeName.charAt(i) != ';' && includeName.charAt(i) != '=') {
            name += includeName.charAt(i);
            i++;
        }
        NamingStyle variableName = new NamingStyle();
        variableName.setVariableName(name);
        if (!variableName.variableNameRegex()) {
            System.out.println("(" + name + ")" + " as a variable name in line " + whichLine + " is wrong!");
            System.out.println("Variable name must be lowerCamelCase and at least two characters");
            System.out.println("_____________________________________________________________________");
        }
    }

    public static void checkNames(String line, int whichLine) {
        String[] lineToArray = line.split(" ");
        if (lineToArray[1].equals("class")) {
            findAndCheckClassName(lineToArray[2], whichLine);
        } else if (lineToArray[2].equals("void") && !lineToArray[3].substring(0, 4).equals("main")) {
            findAndCheckMethodName(lineToArray[3], whichLine);
        } else if (!lineToArray[2].equals("void") && !lineToArray[3].substring(0, 4).equals("main")) {
            findAndCheckMethodName(lineToArray[3], whichLine);
            checkInputsNames(line, whichLine);
        }
    }

    public static void findAndCheckClassName(String includeName, int whichLine) {
        String name = "";
        for (int i = 0; i < includeName.length(); i++) {
            if (includeName.charAt(i) != ' ' && includeName.charAt(i) != '{') {
                name += includeName.charAt(i);
            }
        }
        NamingStyle className = new NamingStyle();
        className.setClassName(name);
        if (!className.classNameRegex()) {
            System.out.println("(" + name + ")" + " as a class name in line " + whichLine + " is wrong!");
            System.out.println("Class name must be UpperCamelCase");
            System.out.println("_____________________________________________________________________");
        }
    }

    public static void findAndCheckMethodName(String includeName, int whichLine) {
        String name = "";
        int i = 0;
        while (i < includeName.length() && includeName.charAt(i) != '(') {
            name += includeName.charAt(i);
            i++;
        }
        NamingStyle methodName = new NamingStyle();
        methodName.setMethodName(name);
        if (!methodName.methodNameRegex()) {
            System.out.println("(" + name + ")" + " as a method name in line " + whichLine + " is wrong!");
            System.out.println("Method name must be lowerCamelCase");
            System.out.println("_____________________________________________________________________");
        }
    }

    public static void checkInputsNames(String line, int whichLine) {
        String inputs = findInputs(line);
        String[] separateInputs = inputs.split(",");
        NamingStyle input = new NamingStyle();
        for (int i = 0; i < separateInputs.length; i++) {
            String name = findName(separateInputs[i]);
            input.setVariableName(name);
            if (!input.variableNameRegex()) {
                System.out.println("(" + name + ")" + " as a variable name in line " + whichLine + " is wrong!");
                System.out.println("Variable name must be lowerCamelCase and at least two characters");
                System.out.println("_____________________________________________________________________");
            }
        }
    }

    public static String findInputs(String line) {
        String input = "";
        int i = 0;
        while (line.charAt(i) != '(') {
            i++;
        }
        i++;
        while (line.charAt(i) != ')') {
            input += line.charAt(i);
            i++;
        }
        return input;
    }

    public static String findName(String input) {
        input = input.trim();
        String[] find = input.split(" ");
        if (find.length == 1) {
            find = find[0].split("]");
            if (find.length == 3) {
                return find[2];
            }
        }
        return find[1];
    }

    public static void checkForLoop(String forLine, int whichLine) {
        ForWhileIf line = new ForWhileIf();
        line.setForLine(forLine);
        if (!line.forRegex()) {
            System.out.println("The location of " + line.findForProblem() + "in the for loop in line " + whichLine
                    + " is incorrect! You have to write for loop exactly in this form: for(some characters){");
            System.out.println("_____________________________________________________________________");
        }
    }

    public static String isThereForVariable(String line) {
        NamingStyle toFind = new NamingStyle();
        for (int i = 3; i < 9; i++) {
            for (int j = 0; j + i < line.length(); j++) {
                toFind.setIsVariable(line.substring(j, j + i));
                if (toFind.canFindVariable()) {
                    String variable = "";
                    while (line.charAt(j + i + 1) != '=' && line.charAt(j + i + 1) != ' ') {
                        variable += line.charAt(j + i + 1);
                        i++;
                    }
                    return variable;
                }
            }
        }
        return "cant find";
    }

    public static void checkForVariable(String line, int whichLine) {
        if (!isThereForVariable(line).equals("cant find")) {
            ForWhileIf toForVariable = new ForWhileIf();
            toForVariable.setForVariable(isThereForVariable(line));
            if (toForVariable.forVariableRegex() == false) {
                System.out.println("(" + isThereForVariable(line) + ")" + " as a for variable name in line "
                        + whichLine + " is wrong! For variable name must be lowerCamelCase");
                System.out.println("_____________________________________________________________________");
            }
        }
    }

    public static void checkWhileLine(String whileLine, int whichLine) {
        ForWhileIf line = new ForWhileIf();
        line.setWhileLine(whileLine);
        if (!line.whileRegex()) {
            System.out.println("The location of " + line.findWhileProblem() + "in the while loop in line " + whichLine
                    + " is incorrect! You have to write while loop exactly in this form: while(some characters){");
            System.out.println("_____________________________________________________________________");
        }
    }

    public static void checkIfLine(String ifLine, int whichLine) {
        ForWhileIf line = new ForWhileIf();
        line.setIfLine(ifLine);
        if (!line.ifRegex()) {
            System.out.println("The location of " + line.findIfProblem() + "in the if condition in line " + whichLine
                    + " is incorrect! You have to write if condition exactly in this form: if(some characters){");
            System.out.println("_____________________________________________________________________");
        }
    }

    public static void checkElseLine(String elseLine, int whichLine, int elseOrElseIf) {
        ForWhileIf line = new ForWhileIf();
        line.setElseLine(elseLine);
        if (!line.elseRegex()) {
            System.out.println("The location of " + line.findElseProblem(elseOrElseIf) + "in the else condition in line "
                    + whichLine + " is incorrect!");
            if (elseOrElseIf == 1) {
                System.out.println("You have to write else if condition exactly in this form:else if(some characters){");
            } else if (elseOrElseIf == 2) {
                System.out.println("You have to write else condition exactly in this form: else{");
            }
            System.out.println("_____________________________________________________________________");
        }
    }


    public static void moveElse(int whichLine) {
        System.out.println("Move your else statement in line " + whichLine + " exactly after the } of previous statement");
        System.out.println("_____________________________________________________________________");
    }


    public static void checkSwitchLine(String switchLine, int whichLine) {
        ForWhileIf line = new ForWhileIf();
        line.setSwitchLine(switchLine);
        if (!line.switchRegex()) {
            System.out.println("The location of " + line.findSwitchProblem() + "in switch in line " + whichLine
                    + " is incorrect! You have to write switch exactly in this form: switch(some characters){");
            System.out.println("_____________________________________________________________________");
        }
    }

    public static void checkCase(String caseLine, int whichLine) {
        if (caseLine.charAt(caseLine.length() - 1) != ':') {
            System.out.println("You have to write : exactly at the end of line " + whichLine);
            System.out.println("_____________________________________________________________________");
        }
    }

    public static void printDefaultError(int whichLine) {
        System.out.println("There is no default for your previous switch in " + whichLine + "!Please add a default");
        System.out.println("_____________________________________________________________________");
    }

    public static void checkCloseBraces(String line, int whichLine) {
        if (line.length() > 1 && line.charAt(0) == '}' && !line.contains("else")) {
            System.out.println("You have to move your commands after } in line " + whichLine + " into next line");
            System.out.println("_____________________________________________________________________");
        }
        if (line.contains("}") && whichChar(line, '}') != 0) {
            System.out.println("Please move } in line " + whichLine + " into next line");
            System.out.println("_____________________________________________________________________");
        }
    }

    public static int whichChar(String line, char character) {
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == character) {
                return i;
            }
        }
        return -1;
    }
}