package chap2;

import io.reactivex.Observable;

public class exdoOnNext {
	  public static void main(String[] args) {

	        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
	          .doOnNext(s -> System.out.println("Processing: " + s))
	          .doAfterNext(s->System.out.print("Processing aftera:"+s))
	          .map(String::length)
	          .subscribe(i -> System.out.println("Received: " + i));
	          System.out.println("////////////////////////////////");
	          Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
	          .doOnComplete(() -> System.out.println("Source is done   emitting!"))
	          .map(String::length)
	          .subscribe(i -> System.out.println("Received: " + i));
	          System.out.println("////////////////////////////////");
	          
	          Observable.just(5, 2, 4, 0, 3, 2, 8)
	          .doOnError(e -> System.out.println("Source failed!"))
	          .map(i -> 10 / i)
	          .doOnError(e -> System.out.println("Division failed!"))
	          .subscribe(i -> System.out.println("RECEIVED: " + i),
	          e -> System.out.println("RECEIVED ERROR: " + e)
	          );
	      }
}
