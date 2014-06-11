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

	String[] STOP_WORDS = { "" };
	List<String> STOP_WORDS_LIST = Arrays.asList(STOP_WORDS);
	
	final ActorRef child = getContext().actorOf(Props.create(ReduceMapActor.class));//.withRouter(new RoundRobinPool(50)), "reduceMap"); 
	static int c = 0;
	int l = 1;
	int i;
	public MapWordActor() {
		i = c++;
		System.out.println("Creating "+this);
		receive(ReceiveBuilder
				.match(String.class, line -> {
					List<WordCount> wordCounts = Stream.of(line.split(" "))
						.map(word -> word.toLowerCase())
						.filter(word -> !STOP_WORDS_LIST.contains(word))
						.filter(word -> !word.trim().isEmpty())
						.filter(word -> word.length() >= 7)
						.map(word -> word.endsWith(",") ? word.substring(0, word.length()-1) : word)
						.map(word -> word.endsWith(".") ? word.substring(0, word.length()-1) : word)
						.map(word -> new WordCount(word))
						.collect(Collectors.toList());
					child.tell(wordCounts, sender());
				}).build());
	}
	
	@Override
	public String toString() {
		return "MWA"+i;
	}
	
}
