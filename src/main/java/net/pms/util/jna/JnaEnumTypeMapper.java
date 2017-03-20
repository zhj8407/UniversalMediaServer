package net.pms.util.jna;

import com.sun.jna.DefaultTypeMapper;


public class JnaEnumTypeMapper extends DefaultTypeMapper {

	{
		addTypeConverter(JnaIntEnum.class, new JnaIntEnumConverter());
	}
}
