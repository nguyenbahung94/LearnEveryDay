package Observable;

import io.reactivex.Observable;

public class Observabledefer {
	 private static int start = 1;
     private static int count = 5;
	 public static void main(String[] args) {
	        
	        Observable<Integer> source = Observable.range(start,count);

	        source.subscribe(i -> System.out.println("Observer 1: " + i));

	        //modify count
	        count = 10;

	        source.subscribe(i -> System.out.println("Observer 2: " + i));
	        
	        
	        Observable<Integer>demoDEfer=Observable.defer(()->
	        	Observable.range(start, count)
	        );
	        
	        demoDEfer.subscribe(i -> System.out.println("demoDEfer Observer 1: " + i));

	        //modify count
	        count = 10;

	        demoDEfer.subscribe(i -> System.out.println("demoDEfer Observer 2: " + i));
	       }
}
