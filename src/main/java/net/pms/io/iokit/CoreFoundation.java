/*
 * Oshi (https://github.com/oshi/oshi)
 *
 * Copyright (c) 2010 - 2017 The Oshi Project Team
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Maintainers:
 * dblock[at]dblock[dot]org
 * widdis[at]gmail[dot]com
 * enrico.bianchi[at]gmail[dot]com
 *
 * Contributors:
 * https://github.com/oshi/oshi/graphs/contributors
 */
package net.pms.io.iokit;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.pms.util.jna.JnaIntEnum;
import net.pms.util.jna.JnaEnumTypeMapper;
import net.pms.util.jna.StringByReference;
import net.pms.util.jna.WStringByReference;
import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.ptr.ByReference;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.ShortByReference;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * CoreFoundation framework for power supply stats. This class should be
 * considered non-API as it may be removed if/when its code is incorporated into
 * the JNA project.
 * <p>
 * This is a copy of {@link oshi.jna.platform.mac.CoreFoundation} where all
 * members are changed from package private to public.
 *
 * @author widdis[at]gmail[dot]com
 * TODO: Fix this and header
 */
@SuppressFBWarnings("UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD")
public interface CoreFoundation extends Library {
	Map<String, Object> options = Collections.unmodifiableMap(new HashMap<String, Object>() {
		private static final long serialVersionUID = 1L;
		{
			put(Library.OPTION_TYPE_MAPPER, new JnaEnumTypeMapper());
		}
	});
    CoreFoundation INSTANCE = (CoreFoundation) Native.loadLibrary("CoreFoundation", CoreFoundation.class, options);

    static final CFAllocatorRef ALLOCATOR = INSTANCE.CFAllocatorGetDefault();

	/**
	 * Releases a Core Foundation object.
	 * <p>
	 * References are not owned if created by functions using "Get". Use
	 * {@link #CFRetain} if object retention is required, and then
	 * {@link #CFRelease} later. Do not use {@link #CFRelease} if you do not
	 * own.
	 * <p>
	 * If the retain count of {@code cf} becomes zero the memory allocated to
	 * the object is deallocated and the object is destroyed. If you create,
	 * copy, or explicitly retain (see {@link #CFRetain}) a Core Foundation
	 * object, you are responsible for releasing it when you no longer need it
	 * (see <a href=
	 * "https://developer.apple.com/library/content/documentation/CoreFoundation/Conceptual/CFMemoryMgmt/CFMemoryMgmt.html#//apple_ref/doc/uid/10000127i"
	 * >Memory Management Programming Guide for Core Foundation</a>).
	 *
	 * @param cf a {@code CFType} object to release. This value must not be
	 *            {@code null}.
	 */
    void CFRelease(CFTypeRef cf);

	/**
	 * Retains a Core Foundation object.
	 *
	 * You should retain a Core Foundation object when you receive it from
	 * elsewhere (that is, you did not create or copy it) and you want it to
	 * persist. If you retain a Core Foundation object you are responsible for
	 * releasing it (see <a href=
	 * "https://developer.apple.com/library/content/documentation/CoreFoundation/Conceptual/CFMemoryMgmt/CFMemoryMgmt.html#//apple_ref/doc/uid/10000127i"
	 * >Memory Management Programming Guide for Core Foundation</a>).
	 *
	 * @param cf the {@code CFType} object to retain. This value must not be
	 *            {@code null}.
	 * @return The input value, {@code cf}.
	 */
    CFTypeRef CFRetain(CFTypeRef cf);

	/**
	 * Returns the unique identifier of an opaque type to which a Core
	 * Foundation object belongs.
	 * <p>
	 * This function returns a value that uniquely identifies the opaque type of
	 * any Core Foundation object. You can compare this value with the known
	 * {@code CFTypeID} identifier obtained with a “GetTypeID” method specific to a
	 * type, for example {@link #CFArrayGetTypeID}. These values might change from
	 * release to release or platform to platform.
	 *
	 * @param cf the {@code CFType} object to examine.
	 * @return A value of type {@code CFTypeID} that identifies the opaque type
	 *         of {@code cf}.
	 */
    int CFGetTypeID(CFTypeRef cf);

    /** Untyped type reference */
    public class CFTypeRef extends PointerType {

    	public CFTypeRef() {
    		super();
    	}

    	public CFTypeRef(Pointer p) {
    		super(p);
    	}
    }

    public class CFNumberRef extends CFTypeRef {

		public CFNumberRef() {
			super();
		}

		public CFNumberRef(Pointer p) {
			super(p);
		}

		public CFNumberRef(CFTypeRef cfNumber) {
			super(cfNumber.getPointer());
		}

    	public static CFNumberRef toCFNumber(byte value) {
    		return INSTANCE.CFNumberCreate(ALLOCATOR, CFNumberType.kCFNumberSInt8Type, new ByteByReference(value));
    	}

    	public static CFNumberRef toCFNumber(short value) {
    		return INSTANCE.CFNumberCreate(ALLOCATOR, CFNumberType.kCFNumberSInt16Type, new ShortByReference(value));
    	}

    	public static CFNumberRef toCFNumber(int value) {
    		return INSTANCE.CFNumberCreate(ALLOCATOR, CFNumberType.kCFNumberSInt32Type, new IntByReference(value));
    	}

    	public static CFNumberRef toCFNumber(long value) {
    		return INSTANCE.CFNumberCreate(ALLOCATOR, CFNumberType.kCFNumberSInt64Type, new LongByReference(value));
    	}

    	public static CFNumberRef toCFNumber(char value) {
    		return INSTANCE.CFNumberCreate(ALLOCATOR, CFNumberType.kCFNumberCharType, new ShortByReference((short) value));
    	}

    	public static CFNumberRef toCFNumber(float value) {
    		return INSTANCE.CFNumberCreate(ALLOCATOR, CFNumberType.kCFNumberFloat32Type, new FloatByReference(value));
    	}

    	public static CFNumberRef toCFNumber(double value) {
    		return INSTANCE.CFNumberCreate(ALLOCATOR, CFNumberType.kCFNumberFloat64Type, new DoubleByReference(value));
    	}

    	public Number getValue() {
    		if (this.getPointer() == null) {
    			return null;
    		}
    		CFNumberType numberType = INSTANCE.CFNumberGetType(this);
    		Memory value = new Memory(8);
    		INSTANCE.CFNumberGetValue(this, numberType, value);
    		switch (numberType) {
				case kCFNumberCFIndexType:
				case kCFNumberSInt32Type:
					return value.getInt(0);
				case kCFNumberCGFloatType:
				case kCFNumberMaxType:
					if (NativeLong.SIZE == 8) {
						return value.getDouble(0);
					} else {
						return value.getFloat(0);
					}
				case kCFNumberCharType:
					return (int) value.getChar(0) ;
				case kCFNumberDoubleType:
				case kCFNumberFloat64Type:
					return value.getDouble(0);
				case kCFNumberFloatType:
				case kCFNumberFloat32Type:
					return value.getFloat(0);
				case kCFNumberLongLongType:
				case kCFNumberSInt64Type:
					return value.getLong(0);
				case kCFNumberIntType:
				case kCFNumberLongType:
				case kCFNumberNSIntegerType:
					return value.getNativeLong(0);
				case kCFNumberSInt16Type:
				case kCFNumberShortType:
					return value.getShort(0);
				case kCFNumberSInt8Type:
					return value.getByte(0);
				default:
					throw new IllegalStateException("Unimplemented value " + numberType);
    		}
    	}

		@Override
		public String toString() {
			if (this.getPointer() == null) {
				return "null";
			}
			return getValue().toString();
		}
		
		/**
		 * Use this as a substitute for cast from any {@link CFTypeRef}
		 * instance.
		 * <p>
		 * {@link CFTypeRef} can't be cast to {@link CFNumberRef} if gotten from
		 * native code because {@link PointerType#fromNative} don't know the
		 * "real" type and thus calls {@link CFTypeRef}'s constructor instead of
		 * {@link CFNumberRef}'s constructor. This instantiates a new
		 * {@link CFNumberRef} and transfers the {@link Pointer} to achieve the
		 * same result.
		 * 
		 * @param cfType the {@link CFTypeRef} to "cast" to {@link CFNumberRef}.
		 * @return The {@link CFNumberRef} instance.
		 */
		public static CFNumberRef toCFNumberRef(CFTypeRef cfType) {
			return new CFNumberRef(cfType == null ? null : cfType.getPointer());
		}
    }

	/**
	 * Creates a {@code CFNumber} with the given value. The type of number
	 * pointed to by the {@code valuePtr} is specified by {@code theType}. If
	 * {@code theType} is a floating point type and the value represents one of
	 * the infinities or NaN, the well-defined {@code CFNumber} for that value
	 * is returned. If either of {@code valuePtr} or {@code theType} is an
	 * invalid value, the result is undefined.
	 *
	 * @param allocator the {@code CFAllocator} to use when allocating memory.
	 * @param theType the {@link CFNumberType} to create.
	 * @param value the initial value of the created {@link CFNumberRef} as a
	 *            {@link ByReference}.
	 */
    CFNumberRef CFNumberCreate(CFAllocatorRef allocator, CFNumberType theType, PointerType value);

	/**
	 * Returns the storage format {@link CFNumberType} of the {@code CFNumber}.
	 * Note that this is not necessarily the type provided in
	 * {@link #CFNumberCreate}.
	 *
	 * @param number the {@link CFNumberRef} whose type to return.
	 * @return The storage format of the {@code CFNumber}.
	 */
    CFNumberType CFNumberGetType(CFNumberRef number);

    /**
     * @param number the {@link CFNumberRef} whose size to return.
     * @return The size in bytes of the type of the number.
     */
    int CFNumberGetByteSize(CFNumberRef number);

