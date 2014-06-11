package com.itkweb.hday.similarakka.actors;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

import com.itkweb.hday.similarakka.messages.WordCount;

public class FileAggregatorActor extends AbstractActor {
	
	
	final ActorRef mapActor = getContext().actorOf(Props.create(MapWordActor.class));//.withRouter(new RoundRobinPool(50)), "mapWord"); 
	final ActorRef resultActor = getContext().actorOf(Props.create(ResultActor.class));//.withRouter(new RoundRobinPool(50)), "mapWord"); 
	
	static int c = 1;
	int i;
	@SuppressWarnings("unchecked")
	public FileAggregatorActor() {
		i = c++;
		receive(ReceiveBuilder
				.match(Map.class, map -> {
					Map<String, WordCount> finalMap = new ConcurrentHashMap<String, WordCount>();
					Map<String, WordCount> reduced = (Map<String, WordCount>) map;
					reduced.keySet().forEach(key -> {
						if (finalMap.containsKey(key)) {
							finalMap.put(key, new WordCount(key, reduced.get(key).getCount() + finalMap.get(key).getCount()));
						}
						else {
							finalMap.put(key, reduced.get(key));
						}
					});
					List<WordCount> coll = finalMap.values().stream().sorted((wc1, wc2) -> Integer.compare(wc2.getCount(),wc1.getCount())).collect(Collectors.toList());
//					resultActor.tell(finalMap, self());
					sender().tell(finalMap, self());
					System.out.println(this.toString() + ": " + coll+" "+finalMap.size());
				})
				.match(String.class, file -> {
					System.out.println(this.toString() + " line: "+file);
					mapActor.tell(file, self());
				}).build());
	}
	@Override
	public String toString() {
		return "FileAggregatorActor[i=" + i + "]";
	}
	
	
}