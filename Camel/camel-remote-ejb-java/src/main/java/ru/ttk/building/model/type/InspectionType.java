package ru.ttk.building.model.type;

/**
 *
 */
public enum InspectionType {

    INSPECTION_ABSENT("INSPECTION_ABSENT", 0),                                  // Нет дома
    INSPECTION_DOOR_IS_NOT_OPENED("INSPECTION_DOOR_IS_NOT_OPENED", 1),          // Не открыли дверь
    INSPECTION_WILL_THINK("INSPECTION_WILL_THINK", 2),                          // Подумает. Прийти/позвонить позже
    INSPECTION_CONTRACT_EXIST("INSPECTION_CONTRACT_EXIST", 3),                  // Заявка/Договор
    INSPECTION_TTK_CLIENT("INSPECTION_TTK_CLIENT", 4),                          // Абонент ТТК
    INSPECTION_REJECTED("INSPECTION_REJECTED", 5);                              // Отказ

    private String name;
    private int code;

    InspectionType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static InspectionType get(int code) {
        switch (code) {
            case 0:
                return INSPECTION_ABSENT;
            case 1:
                return INSPECTION_DOOR_IS_NOT_OPENED;
            case 2:
                return INSPECTION_WILL_THINK;
            case 3:
                return INSPECTION_CONTRACT_EXIST;
            case 4:
                return INSPECTION_TTK_CLIENT;
            case 5:
                return INSPECTION_REJECTED;
            default:
                return INSPECTION_ABSENT;
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