	/**
	 * @param number the {@link CFNumberRef} to evaluate.
	 * @return {@code true} if the type of the {@code CFNumber}'s value is one
	 *         of the defined floating point types.
	 */
    boolean CFNumberIsFloatType(CFNumberRef number);

	/**
	 * Copies the {@code CFNumber}'s value into the space pointed to by
	 * {@code value}, as the specified type. If conversion needs to take place,
	 * the conversion rules follow human expectation and not C's promotion and
	 * truncation rules. If the conversion is lossy, or the value is out of
	 * range, false is returned. Best attempt at conversion will still be in
	 * {@code value}.
	 *
	 * @param cfNumber the {@link CFNumberRef} whose value to get.
	 * @param theType {@code cfNumber}'s {@link CFNumberType}.
	 * @param value (Output) A pointer to where the retrieved value should be
	 *            written.
	 * @return {@code false} if the conversion is lossy or the value is out of
	 *         range, {@code true} otherwise.
	 */
    boolean CFNumberGetValue(CFNumberRef cfNumber, CFNumberType theType, ByReference value);

	/**
	 * Copies the {@code CFNumber}'s value into the space pointed to by
	 * {@code value}, as the specified type. If conversion needs to take place,
	 * the conversion rules follow human expectation and not C's promotion and
	 * truncation rules. If the conversion is lossy, or the value is out of
	 * range, false is returned. Best attempt at conversion will still be in
	 * {@code value}.
	 *
	 * @param cfNumber the {@link CFNumberRef} whose value to get.
	 * @param theType {@code cfNumber}'s {@link CFNumberType}.
	 * @param value (Output) A pointer to where the retrieved value should be
	 *            written.
	 * @return {@code false} if the conversion is lossy or the value is out of
	 *         range, {@code true} otherwise.
	 */
    boolean CFNumberGetValue(CFNumberRef cfNumber, CFNumberType theType, Pointer value);

    public class CFBooleanRef extends CFTypeRef {
    }

    /**
     * Returns the Core Foundation type identifier for the {@code CFBoolean} opaque type.
     *
     * @return The Core Foundation type identifier for {@code CFBoolean} opaque type.
     */
    int CFBooleanGetTypeID();

	/**
	 * Returns the value of a {@code CFBoolean} object as a standard C type {@code Boolean}.
	 *
	 * @param booleanRef the {@code boolean} to examine.
	 * @return The value of boolean.
	 */
	boolean CFBooleanGetValue(CFBooleanRef booleanRef);


    public class CFArrayRef extends CFTypeRef {
    }

    public class CFMutableArrayRef extends CFArrayRef {
    }

	/**
	 * Returns the type identifier for the {@code CFArray} opaque type.
	 * <p>
	 * {@code CFMutableArray} objects have the same type identifier as
	 * {@code CFArray} objects.
	 *
	 * @return The type identifier for the {@code CFArray} opaque type.
	 */
    int CFArrayGetTypeID();

	/**
	 * Returns the number of values currently in the array.
	 *
	 * @param theArray The array to be queried. If this parameter is not a valid
	 *            {@code CFArray}, the behavior is undefined.
	 * @return The number of values in the array.
	 */
    int CFArrayGetCount(CFArrayRef array);

	/**
	 * Retrieves the value at the given index.
	 *
	 * @param theArray The array to be queried. If this parameter is not a valid
	 *            {@code CFArray}, the behavior is undefined.
	 * @param idx The index of the value to retrieve. If the index is outside
	 *            the index space of the array (0 to N-1 inclusive, where N is
	 *            the count of the array), the behavior is undefined.
	 * @return The value with the given index in the array.
	 */
    CFTypeRef CFArrayGetValueAtIndex(CFArrayRef theArray, int idx);

	/**
	 * Adds the value to the array giving it a new largest index.
	 *
	 * @param theArray The array to which the value is to be added. If this
	 *            parameter is not a valid mutable {@code CFArray}, the behavior
	 *            is undefined.
	 * @param value The value to add to the array. The value is retained by the
	 *            array using the retain callback provided when the array was
	 *            created. If the value is not of the sort expected by the
	 *            retain callback, the behavior is undefined. The value is
	 *            assigned to the index one larger than the previous largest
	 *            index, and the count of the array is increased by one.
	 */
    void CFArrayAppendValue(CFMutableArrayRef theArray, CFTypeRef value);

	/**
	 * Adds the value to the array, giving it the given index.
	 *
	 * @param theArray The array to which the value is to be added. If this
	 *            parameter is not a valid mutable {@code CFArray}, the behavior
	 *            is undefined.
	 * @param idx The index to which to add the new value. If the index is
	 *            outside the index space of the array (0 to N inclusive, where
	 *            N is the count of the array before the operation), the
	 *            behavior is undefined. If the index is the same as N, this
	 *            function has the same effect as {@link #CFArrayAppendValue}.
	 * @param value The value to add to the array. The value is retained by the
	 *            array using the retain callback provided when the array was
	 *            created. If the value is not of the sort expected by the
	 *            retain callback, the behavior is undefined. The value is
	 *            assigned to the given index, and all values with equal and
	 *            larger indices have their indexes increased by one.
	 */
    void CFArrayInsertValueAtIndex(CFMutableArrayRef theArray, int idx, CFTypeRef value);

	/**
	 * Changes the value with the given index in the array.
	 *
	 * @param theArray The array in which the value is to be changed. If this
	 *            parameter is not a valid mutable {@code CFArray}, the behavior
	 *            is undefined.
	 * @param idx The index to which to set the new value. If the index is
	 *            outside the index space of the array (0 to N inclusive, where
	 *            N is the count of the array before the operation), the
	 *            behavior is undefined. If the index is the same as N, this
	 *            function has the same effect as {@link #CFArrayAppendValue}.
	 * @param value The value to set in the array. The value is retained by the
	 *            array using the retain callback provided when the array was
	 *            created, and the previous value with that index is released.
	 *            If the value is not of the sort expected by the retain
	 *            callback, the behavior is undefined. The indices of other
	 *            values is not affected.
	 */
    void CFArraySetValueAtIndex(CFMutableArrayRef theArray, int idx, CFTypeRef value);

	/**
	 * Removes the value with the given index from the array.
	 *
	 * @param theArray The array from which the value is to be removed. If this
	 *            parameter is not a valid mutable {@code CFArray}, the behavior
	 *            is undefined.
	 * @param idx The index from which to remove the value. If the index is
	 *            outside the index space of the array (0 to N-1 inclusive,
	 *            where N is the count of the array before the operation), the
	 *            behavior is undefined.
	 */
	void CFArrayRemoveValueAtIndex(CFMutableArrayRef theArray, int idx);

	/**
	 * Removes all the values from the array, making it empty.
	 *
	 * @param theArray The array from which all of the values are to be removed.
	 *            If this parameter is not a valid mutable {@code CFArray}, the
	 *            behavior is undefined.
	 */
	void CFArrayRemoveAllValues(CFMutableArrayRef theArray);

	/**
	 * Exchanges the values at two indices of the array.
	 *
	 * @param theArray The array of which the values are to be swapped. If this
	 *            parameter is not a valid mutable {@code CFArray}, the behavior
	 *            is undefined.
	 * @param idx1 The first index whose values should be swapped. If the index
	 *            is outside the index space of the array (0 to N-1 inclusive,
	 *            where N is the count of the array before the operation), the
	 *            behavior is undefined.
	 * @param idx2 The second index whose values should be swapped. If the index
	 *            is outside the index space of the array (0 to N-1 inclusive,
	 *            where N is the count of the array before the operation), the
	 *            behavior is undefined.
	 */
	void CFArrayExchangeValuesAtIndices(CFMutableArrayRef theArray, int idx1, int idx2);

	public class CFAllocatorRef extends CFTypeRef {
    }

    public class CFStringRef extends CFTypeRef {
    	
        public CFStringRef() {
        	super();
        }

        public CFStringRef(Pointer p) {
        	super(p);
		}

        public CFStringRef(CFTypeRef cfString) {
        	super(cfString.getPointer());
        }

        /**
         * Creates a new CFString from the given Java string.
         *
         * @param s
         *            A string
         * @return A reference to a CFString representing s
         */
        public static CFStringRef toCFString(String s) {
            final char[] chars = s.toCharArray();
            int length = chars.length;
            return INSTANCE.CFStringCreateWithCharacters(null, chars, length);
        }

		@Override
		public String toString() {
			if (this.getPointer() == null) {
				return "null";
			}
			int maxSize = Math.max(
				INSTANCE.CFStringGetMaximumSizeForEncoding(
					INSTANCE.CFStringGetLength(this),
					CFStringBuiltInEncodings.kCFStringEncodingUTF8
				), 1
			);
			StringByReference buffer = new StringByReference(maxSize);
			INSTANCE.CFStringGetCString(this, buffer, maxSize, CFStringBuiltInEncodings.kCFStringEncodingUTF8);
			return buffer.getValue();
		}
		
		/**
		 * Use this as a substitute for cast from any {@link CFTypeRef}
		 * instance.
		 * <p>
		 * {@link CFTypeRef} can't be cast to {@link CFStringRef} if gotten from
		 * native code because {@link PointerType#fromNative} don't know the
		 * "real" type and thus calls {@link CFTypeRef}'s constructor instead of
		 * {@link CFStringRef}'s constructor. This instantiates a new
		 * {@link CFStringRef} and transfers the {@link Pointer} to achieve the
		 * same result.
		 * 
		 * @param cfType the {@link CFTypeRef} to "cast" to {@link CFStringRef}.
		 * @return The {@link CFStringRef} instance.
		 */
		public static CFStringRef toCFStringRef(CFTypeRef cfType) {
			return new CFStringRef(cfType == null ? null : cfType.getPointer());
		}
    }

    public class CFMutableStringRef extends CFStringRef {
    }

