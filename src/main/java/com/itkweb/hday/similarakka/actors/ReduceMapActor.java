package com.itkweb.hday.similarakka.actors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

import com.itkweb.hday.similarakka.messages.WordCount;

public class ReduceMapActor extends AbstractActor {

	final ActorRef child = getContext().actorOf(Props.create(AggregatorActor.class));
	private Map<String, WordCount> reducedMap = new HashMap<String, WordCount>();

	static int c = 1;
	@SuppressWarnings("unchecked")
	public ReduceMapActor() {
		System.out.println("RMA"+(c++));
		receive(ReceiveBuilder
				.match(List.class,
						list -> {
							List<WordCount> wcList = (List<WordCount>) list;
							wcList.forEach(wordCount -> {
								reducedMap.put(wordCount.getWord(),
										reducedMap.containsKey(wordCount
												.getWord()) ? wordCount.inc()
												: wordCount);
							});
							child.tell(reducedMap, self());
//							System.out.println(reducedMap.keySet().size());
						})
				.build());
	}
}
