package chap2;

import io.reactivex.Observable;

public class extakeWhileandskipWhile {
	 public static void main(String[] args) {

	        Observable.range(1,100)
	          .takeWhile(i -> i <=5)
	          .subscribe(i -> System.out.println("RECEIVED takeWhile: " + i));
	        
	        //
	        Observable.range(1,100)
	          .skipWhile(i -> i <= 95)
	          .subscribe(i -> System.out.println("RECEIVED skipWhile : " + i));
	      }
}
