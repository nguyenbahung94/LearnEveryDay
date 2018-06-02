package com.example.nbhung.demotwitter.domain.usercases;

import javax.inject.Singleton;

import dagger.internal.Preconditions;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public abstract class UseCase<T, Params> {
    private Holder holder;

    public UseCase() {
        this.holder = new Holder();
    }

    abstract Observable<T> buildUseCaseObservable(Params params);

    abstract Single<T> buildUseCaseSingle(Params params);

    abstract Completable buildUseCaseCompletable(Params params);

    public UseCase<T, Params> param(Params params) {
        holder.single = buildUseCaseSingle(params);
        holder.completable = buildUseCaseCompletable(params);
        holder.observable = buildUseCaseObservable(params);
        return this;
    }

    public UseCase<T, Params> on(Scheduler executorScheduler, Scheduler postScheduler) {
        holder.checkLegal();
        if (holder.observable != null) {
            holder.observable = holder.observable.subscribeOn(executorScheduler).observeOn(postScheduler);
            return this;
        }
        if (holder.single != null) {
            holder.single = holder.single.subscribeOn(executorScheduler)
                    .observeOn(postScheduler);
            return this;
        }
        if (holder.completable != null) {
            holder.completable =
                    holder.completable.subscribeOn(executorScheduler)
                            .observeOn(postScheduler);
        }
        return this;
    }

    public Disposable excute(DisposableObserver<T> observer) {
        Preconditions.checkNotNull(observer);
        return holder.observable.subscribeWith(observer);
    }
    public Disposable execute(DisposableSingleObserver<T> observer) {
        Preconditions.checkNotNull(observer);
        return holder.single.subscribeWith(observer);
    }
    class Holder {
        Observable<T> observable;
        Single<T> single;
        Completable completable;

        void checkLegal() {
            boolean legal = (observable != null) || (single != null) || (completable != null);
            if (!legal) throw new IllegalStateException("none of RxExecutor has been created");
        }
    }
}
