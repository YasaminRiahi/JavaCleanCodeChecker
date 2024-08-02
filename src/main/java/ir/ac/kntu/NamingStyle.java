package ir.ac.kntu;

public class NamingStyle {
    private String className;

    private String methodName;

    private String variableName;


    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
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
