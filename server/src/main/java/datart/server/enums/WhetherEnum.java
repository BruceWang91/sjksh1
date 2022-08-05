package datart.server.enums;

/**
 * 是否枚举
 */
public enum WhetherEnum {

    NO(0),
    YES(1);

    private Integer value;

    WhetherEnum(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
