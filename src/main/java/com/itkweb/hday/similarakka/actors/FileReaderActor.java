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

public class FileReaderActor extends AbstractActor {

	final ActorRef child = getContext().actorOf(Props.create(FileAggregatorActor.class));//.withRouter(new RoundRobinPool(10)), "fileAggregator"); 
	static int c = 1;
	int i;
	public FileReaderActor() {
		i = c++;
		receive(ReceiveBuilder
				.match(String.class,
						(filename) -> {
							System.out.println(this.toString()+" reading file "+filename);
							Path path = Paths.get(filename);
						    try(Stream<String> lines = Files.lines(path)) {
						    	lines.forEach(line -> {
						    		child.tell(line, sender());
						    	});

						    }							
						}).build());
	}
	@Override
	public String toString() {
		return "FileReaderActor [i=" + i + "]";
	}

}