	/**
	 * Creates an immutable string from a C string.
	 * <p>
	 * References are owned if created by functions including "Create" or "Copy"
	 * and must be released with {@link #CFRelease} to avoid leaking references.
	 * <p>
	 * A C string is a string of 8-bit characters terminated with an 8-bit
	 * {@code null}. {@code Unichar} and {@code Unichar32} are not considered C
	 * strings.
	 *
	 * @param alloc the {@code CFAllocator} to use to allocate memory for the
	 *            new string. Pass {@code null} or {@link #kCFAllocatorDefault}
	 *            to use the current default allocator.
	 * @param cStr the {@code null-terminated} C string to be used to create the
	 *            {@code CFString} object. The string must use an 8-bit
	 *            encoding.
	 * @param encoding the encoding of the characters in the C string. The
	 *            encoding must specify an 8-bit encoding.
	 * @return An immutable string containing {@code cStr} (after stripping off
	 *         the {@code null} terminating character), or {@code null} if there
	 *         was a problem creating the object. Ownership follows the <a href=
	 *         "https://developer.apple.com/library/content/documentation/CoreFoundation/Conceptual/CFMemoryMgmt/Concepts/Ownership.html#//apple_ref/doc/uid/20001148-103029"
	 *         >The Create Rule</a>.
	 */
    CFStringRef CFStringCreateWithCString(CFAllocatorRef alloc, String cStr, CFStringBuiltInEncodings encoding);

	/**
	 * Creates a string from a buffer containing characters in a specified
	 * encoding.
	 * <p>
	 * References are owned if created by functions including "Create" or "Copy"
	 * and must be released with {@link #CFRelease} to avoid leaking references.
	 * <p>
	 * This function handles character data in an “external representation”
	 * format by interpreting any BOM (byte order marker) character and
	 * performing any necessary byte swapping.
	 *
	 * @param alloc the {@code CFAllocator} to use to allocate memory for the
	 *            new string. Pass {@code null} or {@link #kCFAllocatorDefault}
	 *            to use the current default allocator.
	 * @param bytes a buffer containing characters in the encoding specified by
	 *            encoding. The buffer must not contain a length byte (as in
	 *            Pascal buffers) or any terminating {@code null} character (as
	 *            in C buffers).
	 * @param numBytes The number of bytes in {@code bytes}.
	 * @param encoding The string encoding of the characters in {@code bytes}.
	 * @param isExternalRepresentation {@code true} if the characters in the
	 *            byte buffer are in an “external representation” format—that
	 *            is, whether the buffer contains a BOM (byte order marker).
	 *            This is usually the case for bytes that are read in from a
	 *            text file or received over the network. Otherwise, pass
	 *            {@code false}.
	 * @return An immutable string, or {@code null} if there was a problem
	 *         creating the object. Ownership follows the <a href=
	 *         "https://developer.apple.com/library/content/documentation/CoreFoundation/Conceptual/CFMemoryMgmt/Concepts/Ownership.html#//apple_ref/doc/uid/20001148-103029"
	 *         >The Create Rule</a>.
	 */
    CFStringRef CFStringCreateWithBytes(CFAllocatorRef alloc, byte[] bytes, NativeLong numBytes, CFStringBuiltInEncodings encoding, boolean isExternalRepresentation);

	/**
	 * Creates a string from a buffer of Unicode characters.
	 * <p>
	 * References are owned if created by functions including "Create" or "Copy"
	 * and must be released with {@link #CFRelease} to avoid leaking references.
	 * <p>
	 * This function creates an immutable string from a client-supplied
	 * {@code Unicode} buffer. You must supply a count of the characters in the
	 * buffer. This function always copies the characters in the provided buffer
	 * into internal storage. To save memory, this function might choose to
	 * store the characters internally in a 8-bit backing store. That is, just
	 * because a buffer of {@code UniChar} characters was used to initialize the
	 * object does not mean you will get back a non-{@code null} result from
	 * {@link #CFStringGetCharactersPtr}.
	 *
	 * @param alloc the {@code CFAllocator} to use to allocate memory for the
	 *            new string. Pass {@code null} or {@link #kCFAllocatorDefault}
	 *            to use the current default allocator.
	 * @param chars The buffer of {@code Unicode} characters to copy into the
	 *            new string.
	 * @param length The number of characters in the buffer pointed to by chars.
	 *            Only this number of characters will be copied to internal
	 *            storage.
	 * @return An immutable string containing chars, or {@code null} if there
	 *         was a problem creating the object. Ownership follows the <a href=
	 *         "https://developer.apple.com/library/content/documentation/CoreFoundation/Conceptual/CFMemoryMgmt/Concepts/Ownership.html#//apple_ref/doc/uid/20001148-103029"
	 *         >The Create Rule</a>.
	 */
    CFStringRef CFStringCreateWithCharacters(CFAllocatorRef alloc, char[] chars, int length);

	/**
	 * Creates an immutable copy of a string.
	 * <p>
	 * References are owned if created by functions including "Create" or "Copy"
	 * and must be released with {@link #CFRelease} to avoid leaking references.
	 * <p>
	 * The resulting object has the same {@code Unicode} contents as the
	 * original object, but it is always immutable. It might also have different
	 * storage characteristics, and hence might reply differently to functions
	 * such as {@link #CFStringGetCharactersPtr}. Also, if the specified
	 * allocator and the allocator of the original object are the same, and the
	 * string is already immutable, this function may simply increment the
	 * retention count without making a true copy. However, the resulting object
	 * is a true immutable copy, except the operation was a lot more efficient.
	 * You should use this function in situations where a string is or could be
	 * mutable, and you need to take a snapshot of its current value. For
	 * example, you might decide to pass a copy of a string to a function that
	 * stores its current value in a list for later use.
	 *
	 * @param alloc the {@code CFAllocator} to use to allocate memory for the
	 *            new string. Pass {@code null} or {@link #kCFAllocatorDefault}
	 *            to use the current default allocator.
	 * @param theString The {@code CFString} to copy.
	 * @return An immutable string whose contents are identical to
	 *         {@code theString}. Returns {@code null} if there was a problem
	 *         copying the object. Ownership follows the <a href=
	 *         "https://developer.apple.com/library/content/documentation/CoreFoundation/Conceptual/CFMemoryMgmt/Concepts/Ownership.html#//apple_ref/doc/uid/20001148-103029"
	 *         >The Create Rule</a>.
	 */
    CFStringRef CFStringCreateCopy(CFAllocatorRef alloc, CFStringRef theString);

	/**
	 * Creates an empty CFMutableString object.
	 * <p>
	 * This function creates an empty (that is, content-less)
	 * {@code CFMutableString} object. You can add character data to this object
	 * with any of the {@code CFStringAppend...} functions, and thereafter you
	 * can insert, delete, replace, pad, and trim characters with the
	 * appropriate {@code CFString} functions. If the {@code maxLength}
	 * parameter is greater than 0, any attempt to add characters beyond this
	 * limit results in a run-time error.
	 *
	 * @param alloc the {@code CFAllocator} to use to allocate memory for the
	 *            new string. Pass {@code null} or {@link #kCFAllocatorDefault}
	 *            to use the current default allocator.
	 * @param maxLength the maximum number of {@code Unicode} characters that
	 *            can be stored by the returned string. Pass 0 if there should
	 *            be no character limit. Note that initially the string still
	 *            has a length of 0; this parameter simply specifies what the
	 *            maximum size is. {@code CFMutableString} might try to optimize
	 *            its internal storage by paying attention to this value.
	 * @return A new empty {@code CFMutableString} object or {@code null} if
	 *         there was a problem creating the object. Ownership follows the <a
	 *         href=
	 *         "https://developer.apple.com/library/content/documentation/CoreFoundation/Conceptual/CFMemoryMgmt/Concepts/Ownership.html#//apple_ref/doc/uid/20001148-103029"
	 *         >The Create Rule</a>.
	 */
    CFMutableStringRef CFStringCreateMutable(CFAllocatorRef alloc, int maxLength);

	/**
	 * Creates a mutable copy of a string.
	 * <p>
	 * The returned mutable string is identical to the original string except
	 * for (perhaps) the mutability attribute. You can add character data to the
	 * returned string with any of the {@code CFStringAppend...} functions, and
	 * you can insert, delete, replace, pad, and trim characters with the
	 * appropriate {@code CFString} functions. If the maxLength parameter is
	 * greater than 0, any attempt to add characters beyond this limit results
	 * in a run-time error.
	 *
	 * @param alloc the {@code CFAllocator} to use to allocate memory for the
	 *            new string. Pass {@code null} or {@link #kCFAllocatorDefault}
	 *            to use the current default allocator.
	 * @param maxLength the maximum number of {@code Unicode} characters that
	 *            can be stored by the returned object. Pass 0 if there should
	 *            be no character limit. Note that initially the returned object
	 *            still has the same length as the string argument; this
	 *            parameter simply specifies what the maximum size is.
	 *            {@code CFMutableString} might try to optimize its internal
	 *            storage by paying attention to this value.
	 * @param theString the {@code CFString} to copy.
	 * @return A string that has the same contents as theString. Returns
	 *         {@code null} if there was a problem copying the object. Ownership
	 *         follows the <a href=
	 *         "https://developer.apple.com/library/content/documentation/CoreFoundation/Conceptual/CFMemoryMgmt/Concepts/Ownership.html#//apple_ref/doc/uid/20001148-103029"
	 *         >The Create Rule</a>.
	 */
    CFMutableStringRef CFStringCreateMutableCopy(CFAllocatorRef alloc, int maxLength, CFStringRef theString);
	/**
	 * Returns the number (in terms of {@code UTF-16} code pairs) of
	 * {@code Unicode} characters in a string.
	 *
	 * @param str the {@code CFString} to examine.
	 * @return The number (in terms of {@code UTF-16} code pairs) of characters
	 *         stored in {@code theString}.
	 */
    int CFStringGetLength(CFStringRef str);

