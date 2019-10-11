package com.guilhermemoliveira.app.angular.model.entities;

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
public class Player implements Serializable {

	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private static final long serialVersionUID = 1L;
	
	private List<Pit> pits;
	
	public Player(List<Pit> pits) {
		this.pits = Objects.requireNonNull(pits);
	}

}
