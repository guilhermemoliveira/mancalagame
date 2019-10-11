package com.guilhermemoliveira.app.angular.model.entities;

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
public class Pit implements Serializable {

	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private static final long serialVersionUID = 1L;
	
	private Integer stones;
	
	public Pit(Integer stones) {
		this.stones = Objects.requireNonNull(stones);
	}

}
