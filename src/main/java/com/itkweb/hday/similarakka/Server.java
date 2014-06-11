package com.itkweb.hday.similarakka;

import akka.actor.ActorSystem;

import com.typesafe.config.ConfigFactory;

public class Server {
	public static void main(String[] args) {
		// Method 1: local creation, distant deployment
		ActorSystem.create("ServerAkka", ConfigFactory.load(("remote")));
		
		// Method 2: distant creation, remote lookup
//		ActorSystem system = ActorSystem.create("ServerAkka", ConfigFactory.load(("remote")));
//		system.actorOf(Props.create(CustomActor.class), "customactorname");
		
		
		System.out.println("ServerAkka Started");
	}
}
