package com.sunjh.xiyiji.data.xuyujie.enums;

/**
 * @author sunjh
 * @version 1.0
 * @date 2021/10/30 10:36 下午
 */
public enum XuyujieFileTypeEnum {

    DURATION("DURATION", "duration", "duration"),
    MEAN_F0("MEAN_F0", "meanf0", "mean_f0"),
    F0_ACCELERATION("F0_ACCELERATION", "f0acceleration", "f0acceration"),
    EXCURSION_SIZE("EXCURSION_SIZE", "excursionsize", "excursionsize");

    public final String code;
    public final String value;
    public final String description;

    private XuyujieFileTypeEnum(String code, String value, String description) {
        this.code = code;
        this.value = value;
        this.description = description;
    }

    public static XuyujieFileTypeEnum getXuyujieFileTypeEnumByCode(String code) {
        for (XuyujieFileTypeEnum xuyujieFileTypeEnum : XuyujieFileTypeEnum.values()) {
            if (xuyujieFileTypeEnum.code.equals(code)) {
                return xuyujieFileTypeEnum;
            }
        }
        return null;
    }

    public static XuyujieFileTypeEnum getXuyujieFileTypeEnumByValue(String value) {
        for (XuyujieFileTypeEnum xuyujieFileTypeEnum : XuyujieFileTypeEnum.values()) {
            if (xuyujieFileTypeEnum.value.equals(value)) {
                return xuyujieFileTypeEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
