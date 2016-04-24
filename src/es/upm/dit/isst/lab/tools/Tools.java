package es.upm.dit.isst.lab.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class Tools {
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String sha256(String input) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		try {
			md.update(input.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return Tools.bytesToHex(md.digest());
	}

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	public static void sendJson(HttpServletResponse response, Object toJson) throws IOException {
		response.setContentType("application/json");
		PrintWriter out;
		out = response.getWriter();
		String jsonString = new Gson().toJson(toJson);
		out.print(jsonString);
		out.flush();
	}


	public static void sendJson(HttpServletResponse response, Object toJson, Class clazz) throws IOException {
		response.setContentType("application/json");
		PrintWriter out;
		out = response.getWriter();
		String jsonString = new Gson().toJson(toJson, clazz);
		out.print(jsonString);
		out.flush();
	}

	public static String readRequestAsString(HttpServletRequest req) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br;
		br = req.getReader();
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
		return sb.toString();
	}
}