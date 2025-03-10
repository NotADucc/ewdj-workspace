package domain;

import java.util.List;

import lombok.Getter;

@Getter
public class ColorExpertBean implements ColorBean{

	private final List<String> colorsList;

	public ColorExpertBean() {
		colorsList = List.of("light", "brown", "dark");
	}

}