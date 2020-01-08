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
	@ExcelProperty(value = "���", index = 0)
	private Integer id;

	@ExcelProperty(value = "������", index = 1, converter = LocalDateConverter.class)
	private LocalDate transDate;// ������

	@ExcelProperty(value = "������", index = 2, converter = LocalDateConverter.class)
	private LocalDate postDate;// ������

	@ExcelProperty(value = "����ժҪ", index = 3)
	private String description;// ����ժҪ

	@ExcelProperty(value = "����ҽ��", index = 4)
	private BigDecimal rmbAmount;// ����ҽ��

	@ExcelProperty(value = "����ĩ��λ", index = 5)
	private String cardNumber;// ����ĩ��λ

	@ExcelProperty(value = "���׵ؽ��", index = 6)
	private BigDecimal originalTransAmount;// ���׵ؽ��

	@ExcelProperty(value = "���׵ص�", index = 7)
	private String countryOrArea;// ���׵ص�

	@ExcelProperty(value = "���", index = 8)
	private String category;// ���

	public String getCategory() {
		category = "δ֪";
		if (description == null) {
			category = "δ֪";
		} else if (description.indexOf("����") >= 0 //
				|| description.indexOf("�й���·") >= 0 //
				|| description.indexOf("��·Ʊ��") >= 0 //
				|| description.indexOf("��������") >= 0 //
				|| description.indexOf("�й���·") >= 0 //
				|| description.indexOf("������ͨ") >= 0 //
				|| description.indexOf("�����ͨ") >= 0 //
				|| description.indexOf("�ߵ�") >= 0 //
				|| description.indexOf("����ͨ") >= 0 //

		) {
			category = "��ͨ";
		} else if (description.indexOf("����") >= 0 //
				|| description.indexOf("������") >= 0 //
				|| description.indexOf("��������") >= 0 //
				|| description.indexOf("�������") >= 0 //
				|| description.indexOf("�а�") >= 0 //
				|| description.indexOf("����") >= 0 //
				|| description.indexOf("��ҵ����") >= 0 //
				|| description.indexOf("������") >= 0 //
				|| description.indexOf("���ͻ�����ó") >= 0 //

		) {
			category = "����";
		} else if (description.indexOf("����") >= 0 //
				|| description.indexOf("��԰����") >= 0 //
				|| description.indexOf("����԰") >= 0 //
				|| description.indexOf("��ζԵ") >= 0 //
				|| description.indexOf("ζ�����") >= 0 //
				|| description.indexOf("�п���Ʒ") >= 0 //
				|| description.indexOf("�찮����") >= 0 //
				|| description.indexOf("������") >= 0 //
				|| description.indexOf("����") >= 0 //
				|| description.indexOf("���԰") >= 0 //
				|| description.indexOf("���") >= 0 //
				|| description.indexOf("����") >= 0 //
				|| description.indexOf("������") >= 0 //
				|| description.indexOf("�ٹ�԰") >= 0 //
				|| description.indexOf("��Ȼ��") >= 0 //
				|| description.indexOf("Ǯ����") >= 0 //
				|| description.indexOf("�ϵ»�") >= 0 //
				|| description.indexOf("������") >= 0 //
				|| description.indexOf("ɳ��") >= 0 //
				|| description.indexOf("ϲ�ҵ�") >= 0 //
				|| description.indexOf("�̻�") >= 0 //
				|| description.indexOf("����") >= 0 //
				|| description.indexOf("�ϲ����¼�����ҵ����") >= 0 // �����˼ҾƵ�
		) {
			category = "����";
		} else if (description.indexOf("ˮ�γ�") >= 0 //
		) {
			category = "����";
		} else if (description.indexOf("�й��ƶ�") >= 0 //
				|| description.indexOf("����") >= 0 //
		) {
			category = "ͨѶ";
		} else if (description.indexOf("ҽԺ") >= 0 //
				|| description.indexOf("�����ǳ�") >= 0) {
			category = "ҽ��";
		} else if (description.indexOf("��ͯ") >= 0 //
		) {
			category = "����";
		}

		return category;
	}

}
