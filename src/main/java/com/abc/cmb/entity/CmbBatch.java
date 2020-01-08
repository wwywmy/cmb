package com.abc.cmb.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
public class CmbBatch implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3058448172939959979L;

	private Integer id;
	private LocalDate transMonth;// 交易月份
	private List<CmbItem> itemList; // 明细
	
	public int getItemCount() {
		return itemList.size();
	}
	
	public BigDecimal getItemTotal() {
		return itemList.stream().map(CmbItem::getRmbAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
	}
	
	public BigDecimal getSaleItemTotal() {
		return itemList.stream().filter(i->i.getRmbAmount().compareTo(BigDecimal.ZERO)>=0).map(CmbItem::getRmbAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
	}
	
	public BigDecimal getRefundItemTotal() {
		return itemList.stream().filter(i->i.getRmbAmount().compareTo(BigDecimal.ZERO)<0).map(CmbItem::getRmbAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
	}
	
	public LocalDate getTransDateBegin() {
		return itemList.stream().map(CmbItem::getTransDate).distinct().min(LocalDate::compareTo).get();
	}
	
	public LocalDate getTransDateEnd() {
		return itemList.stream().map(CmbItem::getTransDate).distinct().max(LocalDate::compareTo).get();
	}
	
	public List<String> getDescriptionList(){
		return itemList.stream().map(CmbItem::getDescription).distinct().collect(Collectors.toList());
	}
}
