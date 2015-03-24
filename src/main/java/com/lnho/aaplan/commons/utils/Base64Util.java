package com.lnho.aaplan.commons.utils;

import java.io.ByteArrayOutputStream;

/**
 * base64加密.
 * 
 * @author qingwu
 * @date 2014-2-7 下午3:01:18
 */
public class Base64Util {

	private static final char base64EncodeChars[] = { 'A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
			'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', '+', '/' };
	private static final byte base64DecodeChars[] = { -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60,
			61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
			11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1,
			-1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,
			39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1,
			-1 };

	private Base64Util() {
	}

	public static final synchronized String encode(byte data[]) {
		StringBuffer sb = new StringBuffer();
		int r = data.length % 3;
		int len = data.length - r;
		int i;
		for (i = 0; i < len;) {
			int c = (0xff & data[i++]) << 16 | (0xff & data[i++]) << 8 | 0xff
					& data[i++];
			sb.append(base64EncodeChars[c >> 18]);
			sb.append(base64EncodeChars[c >> 12 & 0x3f]);
			sb.append(base64EncodeChars[c >> 6 & 0x3f]);
			sb.append(base64EncodeChars[c & 0x3f]);
		}

		if (r == 1) {
			int c = 0xff & data[i++];
			sb.append(base64EncodeChars[c >> 2]);
			sb.append(base64EncodeChars[(c & 3) << 4]);
			sb.append("==");
		} else if (r == 2) {
			int c = (0xff & data[i++]) << 8 | 0xff & data[i++];
			sb.append(base64EncodeChars[c >> 10]);
			sb.append(base64EncodeChars[c >> 4 & 0x3f]);
			sb.append(base64EncodeChars[(c & 0xf) << 2]);
			sb.append("=");
		}
		return sb.toString();
	}

	public static final synchronized byte[] decode(String str) {
		byte data[] = str.getBytes();
		int len = data.length;
		ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
		for (int i = 0; i < len;) {
			int b1;
			do
				b1 = base64DecodeChars[data[i++]];
			while (i < len && b1 == -1);
			if (b1 == -1)
				break;
			int b2;
			do
				b2 = base64DecodeChars[data[i++]];
			while (i < len && b2 == -1);
			if (b2 == -1)
				break;
			buf.write(b1 << 2 | (b2 & 0x30) >>> 4);
			int b3;
			do {
				b3 = data[i++];
				if (b3 == 61)
					return buf.toByteArray();
				b3 = base64DecodeChars[b3];
			} while (i < len && b3 == -1);
			if (b3 == -1)
				break;
			buf.write((b2 & 0xf) << 4 | (b3 & 0x3c) >>> 2);
			int b4;
			do {
				b4 = data[i++];
				if (b4 == 61)
					return buf.toByteArray();
				b4 = base64DecodeChars[b4];
			} while (i < len && b4 == -1);
			if (b4 == -1)
				break;
			buf.write((b3 & 3) << 6 | b4);
		}

		return buf.toByteArray();
	}

	public static final synchronized String urlSafeEncode(String data) {
		data = encode(data.getBytes());
		data = data.replace("+", "-").replace("/", "_").replace("=", "");
		return data;
	}

	public static final synchronized byte[] urlSafeDecode(String data) {
		data = data.replace("-", "+").replace("_", "/");
		int mod = data.length() % 4;
		if (mod > 0)
			data = (new StringBuilder(String.valueOf(data))).append(
					"====".substring(mod)).toString();
		return decode(data);
	}

}
