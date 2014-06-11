package com.itkweb.hday.similarakka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.itkweb.hday.similarakka.actors.SimilarActor;
import com.typesafe.config.ConfigFactory;

public class Client {
	
	public static void main(String[] args) {
		
		// Method 1: local creation, distant deployment
		//	/!\ DistantActor to change here and in client.conf /!\
		final ActorSystem system = ActorSystem.create("ClientAkka", ConfigFactory.load("remotedeployment"));
		final ActorRef actor = system.actorOf(Props.create(SimilarActor.class), "similarActor");
		
		
		// Method 2: distant creation, remote lookup
//		final ActorSystem system = ActorSystem.create("LookupSystem", ConfigFactory.load("remote"));
//		final String path = "akka.tcp://ServerAkka@127.0.0.1:2552/user/customactorname";
//		final ActorRef actor = system.actorOf(Props.create(CustomActor.class, path), "lookupActor");
		
		
		System.out.println("ClientAkka Started");
		
		actor.tell("/home/etaix/development/workspaces/HDay-luna/similarakka/docs/", ActorRef.noSender());
		//Test.launch(actor);
	}
}
