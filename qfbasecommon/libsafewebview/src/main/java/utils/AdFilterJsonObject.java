/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * 
 * @author pengliyang
 */
public class AdFilterJsonObject {

	public ArrayList<AdUrlPattern> block_patterns;
	public ArrayList<String> ignore_patterns;

	/**
	 * 静态函数：将json数据从文件转成java对象
	 *
	 * @param jsonFilePath
	 *            - 指定定义广告url及白名单url的正则串的文件（json格式）
	 */
	// 调试用
	// public static AdFilterJsonObject getAdFilterJsonObject(InputStream
	// isJsonFile) {
	public static AdFilterJsonObject getAdFilterJsonObject(String jsonFilePath) {
		Gson gson = new Gson();
		try {
			BufferedReader br;
			File file = new File(jsonFilePath);
			br = new BufferedReader(new FileReader(file));

			// 调试用
			// InputStreamReader isr = new InputStreamReader(isJsonFile);
			// br = new BufferedReader(isr);

			// convert the json string back to object
			AdFilterJsonObject obj = gson
					.fromJson(br, AdFilterJsonObject.class);
			return obj;
		} catch (Exception e) {
			return null;
		}
	}

	class AdUrlPattern {
		String resPattern; // 匹配待拦截资源url的正则表达式串
		String thirdParty; // "1": 表示待拦截资源的url只对第三方host有效；为空时，表示对全部host生效
		String hostPattern; // 检测到待拦截资源后，网开一面的host的正则表达式串
	}
}
