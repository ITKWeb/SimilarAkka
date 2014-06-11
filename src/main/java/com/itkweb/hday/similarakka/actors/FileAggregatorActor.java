package com.itkweb.hday.similarakka.actors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.RoundRobinPool;

import com.itkweb.hday.similarakka.messages.WordCount;

public class FileAggregatorActor extends AbstractActor {
	
	private Map<String, WordCount> finalMap = new HashMap<String, WordCount>();
	
	final ActorRef mapActor = getContext().actorOf(Props.create(MapWordActor.class).withRouter(new RoundRobinPool(1)), "mapWord"); 
	
	static int c = 1;
	int i;
	@SuppressWarnings("unchecked")
	public FileAggregatorActor() {
		i = c++;
		System.out.println("Creating "+this);
		receive(ReceiveBuilder
				.match(Map.class, map -> {
					Map<String, WordCount> reduced = (Map<String, WordCount>) map;
					reduced.keySet().forEach(key -> {
						if (finalMap.containsKey(key)) {
							finalMap.put(key, new WordCount(key, reduced.get(key).getCount() + finalMap.get(key).getCount()));
						}
						else {
							finalMap.put(key, reduced.get(key));
						}
					});
					List<WordCount> coll = finalMap.values().stream().sorted((wc1, wc2) -> Integer.compare(wc2.getCount(),wc1.getCount())).limit(10).collect(Collectors.toList());
					sender().tell(finalMap, self());
					sender().tell(new Integer(4), self());
					System.out.println(this.toString() + ": " + coll+" "+finalMap.size());
				})
				.match(String.class, file -> mapActor.tell(file, self())).build());
	}
	@Override
	public String toString() {
		return "FileAggregatorActor[i=" + i + "]";
	}
	
	
}