package ru.ttk.building.model.type;

/**
 *
 */
public enum InspectionStatus {

    PREPARING("PREPARING", 0),
    ASSIGNED("ASSIGNED", 1),
    DONE("DONE", 2);

    private String name;
    private int code;

    InspectionStatus(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static InspectionStatus get(int code) {
        switch (code) {
            case 0:
                return PREPARING;
            case 1:
                return ASSIGNED;
            default:
                return DONE;
        }
    }

    public static int get(InspectionStatus inspectionStatus) {
        if (inspectionStatus != null) {
            return inspectionStatus.getCode();
        }
        return -1;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
