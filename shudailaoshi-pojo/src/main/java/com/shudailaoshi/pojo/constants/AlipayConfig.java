package com.shudailaoshi.pojo.constants;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2017081008127842";

	// 商户私钥，您的PKCS8格式RSA2私钥
	public static String merchant_private_key = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQCTwWzADNOMdtW0sV5JsehVERvugPtD/DVlnNSmxv+B7cPKBv0XwYvf3I5km1IeuH7YaB+b0kvP4N0esJd7Ir8ZS4yjvZE2bDqJYhUTTE0HB6rlT6WJiHndDZMDlwiShAcwHc5QdKARFfBGUAUf0zJBruAnqZVO/vxP+K36P541xy9/Z/C3yMODDTt8GtB0IkN5wPFNWQwU6JB7qjILnc4EeClE68jocPkrIzFTvpJ+S73PzYY/cuDHyjP/M2UUYxMPklItQAgHQjD6PA+V3+ui3sCToDdTaEcReg4eXUWVPZPu8M64E06hvP4Xhs8bOkHzev+cEd26es2R8eDpeB9LAgMBAAECggEBAIS88/Hkk/hQmdRms4oJJeumHy24+gIthdrvdasOg2Q6MfZBKQY6fmpyPgnr725h2LKZeCwIVMs/++YiRzP8FymmUYaYOuhleoEJeF2J4nk6TjELyG8yCCiqlTRYbv8RVVPIsxxcaaBv0ReZs8DPfwq7C+0V0GjTceB2S/XRSVSNZkh9QTdezuz6eO/40iDK9ZC2ecPkEDmtNCxO9YBX7j1/eA0Oe4dwpSp9Dr+cZpcmcvZhkEvgBwqn4IuPlJJZK4qTYfm7EQh+32oCPubwpGyYYbE65UX8EnugB4eODL5/NfnLwHdJTNIuO4yBVRo92lsMQEJZBKWORyscjC/chkECgYEA51ymBjsF91zw1YMRCtUauQVU/l/SB2PqUzR67NdYc0REZo1WuhXcfFsFScqJKxfqvJbCNmk5NtX+FhPDs61m4DUaCF9sBXL2BhHzXz7NqgtBCsXwHN9ZyRNZrw/ZlBrtpx44l3G7aVZh6ZOZbSD3lyeP2Aa0WwJhibQv/qCXnSMCgYEAo32Dc78CQK3wrsRiKziZsnEHq6SlG1iiEYv5x9MlIeN3eu+gkbXG/iE+O1ALD586/jXXeHoPoYPP3BdwUYs9rFLZEzi3xSIvkyL+ZsyuaKIMTI31k09V0RPxbsOQNu2OFXCVdLWPhDHCFpuhIIgQ3rEa/Ko2m6YVsk1vjQrVu7kCgYEAqSX5U9DPOdw/Tzp1uT+4O9EEJL2VZkXMSWyy+nkud4dPotJut7nH6zr4kMx8oH7p5VsrPnCLneWx0B4/FBwQfJcWI0zwQsObzzBMiDP4VPvJX8D7HYDFudZl2TFzV26glW/o3AAQjqDTENwSbdCOc3o/FidAsND60pxkAnsSnZUCgYEAh/31hCgURZzqIVW1muIf5vBiQwJXXGHjndjjUrZtorzFO/trgwjDl8wrKccxDDOVLLO4obxWmDE+i4LrqMi76kWXz4dURaRkEm3/U59aTCrPNDa5WWmapqmCIHKH2UjM8USO7V/BG2MQYSHfa2IDNln3ashjWCFU1tXWvmmO0/ECgYEA4VLD+1MXLvqjbaaIs4G2ptEIorEHmrixUe29S5rA2Ef+IgK8iFjRlWn8es8H4uuOL2YzKVTk3UpTCreVwvERa6/IV6IZ1BnkTlr9mamHLQBCVHakKt6kF5t1DprEWiKmAzbcvjwGMzkA7IqDkAnexRPfjdbh80aYLgI+sJp/NeY=";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
	// 对应APPID下的支付宝公钥。
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjuxSIMUYj5HETC/sNu2MFpnz93rlJsSLG/HYfAwfcynkFsgcPAXdrp5pvHOsWQPlW6X1oProCMXeQY3QXA9+HcRvdNly3Nmt955PYHBFrCLBhrl40tMmseVStgPaBll04PyQoPm0HJyvV4j5IMmOtnPSwmAwXKj/Hy1SQ5gf4Z5PWjIfu1N+GLIaA+iXpNOPxZSV7D2IZHwA8S5ZHBl5nuDoYNQCXTOmeLGXml96JAuuzjO1YRsax63P60r3KUKuopYJPb5p61CjTX/qBpenfTGE/HTAHL4cVp921SNJqN3j6oM+QypfSdvIVNTQpwWzvufZe3LGnVHG7jvHFBKJZwIDAQAB";

	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://101.37.254.91/pay/alipay/payNotify";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://101.37.254.91/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

	// 支付宝网关
	public static String log_path = "C:\\";

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * 
	 * @param sWord
	 *            要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