	/**
	 * Returns the {@code Unicode} character at a specified location in a
	 * string.
	 * <p>
	 * This function is typically called in a loop to fetch the {@code Unicode}
	 * characters of a string in sequence or to fetch a character at a known
	 * position (first or last, for example). Using it in a loop can be
	 * inefficient, especially with longer strings, so consider the
	 * {@code CFStringGetCharacters} function or the in-line buffer functions (
	 * {@code CFStringInitInlineBuffer} and
	 * {@code CFStringGetCharacterFromInlineBuffer}) as alternatives.
	 *
	 * @param theString the {@code CFString} from which the Unicode character is
	 *            obtained.
	 * @param idx the position of the {@code Unicode} character in the
	 *            {@code CFString}.
	 * @return A {@code Unicode} character.
	 */
    char CFStringGetCharacterAtIndex(CFStringRef theString, int idx);

	/**
	 * Copies the character contents of a {@code CFString} to a local C string
	 * buffer after converting the characters to a given encoding.
	 * <p>
	 * This function is useful when you need your own copy of a string’s
	 * character data as a C string. You also typically call it as a “backup”
	 * when a prior call to the {@link #CFStringGetCStringPtr} function fails.
	 *
	 * @param theString the {@code CFString} whose contents you wish to access.
	 * @param buffer the C string buffer into which to copy the string. On
	 *            return, the buffer contains the converted characters. If there
	 *            is an error in conversion, the buffer contains only partial
	 *            results. The buffer must be large enough to contain the
	 *            converted characters and a {@code null} terminator. For
	 *            example, if the string is {@code Toby}, the buffer must be at
	 *            least 5 bytes long.
	 * @param bufferSize the length of {@code buffer} in bytes.
	 * @param encoding The string encoding to which the character contents of
	 *            {@code theString} should be converted. The encoding must
	 *            specify an 8-bit encoding.
	 * @return {@code true} upon success or {@code false} if the conversion
	 *         fails or the provided buffer is too small.
	 */
    boolean CFStringGetCString(CFStringRef theString, StringByReference buffer, int bufferSize, CFStringBuiltInEncodings encoding);

	/**
	 * Quickly obtains a pointer to a C-string buffer containing the characters
	 * of a string in a given encoding.
	 * <p>
	 * This function either returns the requested pointer immediately, with no
	 * memory allocations and no copying, in constant time, or returns
	 * {@code null}. If the latter is the result, call an alternative function
	 * such as the {@link #CFStringGetCString} function to extract the
	 * characters. Whether or not this function returns a valid pointer or
	 * {@code null} depends on many factors, all of which depend on how the
	 * string was created and its properties. In addition, the function result
	 * might change between different releases and on different platforms. So do
	 * not count on receiving a non-{@code null} result from this function under
	 * any circumstances.
	 *
	 * @param theString the {@code CFString} whose contents you wish to access.
	 * @param encoding the string encoding to which the character contents of
	 *            {@code theString} should be converted. The encoding must
	 *            specify an 8-bit encoding.
	 * @return A pointer to a C string or {@code null} if the internal storage
	 *         of {@code theString} does not allow this to be returned
	 *         efficiently.
	 */
    StringByReference CFStringGetCStringPtr(CFStringRef theString, CFStringBuiltInEncodings encoding);

	/**
	 * Quickly obtains a pointer to the contents of a string as a buffer of
	 * Unicode characters.
	 * <p>
	 * This function either returns the requested pointer immediately, with no
	 * memory allocations and no copying, or it returns {@code null}. If the
	 * latter is the result, call an alternative function such as
	 * {@code CFStringGetCharacters} function to extract the characters. Whether
	 * or not this function returns a valid pointer or {@code null} depends on
	 * many factors, all of which depend on how the string was created and its
	 * properties. In addition, the function result might change between
	 * different releases and on different platforms. So do not count on
	 * receiving a non-{@code null} result from this function under any
	 * circumstances (except when the object is created with
	 * {@code CFStringCreateMutableWithExternalCharactersNoCopy}).
	 *
	 * @param theString the {@code CFString} whose contents you wish to access.
	 * @return A pointer to a buffer of {@code Unicode} character, or
	 *         {@code null} if the internal storage of {@code theString} does
	 *         not allow this to be returned efficiently.
	 */
    WStringByReference CFStringGetCharactersPtr(CFStringRef theString);

	/**
	 * Returns the smallest encoding on the current system for the character
	 * contents of a {@code CFString}.
	 * <p>
	 * This function returns the supported encoding that requires the least
	 * space (in terms of bytes needed to represent one character) to represent
	 * the character contents of a string. This information is not always
	 * immediately available, so this function might need to compute it (result
	 * in O(n) time max).
	 *
	 * @param theString the string for which to find the smallest encoding.
	 * @return The string encoding that has the smallest representation of
	 *         {@code theString}. Use {@link CFStringBuiltInEncodings#typeOf} to
	 *         interpret the result if the encoding is among the "built-in"
	 *         encodings.
	 */
    int CFStringGetSmallestEncoding(CFStringRef theString);

	/**
	 * Returns for a {@code CFString} object the character encoding that
	 * requires the least conversion time.
	 * <p>
	 * This is faster than {@link #CFStringGetSmallestEncoding} (result in O(1)
	 * time max).
	 *
	 * @param theString the string for which to determine the fastest encoding.
	 * @return The string encoding to which {@code theString} can be converted
	 *         the fastest. Use {@link CFStringBuiltInEncodings#typeOf} to
	 *         interpret the result if the encoding is among the "built-in"
	 *         encodings.
	 */
    int CFStringGetFastestEncoding(CFStringRef theString);

	/**
	 * Returns the default encoding used by the operating system when it creates
	 * strings. Untagged 8-bit characters are usually in this encoding.
	 * <p>
	 * This function returns the default text encoding used by the OS when it
	 * creates strings. In macOS, this encoding is determined by the user's
	 * preferred language setting. The preferred language is the first language
	 * listed in the International pane of the System Preferences.
	 * <p>
	 * In most situations you will not want to use this function, however,
	 * because your primary interest will be your application's default text
	 * encoding. The application encoding is required when you create a
	 * {@link CFStringRef} from strings stored in Resource Manager resources,
	 * which typically use one of the Mac encodings such as {@code MacRoman} or
	 * {@code MacJapanese}.
	 * <p>
	 * To get your application's default text encoding, call the
	 * {@code GetApplicationTextEncoding} Carbon function.
	 *
	 * @return The default string encoding.
	 */
    int CFStringGetSystemEncoding();

	/**
	 * Returns the maximum number of bytes a string of a specified length (in
	 * Unicode characters) will take up if encoded in a specified encoding.
	 * <p>
	 *
	 * @param length the number of {@code Unicode} characters to evaluate.
	 * @param encoding the string encoding for the number of characters
	 *            specified by length.
	 * @return The maximum number of bytes that could be required to represent
	 *         length number of {@code Unicode} characters with the string
	 *         encoding encoding. The number of bytes that the encoding actually
	 *         ends up requiring when converting any particular string could be
	 *         less than this, but never more.
	 */
    int CFStringGetMaximumSizeForEncoding(int length, CFStringBuiltInEncodings encoding);

	/**
	 * Extracts the contents of a string as a {@code null}-terminated 8-bit
	 * string appropriate for passing to {@code POSIX} APIs, for example,
	 * normalized for {@code HFS+}.
	 * <p>
	 * You can use {@link #CFStringGetMaximumSizeOfFileSystemRepresentation} if
	 * you want to make sure the buffer is of sufficient length.
	 *
	 * @param string the {@code CFString} to convert.
	 * @param buffer the C string buffer into which to copy the string. The
	 *            buffer must be at least {@code maxBufLen} bytes in length. On
	 *            return, the buffer contains the converted characters.
	 * @param maxBufLen the maximum length of the buffer.
	 * @return {@code true} if the string is correctly converted; {@code false}
	 *         if the conversion fails, or the results don’t fit into the
	 *         buffer.
	 */
    boolean CFStringGetFileSystemRepresentation(CFStringRef string, StringByReference buffer, int maxBufLen);

	/**
	 * Determines the upper bound on the number of bytes required to hold the
	 * file system representation of the string.
	 * <p>
	 * The result is returned quickly as a rough approximation, and could be
	 * much larger than the actual space required. The result includes space for
	 * the {@code null}-termination. If you are allocating a buffer for
	 * long-term storage, you should reallocate it to be the right size after
	 * calling {@link #CFStringGetFileSystemRepresentation}.
	 *
	 * @param string the {@code CFString} to convert.
	 * @return The upper bound on the number of bytes required to hold the file
	 *         system representation of the string.
	 */
    int CFStringGetMaximumSizeOfFileSystemRepresentation(CFStringRef string);

	/**
	 * Creates a {@code CFString} from a {@code null}-terminated {@code POSIX}
	 * file system representation.
	 *
	 * @param alloc the {@code CFAllocator} to use to allocate memory for the
	 *            new string. Pass {@code null} or {@link #kCFAllocatorDefault}
	 *            to use the current default allocator.
	 * @param buffer the C string that you want to convert.
	 * @return A string that represents buffer. The result is {@code null} if
	 *         there was a problem in creating the string (possible if the
	 *         conversion fails due to bytes in the buffer not being a valid
	 *         sequence of bytes for the appropriate character encoding).
	 *         Ownership follows the <a href=
	 *         "https://developer.apple.com/library/content/documentation/CoreFoundation/Conceptual/CFMemoryMgmt/Concepts/Ownership.html#//apple_ref/doc/uid/20001148-103029"
	 *         >The Create Rule</a>.
	 */
    CFStringRef CFStringCreateWithFileSystemRepresentation(CFAllocatorRef alloc, char[] buffer);

