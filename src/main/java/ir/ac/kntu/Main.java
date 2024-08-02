package ir.ac.kntu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static int switchCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the file you want to check : ");
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
                LengthChecking lengthChecking = new LengthChecking();
                lengthChecking.checkLineLength(line, countLine);
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
        withoutStartingSpace = withoutStartingSpace.replaceAll("\\s+", " ");
        VariableChecking variableChecking = new VariableChecking();
        ElseIfChecking elseIfChecking = new ElseIfChecking();
        ForChecking forChecking = new ForChecking();
        SwitchCaseChecking switchCaseChecking = new SwitchCaseChecking();
        WhileChecking whileChecking = new WhileChecking();
        if (line.contains("package")) {
            checkPackage(whichLine, empty);
        } else if (line.contains("import") && forImport != whichLine) {
            writeImportError(whichLine);
        } else if (variableChecking.canFindVariable(withoutStartingSpace) == true) {
            variableChecking.findAndCheckVariableName(withoutStartingSpace, whichLine);
        } else if (line.contains("public")) {
            checkNames(withoutStartingSpace, whichLine);
        } else if (line.contains("for")) {
            forChecking.checkForLoop(withoutStartingSpace, whichLine);
        } else if (line.contains("while")) {
            whileChecking.checkWhileLine(withoutStartingSpace, whichLine);
        } else if (withoutStartingSpace.length() > 1 && withoutStartingSpace.substring(0, 2).equals("if")) {
            elseIfChecking.checkIfLine(withoutStartingSpace, whichLine);
        } else if (line.contains("else") && withoutStartingSpace.charAt(0) != '}') {
            elseIfChecking.moveElse(whichLine);
            if (line.contains("else if")) {
                elseIfChecking.checkElseLine(withoutStartingSpace, whichLine, 1);
            } else {
                elseIfChecking.checkElseLine(withoutStartingSpace, whichLine, 2);
            }
        } else if (line.contains("else") && withoutStartingSpace.charAt(0) == '}') {
            if (line.contains("else if")) {
                elseIfChecking.checkElseLine(withoutStartingSpace.substring(1, withoutStartingSpace.length()), whichLine, 1);
            } else {
                elseIfChecking.checkElseLine(withoutStartingSpace.substring(1, withoutStartingSpace.length()), whichLine, 2);
            }
        } else if (line.contains("switch")) {
            switchCount++;
            if (switchCount > 1) {
                switchCaseChecking.printDefaultError(whichLine);
            }
            switchCaseChecking.checkSwitchLine(withoutStartingSpace, whichLine);
        } else if (line.contains("default")) {
            switchCount--;
            findAndCheckSemicolon(withoutStartingSpace, whichLine);
        } else if (line.contains("case")) {
            switchCaseChecking.checkCase(withoutStartingSpace, whichLine);
        } else if (!line.equals("") && !withoutStartingSpace.equals("}")) {
            findAndCheckSemicolon(withoutStartingSpace, whichLine);
        }
    }

    public static void checkSemicolon(String line, int whichLine) {
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


    public static void checkNames(String line, int whichLine) {
        String[] lineToArray = line.split(" ");
        if (lineToArray[1].equals("class")) {
            findAndCheckClassName(lineToArray[2], whichLine);
        } else if (lineToArray[2].equals("void") && !lineToArray[3].substring(0, 4).equals("main")) {
            findAndCheckMethodName(lineToArray[3], whichLine);
            checkInputsNames(line, whichLine);
        } else if (!lineToArray[3].substring(0, 4).equals("main")) {
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
        VariableChecking input = new VariableChecking();
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