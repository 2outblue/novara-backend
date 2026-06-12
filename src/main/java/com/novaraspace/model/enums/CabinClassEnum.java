package com.novaraspace.model.enums;

public enum CabinClassEnum {
    FIRST("First Class", 0, 0),
    MIDDLE("Premium Class", 0, 6300),
    LOWER("Explorer Class", 3640, 7350);

    //TODO: FEES SHOULD ALWAYS BE THE SAME IN THE FRONTEND
    // Move the fees here in the BE
    private final String displayName;
    private final double changeFee;
    private final double refundFee;

    CabinClassEnum(String displayName, double changeFee, double refundFee) {
        this.displayName = displayName;
        this.changeFee = changeFee;
        this.refundFee = refundFee;
    }

    public static CabinClassEnum getEnumFromDisplayName(String displayName) {
        if (displayName.equals(CabinClassEnum.FIRST.getDisplayName())) {
            return CabinClassEnum.FIRST;
        } else if (displayName.equals(CabinClassEnum.MIDDLE.getDisplayName())) {
            return CabinClassEnum.MIDDLE;
        } else if (displayName.equals(CabinClassEnum.LOWER.getDisplayName())) {
            return CabinClassEnum.LOWER;
        } else {
            return null;
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getChangeFee() {
        return changeFee;
    }

    public double getRefundFee() {
        return refundFee;
    }
}
