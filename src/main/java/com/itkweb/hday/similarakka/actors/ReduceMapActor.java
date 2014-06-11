package com.itkweb.hday.similarakka.actors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import com.itkweb.hday.similarakka.messages.WordCount;

public class ReduceMapActor extends AbstractActor {

	static int c = 0;
	int i;
	@SuppressWarnings("unchecked")
	public ReduceMapActor() {
		i = c++;
		System.out.println("Creating "+this);
		receive(ReceiveBuilder
				.match(List.class,
						list -> {
							List<WordCount> wcList = (List<WordCount>) list;
							Map<String, WordCount> reducedMap = new HashMap<String, WordCount>();
							wcList.forEach(wordCount -> {
								reducedMap.put(wordCount.getWord(),
										reducedMap.containsKey(wordCount
												.getWord()) ? wordCount.inc()
												: wordCount);
							});
							sender().tell(reducedMap, ActorRef.noSender());
							System.out.println(this.toString()+" "+reducedMap.keySet().size());
						})
				.build());
	}
	@Override
	public String toString() {
		return "RMA"+i;
	}
	
}
