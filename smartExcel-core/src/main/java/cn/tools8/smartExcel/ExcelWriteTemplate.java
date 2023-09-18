package cn.tools8.smartExcel;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.transform.poi.WritableCellValue;
import org.jxls.transform.poi.WritableHyperlink;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jxls写入模板
 * @author tuaobin 2023/9/18$ 13:52$
 */
public class ExcelWriteTemplate {
    public static Logger logger = LoggerFactory.getLogger(ExcelWriteTemplate.class);

    private static ExcelWriteTemplate instance;

    public static ExcelWriteTemplate getInstance() {
        if (instance == null) {
            synchronized (ExcelWriteTemplate.class) {
                if (instance == null) {
                    instance = new ExcelWriteTemplate();
                }
            }
        }
        return instance;
    }
    /**
     * 导出excel
     *
     * @param template   模板文件
     * @param targetFile 目标文件
     * @param model      jxls表达式数据
     * @Date 2020-10-28 11:54
     */
    public void exportExcel(String template, String targetFile, Map<String, Object> model) throws Exception {
        exportExcel(new File(template),new File(targetFile), model);
    }
    /**
     * 导出excel
     *
     * @param template   模板文件
     * @param targetFile 目标文件
     * @param model      jxls表达式数据
     * @Date 2020-10-28 11:54
     */
    public void exportExcel(File template, File targetFile, Map<String, Object> model) throws Exception {
        exportExcel(template, targetFile, model, buildFuncs());
    }

    /**
     * 导出excel
     *
     * @param template   模板文件
     * @param targetFile 目标文件
     * @param t          jxls表达式数据
     * @Date 2020-10-28 11:54
     */
    public <T> void exportExcel(File template, File targetFile, T... t) throws Exception {
        exportExcel(template, targetFile, buildFuncs(), t);
    }


    /**
     * 导出excel
     *
     * @param template   模板文件
     * @param targetFile 目标文件
     * @param t          jxls表达式数据
     * @param funcs      自定义函数增强
     * @Date 2020-10-28 11:54
     */
    public <T> void exportExcel(File template, File targetFile, Map<String, Object> funcs, T... t) throws Exception, IllegalAccessException {
        try (FileInputStream fileInputStream = new FileInputStream(template);
             FileOutputStream fileOutputStream = new FileOutputStream(targetFile);) {

            exportExcel(fileInputStream, fileOutputStream, buildContext(t), buildFuncs(funcs));
        }
    }


    /**
     * 导出excel
     *
     * @param template   模板文件
     * @param targetFile 目标文件
     * @param model      jxls表达式数据
     * @param funcs      自定义函数增强
     * @Date 2020-10-28 11:54
     */
    public void exportExcel(File template, File targetFile, Map<String, Object> model, Map<String, Object> funcs) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(template);
             FileOutputStream fileOutputStream = new FileOutputStream(targetFile);) {
            exportExcel(fileInputStream, fileOutputStream, buildContext(model), buildFuncs(funcs));
        }
    }

    /**
     * 导出excel
     *
     * @param is    输入流
     * @param os    输出流
     * @param model jxls表达式数据
     * @Author tony_t_peng
     * @Date 2020-10-28 11:54
     */
    public void exportExcel(InputStream is, OutputStream os, Map<String, Object> model) throws IOException {
        exportExcel(is, os, buildContext(model), buildFuncs());
    }

    /**
     * 导出excel
     *
     * @param is 输入流
     * @param os 输出流
     * @Author tony_t_peng
     * @Date 2020-10-28 11:54
     */
    public <T> void exportExcel(InputStream is, OutputStream os, T... t) throws IOException, IllegalAccessException {
        exportExcel(is, os, buildContext(t), buildFuncs());
    }

    /**
     * 导出excel
     *
     * @param is      输入流
     * @param os      输出流
     * @param context jxls表达式数据源
     * @param funcs   自定义函数增强
     * @Author tony_t_peng
     * @Date 2020-10-28 11:54
     */
    private void exportExcel(InputStream is, OutputStream os, Context context, Map<String, Object> funcs) throws IOException {
        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        Transformer transformer = jxlsHelper.createTransformer(is, os);
        //获得配置
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
        //函数增强
        if (funcs != null) {
            evaluator.setJexlEngine(new JexlBuilder().namespaces(funcs).create());
        }
        //必须要这个，否者表格函数统计会错乱
        jxlsHelper.setUseFastFormulaProcessor(false).processTemplate(context, transformer);
    }

    /***
     * 组装context
     * @Date 2020-10-28 16:02
     */
    private Context buildContext(Map<String, Object> model) {
        Context context = PoiTransformer.createInitialContext();
        if (model != null) {
            for (String key : model.keySet()) {
                context.putVar(key, model.get(key));
            }
        }
        return context;
    }

    /***
     * 组装context
     * @Date 2020-10-28 16:02
     */
    private <T> Context buildContext(T... t) throws IllegalAccessException {
        Context context = PoiTransformer.createInitialContext();
        if (t != null) {
            for (T t1 : t) {
                Field[] declaredFields = t1.getClass().getDeclaredFields();
                for (Field field : declaredFields) {
                    field.setAccessible(true);
                    context.putVar(field.getName(), field.get(t1));
                }
            }
        }
        return context;
    }


    /**
     * 默认自定义函数增强
     *
     * @Date 17:37
     */
    private Map<String, Object> buildFuncs(Map<String, Object> funcs) {
        if (funcs == null) {
            funcs = new HashMap<>();
        }
        funcs.put("util", this);
        return funcs;
    }

    /**
     * 默认自定义函数增强
     *
     * @Date 17:37
     */
    private Map<String, Object> buildFuncs() {
        Map<String, Object> funcs = new HashMap<>();
        funcs.put("util", this);
        return funcs;
    }


    //超链接
    public WritableCellValue myHyperlink(String address, String title) {
        return new WritableHyperlink(address, title);
    }

    /**
     * 日期转换
     */
    public String dateToString(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }
}
