package cn.tools8.smartExcel.enums;

/**
 * 通用样式类型
 * @author tuaobin 2023/6/20$ 17:49$
 */
public enum GenericStyleTypeEnum {
    TITLE("title","标题"),
    CONTENT("content","正文"),
    ;
    private String type;
    private String description;

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    GenericStyleTypeEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }
}
