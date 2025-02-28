package chap2;

import io.reactivex.Observable;

public class exdoOnSuccess {
	 public static void main(String[] args) {
	        Observable.just(5, 3, 7, 10, 2, 14)
	          .reduce((total, next) -> total + next)
	          .doOnSuccess(i -> System.out.println("Emitting: " + i))
	          .subscribe(i -> System.out.println("Received: " + i));
	      }
}
