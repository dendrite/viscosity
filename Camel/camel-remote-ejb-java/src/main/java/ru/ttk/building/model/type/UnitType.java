package ru.ttk.building.model.type;

/**
 *
 */
public enum UnitType {

    FLAT("FLAT", 0),
    OFFICE("OFFICE", 1);

    private String name;
    private int code;

    UnitType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static UnitType get(int code) {
        switch (code) {
            case 0:
                return FLAT;
            case 1:
                return OFFICE;
            default:
                return FLAT;
        }
    }

    public static int get(UnitType unitType) {
        if (unitType != null) {
            return unitType.getCode();
        }
        return -1;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
