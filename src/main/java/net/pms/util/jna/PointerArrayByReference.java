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

import com.sun.jna.FromNativeContext;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;


public class PointerArrayByReference extends PointerType {

	/**
	 * Creates an unallocated {@link PointerArrayByReference}.
	 */
	public PointerArrayByReference() {
		super();
	}
	/**
	 * Creates a new instance and allocates space for an array of {@code size} elements.
	 *
	 * @param size the number of {@link Pointer}'s in the array.
	 */
	public PointerArrayByReference(long size) {
		super(new Memory(Pointer.SIZE * size));
		this.size = size;
	}

	protected long size;

	/**
	 * Sets a new array size allocating memory as needed.
	 *
	 * <b>Please note that calling {@link #setSize} while holding a
	 * {@link Pointer} allocated by native code will lead to memory leak as the
	 * reference to the allocated memory is lost</b>. Instead, free the pointer
	 * by sending {@link #getPointer()} to the native free function before
	 * calling {@link #setSize}. {@link #isGarbageCollected()} can be used to
	 * check if the currently held {@link Pointer} is allocated by Java and will
	 * be deallocated by the GC or not.
	 *
	 * @param size the new array size to allocate. Setting the size to 0 will
	 *            set the held {@link Pointer} to {@code null}.
	 */
	public void setSize(long size) {
		if (size < 1) {
			super.setPointer(Pointer.NULL);
			this.size = 0;
		} else if (
			getPointer() instanceof Memory &&
			Pointer.SIZE * size < ((Memory) getPointer()).size() &&
			Math.abs(((Memory) getPointer()).size() - Pointer.SIZE * size) < 100
		) {
			// Reuse the allocated memory if the reduction in size is less than 100 bytes
			this.size = size;
		} else {
			// Allocate new memory
			super.setPointer(new Memory(Pointer.SIZE * size));
			this.size = size;
		}
	}

	public long getSize() {
		return size;
	}

	/**
	 * @return Whether or not the currently held {@link Pointer} is deallocated
	 *         by Java's Garbage Collector or not. If it isn't, you have to free
	 *         it manually by sending {@link #getPointer()} to the native free
	 *         function after use.
	 */
	public boolean isGarbageCollected() {
		return getPointer() == null || getPointer() instanceof Memory;
	}

	/**
	 * Stores the values from {@code pointers} allocating memory as needed.
	 *
	 * <b>Please note that calling {@link #setArray} while holding a
	 * {@link Pointer} allocated by native code will lead to memory leak as the
	 * reference to the allocated memory is lost</b>. Instead, free the pointer
	 * by sending {@link #getPointer()} to the native free function before
	 * calling {@link #setArray}. {@link #isGarbageCollected()} can be used to
	 * check if the currently held {@link Pointer} is allocated by Java and will
	 * be deallocated by the GC or not.
	 *
	 * @param pointers the array of {@link Pointer}s to write to the referenced
	 *            memory. Sending an empty array will set the held
	 *            {@link Pointer} to {@code null}.
	 */
	public void setArray(Pointer[] pointers) {
		if (pointers == null) {
			throw new NullPointerException("pointers cannot be null");
		}
		setSize(pointers.length);
		if (pointers.length > 0) {
			for (int i = 0; i < size; i++) {
				getPointer().setPointer(i * Pointer.SIZE, pointers[i]);
			}
		}
	}

	/**
	 * Generates and returns an array of {@link Pointer}s. This is a copy of the
	 * referenced memory and any changes will not be reflected in the referenced
	 * memory.
	 *
	 * @return An array containing the values of the referenced {@link Pointer} array.
	 */
	public Pointer[] getArray() {
		if (getPointer() == null) {
			return null;
		}
		if (size == 0) {
			return new Pointer[0];
		}
		if (size > Integer.MAX_VALUE) {
			throw new UnsupportedOperationException("Array to big, please read it \"manually\" using getPointer.readPointer()");
		}
		return getPointer().getPointerArray(0, (int) size);
	}

	/**
	 * This is not allowed for {@link PointerArrayByReference}.
	 *
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void setPointer(Pointer p) {
		throw new UnsupportedOperationException("setPointer() is not supported");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object fromNative(Object nativeValue, FromNativeContext context) {
        // Always pass along null pointer values
        if (nativeValue == null) {
            return null;
        }
        super.setPointer((Pointer) nativeValue);
        return this;
	}

}
