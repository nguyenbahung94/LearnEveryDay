package chap1;

import io.reactivex.Observable;

public class ObservableWithCreate {
 public static void main(String[] args) {
	 Observable<String> source=Observable.create(emiter->{
		emiter.onNext("Alph"); 
		emiter.onNext("Al"); 
		emiter.onNext("Al"); 
		emiter.onNext("Alpha4"); 
		emiter.onNext("Alpha5"); 
		emiter.onNext("Alpha6"); 
		emiter.onNext("Alpha7"); 

	 });
	 source.subscribe(s -> System.out.println("RECEIVED: " + s));
	 Observable<Integer> lengths = source.map(String::length);
	 Observable<Integer> filtered = lengths.filter(i -> i >= 5);
	 filtered.subscribe(s -> System.out.println("RECEIVED: " +  s));    
 }
}
