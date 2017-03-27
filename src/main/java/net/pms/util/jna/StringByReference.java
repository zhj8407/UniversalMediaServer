/*
 * Universal Media Server, for streaming any media to DLNA compatible renderers
 * based on the http://www.ps3mediaserver.org. Copyright (C) 2012 UMS
 * developers.
 *
 * This program is a free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; version 2 of the License only.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package net.pms.util.jna;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import com.sun.jna.FromNativeContext;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;


public class StringByReference extends PointerType {

	/**
	 * Creates an unallocated {@link StringByReference}.
	 */
	public StringByReference() {
		super();
	}

	/**
	 * @param dataSize the size to allocate in bytes excluding the {@code null}
	 *            terminator.
	 */
	public StringByReference(long dataSize) {
		super(dataSize < 1 ? Pointer.NULL : new Memory(dataSize + 1));
	}

	/**
	 * Creates a {@link StringByReference} containing {@code value} allocated
	 * to {@code value}'s byte length encoded with
	 * {@link Native#getDefaultStringEncoding}. The new
	 * {@link StringByReference} will be encoded with
	 * {@link Native#getDefaultStringEncoding}.
	 *
	 * @param value the string content.
	 */
	public StringByReference(String value) {
		this(value, Charset.forName(Native.getDefaultStringEncoding()));
	}

	/**
	 * Creates a {@link StringByReference} containing {@code value} allocated
	 * to {@code value}'s byte length encoded with {@code charset}.
	 *
	 * @param value the string content.
	 * @param charset the {@link Charset} to use for encoding.
	 */
	public StringByReference(String value, Charset charset) {
		super(value == null ? Pointer.NULL : new Memory(value.getBytes(charset).length + 1));
		if (value != null) {
			setValue(value, charset);
		}
	}

	/**
	 * Creates a {@link StringByReference} containing {@code value} allocated
	 * to {@code value}'s byte length encoded with {@code charsetName}.
	 *
	 * @param value the string content.
	 * @param charsetName the name of the {@link Charset} to use for encoding.
	 */
	public StringByReference(String value, String charsetName) {
		super(value == null ? Pointer.NULL : new Memory(value.getBytes(Charset.forName(charsetName)).length + 1));
		if (value != null) {
			setValue(value, charsetName);
		}
	}

	/**
	 * Sets this {@link StringByReference}'s content to that of {@code value}
	 * using encoding {@link Native#getDefaultStringEncoding}. Make sure that
	 * sufficient space is allocated, or an {@link IllegalArgumentException}
	 * will be thrown.
	 *
	 * @param value the new string content.
	 * @param charset a supported {@link Charset} to use for encoding.
	 * @throws IllegalArgumentException if the new content is larger then the
	 *             currently allocated memory or the encoding is unsupported.
	 */
	public void setValue(String value) {
		setValue(value, Native.getDefaultStringEncoding());
	}

	/**
	 * Sets this {@link StringByReference}'s content to that of {@code value}.
	 * Make sure that sufficient space is allocated, or an
	 * {@link IllegalArgumentException} will be thrown.
	 *
	 * @param value the new string content.
	 * @param charset a supported {@link Charset} to use for encoding.
	 * @throws IllegalArgumentException if the new content is larger then the
	 *             currently allocated memory or the encoding is unsupported.
	 */
	public void setValue(String value, Charset charset) {
		setValue(value, charset.name());
	}

	/**
	 * Sets this {@link StringByReference}'s content to that of {@code value}.
	 * Make sure that sufficient space is allocated, or an
	 * {@link IllegalArgumentException} will be thrown.
	 *
	 * @param value the new string content.
	 * @param charsetName a valid and supported {@link Charset} name to use for
	 *            encoding.
	 * @throws IllegalArgumentException if the new content is larger then the
	 *             currently allocated memory or the encoding is unsupported.
	 */
    public void setValue(String value, String charsetName) {
    	try {
			if (value.getBytes(charsetName).length > getAllocatedSize()) {
				throw new IllegalArgumentException(
					"String length " +
					value.getBytes(charsetName).length +
					" exceeds allocated size " +
					getAllocatedSize()
				);
			}
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported encoding: " + charsetName, e);
		}
        getPointer().setString(0, value, charsetName);
    }

	/**
	 * Gets this {@link StringByReference}'s content using
	 * {@link Native#getDefaultStringEncoding} for decoding.
	 *
	 * @return The content as a {@link String}.
	 */
    public String getValue() {
    	return getValue(Native.getDefaultStringEncoding());
    }

	/**
	 * Gets this {@link StringByReference}'s content using
	 * {@code charset} for decoding.
	 *
	 * @param charset a supported {@link Charset} to use for decoding.
	 * @return The content as a {@link String}.
	 */
    public String getValue(Charset charset) {
    	return getValue(charset.name());
    }

	/**
	 * Gets this {@link StringByReference}'s content using {@code charsetName}
	 * for decoding.
	 *
	 * @param charsetName a valid and supported {@link Charset} name to use for
	 *            decoding.
	 * @return The content as a {@link String}.
	 */
    public String getValue(String charsetName) {
        return getPointer() == null ? null : getPointer().getString(0, charsetName);
    }

	/**
	 * Gets the size in bytes allocated to this {@link StringByReference}
	 * excluding byte for the {@code null} terminator.
	 *
	 * @return The allocated size in bytes.
	 */
    public long getAllocatedSize() {
    	if (getPointer() instanceof Memory) {
    		return Math.max(((Memory) getPointer()).size() - 1, 0);
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
}
