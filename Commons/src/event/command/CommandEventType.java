package com.shl.poc.strom.commons.event.command;

import com.shl.poc.strom.commons.event.common.EventType;

import java.io.Serializable;

/**
 * User: yshuliga
 * Date: 06.01.14 11:53
 */
public class CommandEventType extends EventType implements Serializable {
	public static final Long serialVersionUID = 5973757367827364L;

	public final String typeName = "COMMAND_EVENT";

	@Override
	public String getTypeName() {
		return typeName;
	}
}
