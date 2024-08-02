package ir.ac.kntu;

public class NameChecking {
    private String className;

    private String methodName;


    public void checkNames(String line, int whichLine) {
        line = line.trim();
        String[] lineToArray = line.split(" ");
        if (lineToArray[1].equals("class")) {
            findAndCheckClassName(lineToArray[2], whichLine);
        } else {
            findAndCheckMethodName(lineToArray[3], whichLine);
            checkInputsNames(line, whichLine);
        }
    }

    public void findAndCheckClassName(String includeName, int whichLine) {
        String name = "";
        for (int i = 0; i < includeName.length(); i++) {
            if (includeName.charAt(i) != ' ' && includeName.charAt(i) != '{') {
                name += includeName.charAt(i);
            }
        }
        this.className = name;
        if (!classNameRegex()) {
            System.out.println("(" + name + ")" + " as a class name in line " + whichLine + " is wrong!");
            System.out.println("Class name must be UpperCamelCase");
            System.out.println("_____________________________________________________________________");
        }
    }

    public void findAndCheckMethodName(String includeName, int whichLine) {
        String name = "";
        int i = 0;
        while (i < includeName.length() && includeName.charAt(i) != '(') {
            name += includeName.charAt(i);
            i++;
        }
        this.methodName = name;
        if (!methodNameRegex()) {
            System.out.println("(" + name + ")" + " as a method name in line " + whichLine + " is wrong!");
            System.out.println("Method name must be lowerCamelCase");
            System.out.println("_____________________________________________________________________");
        }
    }

    public void checkInputsNames(String line, int whichLine) {
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

    public String findInputs(String line) {
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

    public String findName(String input) {
        input = input.trim();
        String[] find = input.split(" ");
        return find[1];
    }

    public boolean classNameRegex() {
        return this.className.matches("([A-Z][a-z]+)|([A-Z][a-z]*[A-Z][a-z]*)|" +
                "([A-Z][a-z]*[A-Z][a-z]*[A-Z][a-z]*)");
    }

    public boolean methodNameRegex() {
        return this.methodName.matches("([a-z]{2,})|([a-z]+[A-Z]{1}[a-z]*)|" +
                "([a-z]+[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*)|([a-z]+[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*)");
    }
}
