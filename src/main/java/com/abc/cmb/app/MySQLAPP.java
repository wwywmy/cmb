package com.abc.cmb.app;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;

public class MySQLAPP {

	private final static String dirPath = "d:/db/bwzb";
	private final static String fileFullPath = dirPath + "/" + "bwzb.sql";

	// V2.0.202004262020__sql_example.sql
	private final static String flywayBaseVersion = "2.0.1000";
	private final static String[] vs = flywayBaseVersion.split("\\.");

	public static void main(String[] args) {

		String dirPathTable = dirPath + "/" + "table";
		String dirPathScript = dirPath + "/" + "script";
		String dirPathFlyway = dirPath + "/" + "flyway";
		
		int third = Integer.parseInt(vs[vs.length - 1]);

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

					// flyway
					
					third++;
					// V2.0.202004262020__sql_example.sql
					String flywayName = StringUtils.join(//
							"V", //
							vs[0], ".", vs[1], ".", third, //
							"__", //
							tableName, "_", (isTable ? "table" : "script"), //
							".", "sql"//
					);
					
					filePath = dirPathFlyway + "/" + flywayName;
					writer = new FileWriter(filePath);
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
