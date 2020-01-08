package com.abc.cmb.app;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.abc.cmb.entity.CmbBatch;
import com.abc.cmb.entity.CmbItem;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.StrUtil;

public class CmbApp {
	private final static String DIRECTORY_PATH_STRING = "D:/cmb";
	private final static String DIRECTORY_SEPARATE = "/";
	// private final static String FILE_NAME = "2019-09.txt";
	private final static String FILE_NAME = "2019-12.txt";

	public static void main(String[] args) {
		String fileFullPath = StrUtil.concat(true, DIRECTORY_PATH_STRING, DIRECTORY_SEPARATE, FILE_NAME);

		String fileMainName = FileUtil.mainName(fileFullPath);
//		System.out.println(fileMainName);// 2019-09

		LocalDate transMonth = DateUtil.toLocalDateTime(DateUtil.parse(fileMainName, "yyyy-MM")).toLocalDate();// �˻��·�
//		System.out.println(transMonth);// 2019-09-01

		FileReader fileReader = new FileReader(fileFullPath);
		List<String> lines = fileReader.readLines();
//		for (String line : lines) {
//			System.out.println(line);
//		}

		List<CmbItem> itemList = new ArrayList<CmbItem>();

		int index = 1;
		for (int i = 0; i < lines.size(); i = i + 7) {
			System.out.println(i);
			System.out.println(lines.get(i + 3));
			CmbItem item = CmbItem.builder()//
					.id(index++) //
					.transDate(getFullLocalDate(transMonth, lines.get(i + 0)))// ������
					.postDate(getFullLocalDate(transMonth, lines.get(i + 1)))// ������
					.description(lines.get(i + 2))// ����ժҪ
					.rmbAmount(new BigDecimal(lines.get(i + 3).replaceAll("��", "").replaceAll(",", "").replaceAll(" ", "")))// ����ҽ��
					.cardNumber(lines.get(i + 4))// ����ĩ��λ
					.originalTransAmount(new BigDecimal(lines.get(i + 5).replaceAll(",", "")))// ���׵ؽ��
					.countryOrArea(lines.get(i + 6))// ���׵ص�
					.build();

			System.out.println(ToStringBuilder.reflectionToString(item, ToStringStyle.MULTI_LINE_STYLE));
			itemList.add(item);
		}

		CmbBatch batch = CmbBatch.builder()//
				.id(1)//
				.transMonth(transMonth)//
				.itemList(itemList)//
				.build();

		// System.out.println(ToStringBuilder.reflectionToString(batch,
		// ToStringStyle.MULTI_LINE_STYLE));
		System.out.println(JSON.toJSONString(batch));

		try {
			writeExcelOneSheetOnceWrite(batch);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static LocalDate getFullLocalDate(LocalDate transMonth, String MMdd) {
		int year = transMonth.getYear();
		LocalDateTime localDateTime = DateUtil.toLocalDateTime(DateUtil.parse(MMdd, "MMdd"));
		Month month = localDateTime.getMonth();
		// ���������Ϊ12�·ݣ������Ҫ��һ
		// ��202001�·��˵�����Ϊ201911~202012
//		if (Month.NOVEMBER.equals(month) || Month.DECEMBER.equals(month)) {
//			year--;
//		}

		if (Month.NOVEMBER.equals(month) || Month.DECEMBER.equals(month)) {
			year--;
		}

		return LocalDate.of(year, month, localDateTime.getDayOfMonth());
	}

	public static void writeExcelOneSheetOnceWrite(CmbBatch batch) throws IOException {
		// ����EXCEL��ָ�����·��
		String fileMainName = FileUtil.mainName(FILE_NAME);
		String excelFullPath = StrUtil.concat(true, DIRECTORY_PATH_STRING, DIRECTORY_SEPARATE, fileMainName + ".xlsx");

		EasyExcel.write(excelFullPath, CmbItem.class).sheet("sheet1").doWrite(batch.getItemList());

		System.out.println(excelFullPath);
	}

}