	/**
	 * Compares one {@code CFString} with another {@code CFString}.
	 * <p>
	 * You can affect how the comparison proceeds by specifying one or more
	 * option flags in {@code compareOptions}. Not all comparison options are currently
	 * implemented.
	 *
	 * @param theString1 the first {@code CFString} to use in the comparison.
	 * @param theString2 The second {@code CFString} to use in the comparison.
	 * @param compareOptions Flags that select different types of comparisons,
	 *            such as localized comparison, case-insensitive comparison, and
	 *            non-literal comparison. If you want the default comparison
	 *            behavior, pass 0. See {@link CFStringCompareFlags} for the
	 *            available flags.
	 * @return A {@link CFComparisonResult} value that indicates whether {@code theString1} is
	 *         equal to, less than, or greater than {@code theString2}.
	 */
    CFComparisonResult CFStringCompare(CFStringRef theString1, CFStringRef theString2, int compareOptions);

    public class CFDictionaryRef extends CFTypeRef {
    }

    public class CFMutableDictionaryRef extends CFDictionaryRef {
    }

	/**
	 * Creates a new immutable dictionary with the key-value pairs from the
	 * given dictionary.
	 *
	 * @param allocator the {@code CFAllocator} which should be used to allocate
	 *            memory for the dictionary and its storage for values. This
	 *            parameter may be {@code null} in which case the current
	 *            default {@code CFAllocator} is used. If this reference is not
	 *            a valid {@code CFAllocator}, the behavior is undefined.
	 * @param theDict the dictionary which is to be copied. The keys and values
	 *            from the dictionary are copied as pointers into the new
	 *            dictionary (that is, the values themselves are copied, not
	 *            that which the values point to, if anything). However, the
	 *            keys and values are also retained by the new dictionary using
	 *            the retain function of the original dictionary. The count of
	 *            the new dictionary will be the same as the given dictionary.
	 *            The new dictionary uses the same callbacks as the dictionary
	 *            to be copied. If this parameter is not a valid
	 *            {@code CFDictionary}, the behavior is undefined.
	 * @return A reference to the new immutable {@code CFDictionary}.
	 */
	CFDictionaryRef CFDictionaryCreateCopy(CFAllocatorRef allocator, CFDictionaryRef theDict);

	/**
	 * Creates a new mutable dictionary.
	 *
	 * @param allocator the {@code CFAllocator} which should be used to allocate
	 *            memory for the dictionary and its storage for values. This
	 *            parameter may be {@code null} in which case the current
	 *            default {@code CFAllocator} is used. If this reference is not
	 *            a valid {@code CFAllocator}, the behavior is undefined.
	 * @param capacity a hint about the number of values that will be held by
	 *            the {@code CFDictionary}. Pass 0 for no hint. The
	 *            implementation may ignore this hint, or may use it to optimize
	 *            various operations. A dictionary's actual capacity is only
	 *            limited by address space and available memory constraints. If
	 *            this parameter is negative, the behavior is undefined.
	 * @param keyCallBacks a {@link Pointer} to a
	 *            {@code CFDictionaryKeyCallBacks} structure initialized with
	 *            the callbacks for the dictionary to use on each key in the
	 *            dictionary. A copy of the contents of the callbacks structure
	 *            is made, so that a pointer to a structure on the stack can be
	 *            passed in, or can be reused for multiple dictionary creations.
	 *            If the version field of this callbacks structure is not one of
	 *            the defined ones for {@code CFDictionary}, the behavior is
	 *            undefined. The retain field may be {@code null}, in which case
	 *            the {@code CFDictionary} will do nothing to add a retain to
	 *            the keys of the contained values. The release field may be
	 *            {@code null}, in which case the {@code CFDictionary} will do
	 *            nothing to remove the dictionary's retain (if any) on the keys
	 *            when the dictionary is destroyed or a key-value pair is
	 *            removed. If the {@code copyDescription} field is {@code null},
	 *            the dictionary will create a simple description for a key. If
	 *            the equal field is {@code null}, the dictionary will use
	 *            pointer equality to test for equality of keys. If the hash
	 *            field is {@code null}, a key will be converted from a pointer
	 *            to an integer to compute the hash code. This callbacks
	 *            parameter itself may be {@code null}, which is treated as if a
	 *            valid structure of version 0 with all fields {@code null} had
	 *            been passed in. Otherwise, if any of the fields are not valid
	 *            pointers to functions of the correct type, or this parameter
	 *            is not a valid pointer to a {@code CFDictionaryKeyCallBacks}
	 *            callbacks structure, the behavior is undefined. If any of the
	 *            keys put into the dictionary is not one understood by one of
	 *            the callback functions the behavior when that callback
	 *            function is used is undefined.
	 * @param valueCallBacks a {@link Pointer} to a
	 *            {@code CFDictionaryValueCallBacks} structure initialized with
	 *            the callbacks for the dictionary to use on each value in the
	 *            dictionary. The retain callback will be used within this
	 *            function, for example, to retain all of the new values from
	 *            the values C array. A copy of the contents of the callbacks
	 *            structure is made, so that a pointer to a structure on the
	 *            stack can be passed in, or can be reused for multiple
	 *            dictionary creations. If the version field of this callbacks
	 *            structure is not one of the defined ones for
	 *            {@code CFDictionary}, the behavior is undefined. The retain
	 *            field may be {@code null}, in which case the
	 *            {@code CFDictionary} will do nothing to add a retain to values
	 *            as they are put into the dictionary. The release field may be
	 *            {@code null}, in which case the {@code CFDictionary} will do
	 *            nothing to remove the dictionary's retain (if any) on the
	 *            values when the dictionary is destroyed or a key-value pair is
	 *            removed. If the {@code copyDescription} field is {@code null},
	 *            the dictionary will create a simple description for a value.
	 *            If the equal field is {@code null}, the dictionary will use
	 *            pointer equality to test for equality of values. This
	 *            callbacks parameter itself may be {@code null}, which is
	 *            treated as if a valid structure of version 0 with all fields
	 *            {@code null} had been passed in. Otherwise, if any of the
	 *            fields are not valid pointers to functions of the correct
	 *            type, or this parameter is not a valid pointer to a
	 *            {@code CFDictionaryValueCallBacks} callbacks structure, the
	 *            behavior is undefined. If any of the values put into the
	 *            dictionary is not one understood by one of the callback
	 *            functions the behavior when that callback function is used is
	 *            undefined.
	 * @return A reference to the new mutable {@code CFDictionary}.
	 */
	CFMutableDictionaryRef CFDictionaryCreateMutable(
		CFAllocatorRef allocator,
		int capacity,
		Pointer keyCallBacks,
		Pointer valueCallBacks
	);

	/**
	 * Creates a new mutable dictionary with the key-value pairs from the given
	 * dictionary.
	 *
	 * @param allocator the {@code CFAllocator} which should be used to allocate
	 *            memory for the dictionary and its storage for values. This
	 *            parameter may be {@code null} in which case the current
	 *            default {@code CFAllocator} is used. If this reference is not
	 *            a valid {@code CFAllocator}, the behavior is undefined.
	 * @param capacity a hint about the number of values that will be held by
	 *            the {@code CFDictionary}. Pass 0 for no hint. The
	 *            implementation may ignore this hint, or may use it to optimize
	 *            various operations. A dictionary's actual capacity is only
	 *            limited by address space and available memory constraints.
	 *            This parameter must be greater than or equal to the count of
	 *            the dictionary which is to be copied, or the behavior is
	 *            undefined. If this parameter is negative, the behavior is
	 *            undefined.
	 * @param theDict the dictionary which is to be copied. The keys and values
	 *            from the dictionary are copied as pointers into the new
	 *            dictionary (that is, the values themselves are copied, not
	 *            that which the values point to, if anything). However, the
	 *            keys and values are also retained by the new dictionary using
	 *            the retain function of the original dictionary. The count of
	 *            the new dictionary will be the same as the given dictionary.
	 *            The new dictionary uses the same callbacks as the dictionary
	 *            to be copied. If this parameter is not a valid
	 *            {@code CFDictionary}, the behavior is undefined.
	 * @return A reference to the new mutable {@code CFDictionary}.
	 */
	CFMutableDictionaryRef CFDictionaryCreateMutableCopy(
		CFAllocatorRef allocator,
		int capacity,
		CFDictionaryRef theDict
	);

	/**
	 * Returns the number of values currently in the dictionary.
	 *
	 * @param theDict the dictionary to be queried. If this parameter is not a
	 *            valid {@code CFDictionary}, the behavior is undefined.
	 * @return The number of values in the dictionary.
	 */
	int CFDictionaryGetCount(CFDictionaryRef theDict);

	/**
	 * Counts the number of times the given key occurs in the dictionary.
	 *
	 * @param theDict the dictionary to be searched. If this parameter is not a
	 *            valid CFDictionary, the behavior is undefined.
	 * @param key the key for which to find matches in the dictionary. The
	 *            {@code hash()} and {@code equal()} key callbacks provided when
	 *            the dictionary was created are used to compare. If the
	 *            {@code hash()} key callback was {@code null}, the key is
	 *            treated as a pointer and converted to an integer. If the
	 *            {@code equal()} key callback was {@code null}, pointer
	 *            equality (in C, {@code ==}) is used. If key, or any of the
	 *            keys in the dictionary, are not understood by the
	 *            {@code equal()} callback, the behavior is undefined.
	 * @return {@code 1} if a matching key is used by the dictionary, {@code 0}
	 *         otherwise.
	 */
	int CFDictionaryGetCountOfKey(CFDictionaryRef theDict, CFTypeRef key);

	/**
	 * Counts the number of times the given value occurs in the dictionary.
	 *
	 * @param theDict the dictionary to be searched. If this parameter is not a
	 *            valid {@code CFDictionary}, the behavior is undefined.
	 * @param value the value for which to find matches in the dictionary. The
	 *            {@code equal()} callback provided when the dictionary was
	 *            created is used to compare. If the {@code equal()} value
	 *            callback was {@code null}, pointer equality (in C, {@code ==})
	 *            is used. If value, or any of the values in the dictionary, are
	 *            not understood by the {@code equal()} callback, the behavior
	 *            is undefined.
	 * @return The number of times the given value occurs in the dictionary.
	 */
	int CFDictionaryGetCountOfValue(CFDictionaryRef theDict, CFTypeRef value);

