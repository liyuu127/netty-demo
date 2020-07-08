package cn.liyu.netty.demo.codec.jackcon;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ObjectMapper instance.
 * 
 * @since 1.0.0 2020年1月2日
 * @author liyu
 */
public class JacksonMapper {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static ObjectMapper getInstance() {
		return MAPPER;
	}

}
