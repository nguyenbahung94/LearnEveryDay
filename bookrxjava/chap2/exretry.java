package chap2;

import io.reactivex.Observable;

public class exretry {
	 public static void main(String[] args) {

	        Observable.just(5, 2, 4, 0, 3, 2, 8)
	          .map(i -> 10 / i)
	          .retry(2)
	          .subscribe(i -> System.out.println("RECEIVED: " + i),
	          e -> System.out.println("RECEIVED ERROR: " + e)
	          );
	      }
}
