package com.guilhermemoliveira.app.angular.model.dtos;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
	
	public PitDTO(@NotNull @Min(0) Integer stones) {
		this.stones = stones;
	}

}
