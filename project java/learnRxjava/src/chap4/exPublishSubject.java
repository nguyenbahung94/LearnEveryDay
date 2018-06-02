package chap4;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class exPublishSubject {
	   public static void main(String[] args) {

	        Subject<String> subject = PublishSubject.create();

	        subject.map(String::length)
            .subscribe(System.out::println);

	        subject.onNext("Alpha");
	        subject.onNext("Beta");
	        subject.onNext("Gamma");
	        subject.onComplete();
	      
	    }
}