	/**
	 * Reports whether or not the key is in the dictionary.
	 *
	 * @param theDict the dictionary to be searched. If this parameter is not a
	 *            valid {@code CFDictionary}, the behavior is undefined.
	 * @param key the key for which to find matches in the dictionary. The
	 *            {@code hash()} and {@code equal()} key callbacks provided when
	 *            the dictionary was created are used to compare. If the
	 *            {@code hash()} key callback was {@code null}, the key is
	 *            treated as a pointer and converted to an integer. If the
	 *            {@code equal()} key callback was {@code null}, pointer
	 *            equality (in C, {@code ==}) is used. If key, or any of the
	 *            keys in the dictionary, are not understood by the
	 *            {@code equal()} callback, the behavior is undefined.
	 * @return {@code true}, if the key is in the dictionary, otherwise
	 *         {@code false}.
	 */
	boolean CFDictionaryContainsKey(CFDictionaryRef theDict, CFTypeRef key);

	/**
	 * Reports whether or not the value is in the dictionary.
	 *
	 * @param theDict the dictionary to be searched. If this parameter is not a
	 *            valid {@code CFDictionary}, the behavior is undefined.
	 * @param value the value for which to find matches in the dictionary. The
	 *            {@code equal()} callback provided when the dictionary was
	 *            created is used to compare. If the {@code equal()} callback
	 *            was {@code null}, pointer equality (in C, {@code ==}) is used.
	 *            If value, or any of the values in the dictionary, are not
	 *            understood by the {@code equal()} callback, the behavior is
	 *            undefined.
	 * @return {@code true}, if the value is in the dictionary, otherwise
	 *         {@code false}.
	 */
	boolean CFDictionaryContainsValue(CFDictionaryRef theDict, CFTypeRef value);

	/**
	 * Retrieves the value associated with the given key.
	 *
	 * @param theDict the dictionary to be queried. If this parameter is not a
	 *            valid {@code CFDictionary}, the behavior is undefined.
	 * @param key the key for which to find a match in the dictionary. The
	 *            {@code hash()} and equal() key callbacks provided when the dictionary
	 *            was created are used to compare. If the {@code hash()} key callback
	 *            was {@code null}, the key is treated as a pointer and converted to an
	 *            integer. If the {@code equal()} key callback was {@code null}, pointer
	 *            equality (in C, {@code ==}) is used. If key, or any of the keys in the
	 *            dictionary, are not understood by the {@code equal()} callback, the
	 *            behavior is undefined.
	 * @return The value with the given key in the dictionary, or {@code null} if no
	 *         key-value pair with a matching key exists. Since {@code null} can be a
	 *         valid value in some dictionaries, the function
	 *         {@link #CFDictionaryGetValueIfPresent} must be used to distinguish
	 *         {@code null-no-found} from {@code null-is-the-value}.
	 */
    CFTypeRef CFDictionaryGetValue(CFDictionaryRef dictionary, CFTypeRef key);

	/**
	 * Retrieves the value associated with the given key.
	 *
	 * @param theDict the dictionary to be queried. If this parameter is not a
	 *            valid {@code CFDictionary}, the behavior is undefined.
	 * @param key the key for which to find a match in the dictionary. The
	 *            {@code hash()} and {@code equal()} key callbacks provided when
	 *            the dictionary was created are used to compare. If the
	 *            {@code hash()} key callback was {@code null}, the key is
	 *            treated as a pointer and converted to an integer. If the
	 *            {@code equal()} key callback was {@code null}, pointer
	 *            equality (in C, {@code ==}) is used. If key, or any of the
	 *            keys in the dictionary, are not understood by the
	 *            {@code equal()} callback, the behavior is undefined.
	 * @param value a pointer to memory which should be filled with the
	 *            pointer-sized value if a matching key is found. If no key
	 *            match is found, the contents of the storage pointed to by this
	 *            parameter are undefined. This parameter may be {@code null},
	 *            in which case the value from the dictionary is not returned
	 *            (but the return value of this function still indicates whether
	 *            or not the key-value pair was present).
	 * @return {@code true} if a matching key was found, {@code false}
	 *         otherwise.
	 */
    boolean CFDictionaryGetValueIfPresent(CFDictionaryRef dictionary, CFTypeRef key, CFTypeRef value);

	/**
	 * Fills the two buffers with the keys and values from the dictionary.
	 *
	 * @param theDict the dictionary to be queried. If this parameter is not a
	 *            valid {@code CFDictionary}, the behavior is undefined.
	 * @param keys a C array of pointer-sized values to be filled with keys from
	 *            the dictionary. The keys and values C arrays are parallel to
	 *            each other (that is, the items at the same indices form a
	 *            key-value pair from the dictionary). This parameter may be
	 *            {@code null} if the keys are not desired. If this parameter is
	 *            not a valid pointer to a C array of at least
	 *            {@link #CFDictionaryGetCount()} pointers, or {@code null}, the
	 *            behavior is undefined.
	 * @param values a C array of pointer-sized values to be filled with values
	 *            from the dictionary. The keys and values C arrays are parallel
	 *            to each other (that is, the items at the same indices form a
	 *            key-value pair from the dictionary). This parameter may be
	 *            {@code null} if the values are not desired. If this parameter
	 *            is not a valid pointer to a C array of at least
	 *            {@link #CFDictionaryGetCount} pointers, or
	 *            {@code null}, the behavior is undefined.
	 */
    void CFDictionaryGetKeysAndValues(CFDictionaryRef theDict, CFTypeRef keys, CFTypeRef values);

	/**
	 * Adds the key-value pair to the dictionary if no such key already exists.
	 *
	 * @param theDict the dictionary to which the value is to be added. If this
	 *            parameter is not a valid mutable {@code CFDictionary}, the
	 *            behavior is undefined.
	 * @param key the key of the value to add to the dictionary. The key is
	 *            retained by the dictionary using the retain callback provided
	 *            when the dictionary was created. If the key is not of the sort
	 *            expected by the retain callback, the behavior is undefined. If
	 *            a key which matches this key is already present in the
	 *            dictionary, this function does nothing ("add if absent").
	 * @param value the value to add to the dictionary. The value is retained by
	 *            the dictionary using the retain callback provided when the
	 *            dictionary was created. If the value is not of the sort
	 *            expected by the retain callback, the behavior is undefined.
	 */
	void CFDictionaryAddValue(CFMutableDictionaryRef theDict, CFTypeRef key, CFTypeRef value);

	/**
	 * Sets the value of the key in the dictionary.
	 *
	 * @param theDict the dictionary to which the value is to be set. If this
	 *            parameter is not a valid mutable {@code CFDictionary}, the
	 *            behavior is undefined.
	 * @param key the key of the value to set into the dictionary. If a key
	 *            which matches this key is already present in the dictionary,
	 *            only the value is changed
	 *            ("add if absent, replace if present"). If no key matches the
	 *            given key, the key-value pair is added to the dictionary. If
	 *            added, the key is retained by the dictionary, using the retain
	 *            callback provided when the dictionary was created. If the key
	 *            is not of the sort expected by the key retain callback, the
	 *            behavior is undefined.
	 * @param value the value to add to or replace into the dictionary. The
	 *            value is retained by the dictionary using the retain callback
	 *            provided when the dictionary was created, and the previous
	 *            value if any is released. If the value is not of the sort
	 *            expected by the retain or release callbacks, the behavior is
	 *            undefined.
	 */
    void CFDictionarySetValue(CFMutableDictionaryRef dict, CFTypeRef key, CFTypeRef value);

	/**
	 * Replaces the value of the key in the dictionary.
	 *
	 * @param theDict the dictionary to which the value is to be replaced. If
	 *            this parameter is not a valid mutable {@code CFDictionary},
	 *            the behavior is undefined.
	 * @param key the key of the value to replace in the dictionary. If a key
	 *            which matches this key is present in the dictionary, the value
	 *            is changed to the given value, otherwise this function does
	 *            nothing ("replace if present").
	 * @param value the value to replace into the dictionary. The value is
	 *            retained by the dictionary using the retain callback provided
	 *            when the dictionary was created, and the previous value is
	 *            released. If the value is not of the sort expected by the
	 *            retain or release callbacks, the behavior is undefined.
	 */
	void CFDictionaryReplaceValue(CFMutableDictionaryRef theDict, CFTypeRef key, CFTypeRef value);

	/**
	 * Removes the value of the key from the dictionary if present.
	 *
	 * @param theDict the dictionary from which the value is to be removed. If
	 *            this parameter is not a valid mutable {@code CFDictionary},
	 *            the behavior is undefined.
	 * @param key the key of the value to remove from the dictionary. If a key
	 *            which matches this key is present in the dictionary, the
	 *            key-value pair is removed from the dictionary, otherwise this
	 *            function does nothing ("remove if present").
	 */
	void CFDictionaryRemoveValue(CFMutableDictionaryRef theDict, CFTypeRef key);

	/**
	 * Removes all the values from the dictionary, making it empty.
	 *
	 * @param theDict the dictionary from which all of the values are to be
	 *            removed. If this parameter is not a valid mutable
	 *            {@code CFDictionary}, the behavior is undefined.
	 */
	void CFDictionaryRemoveAllValues(CFMutableDictionaryRef theDict);

	/**
	 * Gets the default allocator object for the current thread.
	 * <p>
	 * See the discussion for <a href=
	 * "https://developer.apple.com/reference/corefoundation/1521134-cfallocatorsetdefault?language=objc"
	 * >CFAllocatorSetDefault</a> for more detail on the default allocator and
	 * for advice on how and when to set a custom allocator as the default.
	 *
	 * @return A reference to the default allocator for the current thread. If
	 *         none has been explicitly set, returns the generic system
	 *         allocator, {@code kCFAllocatorSystemDefault}. Ownership follows
	 *         the <a href=
	 *         "https://developer.apple.com/library/content/documentation/CoreFoundation/Conceptual/CFMemoryMgmt/Concepts/Ownership.html#//apple_ref/doc/uid/20001148-103029"
	 *         >The Create Rule</a>.
	 */
    CFAllocatorRef CFAllocatorGetDefault();

