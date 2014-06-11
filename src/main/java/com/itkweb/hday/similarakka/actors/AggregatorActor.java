package com.itkweb.hday.similarakka.actors;

import java.util.HashMap;
import java.util.Map;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import com.itkweb.hday.similarakka.messages.WordCount;

public class AggregatorActor extends AbstractActor {
	
	private Map<String, WordCount> finalMap = new HashMap<String, WordCount>();
	
	@SuppressWarnings("unused")
	public AggregatorActor() {
		receive(ReceiveBuilder.match(Map.class, map -> {
			Map<String, WordCount> reduced = (Map<String, WordCount>) map;
			reduced.keySet().forEach(key -> {
				if (finalMap.containsKey(key)) {
					finalMap.put(key, new WordCount(key, reduced.get(key).getCount() + finalMap.get(key).getCount()));
				}
				else {
					finalMap.put(key, reduced.get(key));
				}
			});
			System.out.println(finalMap.keySet().size());
		}).build());
	}
}