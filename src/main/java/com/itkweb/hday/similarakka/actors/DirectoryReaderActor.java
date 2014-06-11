package com.itkweb.hday.similarakka.actors;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.RoundRobinPool;

public class DirectoryReaderActor extends AbstractActor {

	final ActorRef child = getContext().actorOf(Props.create(FileReaderActor.class).withRouter(new RoundRobinPool(10)), "fileReader"); 
	
	public DirectoryReaderActor() {
		receive(ReceiveBuilder.match(String.class, dir -> {
	        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("/home/etaix/development/workspaces/HDay-luna/similarakka/docs"))) {
	            for (Path path : directoryStream) {
	                child.tell(path.toString(), sender());
	            }
	        } 
		}).build());
	}
}
