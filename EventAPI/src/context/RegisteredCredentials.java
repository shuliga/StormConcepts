package com.shl.poc.storm.api.context;

import com.shl.poc.strom.commons.security.Credentials;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;

/**
 * User: yshuliga
 * Date: 07.01.14 13:00
 */
@ApplicationScoped
public class RegisteredCredentials extends HashSet<Credentials> {

	public RegisteredCredentials(){
	}

	@PostConstruct
	protected void init(){
		add(new Credentials("Adam", Credentials.Roles.PHYSICIAN.getLabel()));
		add(new Credentials("Eve", Credentials.Roles.NURSE.getLabel()));
		add(new Credentials("PharmacyDataStream", Credentials.Roles.SYSTEM.getLabel()));
	}
}
