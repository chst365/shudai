package com.shudailaoshi.utils;

import java.math.BigDecimal;

/**
 * java.math.BigDecimal：不可变的、任意精度的有符号十进制数。BigDecimal
 * 由任意精度的整数非标度值和32位的整数标度(scale)组成。
 * 如果为零或正数，则标度是小数点后的位数。如果为负数，则将该数的非标度值乘以10的负scale次幂。
 * 因此，BigDecimal表示的数值是(unscaledValue × 10-scale)。 与之相关的还有两个类：
 * java.math.MathContext：该对象是封装上下文设置的不可变对象，它描述数字运算符的某些规则，如数据的精度，舍入方式等。
 * java.math.RoundingMode：这是一种枚举类型，定义了很多常用的数据舍入方式。
 * 这个类用起来还是很比较复杂的，原因在于舍入模式，数据运算规则太多太多，
 * 不是数学专业出身的人看着中文API都难以理解，这些规则在实际中使用的时候在翻阅都来得及。
 * 在银行、帐户、计费等领域，BigDecimal提供了精确的数值计算。其中8种舍入方式值得掌握。
 * 
 * 1、ROUND_UP 舍入远离零的舍入模式。 在丢弃非零部分之前始终增加数字(始终对非零舍弃部分前面的数字加1)。
 * 注意，此舍入模式始终不会减少计算值的大小。
 * 
 * 2、ROUND_DOWN 接近零的舍入模式。 在丢弃某部分之前始终不增加数字(从不对舍弃部分前面的数字加1，即截短)。
 * 注意，此舍入模式始终不会增加计算值的大小。
 * 
 * 3、ROUND_CEILING 接近正无穷大的舍入模式。 如果 BigDecimal 为正，则舍入行为与 ROUND_UP 相同; 如果为负，则舍入行为与
 * ROUND_DOWN 相同。 注意，此舍入模式始终不会减少计算值。
 * 
 * 4、ROUND_FLOOR 接近负无穷大的舍入模式。 如果 BigDecimal 为正，则舍入行为与 ROUND_DOWN 相同; 如果为负，则舍入行为与
 * ROUND_UP 相同。 注意，此舍入模式始终不会增加计算值。
 * 
 * 5、ROUND_HALF_UP 向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。 如果舍弃部分 >= 0.5，则舍入行为与
 * ROUND_UP 相同;否则舍入行为与 ROUND_DOWN 相同。 注意，这是我们大多数人在小学时就学过的舍入模式(四舍五入)。
 * 
 * 6、ROUND_HALF_DOWN 向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为上舍入的舍入模式。 如果舍弃部分 > 0.5，则舍入行为与
 * ROUND_UP 相同;否则舍入行为与 ROUND_DOWN 相同(五舍六入)。
 * 
 * 7、ROUND_HALF_EVEN 向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则向相邻的偶数舍入。 如果舍弃部分左边的数字为奇数，则舍入行为与
 * ROUND_HALF_UP 相同; 如果为偶数，则舍入行为与 ROUND_HALF_DOWN 相同。
 * 注意，在重复进行一系列计算时，此舍入模式可以将累加错误减到最小。 此舍入模式也称为“银行家舍入法”，主要在美国使用。四舍六入，五分两种情况。
 * 如果前一位为奇数，则入位，否则舍去。 以下例子为保留小数点1位，那么这种舍入方式下的结果。 1.15>1.2 1.25>1.2
 * 
 * 8、ROUND_UNNECESSARY 断言请求的操作具有精确的结果，因此不需要舍入。
 * 如果对获得精确结果的操作指定此舍入模式，则抛出ArithmeticException。
 * 
 */
/**
 * 计算工具类
 * 
 * @author Liaoyifan
 * @date 2016年8月19日
 *
 */
public class NumberUtil {

	private static final int SCALE = 2;
	
	private NumberUtil() {
	}

	/**
	 * 四舍五入(保留2位小数)
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal round(final BigDecimal value) {
		return value.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 四舍五入(保留2位小数)
	 * 
	 * @param value
	 * @return
	 */
	public static double round(final double value) {
		return BigDecimal.valueOf(value).setScale(SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 相加
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double add(final double d1, final double d2) {
		return BigDecimal.valueOf(d1).add(BigDecimal.valueOf(d2)).doubleValue();
	}

	/**
	 * 相加
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal add(final BigDecimal d1, final BigDecimal d2) {
		return d1.add(d2);
	}

	/**
	 * 相减
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double sub(final double d1, final double d2) {
		return BigDecimal.valueOf(d1).subtract(BigDecimal.valueOf(d2)).doubleValue();
	}

	/**
	 * 相减
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal sub(final BigDecimal d1, final BigDecimal d2) {
		return d1.subtract(d2);
	}

	/**
	 * 相乘
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double mul(final double d1, final double d2) {
		return BigDecimal.valueOf(d1).multiply(BigDecimal.valueOf(d2)).doubleValue();
	}

	/**
	 * 相乘
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal mul(final BigDecimal d1, final BigDecimal d2) {
		return d1.multiply(d2);
	}

	/**
	 * 相除 (保留2位小数)
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double div(final double d1, final double d2) {
		return BigDecimal.valueOf(d1).divide(BigDecimal.valueOf(d2), SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 相除 (保留2位小数)
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal div(final BigDecimal d1, final BigDecimal d2) {
		return d1.divide(d2, SCALE, BigDecimal.ROUND_HALF_UP);
	}

}
