package com.shl.poc.strom.commons.event.patient;

import com.shl.poc.strom.commons.data.PharmacyEventData;

/**
 * User: yshuliga
 * Date: 06.01.14 14:43
 */
public class PharmacyDataEvent extends PatientDataEvent {

	public PharmacyEventData pharmacyEventData;

	public PharmacyDataEvent(String insuranceId, PharmacyEventData payload) {
		super(insuranceId, payload.patientData);
		this.pharmacyEventData = payload;
	}

	@Override
	public String getRule() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
