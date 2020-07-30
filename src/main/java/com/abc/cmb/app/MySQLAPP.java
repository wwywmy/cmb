package com.abc.cmb.app;

import java.util.List;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;

public class MySQLAPP {

	private final static String dirPath = "d:/db/bwzb";
	private final static String fileFullPath = dirPath + "/" + "bwzb.sql";

	public static void main(String[] args) {

		String dirPathTable = dirPath + "/" + "table";
		String dirPathScript = dirPath + "/" + "script";

		StringBuffer buffer = new StringBuffer();
		int begin = 0;
		int end = 0;

		boolean isTable = false;
		boolean isScript = false;
		String tableName = "";

		FileReader fileReader = new FileReader(fileFullPath);
		List<String> lines = fileReader.readLines();
		for (String line : lines) {

			if ("-- ----------------------------".equals(line)) {
				begin++;
			}

			if (begin > 0) {

				if ("".equals(line)) {
					end++;
				} else {
					buffer.append(line);
					buffer.append(System.getProperty("line.separator"));

					if (line.startsWith("-- Table structure for")) {
						isTable = true;
						String[] cells = line.split(" ");
						tableName = cells[cells.length - 1];
					}
					if (line.startsWith("-- Records of")) {
						isScript = true;
						String[] cells = line.split(" ");
						tableName = cells[cells.length - 1];
					}
				}

				if (end > 0) {

					String filePath = "";
					if (isTable) {
						filePath = dirPathTable + "/" + tableName + ".sql";
					}
					if (isScript) {
						filePath = dirPathScript + "/" + tableName + ".sql";
					}
					FileWriter writer = new FileWriter(filePath);
					writer.write(buffer.toString());

					begin = 0;
					end = 0;
					isTable = false;
					isScript = false;
					tableName = "";
					buffer = new StringBuffer();
				}
			}

		}

	}

}
