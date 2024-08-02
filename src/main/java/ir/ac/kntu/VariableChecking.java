package ir.ac.kntu;

public class VariableChecking {

    private int indexOfVariable;

    private String variableName;

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public boolean canFindVariable(String line) {
        String[] isVariable = line.split(" ");
        for (int i = 0; i < isVariable.length; i++) {
            indexOfVariable = i + 1;
            if (isVariable[i].length() > 1 && isVariable[i].charAt(0) == '(') {
                isVariable[i] = isVariable[i].substring(1);
            }
            if (isVariable[i].equals("int") || isVariable[i].equals("int[]")) {
                return true;
            } else if (isVariable[i].equals("float") || isVariable[i].equals("float[]")) {
                return true;
            } else if (isVariable[i].equals("double") || isVariable[i].equals("double[]")) {
                return true;
            } else if (isVariable[i].equals("String") || isVariable[i].equals("String[]")) {
                return true;
            } else if (isVariable[i].equals("boolean") || isVariable[i].equals("boolean[]")) {
                return true;
            } else if (isVariable[i].equals("char") || isVariable[i].equals("char[]")) {
                return true;
            } else if (isVariable[i].equals("long") || isVariable[i].equals("long[]")) {
                return true;
            } else if (isVariable[i].equals("byte") || isVariable[i].equals("byte[]")) {
                return true;
            }
        }
        return false;
    }


    public void findAndCheckVariableName(String line, int whichLine) {
        String[] lineToArray = line.split(" ");
        String includeName = lineToArray[indexOfVariable];
        String name = "";
        int i = 0;
        while (i < includeName.length() && includeName.charAt(i) != ';' && includeName.charAt(i) != '=') {
            name += includeName.charAt(i);
            i++;
        }
        this.variableName = name;
        if (line.contains("for")) {
            if (!forVariableRegex()) {
                System.out.println("(" + name + ")" + " as a for variable name in line "
                        + whichLine + " is wrong!");
                System.out.println("For variable name must be lowerCamelCase");
                System.out.println("_____________________________________________________________________");
            }
        } else {
            if (!variableNameRegex()) {
                System.out.println("(" + name + ")" + " as a variable name in line " + whichLine + " is wrong!");
                System.out.println("Variable name must be lowerCamelCase and at least two characters");
                System.out.println("_____________________________________________________________________");
            }
        }
    }


    public boolean variableNameRegex() {
        return this.variableName.matches("([a-z]{2,})|([a-z]+[A-Z]{1}[a-z]*)|" +
                "([a-z]+[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*)|([a-z]+[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*)");
    }

    public boolean forVariableRegex() {
        return this.variableName.matches("(([a-z]+)|[a-z]+[A-Z]{1}[a-z]*)|" +
                "([a-z]+[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*)|([a-z]+[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*)");
    }

}