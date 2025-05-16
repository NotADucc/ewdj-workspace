package dto.model;

import lombok.Getter;

@Getter
public record ErrorOutputDTO {
	private String message;
	private int status;

	public ErrorOutputDTO(String message, int status) {
		this.message = message;
		this.status = status;
	}
}