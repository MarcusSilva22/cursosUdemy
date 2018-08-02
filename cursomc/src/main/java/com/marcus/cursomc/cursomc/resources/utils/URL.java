package com.marcus.cursomc.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class URL {
	
	public static String decodeParam(String s){
		try {
			URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) { 
			return "";
		}
		return s;
	}
	
	public static List<Integer> decodeIntList(String s){
		return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt((String) x)).collect(Collectors.toList());		
	}

}
