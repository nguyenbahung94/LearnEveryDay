package chap1;

import io.reactivex.Observable;

public class ShorthandObserverswithlambdas {
public static void main(String[] args) {
	Observable<String> source=Observable.just("ones","twoss","three");
	source.map(String::length).filter(i->i>=5)
	.subscribe(i->System.out.println(i),
			Throwable::getStackTrace);
}
}
