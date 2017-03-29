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

import java.util.Collection;
import com.sun.jna.Pointer;
import net.pms.io.iokit.CoreFoundation.CFStringBuiltInEncodings;

/**
 * A representation of an array {@code CFStringEncoding} terminated by
 * {@link CFStringBuiltInEncodings#kCFStringEncodingInvalidId}.
 *
 * @see TerminatedArray
 *
 * @author Nadahar
 */
public class TerminatedStringEncodingArray extends TerminatedIntArray {

	public TerminatedStringEncodingArray() {
	}

	public TerminatedStringEncodingArray(Pointer p) {
		super(p);
	}

	public TerminatedStringEncodingArray(Collection<? extends Integer> source) {
		super(source);
	}

	@Override
	public Integer getTerminator() {
		return CFStringBuiltInEncodings.kCFStringEncodingInvalidId.getValue();
	}
}
