package chap2;

import io.reactivex.Observable;

public class exskip {
	 public static void main(String[] args) {

	        Observable.range(1,100)
	          .skip(90)
	          .subscribe(i -> System.out.println("RECEIVED: " + i));
	      }
}
