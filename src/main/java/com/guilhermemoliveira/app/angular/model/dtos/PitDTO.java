package com.guilhermemoliveira.app.angular.model.dtos;

import java.io.Serializable;
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
public class PitDTO implements Serializable {

	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private static final long serialVersionUID = 1L;
	
	private Integer stones;
	
	public PitDTO(Integer stones) {
		this.stones = Objects.requireNonNull(stones);
	}

}
