package com.shl.poc.strom.commons.event.patient;

import com.shl.poc.strom.commons.event.common.EventType;

/**
 * User: yshuliga
 * Date: 06.01.14 11:53
 */
public class PatientDataEventType extends EventType {

	public final String typeName = "PATIENT_DATA_EVENT";

	@Override
	public String getTypeName() {
		return typeName;
	}
}
