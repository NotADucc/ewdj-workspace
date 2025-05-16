package com.springBoot.EWDJ_End.util;

import java.util.List;

import domain.IHasSpeakers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import validator.HasDuplicateSpeakers;

@AllArgsConstructor @Getter
@HasDuplicateSpeakers
public class SpeakersClass implements IHasSpeakers {
	private List<String> speakers;
}