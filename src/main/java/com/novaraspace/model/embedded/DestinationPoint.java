package com.novaraspace.model.embedded;

import jakarta.persistence.Embeddable;

@Embeddable
public class DestinationPoint {
    private int angle;
    private String textTop;
    private String textBottom;
    private String pointId;

    public int getAngle() {
        return angle;
    }

    public DestinationPoint setAngle(int angle) {
        this.angle = angle;
        return this;
    }

    public String getTextTop() {
        return textTop;
    }

    public DestinationPoint setTextTop(String textTop) {
        this.textTop = textTop;
        return this;
    }

    public String getTextBottom() {
        return textBottom;
    }

    public DestinationPoint setTextBottom(String textBottom) {
        this.textBottom = textBottom;
        return this;
    }

    public String getPointId() {
        return pointId;
    }

    public DestinationPoint setPointId(String pointId) {
        this.pointId = pointId;
        return this;
    }
}
