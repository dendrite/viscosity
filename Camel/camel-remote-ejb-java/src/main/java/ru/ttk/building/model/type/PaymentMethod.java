package ru.ttk.building.model.type;

/**
 *
 *
 */
public enum PaymentMethod {

    CREDIT_PAYMENT("CREDIT_PAYMENT", 0),
    ADVANCE_PAYMENT("ADVANCE_PAYMENT", 1);

    private String name;
    private int code;

    PaymentMethod(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static PaymentMethod get(int code) {
        switch (code) {
            case 0:
                return CREDIT_PAYMENT;
            case 1:
                return ADVANCE_PAYMENT;
            default:
                return CREDIT_PAYMENT;
        }
    }

    public static int get(PaymentMethod paymentMethod) {
        if (paymentMethod != null) {
            return paymentMethod.getCode();
        }
        return -1;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
