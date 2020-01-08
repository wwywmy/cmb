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

		LocalDate transMonth = DateUtil.toLocalDateTime(DateUtil.parse(fileMainName, "yyyy-MM")).toLocalDate();// 账户月份
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
					.transDate(getFullLocalDate(transMonth, lines.get(i + 0)))// 交易日
					.postDate(getFullLocalDate(transMonth, lines.get(i + 1)))// 记账日
					.description(lines.get(i + 2))// 交易摘要
					.rmbAmount(new BigDecimal(lines.get(i + 3).replaceAll("￥", "").replaceAll(",", "").replaceAll(" ", "")))// 人民币金额
					.cardNumber(lines.get(i + 4))// 卡号末四位
					.originalTransAmount(new BigDecimal(lines.get(i + 5).replaceAll(",", "")))// 交易地金额
					.countryOrArea(lines.get(i + 6))// 交易地点
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
		// 如果交易日为12月份，则年份要减一
		// 如202001月份账单区间为201911~202012
//		if (Month.NOVEMBER.equals(month) || Month.DECEMBER.equals(month)) {
//			year--;
//		}

		if (Month.NOVEMBER.equals(month) || Month.DECEMBER.equals(month)) {
			year--;
		}

		return LocalDate.of(year, month, localDateTime.getDayOfMonth());
	}

	public static void writeExcelOneSheetOnceWrite(CmbBatch batch) throws IOException {
		// 生成EXCEL并指定输出路径
		String fileMainName = FileUtil.mainName(FILE_NAME);
		String excelFullPath = StrUtil.concat(true, DIRECTORY_PATH_STRING, DIRECTORY_SEPARATE, fileMainName + ".xlsx");

		EasyExcel.write(excelFullPath, CmbItem.class).sheet("sheet1").doWrite(batch.getItemList());

		System.out.println(excelFullPath);
	}

}