    /** This is a synonym for {@code null}, if you'd rather use a named constant. */
    final CFAllocatorRef kCFAllocatorDefault = new CFAllocatorRef();

    /** A "buffer" type - holding bytes. */
    public class CFDataRef extends CFTypeRef {
    }

    public class CFMutableDataRef extends CFDataRef {
    }

	/**
	 * Returns the type identifier for the {@code CFData} opaque type.
	 * <p>
	 * {@code CFMutableData} objects have the same type identifier as
	 * {@code CFData} objects.
	 *
	 * @return The type identifier for the {@code CFData} opaque type.
	 */
    int CFDataGetTypeID();

	/**
	 * Creates an immutable {@code CFData} object using data copied from a
	 * specified byte buffer.
	 * <p>
	 * You must supply a count of the bytes in the buffer. This function always
	 * copies the bytes in the provided buffer into internal storage.
	 *
	 * @param allocator the {@code CFAllocator} to use to allocate memory for
	 *            the new object. Pass {@code null} or
	 *            {@link #kCFAllocatorDefault} to use the current default
	 *            allocator.
	 * @param bytes a pointer to the byte buffer that contains the raw data to
	 *            be copied into the new {@code CFData}.
	 * @param length the number of bytes in the buffer (bytes).
	 * @return A new {@code CFData} object, or {@code null} if there was a
	 *         problem creating the object. Ownership follows the <a href=
	 *         "https://developer.apple.com/library/content/documentation/CoreFoundation/Conceptual/CFMemoryMgmt/Concepts/Ownership.html#//apple_ref/doc/uid/20001148-103029"
	 *         >The Create Rule</a>.
	 */
    CFDataRef CFDataCreate(CFAllocatorRef allocator, byte[] bytes, int length);

	/**
	 * Creates an immutable copy of a {@code CFData} object.
	 * <p>
	 * The resulting object has the same byte contents as the original object,
	 * but it is always immutable. If the specified allocator and the allocator
	 * of the original object are the same, and the {@code CFData} is already
	 * immutable, this function may simply increment the retain count without
	 * making a true copy. To the caller, however, the resulting object is a
	 * true immutable copy, except the operation was more efficient.
	 * <p>
	 * Use this function when you need to pass a {@code CFData} object into
	 * another function by value (not reference).
	 *
	 * @param allocator the {@code CFAllocator} to use to allocate memory for
	 *            the new object. Pass {@code null} or
	 *            {@link #kCFAllocatorDefault} to use the current default
	 *            allocator.
	 * @param theData the {@code CFData} object to copy.
	 * @return An immutable copy of {@code theData}, or {@code null} if there
	 *         was a problem creating the object. Ownership follows the <a href=
	 *         "https://developer.apple.com/library/content/documentation/CoreFoundation/Conceptual/CFMemoryMgmt/Concepts/Ownership.html#//apple_ref/doc/uid/20001148-103029"
	 *         >The Create Rule</a>.
	 */
    CFDataRef CFDataCreateCopy(CFAllocatorRef allocator, CFDataRef theData);

	/**
	 * Creates an empty {@code CFMutableData} object.
	 * <p>
	 * This function creates an empty (that is, content-less)
	 * {@code CFMutableData} object. You can add raw data to this object with
	 * the {@link #CFDataAppendBytes} function, and thereafter you can replace
	 * and delete characters with the appropriate {@code CFMutableData}
	 * functions. If the capacity parameter is greater than 0, any attempt to
	 * add characters beyond this limit can result in undefined behavior.
	 *
	 * @param allocator the {@code CFAllocator} to use to allocate memory for
	 *            the new object. Pass {@code null} or
	 *            {@link #kCFAllocatorDefault} to use the current default
	 *            allocator.
	 * @param capacity the maximum number of bytes that the {@code CFData}
	 *            object can contain. The {@code CFData} object starts empty and
	 *            can grow to contain this number of values (and it can have
	 *            less). Pass 0 to specify that the maximum capacity is not
	 *            limited. The value must not be negative.
	 * @return A {@code CFMutableData} object or {@code null} if there was a
	 *         problem creating the object. Ownership follows the <a href=
	 *         "https://developer.apple.com/library/content/documentation/CoreFoundation/Conceptual/CFMemoryMgmt/Concepts/Ownership.html#//apple_ref/doc/uid/20001148-103029"
	 *         >The Create Rule</a>.
	 */
    CFMutableDataRef CFDataCreateMutable(CFAllocatorRef allocator, int capacity);

	/**
	 * Creates a {@code CFMutableData} object by copying another {@code CFData}
	 * object.
	 *
	 * @param allocator the {@code CFAllocator} to use to allocate memory for
	 *            the new object. Pass {@code null} or
	 *            {@link #kCFAllocatorDefault} to use the current default
	 *            allocator.
	 * @param capacity the maximum number of bytes that the
	 *            {@code CFMutableData} object can contain. The
	 *            {@code CFMutableData} object starts with the same length as
	 *            the original object, and can grow to contain this number of
	 *            bytes. Pass 0 to specify that the maximum capacity is not
	 *            limited. If non-0, capacity must be greater than or equal to
	 *            the length of {@code theData}.
	 * @param theData the {@code CFData} object to be copied.
	 * @return A {@code CFMutableData} object that has the same contents as the
	 *         original object. Returns {@code null} if there was a problem
	 *         copying the object. Ownership follows the <a href=
	 *         "https://developer.apple.com/library/content/documentation/CoreFoundation/Conceptual/CFMemoryMgmt/Concepts/Ownership.html#//apple_ref/doc/uid/20001148-103029"
	 *         >The Create Rule</a>.
	 */
    CFMutableDataRef CFDataCreateMutableCopy(CFAllocatorRef allocator, int capacity, CFDataRef theData);

	/**
	 * Returns the number of bytes contained by a {@code CFData} object.
	 *
	 * @param theData the {@code CFData} object to examine.
	 * @return The number of bytes in {@code theData}.
	 */
    int CFDataGetLength(CFTypeRef theData);

	/**
	 * Returns a read-only pointer to the bytes of a {@code CFData} object.
	 * <p>
	 * This function is guaranteed to return a pointer to a {@code CFData}
	 * object's internal bytes. {@code CFData}, unlike {@code CFString}, does
	 * not hide its internal storage.
	 *
	 * @param theData the {@code CFData} object to examine.
	 * @return A read-only pointer to the bytes associated with {@code theData}.
	 */
    PointerByReference CFDataGetBytePtr(CFDataRef theData);

	/**
	 * Returns a pointer to a mutable byte buffer of a {@code CFMutableData}
	 * object.
	 * <p>
	 * If the length of {@code theData}’s data is not zero, this function is
	 * guaranteed to return a pointer to a {@code CFMutableData} object's
	 * internal bytes. If the length of {@code theData}’s data is zero, this
	 * function may or may not return {@code null} dependent upon many factors
	 * related to how the object was created (moreover, in this case the
	 * function result might change between different releases and on different
	 * platforms).
	 *
	 * @param theData a {@code CFMutableData} object. If you pass an immutable
	 *            {@code CFData} object, the behavior is not defined.
	 * @return A pointer to the bytes associated with {@code theData}.
	 */
    PointerByReference CFDataGetMutableBytePtr(CFMutableDataRef theData);

	/**
	 * Resets the length of a {@code CFMutableData} object's internal byte
	 * buffer.
	 * <p>
	 * This function resets the length of a {@code CFMutableData} object’s
	 * underlying byte buffer to a new size. If that size is less than the
	 * current size, it truncates the excess bytes. If that size is greater than
	 * the current size, it zero-fills the extension to the byte buffer.
	 *
	 * @param theData a {@code CFMutableData} object. If you pass an immutable
	 *            {@code CFData} object, the behavior is not defined.
	 * @param length the new size of {@code theData}’s byte buffer.
	 */
    void CFDataSetLength(CFMutableDataRef theData, int length);

	/**
	 * Increases the length of a {@code CFMutableData} object's internal byte
	 * buffer, zero-filling the extension to the buffer.
	 * <p>
	 * This function increases the length of a {@code CFMutableData} object’s
	 * underlying byte buffer to a new size, initializing the new bytes to 0.
	 *
	 * @param theData a {@code CFMutableData} object. If you pass an immutable
	 *            {@code CFData} object, the behavior is not defined.
	 * @param extraLength the number of bytes by which to increase the byte
	 *            buffer.
	 */
    void CFDataIncreaseLength(CFMutableDataRef theData, int extraLength);

	/**
	 * Appends the bytes from a byte buffer to the contents of a {@code CFData}
	 * object.
	 *
	 * @param theData a {@code CFMutableData} object. If you pass an immutable
	 *            {@code CFData} object, the behavior is not defined.
	 * @param bytes a pointer to the buffer of bytes to be added to
	 *            {@code theData}.
	 * @param length The number of bytes in the byte buffer {@code bytes}.
	 */
    void CFDataAppendBytes(CFMutableDataRef theData, byte[] bytes, int length);

