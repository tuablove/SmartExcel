# SmartExcel
支持excel读取对象，导入转对象，对象导出excel,支持列别名，模板填入等功能

### 目前支持
#### 读取
* excel 快速读取到对象
* 指定读取开始行

#### 写入

* excel 快速写入
  * 支持多行表头写入
  * 

### Child 标题分色（兼容方案）
在包含子对象表头时，可按 child 下标给标题分组着色。

样式命中顺序：
1. `ExcelWriteConfig#setChildTitleCellStyleHandler(...)` 返回的样式
2. 标题样式 key: `childTitle_{N}:标题名`
3. 标题样式 key: `childTitle_{N}`
4. 标题样式 key: `标题名`
5. 默认标题样式

其中 `N` 为 1-based 下标（第一组 child 为 `childTitle_1`）。