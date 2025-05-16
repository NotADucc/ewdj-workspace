package com.springBoot.EWDJ_End.util;

import java.util.List;

import domain.IHasSpeakers;
import validator.HasDuplicateSpeakers;

@HasDuplicateSpeakers
public class SpeakersClass implements IHasSpeakers {
	private List<String> speakers;

	public SpeakersClass(List<String> speakers) {
		this.speakers = speakers;
	}

	@Override
	public List<String> getSpeakers() {
		return speakers;
	}
}