package ua.kapitonenko.domain;

import org.apache.commons.lang3.StringUtils;
import ua.kapitonenko.config.Application;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReportField extends Model implements Serializable {
	private String name;
	private String bundle = Application.getParam(Application.MESSAGE_BUNDLE);
	private int fractionalDigits;
	private BigDecimal salesValue;
	private BigDecimal refundsValue;
	
	public ReportField() {
	}
	
	public ReportField(String name, BigDecimal salesValue, BigDecimal refundsValue, String bundle, int fractionalDigits) {
		this.name = name;
		this.salesValue = salesValue;
		this.refundsValue = refundsValue;
		this.fractionalDigits = fractionalDigits;
		
		if (!StringUtils.isEmpty(bundle)) {
			this.bundle = bundle;
		}
	}
	
	public String getBundle() {
		return bundle;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getSalesValue() {
		return salesValue;
	}
	
	public void setSalesValue(BigDecimal salesValue) {
		this.salesValue = salesValue;
	}
	
	public BigDecimal getRefundsValue() {
		return refundsValue;
	}
	
	public void setRefundsValue(BigDecimal refundsValue) {
		this.refundsValue = refundsValue;
	}
	
	public int getFractionalDigits() {
		return fractionalDigits;
	}
}
