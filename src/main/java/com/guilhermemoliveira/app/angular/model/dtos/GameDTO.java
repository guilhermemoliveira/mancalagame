package com.guilhermemoliveira.app.angular.model.dtos;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
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
public class GameDTO implements Serializable {

	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private static final long serialVersionUID = 1L;
	
	private List<PlayerDTO> players;
	
	public GameDTO(@NotNull @NotEmpty @Min(0) List<PlayerDTO> players) {
		this.players = players;
	}

}
