package net.pms.util.jna;

/**
 * This interface provides the possibility for automatic conversion between
 * {@link Enum} and C-style integer enums.
 */
public interface JnaIntEnum<T> {
	int getValue();
	T typeForValue(int value);
}
