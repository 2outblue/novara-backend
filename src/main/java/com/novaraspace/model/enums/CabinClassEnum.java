package com.novaraspace.model.enums;

public enum CabinClassEnum {
    FIRST("A Class", 0, 0),
    MIDDLE("B Class", 0, 6300),
    LOWER("C Class", 3640, 7350);

    //TODO: FEES SHOULD ALWAYS BE THE SAME IN THE FRONTEND
    // Build some endpoint so the frontend can get the fee data from here or some
    // JSON env file which will also be used through some @Component to get the fees
    // in the backend as well instead of having them directly in the enum like that
    private final String displayName;
    private final double changeFee;
    private final double refundFee;

    CabinClassEnum(String displayName, double changeFee, double refundFee) {
        this.displayName = displayName;
        this.changeFee = changeFee;
        this.refundFee = refundFee;
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
