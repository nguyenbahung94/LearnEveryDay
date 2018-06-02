package chap2;

import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Observable;

public class extoList {
	public static void main(String[] args) {

        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
          .toList()
          .subscribe(s -> System.out.println("Received: " + s));
        
        Observable.range(1,1000)
        .toList(1000)
        .subscribe(s -> System.out.println("Received: " + s));
        
        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
        .toList(CopyOnWriteArrayList::new)
        .subscribe(s -> System.out.println("Received: " + s));
        
      }

}
