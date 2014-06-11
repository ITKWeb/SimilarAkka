package com.itkweb.hday.similarakka.actors;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.RoundRobinPool;

import com.itkweb.hday.similarakka.messages.FileToRead;

public class FileReaderActor extends AbstractActor {

	final ActorRef child = getContext().actorOf(Props.create(MapWordActor.class)); //.withRouter(new RoundRobinPool(1))); 
	static int c = 1;
	public FileReaderActor() {
		System.out.println("FRA"+(c++));
		receive(ReceiveBuilder
				.match(FileToRead.class,
						(message) -> {
							Path path = Paths.get(message.getFilename());
						    try(Stream<String> lines = Files.lines(path)) {
						    	lines.forEach(line -> {
						    		child.tell(line, self());
						    	});

						    }							
						}).build());
	}

}
