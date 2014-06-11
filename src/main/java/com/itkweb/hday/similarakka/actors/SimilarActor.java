package com.itkweb.hday.similarakka.actors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

import com.itkweb.hday.similarakka.messages.WordCount;

public class SimilarActor extends AbstractActor {
	
	private Map<String, WordCount> finalMap = new HashMap<String, WordCount>();
	
	final ActorRef dirActor = getContext().actorOf(Props.create(DirectoryReaderActor.class), "directoryReader"); 
	
	@SuppressWarnings("unchecked")
	public SimilarActor() {
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
					System.out.println("AA: " + coll+" "+finalMap.size());
				})
				.match(String.class, dir -> dirActor.tell(dir, self()))
				.match(Integer.class, i -> {
					System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");	
				})
				.build());
	}
}