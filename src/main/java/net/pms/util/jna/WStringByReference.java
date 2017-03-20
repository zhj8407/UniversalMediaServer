package net.pms.util.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;


public class WStringByReference extends PointerType {

	/**
	 * Creates an unallocated {@link WStringByReference}.
	 */
	public WStringByReference() {
		super();
	}

	/**
	 * @param dataSize the size to allocate in bytes excluding the {@code null}
	 *            terminator.
	 */
	public WStringByReference(int dataSize) {
		super(dataSize < 1 ? Pointer.NULL : new Memory(dataSize + Native.WCHAR_SIZE));
	}

	/**
	 * Creates a {@link WStringByReference} containing {@code value} allocated
	 * to {@code value}'s byte length using {@code wchar_t}.
	 *
	 * @param value the string content.
	 */
	public WStringByReference(String value) {
		super(value == null ? Pointer.NULL : new Memory(getNumberOfBytes(value) + Native.WCHAR_SIZE));
		if (value != null) {
			setValue(value);
		}
	}

	/**
	 * Sets this {@link WStringByReference}'s content to that of {@code value}.
	 * Make sure that sufficient space is allocated, or an
	 * {@link IllegalArgumentException} will be thrown.
	 *
	 * @param value the new string content.
	 * @throws IllegalArgumentException if the new content is larger then the
	 *             currently allocated memory.
	 */
    public void setValue(String value) {
		if (getNumberOfBytes(value) > getAllocatedSize()) {
			throw new IllegalArgumentException(
				"String length " +
				getNumberOfBytes(value) +
				" exceeds allocated size " +
				getAllocatedSize()
			);
		}
        getPointer().setWideString(0, value);
    }

	/**
	 * Gets this {@link WStringByReference}'s content.
	 *
	 * @return The content as a {@link String}.
	 */
    public String getValue() {
        return getPointer() == null ? null : getPointer().getWideString(0);
    }

	/**
	 * Gets the size in bytes allocated to this {@link WStringByReference}
	 * excluding byte for the {@code null} terminator.
	 *
	 * @return The allocated size in bytes.
	 */
    public long getAllocatedSize() {
    	if (getPointer() instanceof Memory) {
    		return Math.max(((Memory) getPointer()).size() - Native.WCHAR_SIZE, 0);
    	}
    	return 0;
    }

    @Override
	public Object fromNative(Object nativeValue, FromNativeContext context) {
        // Always pass along null pointer values
        if (nativeValue == null) {
            return null;
        }
    	if (getPointer() == null) {
    		StringByReference sbr = new StringByReference();
    		sbr.setPointer((Pointer) nativeValue);
    		return sbr;
    	}
    	this.setPointer((Pointer) nativeValue);
    	return this;
	}

    @Override
    public String toString() {
    	if (getPointer() == null) {
    		return "null";
    	}
    	return getValue();
    }

	/**
	 * Calculates the length in bytes of {@code string} as a native
	 * {@code wstring}.
	 *
	 * @param string the string to evaluate.
	 * @return the byte-length of {@code string}.
	 */
    public static int getNumberOfBytes(String string) {
    	if (string == null) {
    		return 0;
    	}
    	if (Native.WCHAR_SIZE == 4) {
    		return string.codePointCount(0, string.length()) * Native.WCHAR_SIZE;
    	}
		final int length = string.length();
		int byteLength = 0;
		for (int offset = 0; offset < length; ) {
		   int codepoint = string.codePointAt(offset);
		   byteLength += Character.charCount(codepoint) * Native.WCHAR_SIZE;
		   offset += Character.charCount(codepoint);
		}
    	return byteLength;
    }
}
