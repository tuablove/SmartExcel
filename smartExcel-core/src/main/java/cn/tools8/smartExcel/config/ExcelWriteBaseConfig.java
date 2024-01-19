package cn.tools8.smartExcel.config;

/**
 * 写入excel配置
 *
 * @author tuaobin 2023/6/15 10:41
 */
public class ExcelWriteBaseConfig{
    /**
     * 密码
     */
    private String password;
    /**
     * 输出文件地址
     */
    private String filePath;

    /**
     * 分组
     * @return
     */
    private Class<?>[] groups;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Class<?>[] getGroups() {
        return groups;
    }

    public void setGroups(Class<?>[] groups) {
        this.groups = groups;
    }
}
