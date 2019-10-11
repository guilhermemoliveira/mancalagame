package com.guilhermemoliveira.app.model;

import com.guilhermemoliveira.app.model.Enum.WhichPlayer;

import lombok.Data;

@Data
public class Turn {
	private WhichPlayer whichPlayer;
	private String message;
}
