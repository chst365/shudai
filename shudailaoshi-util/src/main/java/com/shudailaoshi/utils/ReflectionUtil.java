package com.shudailaoshi.utils;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 反射工具类.
 * 
 * 提供访问私有变量,获取泛型类型Class,提取集合中元素的属性,转换字符串到对象等Util函数.
 * 
 */
public class ReflectionUtil {

	private static final Logger log = LoggerFactory.getLogger(ReflectionUtil.class);

	static {
		DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
		ConvertUtils.register(dc, Date.class);
	}

	/**
	 * 调用Getter方法.
	 */
	public static Object invokeGetterMethod(Object target, String propertyName) {
		String getterMethodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(target, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 调用Setter方法.使用value的Class来查找Setter方法.
	 */
	public static void invokeSetterMethod(Object target, String propertyName, Object value) {
		invokeSetterMethod(target, propertyName, value, null);
	}

	/**
	 * 调用Setter方法.
	 * 
	 * @propertyType 用于查找Setter方法,为空时使用value的Class替代.
	 */
	public static void invokeSetterMethod(Object target, String propertyName, Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(propertyName);
		invokeMethod(target, setterMethodName, new Class[] { type }, new Object[] { value });
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static Object getFieldValue(final Object object, final String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			log.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object object, final String fieldName, final Object value) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			log.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 */
	public static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes,
			final Object[] parameters) {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
		}

		method.setAccessible(true);

		try {
			return method.invoke(object, parameters);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 */
	public static Object invokeMethod(final Object object, final String methodName, final Object[] parameters) {
		Class<?>[] parameterTypes = null;
		if (parameters != null) {
			parameterTypes = new Class[parameters.length];
			for (int i = 0; i < parameters.length; i++) {
				parameterTypes[i] = parameters[i].getClass();
			}
		}
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
		}

		method.setAccessible(true);

		try {
			return method.invoke(object, parameters);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	protected static Field getDeclaredField(final Object object, final String fieldName) {
		Assert.notNull(object, "object不能为空");
		Assert.hasText(fieldName, "fieldName");
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 强行设置Field可访问.
	 */
	protected static void makeAccessible(final Field field) {
		if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
			field.setAccessible(true);
		}
	}

	/**
	 * 循环向上转型,获取对象的DeclaredMethod.
	 * 
	 * 如向上转型到Object仍无法找到, 返回null.
	 */
	protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
		Assert.notNull(object, "object不能为空");

		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 通过反射,获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class. eg. public UserDao
	 * extends HibernateDao<User>
	 *
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be
	 *         determined
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
	 * 
	 * 如public UserDao extends HibernateDao<User,Long>
	 *
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be
	 *         determined
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(final Class clazz, final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			log.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			log.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			log.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成List.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertyName
	 *            要提取的属性名.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List convertElementPropertyToList(final Collection collection, final String propertyName) {
		List list = new ArrayList();

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * 提取集合中的对象的属性(通过getter函数), 组合成由分割符分隔的字符串.
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertyName
	 *            要提取的属性名.
	 * @param separator
	 *            分隔符.
	 */
	@SuppressWarnings("rawtypes")
	public static String convertElementPropertyToString(final Collection collection, final String propertyName,
			final String separator) {
		List list = convertElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 转换字符串到相应类型.
	 * 
	 * @param value
	 *            待转换的字符串
	 * @param toType
	 *            转换目标类型
	 */
	public static Object convertStringToObject(String value, Class<?> toType) {
		try {
			return ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * 将反射时的checked exception转换为unchecked exception.
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}

	/**
	 * 获取Method的形参名称列表
	 * 
	 * @param method
	 *            需要解析的方法
	 * @return 形参名称列表,如果没有调试信息,将返回null
	 */
	public static List<String> getParamNames(Method method) {
		try {
			int size = method.getParameterTypes().length;
			if (size == 0)
				return new ArrayList<String>(0);
			List<String> list = getParamNames(method.getDeclaringClass()).get(getKey(method));
			if (list != null && list.size() != size)
				return list.subList(0, size);
			return list;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取Constructor的形参名称列表
	 * 
	 * @param constructor
	 *            需要解析的构造函数
	 * @return 形参名称列表,如果没有调试信息,将返回null
	 */
	public static List<String> getParamNames(Constructor<?> constructor) {
		try {
			int size = constructor.getParameterTypes().length;
			if (size == 0)
				return new ArrayList<String>(0);
			List<String> list = getParamNames(constructor.getDeclaringClass()).get(getKey(constructor));
			if (list != null && list.size() != size)
				return list.subList(0, size);
			return list;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	// ---------------------------------------------------------------------------------------------------

	/**
	 * 获取一个类的所有方法/构造方法的形参名称Map
	 * 
	 * @param klass
	 *            需要解析的类
	 * @return 所有方法/构造方法的形参名称Map
	 * @throws IOException
	 *             如果有任何IO异常,不应该有,如果是本地文件,那100%遇到bug了
	 */
	public static Map<String, List<String>> getParamNames(Class<?> klass) throws IOException {
		InputStream in = klass.getResourceAsStream("/" + klass.getName().replace('.', '/') + ".class");
		return getParamNames(in);
	}

	public static Map<String, List<String>> getParamNames(InputStream in) throws IOException {
		DataInputStream dis = new DataInputStream(new BufferedInputStream(in));
		Map<String, List<String>> names = new HashMap<String, List<String>>();
		Map<Integer, String> strs = new HashMap<Integer, String>();
		dis.skipBytes(4);// Magic
		dis.skipBytes(2);// 副版本号
		dis.skipBytes(2);// 主版本号

		// 读取常量池
		int constant_pool_count = dis.readUnsignedShort();
		for (int i = 0; i < (constant_pool_count - 1); i++) {
			byte flag = dis.readByte();
			switch (flag) {
			case 7:// CONSTANT_Class:
				dis.skipBytes(2);
				break;
			case 9:// CONSTANT_Fieldref:
			case 10:// CONSTANT_Methodref:
			case 11:// CONSTANT_InterfaceMethodref:
				dis.skipBytes(2);
				dis.skipBytes(2);
				break;
			case 8:// CONSTANT_String:
				dis.skipBytes(2);
				break;
			case 3:// CONSTANT_Integer:
			case 4:// CONSTANT_Float:
				dis.skipBytes(4);
				break;
			case 5:// CONSTANT_Long:
			case 6:// CONSTANT_Double:
				dis.skipBytes(8);
				i++;// 必须跳过一个,这是class文件设计的一个缺陷,历史遗留问题
				break;
			case 12:// CONSTANT_NameAndType:
				dis.skipBytes(2);
				dis.skipBytes(2);
				break;
			case 1:// CONSTANT_Utf8:
				int len = dis.readUnsignedShort();
				byte[] data = new byte[len];
				dis.read(data);
				strs.put(i + 1, new String(data, "UTF-8"));// 必然是UTF8的
				break;
			case 15:// CONSTANT_MethodHandle:
				dis.skipBytes(1);
				dis.skipBytes(2);
				break;
			case 16:// CONSTANT_MethodType:
				dis.skipBytes(2);
				break;
			case 18:// CONSTANT_InvokeDynamic:
				dis.skipBytes(2);
				dis.skipBytes(2);
				break;
			default:
				throw new RuntimeException("Impossible!! flag=" + flag);
			}
		}

		dis.skipBytes(2);// 版本控制符
		dis.skipBytes(2);// 类名
		dis.skipBytes(2);// 超类

		// 跳过接口定义
		int interfaces_count = dis.readUnsignedShort();
		dis.skipBytes(2 * interfaces_count);// 每个接口数据,是2个字节

		// 跳过字段定义
		int fields_count = dis.readUnsignedShort();
		for (int i = 0; i < fields_count; i++) {
			dis.skipBytes(2);
			dis.skipBytes(2);
			dis.skipBytes(2);
			int attributes_count = dis.readUnsignedShort();
			for (int j = 0; j < attributes_count; j++) {
				dis.skipBytes(2);// 跳过访问控制符
				int attribute_length = dis.readInt();
				dis.skipBytes(attribute_length);
			}
		}

		// 开始读取方法
		int methods_count = dis.readUnsignedShort();
		for (int i = 0; i < methods_count; i++) {
			dis.skipBytes(2); // 跳过访问控制符
			String methodName = strs.get(dis.readUnsignedShort());
			String descriptor = strs.get(dis.readUnsignedShort());
			short attributes_count = dis.readShort();
			for (int j = 0; j < attributes_count; j++) {
				String attrName = strs.get(dis.readUnsignedShort());
				int attribute_length = dis.readInt();
				if ("Code".equals(attrName)) { // 形参只在Code属性中
					dis.skipBytes(2);
					dis.skipBytes(2);
					int code_len = dis.readInt();
					dis.skipBytes(code_len); // 跳过具体代码
					int exception_table_length = dis.readUnsignedShort();
					dis.skipBytes(8 * exception_table_length); // 跳过异常表

					int code_attributes_count = dis.readUnsignedShort();
					for (int k = 0; k < code_attributes_count; k++) {
						int str_index = dis.readUnsignedShort();
						String codeAttrName = strs.get(str_index);
						int code_attribute_length = dis.readInt();
						if ("LocalVariableTable".equals(codeAttrName)) {// 形参在LocalVariableTable属性中
							int local_variable_table_length = dis.readUnsignedShort();
							List<String> varNames = new ArrayList<String>(local_variable_table_length);
							for (int l = 0; l < local_variable_table_length; l++) {
								dis.skipBytes(2);
								dis.skipBytes(2);
								String varName = strs.get(dis.readUnsignedShort());
								dis.skipBytes(2);
								dis.skipBytes(2);
								if (!"this".equals(varName)) // 非静态方法,第一个参数是this
									varNames.add(varName);
							}
							names.put(methodName + "," + descriptor, varNames);
						} else
							dis.skipBytes(code_attribute_length);
					}
				} else
					dis.skipBytes(attribute_length);
			}
		}
		dis.close();
		return names;
	}

	/**
	 * 传入Method或Constructor,获取getParamNames方法返回的Map所对应的key
	 */
	public static String getKey(Object obj) {
		StringBuilder sb = new StringBuilder();
		if (obj instanceof Method) {
			sb.append(((Method) obj).getName()).append(',');
			getDescriptor(sb, (Method) obj);
		} else if (obj instanceof Constructor) {
			sb.append("<init>,"); // 只有非静态构造方法才能用有方法参数的,而且通过反射API拿不到静态构造方法
			getDescriptor(sb, (Constructor<?>) obj);
		} else
			throw new RuntimeException("Not Method or Constructor!");
		return sb.toString();
	}

	public static void getDescriptor(StringBuilder sb, Method method) {
		sb.append('(');
		for (Class<?> klass : method.getParameterTypes())
			getDescriptor(sb, klass);
		sb.append(')');
		getDescriptor(sb, method.getReturnType());
	}

	public static void getDescriptor(StringBuilder sb, Constructor<?> constructor) {
		sb.append('(');
		for (Class<?> klass : constructor.getParameterTypes())
			getDescriptor(sb, klass);
		sb.append(')');
		sb.append('V');
	}

	/** 本方法来源于ow2的asm库的Type类 */
	public static void getDescriptor(final StringBuilder buf, final Class<?> c) {
		Class<?> d = c;
		while (true) {
			if (d.isPrimitive()) {
				char car;
				if (d == Integer.TYPE) {
					car = 'I';
				} else if (d == Void.TYPE) {
					car = 'V';
				} else if (d == Boolean.TYPE) {
					car = 'Z';
				} else if (d == Byte.TYPE) {
					car = 'B';
				} else if (d == Character.TYPE) {
					car = 'C';
				} else if (d == Short.TYPE) {
					car = 'S';
				} else if (d == Double.TYPE) {
					car = 'D';
				} else if (d == Float.TYPE) {
					car = 'F';
				} else /* if (d == Long.TYPE) */ {
					car = 'J';
				}
				buf.append(car);
				return;
			} else if (d.isArray()) {
				buf.append('[');
				d = d.getComponentType();
			} else {
				buf.append('L');
				String name = d.getName();
				int len = name.length();
				for (int i = 0; i < len; ++i) {
					char car = name.charAt(i);
					buf.append(car == '.' ? '/' : car);
				}
				buf.append(';');
				return;
			}
		}
	}
}
