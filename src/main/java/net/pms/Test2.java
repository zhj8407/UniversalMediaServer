package net.pms;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.IntByReference;
import net.pms.io.iokit.CoreFoundation;
import net.pms.io.iokit.CoreFoundation.CFComparisonResult;
import net.pms.io.iokit.CoreFoundation.CFDictionaryRef;
import net.pms.io.iokit.CoreFoundation.CFMutableStringRef;
import net.pms.io.iokit.CoreFoundation.CFNumberRef;
import net.pms.io.iokit.CoreFoundation.CFNumberType;
import net.pms.io.iokit.CoreFoundation.CFStringBuiltInEncodings;
import net.pms.io.iokit.CoreFoundation.CFStringCompareFlags;
import net.pms.io.iokit.CoreFoundation.CFStringRef;
import net.pms.io.iokit.IOKit;
import net.pms.io.iokit.IOReturn;
import net.pms.util.jna.StringByReference;
import net.pms.util.jna.UTF16StringByReference;
import net.pms.util.jna.WStringByReference;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//com.sun.jna.platform.mac.XAttrUtil
		//System.out.print(JnaTest.getInstance().hasTrash() ? "Yes" : "Nope");
		//System.out.print(FileManager.INSTANCE.FSPathMakeRef("", 0, new ByteByReference((byte) 1)));
		Native.setProtected(true);
		CoreFoundation coreFoundation = CoreFoundation.INSTANCE;
		/*IntByReference assertionID = new IntByReference();
		CFStringRef assertionType = CFStringRef.toCFString(IOKit.kIOPMAssertPreventUserIdleSystemSleep);
		CFStringRef name = CFStringRef.toCFString("TestName");
		/*int ioResult = IOKit.INSTANCE.IOPMAssertionCreateWithName(assertionType, IOKit.kIOPMAssertionLevelOn, name, assertionID);
		System.out.println(ioResult);
		System.out.println(Integer.toHexString(ioResult));
		System.out.println(assertionID.getValue());*/
		/*CFStringRef details = CFStringRef.toCFString("Testing out IOKit");
		CFStringRef timeOutAction = CFStringRef.toCFString(IOKit.kIOPMAssertionTimeoutActionTurnOff);
		IOReturn ioResult = IOKit.INSTANCE.IOPMAssertionCreateWithDescription(assertionType, name, details, null, null, 10d, timeOutAction, assertionID);
		System.out.println(ioResult);
		System.out.println(Integer.toHexString(ioResult.getValue()));
		System.out.println(assertionID.getValue());
		CFDictionaryRef dict =
		IOKit.INSTANCE.IOPMAssertionCopyProperties(assertionID.getValue());
		CFStringRef key = CFStringRef.toCFString(IOKit.kIOPMAssertionTimeoutActionKey);
		CFStringRef value = CFStringRef.toCFStringRef(coreFoundation.CFDictionaryGetValue(dict, key));
		System.out.println(value);
		CFNumberRef i = CFNumberRef.toCFNumber(43.8f);
		System.out.println(i);
		coreFoundation.CFRelease(i);*/
		coreFoundation.CFNullGetTypeID();
		CFStringRef cfString = CFStringRef.toCFString("Test 𐘺𐝀 stringøæåß づ");
		CFNumberRef cfInteger = CFNumberRef.toCFNumber(110);
		CFNumberRef cfDouble = CFNumberRef.toCFNumber(93.8);
		IntByReference refInt = new IntByReference();
		coreFoundation.CFNumberGetValue(cfInteger, CFNumberType.kCFNumberSInt32Type, refInt);
		refInt.getValue();
		DoubleByReference refDouble = new DoubleByReference();
		coreFoundation.CFNumberGetValue(cfDouble, CFNumberType.kCFNumberFloat64Type, refDouble);
		refDouble.getValue();
		coreFoundation.CFNumberGetValue(cfDouble, CFNumberType.kCFNumberSInt32Type, refInt);
		StringByReference refString = new StringByReference("Test reference string");
		CFStringRef cfString2 = coreFoundation.CFStringCreateWithCString(null, "Test string 2", CFStringBuiltInEncodings.kCFStringEncodingWindowsLatin1.getValue());
		byte[] stringBytes = "Test byte array string".getBytes(StandardCharsets.UTF_16BE);
		CFStringRef cfString3 = coreFoundation.CFStringCreateWithBytes(null, stringBytes, stringBytes.length, CFStringBuiltInEncodings.kCFStringEncodingUTF16BE.getValue(), false);
		CFMutableStringRef mutableString = coreFoundation.CFStringCreateMutable(null, 20);
		refString = new StringByReference(20);
		boolean b = coreFoundation.CFStringGetCString(cfString2, refString, refString.getAllocatedSize(), CFStringBuiltInEncodings.kCFStringEncodingISOLatin1.getValue());
		refString = coreFoundation.CFStringGetCStringPtr(cfString, CFStringBuiltInEncodings.kCFStringEncodingMacRoman.getValue());
		//char[] chars = coreFoundation.CFStringGetCharactersPtr(cfString);
		UTF16StringByReference chars = coreFoundation.CFStringGetCharactersPtr(cfString);
		chars.getValue();
		System.out.println(chars.toString());
		chars.setValue("Jule n𐝃ssen jeß ¢");
		System.out.println(chars.toString());
		coreFoundation.CFStringGetSmallestEncoding(cfString);
		coreFoundation.CFStringGetFastestEncoding(cfString);
		coreFoundation.CFStringGetMaximumSizeOfFileSystemRepresentation(cfString);
		refString = new StringByReference(40);
		b = coreFoundation.CFStringGetFileSystemRepresentation(cfString, refString, 40);
		CFStringRef cfString4 = coreFoundation.CFStringCreateWithFileSystemRepresentation(null, refString);
		CFComparisonResult cRes = coreFoundation.CFStringCompare(cfString4, cfString, CFStringCompareFlags.kCFCompareCaseInsensitive.getValue());
		coreFoundation.CFStringIsEncodingAvailable(CFStringBuiltInEncodings.kCFStringEncodingNonLossyASCII.getValue());
		Pointer p = coreFoundation.CFStringGetListOfAvailableEncodings();
		//List<Integer> intList = coreFoundation.CFStringGetListOfAvailableEncodings();
		
	}

}

