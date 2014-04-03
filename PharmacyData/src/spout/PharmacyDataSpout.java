package com.shl.poc.storm.spout;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import com.google.gson.Gson;
import com.shl.poc.storm.context.UserContext;
import com.shl.poc.strom.commons.data.PharmacyEventData;
import com.shl.poc.strom.commons.event.patient.PharmacyDataEvent;
import com.shl.poc.storm.queue.PatientDataEventQueue;

import java.util.Map;

/**
 * User: yshuliga
 * Date: 06.01.14 11:20
 */
public class PharmacyDataSpout extends BaseRichSpout {

	private SpoutOutputCollector _connector;
	private Gson gson;

	@Override
	public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
		_connector = spoutOutputCollector;
		gson = new Gson();
	}

	@Override
	public void nextTuple() {
		if (UserContext.getInstance().size() < 2) {
			return;
		}
		PharmacyDataEvent event = PatientDataEventQueue.getInstance().pollEvent();
		if (event != null){
			_connector.emit(new Values(event.getEventTypeName(), event.insuranceId, getPayloadJSON(event.pharmacyEventData)));
		}
	}

	private String getPayloadJSON(PharmacyEventData data) {
		return gson.toJson(data);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("eventType", "patientId", "patientDataJSON"));
	}

}
