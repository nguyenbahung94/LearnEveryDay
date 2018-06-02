package chap1;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

public class useFromIterable {
	 public static void main(String[] args) {

	        List<String> items =
	          Arrays.asList("A", "B", "Gamm", "Delta", "Epsilon");

	        Observable<String> source = Observable.fromIterable(items);
	        source.map(String::length).filter(i -> i >= 5)
	          .subscribe(s -> System.out.println("RECEIVED: " + s));
	      }
}
