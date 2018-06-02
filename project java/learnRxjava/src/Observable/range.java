package Observable;

import io.reactivex.Observable;

public class range {
	public static void main(String[] args) {
        Observable.range(1,10)
          .subscribe(s -> System.out.println("RECEIVEDAAAA: " + s));
        
        Observable.range(5,10)
        .subscribe(s -> System.out.println("RECEIVEDBBBB: " + s));

      }
}
