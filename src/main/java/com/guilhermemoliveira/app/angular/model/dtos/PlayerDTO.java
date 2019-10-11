package com.guilhermemoliveira.app.angular.model.dtos;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class PlayerDTO implements Serializable {

	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private static final long serialVersionUID = 1L;
	
	private List<PitDTO> pits;
	
	public PlayerDTO(List<PitDTO> pits) {
		this.pits = Objects.requireNonNull(pits);
	}

}
