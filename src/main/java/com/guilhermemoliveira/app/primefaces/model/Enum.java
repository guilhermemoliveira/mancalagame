package com.guilhermemoliveira.app.primefaces.model;

public class Enum {
	
	public enum WhichPlayer {

		PLAYER_ONE(0) {
			@Override
			public String toString() {
				return "Player 1";
			}
		},
		PLAYER_TWO(1) {
			@Override
			public String toString() {
				return "Player 2";
			}
		};

		private int value;

		private WhichPlayer(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

	}
}
