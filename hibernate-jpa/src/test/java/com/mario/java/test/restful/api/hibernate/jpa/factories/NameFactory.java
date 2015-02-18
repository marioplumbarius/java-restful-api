package com.mario.java.test.restful.api.hibernate.jpa.factories;

public final class NameFactory {
	public static String createValidName() {
		return createName("", 20);
	}

	public static String createName(String name, int size){
		if(size == 0) return name;

		return createName(name+="a", --size);
	}
}
