package com.github.ocelotwars.engine;

import java.util.Objects;

public class Position {
	public final int x;
	public final int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position normalize(Dimension dimension) {
		int x = positiveModulo(this.x, dimension.width);
		int y = positiveModulo(this.y, dimension.height);
		return new Position(x, y);
	}

	private int positiveModulo(int i, int modulus) {
		int result = i % modulus;
		if (result < 0) {
			result += modulus;
		}
		return result;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Position other = (Position) obj;
		return other.x == x
			&& other.y == y;
	}

}
