package chap2;

import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;

public class extoMapAndToMultiMap {
	  public static void main(String[] args) {

	        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
	          .toMap(s -> s.charAt(0))
	          .subscribe(s -> System.out.println("Received: " + s));
	        
	        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
	          .toMap(s -> s.charAt(0), String::length)
	          .subscribe(s -> System.out.println("Received: " + s));
	        
	        
	        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
	          .toMap(s -> s.charAt(0),s->s.charAt(1))
	          .subscribe(s -> System.out.println("Received: " + s));
	        System.out.println("////////////////////////////");
	        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
	          .toMap(s -> s.charAt(0), String::length,   
	ConcurrentHashMap::new)
	          .subscribe(s -> System.out.println("Received: " + s));
	        
	        Observable.just(5, 2, 4, 0, 3, 2, 8)
	          .map(i -> 10 / i)
	          .subscribe(i -> System.out.println("RECEIVED: " + i),
	          e -> System.out.println("RECEIVED ERROR: " + e)
	          );
	      }
}
