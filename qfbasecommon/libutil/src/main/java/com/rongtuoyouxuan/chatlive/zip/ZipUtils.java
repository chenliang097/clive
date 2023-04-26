package com.rongtuoyouxuan.chatlive.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author zhenglei
 * zip操作文件
 */
public class ZipUtils {
	/**
	 * 解压缩文件
	 * @param zipFileString zip文件
	 * @param outPathString 存储路径
	 * @throws Exception
	 */
	public synchronized static void UnZipFolder(String zipFileString, String outPathString)
			throws Exception {
		File outPathFile = new File(outPathString);
		if(!outPathFile.exists()) {
			outPathFile.mkdir();
		}
		ZipInputStream inZip = new ZipInputStream(new FileInputStream(
				zipFileString));
		ZipEntry zipEntry;
		String szName = "";
		while ((zipEntry = inZip.getNextEntry()) != null) {
			szName = zipEntry.getName();
			if (szName.contains("../")) {
				continue;
			}
			if (zipEntry.isDirectory()) {
				szName = szName.substring(0, szName.length() - 1);
				File folder = new File(outPathString + File.separator + szName);
				folder.mkdirs();
			} else {

				File file = new File(outPathString + File.separator + szName);
				file.createNewFile();
				FileOutputStream out = new FileOutputStream(file);
				int len;
				byte[] buffer = new byte[1024];
				while ((len = inZip.read(buffer)) != -1) {
					out.write(buffer, 0, len);
					out.flush();
				}
				out.close();
			}
		}
		inZip.close();
	}

	/**
	 * 压缩文件
	 * @param srcFileString 原始文件路径
	 * @param zipFileString zip的全路径+文件名
	 * @throws Exception
	 */
	public synchronized static void ZipFolder(String srcFileString, String zipFileString)
			throws Exception {
		ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(
				zipFileString));
		File file = new File(srcFileString);
		ZipFiles(file.getParent() + File.separator, file.getName(), outZip);
		outZip.finish();
		outZip.close();
	}

	private static void ZipFiles(String folderString, String fileString,
								 ZipOutputStream zipOutputSteam) throws Exception {
		if (zipOutputSteam == null)
			return;
		File file = new File(folderString + fileString);
		if (file.isFile()) {
			ZipEntry zipEntry = new ZipEntry(fileString);
			FileInputStream inputStream = new FileInputStream(file);
			zipOutputSteam.putNextEntry(zipEntry);
			int len;
			byte[] buffer = new byte[4096];
			while ((len = inputStream.read(buffer)) != -1) {
				zipOutputSteam.write(buffer, 0, len);
			}
			zipOutputSteam.closeEntry();
		} else {
			String fileList[] = file.list();
			if (fileList.length <= 0) {
				ZipEntry zipEntry = new ZipEntry(fileString + File.separator);
				zipOutputSteam.putNextEntry(zipEntry);
				zipOutputSteam.closeEntry();
			}
			for (int i = 0; i < fileList.length; i++) {
				ZipFiles(folderString, fileString + File.separator
						+ fileList[i], zipOutputSteam);
			}
		}
	}

	public static InputStream UpZip(String zipFileString, String fileString)
			throws Exception {
		ZipFile zipFile = new ZipFile(zipFileString);
		ZipEntry zipEntry = zipFile.getEntry(fileString);
		return zipFile.getInputStream(zipEntry);
	}

	public static List<File> GetFileList(String zipFileString,
										 boolean bContainFolder, boolean bContainFile) throws Exception {
		List<File> fileList = new ArrayList<File>();
		ZipInputStream inZip = new ZipInputStream(new FileInputStream(
				zipFileString));
		ZipEntry zipEntry;
		String szName = "";
		while ((zipEntry = inZip.getNextEntry()) != null) {
			szName = zipEntry.getName();
			if (szName.contains("../")) {
				continue;
			}
			if (zipEntry.isDirectory()) {
				szName = szName.substring(0, szName.length() - 1);
				File folder = new File(szName);
				if (bContainFolder) {
					fileList.add(folder);
				}

			} else {
				File file = new File(szName);
				if (bContainFile) {
					fileList.add(file);
				}
			}
		}
		inZip.close();
		return fileList;
	}
}
