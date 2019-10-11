package com.guilhermemoliveira.app.primefaces.model;

import com.guilhermemoliveira.app.primefaces.model.Enum.WhichPlayer;

import lombok.Data;

@Data
public class Turn {
	private WhichPlayer whichPlayer;
	private String message;
}
