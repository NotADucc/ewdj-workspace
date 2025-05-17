package com.springBoot.EWDJ_End.util;

import java.util.List;

import domain.IHasSpeakers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import validator.HasEmptySpeakers;

@AllArgsConstructor @Getter
@HasEmptySpeakers
public class EmptySpeakersClass implements IHasSpeakers {
	private List<String> speakers;
}