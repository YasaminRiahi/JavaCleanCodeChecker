package ir.ac.kntu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static int switchCount = 0;

    public static void main(String[] args) {
        TabChecking tabChecking = new TabChecking();
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
                    tabChecking.isTabEnough(line, tab, countLine);
                    tab += 4;
                } else {
                    tabChecking.isTabEnough(line, tab, countLine);
                }
                tab = tabChecking.findNumberOfTabs(line, tab);
                LengthChecking lengthChecking = new LengthChecking();
                lengthChecking.checkLineLength(line, countLine);
                checkCloseBraces(removeStartingSpace(line), countLine);
                checkAll(line, countLine, countEmptyLine, countForImport);
                SemicolonChecking semicolonChecking = new SemicolonChecking();
                semicolonChecking.checkSemicolon(line, countLine);
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

    public static void checkAll(String line, int whichLine, int empty, int forImport) {
        String withoutStartingSpace = removeStartingSpace(line);
        withoutStartingSpace = withoutStartingSpace.replaceAll("\\s+", " ");
        VariableChecking variableChecking = new VariableChecking();
        ElseIfChecking elseIfChecking = new ElseIfChecking();
        ForChecking forChecking = new ForChecking();
        SwitchCaseChecking switchCaseChecking = new SwitchCaseChecking();
        WhileChecking whileChecking = new WhileChecking();
        NameChecking nameChecking = new NameChecking();
        SemicolonChecking semicolonChecking = new SemicolonChecking();
        if (line.contains("package")) {
            checkPackage(whichLine, empty);
        } else if (line.contains("import") && forImport != whichLine) {
            writeImportError(whichLine);
        } else if (line.contains("public")) {
            nameChecking.checkNames(withoutStartingSpace, whichLine);
        } else if (variableChecking.canFindVariable(withoutStartingSpace)) {
            variableChecking.findAndCheckVariableName(withoutStartingSpace, whichLine);
        } else if (line.contains("for")) {
            forChecking.checkForLoop(withoutStartingSpace, whichLine);
        } else if (line.contains("while")) {
            whileChecking.checkWhileLine(withoutStartingSpace, whichLine);
        } else if (withoutStartingSpace.startsWith("if")) {
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
                elseIfChecking.checkElseLine(withoutStartingSpace.substring(1), whichLine, 1);
            } else {
                elseIfChecking.checkElseLine(withoutStartingSpace.substring(1), whichLine, 2);
            }
        } else if (line.contains("switch")) {
            switchCount++;
            if (switchCount > 1) {
                switchCaseChecking.printDefaultError(whichLine);
            }
            switchCaseChecking.checkSwitchLine(withoutStartingSpace, whichLine);
        } else if (line.contains("default")) {
            switchCount--;
            semicolonChecking.findAndCheckSemicolon(withoutStartingSpace, whichLine);
        } else if (line.contains("case")) {
            switchCaseChecking.checkCase(withoutStartingSpace, whichLine);
        } else if (!line.equals("") && !withoutStartingSpace.equals("}")) {
            semicolonChecking.findAndCheckSemicolon(withoutStartingSpace, whichLine);
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