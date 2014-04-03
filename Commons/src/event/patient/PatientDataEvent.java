package com.shl.poc.strom.commons.event.patient;

import com.shl.poc.strom.commons.data.PatientData;
import com.shl.poc.strom.commons.event.common.AbstractEvent;
import com.shl.poc.strom.commons.event.common.EventType;

/**
 * User: yshuliga
 * Date: 06.01.14 11:58
 */
public abstract class PatientDataEvent extends AbstractEvent {

	public String insuranceId;

	public PatientDataEvent(String insuranceId, PatientData payload){
		super(payload, "PatientEventStream");
		this.insuranceId = insuranceId;
	}

	@Override
	protected EventType createEventType() {
		return new PatientDataEventType();
	}

	public abstract String getRule();

}
