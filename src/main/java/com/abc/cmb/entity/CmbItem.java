package com.abc.cmb.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.abc.cmb.app.LocalDateConverter;
import com.alibaba.excel.annotation.ExcelProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
public class CmbItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8643170279721364548L;
	@ExcelProperty(value = "序号", index = 0)
	private Integer id;

	@ExcelProperty(value = "交易日", index = 1, converter = LocalDateConverter.class)
	private LocalDate transDate;// 交易日

	@ExcelProperty(value = "记账日", index = 2, converter = LocalDateConverter.class)
	private LocalDate postDate;// 记账日

	@ExcelProperty(value = "交易摘要", index = 3)
	private String description;// 交易摘要

	@ExcelProperty(value = "人民币金额", index = 4)
	private BigDecimal rmbAmount;// 人民币金额

	@ExcelProperty(value = "卡号末四位", index = 5)
	private String cardNumber;// 卡号末四位

	@ExcelProperty(value = "交易地金额", index = 6)
	private BigDecimal originalTransAmount;// 交易地金额

	@ExcelProperty(value = "交易地点", index = 7)
	private String countryOrArea;// 交易地点

	@ExcelProperty(value = "类别", index = 8)
	private String category;// 类别

	public String getCategory() {
		category = "未知";
		if (description == null) {
			category = "未知";
		} else if (description.indexOf("地铁") >= 0 //
				|| description.indexOf("中国铁路") >= 0 //
				|| description.indexOf("铁路票务") >= 0 //
				|| description.indexOf("嘀嘀无限") >= 0 //
				|| description.indexOf("中国铁路") >= 0 //
				|| description.indexOf("公共交通") >= 0 //
				|| description.indexOf("轨道交通") >= 0 //
				|| description.indexOf("高德") >= 0 //
				|| description.indexOf("深圳通") >= 0 //

		) {
			category = "交通";
		} else if (description.indexOf("京东") >= 0 //
				|| description.indexOf("阿里云") >= 0 //
				|| description.indexOf("网银在线") >= 0 //
				|| description.indexOf("华润万家") >= 0 //
				|| description.indexOf("中百") >= 0 //
				|| description.indexOf("超市") >= 0 //
				|| description.indexOf("中业爱民") >= 0 //
				|| description.indexOf("便利店") >= 0 //
				|| description.indexOf("世纪华腾商贸") >= 0 //

		) {
			category = "购物";
		} else if (description.indexOf("汤包") >= 0 //
				|| description.indexOf("禾园星语") >= 0 //
				|| description.indexOf("麦香园") >= 0 //
				|| description.indexOf("津味缘") >= 0 //
				|| description.indexOf("味觉馄饨") >= 0 //
				|| description.indexOf("尚客优品") >= 0 //
				|| description.indexOf("嘴爱鱼锡") >= 0 //
				|| description.indexOf("柠檬鱼") >= 0 //
				|| description.indexOf("丁记") >= 0 //
				|| description.indexOf("韬乐园") >= 0 //
				|| description.indexOf("快餐") >= 0 //
				|| description.indexOf("餐饮") >= 0 //
				|| description.indexOf("服务区") >= 0 //
				|| description.indexOf("百果园") >= 0 //
				|| description.indexOf("果然鲜") >= 0 //
				|| description.indexOf("钱大妈") >= 0 //
				|| description.indexOf("肯德基") >= 0 //
				|| description.indexOf("星期五") >= 0 //
				|| description.indexOf("沙县") >= 0 //
				|| description.indexOf("喜家德") >= 0 //
				|| description.indexOf("禾火") >= 0 //
				|| description.indexOf("溢香") >= 0 //
				|| description.indexOf("南昌高新技术产业开发") >= 0 // 城乐人家酒店
		) {
			category = "餐饮";
		} else if (description.indexOf("水滴筹") >= 0 //
		) {
			category = "人情";
		} else if (description.indexOf("中国移动") >= 0 //
				|| description.indexOf("电信") >= 0 //
		) {
			category = "通讯";
		} else if (description.indexOf("医院") >= 0 //
				|| description.indexOf("海王星辰") >= 0) {
			category = "医疗";
		} else if (description.indexOf("儿童") >= 0 //
		) {
			category = "育儿";
		}

		return category;
	}

}
