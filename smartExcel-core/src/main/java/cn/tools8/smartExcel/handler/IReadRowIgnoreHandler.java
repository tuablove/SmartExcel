package cn.tools8.smartExcel.handler;

/**
 * 数据行是否忽略
 * @author tuaobin 2023/6/16$ 16:23$
 */
public interface IReadRowIgnoreHandler {
    /**
     * 是否忽略
     * @param sheetIndex sheet的索引
     * @param row 行的索引
     * @param rowData 行的数据<T>
     * @return
     */
    boolean ignore(Integer sheetIndex,Integer row, Object rowData);
}
