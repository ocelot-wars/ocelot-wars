package com.github.ocelotwars.service;

public class Accept implements Message {

	@Override
	public void apply(MessageInterpreter messageInterpreter) {
		messageInterpreter.visitAccept(this);
	}

}
