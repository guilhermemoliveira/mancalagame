package br.com.guilhermedeoliveira.bolgame.model;

import br.com.guilhermedeoliveira.bolgame.model.Enum.WhichPlayer;
import lombok.Data;

@Data
public class Turn {
	private WhichPlayer whichPlayer;
	private String message;
}