	/**
	 * This represents the {@code CF_ENUM} with the same name.
	 * <p>
	 * Use {@link #getValue()} to convert a {@link CFNumberType} to its integer
	 * value. Use {@link #typeOf} to convert an integer value to a
	 * {@link CFNumberType}.
	 */
    public enum CFNumberType implements JnaIntEnum<CFNumberType> {
        /** Eight-bit, signed integer. The {@code SInt8} data type is defined in {@code MacTypes.h}. */
        kCFNumberSInt8Type(1),
        /** Sixteen-bit, signed integer. The {@code SInt16} data type is defined in {@code MacTypes.h}. */
        kCFNumberSInt16Type(2),
        /** Thirty-two-bit, signed integer. The {@code SInt32} data type is defined in {@code MacTypes.h}. */
        kCFNumberSInt32Type(3),
        /** Sixty-four-bit, signed integer. The {@code SInt64} data type is defined in {@code MacTypes.h}. */
        kCFNumberSInt64Type(4),
        /** Thirty-two-bit real. The {@code Float32} data type is defined in {@code MacTypes.h}. */
        kCFNumberFloat32Type(5),
        /** Sixty-four-bit real. The {@code Float64} data type is defined in {@code MacTypes.h} and conforms to the 64-bit IEEE 754 standard. */
    	kCFNumberFloat64Type(6),
        /** Basic C {@code char} type. */
    	kCFNumberCharType(7),
        /** Basic C {@code short} type. */
    	kCFNumberShortType(8),
        /** Basic C {@code int} type. */
    	kCFNumberIntType(9),
        /** Basic C {@code long} type. */
    	kCFNumberLongType(10),
        /** Basic C {@code long long} type. */
    	kCFNumberLongLongType(11),
        /** Basic C {@code float} type. */
    	kCFNumberFloatType(12),
        /** Basic C {@code double} type. */
    	kCFNumberDoubleType(13),
        /** {@code CFIndex} value. */
    	kCFNumberCFIndexType(14),
    	/**
    	 * {@code NSInteger} value.
    	 * @since OS X 10.5
    	 */
    	kCFNumberNSIntegerType(15),
    	/**
    	 * {@code CGFloat} value.
    	 * @since OS X 10.5
    	 */
    	kCFNumberCGFloatType(16),
        /** Same as {@code cgFloatType}. */
    	kCFNumberMaxType(16);

        private final int value;

        private CFNumberType(int value) {
        	this.value = value;
        }

        @Override
        public int getValue() {
        	return value;
        }

		@Override
		public CFNumberType typeForValue(int value) {
			return typeOf(value);
		}

		public static CFNumberType typeOf(int value) {
        	for (CFNumberType entry : CFNumberType.values()) {
        		if (value == entry.getValue()) {
        			return entry;
        		}
        	}
        	return null;
        }
    }

	/**
	 * This represents the {@code CF_ENUM} with the same name.
	 * <p>
	 * Use {@link #getValue()} to convert a {@link CFStringBuiltInEncodings} to
	 * its integer value. Use {@link #typeOf} to convert an integer value to a
	 * {@link CFStringBuiltInEncodings}.
	 */
    public enum CFStringBuiltInEncodings implements JnaIntEnum<CFStringBuiltInEncodings> {
    	kCFStringEncodingMacRoman(0),
    	/** ANSI codepage 1252 */
    	kCFStringEncodingWindowsLatin1(0x0500),
		/** ISO 8859-1 */
		kCFStringEncodingISOLatin1(0x0201),
		/** NextStep encoding*/
		kCFStringEncodingNextStepLatin(0x0B01),
		/**
		 * 0..127 (in creating CFString, values greater than 0x7F are treated as
		 * corresponding Unicode value)
		 */
		kCFStringEncodingASCII(0x0600),
		/**
		 * kTextEncodingUnicodeDefault + kTextEncodingDefaultFormat (aka
		 * kUnicode16BitFormat)
		 */
		kCFStringEncodingUnicode(0x0100),
		/** kTextEncodingUnicodeDefault + kUnicodeUTF8Format */
		kCFStringEncodingUTF8(0x08000100),
		/** 7bit Unicode variants used by Cocoa & Java */
		kCFStringEncodingNonLossyASCII(0x0BFF),
		/**
		 * kTextEncodingUnicodeDefault + kUnicodeUTF16Format (alias of
		 * kCFStringEncodingUnicode)
		 */
		kCFStringEncodingUTF16(0x0100),
		/** kTextEncodingUnicodeDefault + kUnicodeUTF16BEFormat */
		kCFStringEncodingUTF16BE(0x10000100),
		/** kTextEncodingUnicodeDefault + kUnicodeUTF16LEFormat */
		kCFStringEncodingUTF16LE(0x14000100),
		/** kTextEncodingUnicodeDefault + kUnicodeUTF32Format */
		kCFStringEncodingUTF32(0x0c000100),
		/** kTextEncodingUnicodeDefault + kUnicodeUTF32BEFormat */
		kCFStringEncodingUTF32BE(0x18000100),
		/** kTextEncodingUnicodeDefault + kUnicodeUTF32LEFormat */
		kCFStringEncodingUTF32LE(0x1c000100),
		kCFStringEncodingInvalidId(0xffffffff);

    	private final int value;

		private CFStringBuiltInEncodings(int value) {
			this.value = value;
		}

		@Override
		public int getValue() {
			return value;
		}

		@Override
		public CFStringBuiltInEncodings typeForValue(int value) {
			return typeOf(value);
		}

		public static CFStringBuiltInEncodings typeOf(int value) {
        	for (CFStringBuiltInEncodings entry : CFStringBuiltInEncodings.values()) {
        		if (value == entry.getValue()) {
        			return entry;
        		}
        	}
        	return null;
        }
    }

	/**
	 * This represents the {@code CF_ENUM} with the same name.
	 * <p>
	 * Use {@link #getValue()} to convert a {@link CFStringCompareFlags} to its
	 * integer value. Use {@link #typeOf} to convert an integer value to a
	 * {@link CFStringCompareFlags}.
	 */
    public enum CFStringCompareFlags implements JnaIntEnum<CFStringCompareFlags> {
	    kCFCompareCaseInsensitive(1),
		/** Starting from the end of the string */
	    kCFCompareBackwards(4),
		/** Only at the specified starting point */
	    kCFCompareAnchored(8),
		/** If specified, loose equivalence is performed (o-umlaut == o, umlaut) */
	    kCFCompareNonliteral(16),
		/** User's default locale is used for the comparisons */
	    kCFCompareLocalized(32),
		/** Numeric comparison is used; that is, Foo2.txt < Foo7.txt < Foo25.txt */
	    kCFCompareNumerically(64),
		/**
		 * If specified, ignores diacritics (o-umlaut == o)
		 *
		 * @since OS X 10.5
		 */
	    kCFCompareDiacriticInsensitive(128),
		/**
		 * If specified, ignores width differences ('a' == UFF41)
		 *
		 * @since OS X 10.5
		 */
	    kCFCompareWidthInsensitive(256),
		/**
		 * If specified, comparisons are forced to return either
		 * {@code kCFCompareLessThan} or {@code kCFCompareGreaterThan} if the
		 * strings are equivalent but not strictly equal, for stability when
		 * sorting (e.g. "aaa" > "AAA" with {@link #kCFCompareCaseInsensitive}
		 * specified)
		 *
		 * @since OS X 10.5
		 */
	    kCFCompareForcedOrdering(512);

	    private final int value;

	    private CFStringCompareFlags(int value) {
	    	this.value = value;
	    }

		@Override
		public int getValue() {
			return value;
		}
		@Override
		public CFStringCompareFlags typeForValue(int value) {
			return typeOf(value);
		}

		public static CFStringCompareFlags typeOf(int value) {
        	for (CFStringCompareFlags entry : CFStringCompareFlags.values()) {
        		if (value == entry.getValue()) {
        			return entry;
        		}
        	}
        	return null;
        }
    }

	/**
	 * This represents the {@code CF_ENUM} with the same name.
	 * <p>
	 * Use {@link #getValue()} to convert a {@link CFStringNormalizationForm} to
	 * its integer value. Use {@link #typeOf} to convert an integer value to a
	 * {@link CFStringNormalizationForm}.
	 */
    public enum CFStringNormalizationForm implements JnaIntEnum<CFStringNormalizationForm> {
    	/** Canonical Decomposition */
    	kCFStringNormalizationFormD(0),
    	/** Compatibility Decomposition */
    	kCFStringNormalizationFormKD(1),
		/** Canonical Decomposition followed by Canonical Composition */
		kCFStringNormalizationFormC(2),
		/** Compatibility Decomposition followed by Canonical Composition */
		kCFStringNormalizationFormKC(3);

    	private final int value;

		private CFStringNormalizationForm(int value) {
			this.value = value;
		}

		@Override
		public int getValue() {
			return value;
		}

		@Override
		public CFStringNormalizationForm typeForValue(int value) {
			return typeOf(value);
		}

		public static CFStringNormalizationForm typeOf(int value) {
        	for (CFStringNormalizationForm entry : CFStringNormalizationForm.values()) {
        		if (value == entry.getValue()) {
        			return entry;
        		}
        	}
        	return null;
        }
    }

	/**
	 * This represents the value of a {@code CFComparisonResult}.
	 * <p>
	 * Use {@link #getValue()} to convert a {@link CFComparisonResult} to its
	 * integer value. Use {@link #typeOf} to convert an integer value to a
	 * {@link CFComparisonResult}.
	 */
    public enum CFComparisonResult implements JnaIntEnum<CFComparisonResult> {
    	/** Returned by a comparison function if the first value is less than the second value. */
    	kCFCompareLessThan(-1),
    	/** Returned by a comparison function if the first value is equal to the second value. */
    	kCFCompareEqualTo(0),
    	/** Returned by a comparison function if the first value is greater than the second value. */
    	kCFCompareGreaterThan(1);

    	private final int value;

		private CFComparisonResult(int value) {
			this.value = value;
		}

		@Override
		public int getValue() {
			return value;
		}

		@Override
		public CFComparisonResult typeForValue(int value) {
			return typeOf(value);
		}

		public static CFComparisonResult typeOf(int value) {
			if (value > 0) {
				return kCFCompareGreaterThan;
			}
			if (value < 0) {
				return kCFCompareLessThan;
			}
			return kCFCompareEqualTo;
		}
    }
}