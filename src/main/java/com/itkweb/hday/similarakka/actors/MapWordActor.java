package com.itkweb.hday.similarakka.actors;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.RoundRobinPool;

import com.itkweb.hday.similarakka.messages.WordCount;

public class MapWordActor extends AbstractActor {

	String[] STOP_WORDS = { "le", "du", "de", "par", "la", "je", "tu", "il", "elle", "vous", "nous", "ils", "elles" };
	List<String> STOP_WORDS_LIST = Arrays.asList(STOP_WORDS);
	
	final ActorRef child = getContext().actorOf(Props.create(ReduceMapActor.class)); //.withRouter(new RoundRobinPool(1))); 
	static int c = 1;
	int l = 1;
	public MapWordActor() {
		System.out.println("MWA"+(c++));

		receive(ReceiveBuilder
				.match(String.class, line -> {
					List<WordCount> wordCounts = Stream.of(line.split(" "))
						.map(word -> word.toLowerCase())
						.filter(word -> !STOP_WORDS_LIST.contains(word))
						.filter(word -> !word.trim().isEmpty())
						.map(word -> word.endsWith(",") ? word.substring(0, word.length()-1) : word)
						.map(word -> word.endsWith(".") ? word.substring(0, word.length()-1) : word)
						.map(word -> new WordCount(word))
						.collect(Collectors.toList());
//					System.out.println("MWA"+c+" line:"+(l++));
					child.tell(wordCounts, self());
				}).build());
	}
	
}
