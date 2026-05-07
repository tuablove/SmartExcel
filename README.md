# SmartExcel

`smartExcel-core` 是一个基于 Apache POI 的 Excel 读写组件，支持注解驱动映射、样式扩展、动态列、模板写入、子对象展开、表达式表头、校验回填等能力。

---

## smartExcel-core 功能清单

## 读取能力（Excel -> 对象 / Map）

- 支持读取为对象：`ExcelReader<T>#read(...)`
- 支持读取为 `List<Map<String,Object>>`：`ExcelReader<T>#readToMap(...)`
- 支持指定 sheet（索引 / 名称）、标题行、数据起始行
- 支持读取密码（加密文件）
- 支持按注解分组读取（groups）
- 支持字段读取转换器 `IReadValueConverter`
- 支持行忽略回调 `IReadRowIgnoreHandler`
- 支持逐行回调 `IReadRowHandler`
- 支持 Bean Validation 校验（含 groups）
- 支持校验信息回填到实体字段（`@ExcelImportValidateMessage`）

关键注解：

- `@ExcelImport`：列映射（标题名 / 列字母）、读取转换器、分组
- `@ExcelImportValidateMessage`：接收校验错误信息

---

## 写入能力（对象 -> Excel）

- 基础写入：`ExcelWriter<T>#write(...)`
- 支持多行表头（`@ExcelExport(titleNames=...)`）
- 支持按字段顺序写出（order / 处理器）
- 支持字段包含与排除（include / exclude）
- 支持写入转换器 `IWriteValueConverter`
- 支持数据单元格样式处理器 `IWriteDataCellStyleHandler`
- 支持全局样式、标题样式、数据初始化样式处理器
- 支持表头表达式（JEXL，`IWriteTitleExpressionHandler`）
- 支持自动列宽（含最小宽度）
- 支持纵向合并（`ExcelMergeTypeEnum.VERTICAL_ALL`）
- 支持冻结表头行
- 支持按 Excel 行上限自动分页到多 sheet
- 支持写入密码保护

关键注解：

- `@ExcelExport`：导出列、标题、顺序、忽略、分组、写转换器
- `@ExcelStyle`：数据格式、样式处理、自动列宽、最小宽度、合并策略

---

## 动态列与子对象能力

- 支持 `WriteDataBase` + `DynamicColumn` 动态列写入
- 支持子对象集合 `writeDateChildren` 展开成并列列组
- 支持 `maxChildrenCount` 控制子对象最大展开数
- 支持子对象标题表达式（如按第 N 组动态命名）
- 支持 child 标题分组着色（见下）

---

## 模板写入能力

- `ExcelWriteTemplate`：JXLS 模板渲染（`jx:area`）
- `ExcelWriterTemplateSimple`：模板文件按起始行批量填充
- `ExcelDynamicTemplateWriter`：按模板标题动态匹配列写入

相关配置：

- `ExcelWriteTemplateConfig`
- `ExcelWriteDynamicTemplateConfig`

---

## Child 标题分色（兼容方案）

在包含子对象表头时，可按 child 下标给标题分组着色。

可用方式：

- 配置回调：`ExcelWriteConfig#setChildTitleCellStyleHandler(...)`
- 或使用标题样式 key 约定

样式命中顺序：

1. `childTitleCellStyleHandler` 返回样式
2. `childTitle_{N}:标题名`
3. `childTitle_{N}`
4. `标题名`（既有标题样式）
5. 默认标题样式

其中 `N` 为 1-based 下标（第一组 child 为 `childTitle_1`）。

---

## 主要配置类

- 读取：
  - `ExcelReaderConfig`
  - `ExcelReaderSheetConfig`
  - `ExcelReaderWriteConfig`
- 写入：
  - `ExcelWriteBaseConfig`
  - `ExcelWriteFieldConfig`
  - `ExcelWriteConfig`
  - `ExcelWriteTemplateConfig`
  - `ExcelWriteDynamicTemplateConfig`

---

## 已有示例（smartExcel-demo）

- 读取对象：`ReadTest`
- 读取为 Map：`ReadToMapTest`
- 分组读取：`ReadTestWithGroups`
- 普通写入：`WriteCommonTest`
- 动态列写入：`WriteDynamicColumnTest`
- 子对象写入：`WriteWithChildrenTest`
- 动态列 + 子对象：`WriteDynamicColumnWithChildrenTest`
- 模板写入：`WriterTemplateTest`
- 简单模板写入：`WriterTemplateSimpleTest`
- 动态模板写入：`WriterDynamicTemplateTest`

---

## 注意事项

- 读取对象依赖无参构造（通过反射实例化）
- 普通写入的多 sheet 为自动分页机制（按类型最大行数）
- 模板简单写入 `ExcelWriterTemplateSimple` 默认操作第一个 sheet
- 合并策略当前主要是纵向整列合并（`VERTICAL_ALL`）