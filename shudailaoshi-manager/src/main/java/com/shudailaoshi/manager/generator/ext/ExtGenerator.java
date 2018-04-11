package com.shudailaoshi.manager.generator.ext;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.shudailaoshi.entity.sys.Operating;
import com.shudailaoshi.manager.generator.base.BaseGenerator;
import com.shudailaoshi.pojo.annotation.Comment;
import com.shudailaoshi.utils.DateUtil;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

/**
 * 生成Ext
 * 
 * @author Liaoyifan
 * @date 2016年8月18日
 *
 */
public class ExtGenerator extends BaseGenerator {

	public static void main(String[] args) {
		new ExtGenerator().generate("/Users/liaoyifan/Downloads/", Operating.class);
	}

	/**
	 * 生成ext
	 * 
	 * @param generatePath
	 * @param clazz
	 */
	public void generate(String generatePath, Class<?> clazz) {
		try {
			String className = clazz.getPackage() + "." + clazz.getSimpleName();
			String moduleName = className.substring(0, className.lastIndexOf(".")).substring(className.substring(0, className.lastIndexOf(".")).lastIndexOf(".") + 1);
			String entityName = className.substring(className.lastIndexOf(".") + 1);
			String packageName = entityName.toLowerCase().charAt(0) + entityName.substring(1);
			System.out.println(DateUtil.getMillisecondString() + "==>>开始生成" + entityName + "ViewController.js");
			this.generateController(generatePath, moduleName, packageName, entityName);
			System.out.println(DateUtil.getMillisecondString() + "==>>开始生成" + entityName + "Model.js");
			this.generateModel(generatePath, packageName, entityName, clazz);
			System.out.println(DateUtil.getMillisecondString() + "==>>开始生成" + entityName + "Store.js");
			this.generateStore(generatePath, moduleName, packageName, entityName);
			System.out.println(DateUtil.getMillisecondString() + "==>>开始生成" + entityName + "View.js");
			this.generateView(generatePath, packageName, entityName);
			System.out.println(DateUtil.getMillisecondString() + "==>>开始生成" + entityName + "List.js");
			this.generateList(generatePath, packageName, entityName, clazz);
			System.out.println(DateUtil.getMillisecondString() + "==>>开始生成" + entityName + "Search.js");
			this.generateSearch(generatePath, packageName, entityName,clazz);
			System.out.println(DateUtil.getMillisecondString() + "==>>开始生成" + entityName + "Window.js");
			this.generateWindow(generatePath, packageName, entityName,clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void generateController(String generatePath, String moduleName, String packageName, String entityName) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("moduleName", moduleName);
		params.put("packageName", packageName);
		params.put("entityName", entityName);
		System.out.println(DateUtil.getMillisecondString() + "==>>获取" + entityName + "ViewController.js模板");
		Template template = super.config.getTemplate("viewcontroller.ftl");
		String filePath = generatePath + "/" + this.getClass().getSimpleName() + "/app/controller/" + packageName + "/" + entityName + "ViewController.js";
		System.out.println(DateUtil.getMillisecondString() + "==>>写入" + entityName + "ViewController.js");
		template.process(params, new OutputStreamWriter(FileUtils.openOutputStream(new File(filePath))));
		System.out.println(DateUtil.getMillisecondString() + "==>>" + filePath + "生成完毕");
	}

	private void generateModel(String generatePath, String packageName, String entityName, Class<?> clazz) throws ClassNotFoundException, TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("packageName", packageName);
		params.put("entityName", entityName);
		LinkedList<String> fieldList = new LinkedList<String>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			if (!name.equals("serialVersionUID")) {
				fieldList.add("'" + name + "'");
			}
		}
		fieldList.addFirst("'id'");
		fieldList.addLast("'createTime'");
		fieldList.addLast("'modifyTime'");
		fieldList.addLast("'status'");
		params.put("fields", StringUtils.join(fieldList, ","));
		System.out.println(DateUtil.getMillisecondString() + "==>>获取" + entityName + "Model.js模板");
		Template template = super.config.getTemplate("model.ftl");
		String filePath = generatePath + "/" + this.getClass().getSimpleName() + "/app/model/" + packageName + "/" + entityName + "Model.js";
		System.out.println(DateUtil.getMillisecondString() + "==>>写入" + entityName + "Model.js");
		template.process(params, new OutputStreamWriter(FileUtils.openOutputStream(new File(filePath))));
		System.out.println(DateUtil.getMillisecondString() + "==>>" + filePath + "生成完毕");
	}

