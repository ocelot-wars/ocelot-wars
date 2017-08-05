package com.github.ocelotwars.service;

public interface MessageInterpreter {

	void visitRegister(Register register);
	
	void visitAccept(Accept accept);
}
