package inputValidator;

public class ReturnValues {
    final private double returningNumber;
    final private boolean returningBoolean;

    public ReturnValues(double returningNumber, boolean returningBoolean){
        this.returningNumber = returningNumber;
        this.returningBoolean = returningBoolean;
    }

    public double getReturningNumber() {
        return returningNumber;
    }

    public boolean getReturningBoolean() {
        return returningBoolean;
    }
}