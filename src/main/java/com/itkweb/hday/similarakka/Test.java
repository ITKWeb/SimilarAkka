package com.itkweb.hday.similarakka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.itkweb.hday.similarakka.actors.SimilarActor;

public class Test {

    public static void main(String[] args) {
    	
        final ActorSystem system = ActorSystem.create("similarakka");

    	final ActorRef aggregator = system.actorOf(Props.create(SimilarActor.class), "similarActor");
    	aggregator.tell("/home/etaix/development/workspaces/HDay-luna/similarakka/docs/", ActorRef.noSender());

    }
}
