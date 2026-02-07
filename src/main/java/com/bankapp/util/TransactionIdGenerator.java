package com.bankapp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class TransactionIdGenerator {

	public static String generate() {
		return "TXN" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
				+ UUID.randomUUID().toString().substring(0, 5).toUpperCase();
	}
}
