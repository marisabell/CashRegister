package ua.kapitonenko.app.service;

import ua.kapitonenko.app.domain.Receipt;
import ua.kapitonenko.app.domain.records.ReceiptRecord;
import ua.kapitonenko.app.domain.records.ReceiptType;

import java.util.List;

public interface ReceiptService {
	
	boolean update(Receipt calculator);
	
	boolean create(Receipt calculator);
	
	List<Receipt> getReceiptList(int offset, int limit);
	
	List<Receipt> getReceiptList(Long cashboxId);
	
	ReceiptRecord findById(Long receiptId);
	
	boolean cancel(Long receiptId);
	
	long getReceiptsCount();
	
/*	List<Receipt> getSales(Long cashboxId);
	
	List<Receipt> getRefunds(Long cashboxId);*/
	
	ReceiptType findReceiptType(Long id);
}