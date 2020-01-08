package com.abc.cmb.app;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class LocalDateConverter implements Converter<LocalDate> {
	@Override
	public Class supportJavaTypeKey() {
		return LocalDate.class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	@Override
	public LocalDate convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
			GlobalConfiguration globalConfiguration) throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
			// return DateUtils.parseDate(cellData.getStringValue(), null);
			return LocalDate.parse(cellData.getStringValue());
		} else {
//			return DateUtils.parseDate(cellData.getStringValue(),
//					contentProperty.getDateTimeFormatProperty().getFormat());
			return LocalDate.parse(cellData.getStringValue(),
					DateTimeFormatter.ofPattern(contentProperty.getDateTimeFormatProperty().getFormat()));
		}
	}

	@Override
	public CellData convertToExcelData(LocalDate value, ExcelContentProperty contentProperty,
			GlobalConfiguration globalConfiguration) {
		if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
			//return new CellData(DateUtils.format(value, null));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			return new CellData(formatter.format(value));
		} else {
//			return new CellData(DateUtils.format(value, contentProperty.getDateTimeFormatProperty().getFormat()));
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(contentProperty.getDateTimeFormatProperty().getFormat());
			return new CellData(formatter.format(value));
		}
	}
}
