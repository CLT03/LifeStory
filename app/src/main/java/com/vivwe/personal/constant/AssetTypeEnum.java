package com.vivwe.personal.constant;

/**
 * ahtor: super_link
 * date: 2019/6/10 15:21
 * remark:
 */
public enum AssetTypeEnum {

    IMAGE("图片", 1), GIF("gif", 2), OTHER("其他", 3);

    // 成员变量
    private String value;
    private int index;

    private AssetTypeEnum(String value, int index) {
        this.value = value;
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }
}
