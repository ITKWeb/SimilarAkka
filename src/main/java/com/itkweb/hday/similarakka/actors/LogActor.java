package com.itkweb.hday.similarakka.actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

public class LogActor extends AbstractActor {

	public LogActor() {
		receive(ReceiveBuilder.match(Object.class, message -> System.out.println(message)).build());
	}
	
}
