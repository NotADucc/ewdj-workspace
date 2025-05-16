package com.springBoot.EWDJ_End.util;

import domain.IHasBeamer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import validator.BeamerChecksum;


@AllArgsConstructor @Getter
@BeamerChecksum(divisor = 97)
public class BeamerClass implements IHasBeamer {
	private String beamercode;
	private Integer beamercheck;
}