package ir.ac.kntu;

public class NamingStyle {
    private String className;
    private String methodName;
    private String variableName;

    private String isVariable;

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public void setIsVariable(String isVariable) {
        this.isVariable = isVariable;
    }

    public boolean classNameRegex() {
        return this.className.matches("([A-Z][a-z]+)|([A-Z][a-z]*[A-Z][a-z]*)|" +
                "([A-Z][a-z]*[A-Z][a-z]*[A-Z][a-z]*)");
    }

    public boolean methodNameRegex() {
        return this.methodName.matches("([a-z]{2,})|([a-z]+[A-Z]{1}[a-z]*)|" +
                "([a-z]+[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*)|([a-z]+[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*)");
    }

    public boolean variableNameRegex() {
        return this.variableName.matches("([a-z]{2,})|([a-z]+[A-Z]{1}[a-z]*)|" +
                "([a-z]+[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*)|([a-z]+[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*[A-Z]{1}[a-z]*)");
    }

    public boolean canFindVariable() {
        if (isVariable.equals("int") || isVariable.equals("int[]")) {
            return true;
        } else if (isVariable.equals("float") || isVariable.equals("float[]")) {
            return true;
        } else if (isVariable.equals("double") || isVariable.equals("double[]")) {
            return true;
        } else if (isVariable.equals("String") || isVariable.equals("String[]")) {
            return true;
        } else if (isVariable.equals("boolean") || isVariable.equals("boolean[]")) {
            return true;
        } else if (isVariable.equals("char") || isVariable.equals("char[]")) {
            return true;
        } else if (isVariable.equals("long") || isVariable.equals("long[]")) {
            return true;
        } else if (isVariable.equals("byte") || isVariable.equals("byte[]")) {
            return true;
        }
        return false;
    }
}
