package com.shl.poc.storm.starter.tools;

public interface Rankable extends Comparable<Rankable> {

  Object getObject();

  long getCount();

}