	private void generateStore(String generatePath, String moduleName, String packageName, String entityName) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("moduleName", moduleName);
		params.put("packageName", packageName);
		params.put("entityName", entityName);
		System.out.println(DateUtil.getMillisecondString() + "==>>获取" + entityName + "Store.js模板");
		Template template = super.config.getTemplate("store.ftl");
		String filePath = generatePath + "/" + this.getClass().getSimpleName() + "/app/store/" + packageName + "/" + entityName + "Store.js";
		System.out.println(DateUtil.getMillisecondString() + "==>>写入" + entityName + "Store.js");
		template.process(params, new OutputStreamWriter(FileUtils.openOutputStream(new File(filePath))));
		System.out.println(DateUtil.getMillisecondString() + "==>>" + filePath + "生成完毕");
	}

	private void generateView(String generatePath, String packageName, String entityName) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("packageName", packageName);
		params.put("entityName", entityName);
		System.out.println(DateUtil.getMillisecondString() + "==>>获取" + entityName + "View.js模板");
		Template template = super.config.getTemplate("view.ftl");
		String filePath = generatePath + "/" + this.getClass().getSimpleName() + "/app/view/" + packageName + "/" + entityName + "View.js";
		System.out.println(DateUtil.getMillisecondString() + "==>>写入" + entityName + "View.js");
		template.process(params, new OutputStreamWriter(FileUtils.openOutputStream(new File(filePath))));
		System.out.println(DateUtil.getMillisecondString() + "==>>" + filePath + "生成完毕");
	}

	private void generateList(String generatePath, String packageName, String entityName, Class<?> clazz) throws ClassNotFoundException, TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("packageName", packageName);
		params.put("entityName", entityName);
		LinkedList<String> columnList = new LinkedList<String>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			if (!name.equals("serialVersionUID")) {
				columnList.add("{\r\n\t\t\ttext : '" + field.getAnnotation(Comment.class).value() + "',\r\n\t\t\tdataIndex : '" + name + "'\r\n\t\t}");
			}
		}
		columnList.addLast("{\r\n\t\t\ttext : '创建时间',\r\n\t\t\tdataIndex : 'createTime',\r\n\t\t\trenderer : function(value) {\r\n\t\t\t\treturn DateUtil.timeToString(value,DateUtil.TIME);\r\n\t\t\t}\r\n\t\t}");
		columnList.addLast("{\r\n\t\t\ttext : '状态',\r\n\t\t\tdataIndex : 'status',\r\n\t\t\trenderer : function(value) {\r\n\t\t\t\treturn StatusEnum.getHtml(value);\r\n\t\t\t}\r\n\t\t}");
		params.put("columns", StringUtils.join(columnList, ","));
		System.out.println(DateUtil.getMillisecondString() + "==>>获取" + entityName + "List.js模板");
		Template template = super.config.getTemplate("list.ftl");
		String filePath = generatePath + "/" + this.getClass().getSimpleName() + "/app/view/" + packageName + "/" + entityName + "List.js";
		System.out.println(DateUtil.getMillisecondString() + "==>>写入" + entityName + "List.js");
		template.process(params, new OutputStreamWriter(FileUtils.openOutputStream(new File(filePath))));
		System.out.println(DateUtil.getMillisecondString() + "==>>" + filePath + "生成完毕");
	}

	private void generateSearch(String generatePath, String packageName, String entityName,Class<?> clazz) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("packageName", packageName);
		params.put("entityName", entityName);
		LinkedList<String> columnList = new LinkedList<String>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			if (!name.equals("serialVersionUID")) {
				columnList.add("{\r\n\t\t\t\tfieldLabel : '" + field.getAnnotation(Comment.class).value() + "',\r\n\t\t\t\tname : '" + name + "'\r\n\t\t\t}");
			}
		}
		params.put("columns", StringUtils.join(columnList, ","));
		System.out.println(DateUtil.getMillisecondString() + "==>>获取" + entityName + "Search.js模板");
		Template template = super.config.getTemplate("search.ftl");
		String filePath = generatePath + "/" + this.getClass().getSimpleName() + "/app/view/" + packageName + "/" + entityName + "Search.js";
		System.out.println(DateUtil.getMillisecondString() + "==>>写入" + entityName + "Search.js");
		template.process(params, new OutputStreamWriter(FileUtils.openOutputStream(new File(filePath))));
		System.out.println(DateUtil.getMillisecondString() + "==>>" + filePath + "生成完毕");
	}

	private void generateWindow(String generatePath, String packageName, String entityName,Class<?> clazz) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("packageName", packageName);
		params.put("entityName", entityName);
		Field[] fields = clazz.getDeclaredFields();
		LinkedList<String> columnList = new LinkedList<String>();
		for (Field field : fields) {
			String name = field.getName();
			if (!name.equals("serialVersionUID")) {
				columnList.add("{\r\n\t\t\tallowBlank : false,\r\n\t\t\tbeforeLabelTextTpl : [ \'<font color=red data-qtip=必填选项>*</font>\' ],\r\n\t\t\tfieldLabel : '" + field.getAnnotation(Comment.class).value()+ "',\r\n\t\t\tname : '" + name+ "'\r\n\t\t}");
			}
		}
		params.put("columns", StringUtils.join(columnList, ","));
		System.out.println(DateUtil.getMillisecondString() + "==>>获取" + entityName + "Window.js模板");
		Template template = super.config.getTemplate("window.ftl");
		String filePath = generatePath + "/" + this.getClass().getSimpleName() + "/app/view/" + packageName + "/" + entityName + "Window.js";
		System.out.println(DateUtil.getMillisecondString() + "==>>写入" + entityName + "Window.js");
		template.process(params, new OutputStreamWriter(FileUtils.openOutputStream(new File(filePath))));
		System.out.println(DateUtil.getMillisecondString() + "==>>" + filePath + "生成完毕");
	}

}
