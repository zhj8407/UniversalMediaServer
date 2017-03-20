package net.pms;

import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;
import net.pms.io.iokit.CoreFoundation;
import net.pms.io.iokit.CoreFoundation.CFDictionaryRef;
import net.pms.io.iokit.CoreFoundation.CFNumberRef;
import net.pms.io.iokit.CoreFoundation.CFStringRef;
import net.pms.io.iokit.IOKit;
import net.pms.io.iokit.IOReturn;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//com.sun.jna.platform.mac.XAttrUtil
		//System.out.print(JnaTest.getInstance().hasTrash() ? "Yes" : "Nope");
		//System.out.print(FileManager.INSTANCE.FSPathMakeRef("", 0, new ByteByReference((byte) 1)));
		Native.setProtected(true);
		IntByReference assertionID = new IntByReference();
		CFStringRef assertionType = CFStringRef.toCFString(IOKit.kIOPMAssertPreventUserIdleSystemSleep);
		CFStringRef name = CFStringRef.toCFString("TestName");
		/*int ioResult = IOKit.INSTANCE.IOPMAssertionCreateWithName(assertionType, IOKit.kIOPMAssertionLevelOn, name, assertionID);
		System.out.println(ioResult);
		System.out.println(Integer.toHexString(ioResult));
		System.out.println(assertionID.getValue());*/
		CFStringRef details = CFStringRef.toCFString("Testing out IOKit");
		CFStringRef timeOutAction = CFStringRef.toCFString(IOKit.kIOPMAssertionTimeoutActionTurnOff);
		IOReturn ioResult = IOKit.INSTANCE.IOPMAssertionCreateWithDescription(assertionType, name, details, null, null, 10d, timeOutAction, assertionID);
		System.out.println(ioResult);
		System.out.println(Integer.toHexString(ioResult.getValue()));
		System.out.println(assertionID.getValue());
		CFDictionaryRef dict =
		IOKit.INSTANCE.IOPMAssertionCopyProperties(assertionID.getValue());
		CoreFoundation coreFoundation = CoreFoundation.INSTANCE;
		CFStringRef key = CFStringRef.toCFString(IOKit.kIOPMAssertionTimeoutActionKey);
		CFStringRef value = CFStringRef.toCFStringRef(coreFoundation.CFDictionaryGetValue(dict, key));
		System.out.println(value);
		CFNumberRef i = CFNumberRef.toCFNumber(43.8f);
		System.out.println(i);
		coreFoundation.CFRelease(i);
	}

}

