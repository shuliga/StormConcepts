package com.shl.poc.strom.commons.event.notification;

import com.shl.poc.strom.commons.event.common.AbstractEvent;
import com.shl.poc.strom.commons.event.common.EventType;

import java.io.Serializable;

/**
 * User: yshuliga
 * Date: 10.01.14 15:58
 */
public class NotificationEvent extends AbstractEvent implements Serializable {

	static final long serialVersionUID = 253787460479L;

	public String targetId;
	private String notificationMessage;
	public String eventId;
	public String eventPayloadReference;

	public NotificationEvent(){
	}

	public NotificationEvent(String targetId, String notificationMessage, String eventId, String eventPayloadReference, String senderName){
		super(notificationMessage, senderName);
		this.targetId = targetId;
		this.notificationMessage = notificationMessage;
		this.eventId = eventId;
		this.eventPayloadReference = eventPayloadReference;
	}

	@Override
	protected EventType createEventType() {
		return new NotificationEventType();
	}
}
