package com.bat.firstcom.supervisorapp.domain.usecases;

import dagger.internal.Preconditions;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Abstract class for a Use Case.
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 * <p>
 * By convention each UseCase implementation will return the result using a {@link DisposableObserver}
 * that will execute its job in a background thread and will post the result in the UI thread.
 * Generic T: data return (Collection, model)
 * Generic Params: parameter, Void if it's unnecessary.
 */
public abstract class UseCase<T, Params> {

    private Holder holder;

    UseCase() {
        holder = new Holder();
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    abstract Observable<T> buildUseCaseObservable(Params params);

    abstract Single<T> buildUseCaseSingle(Params params);

    abstract Completable buildUseCaseCompletable(Params params);

    /**
     * @param params Parameters (Optional) used to build/execute this use case.
     * @return UseCase itself
     */
    public UseCase<T, Params> param(Params params) {
        holder.single = buildUseCaseSingle(params);
        holder.observable = buildUseCaseObservable(params);
        holder.completable = buildUseCaseCompletable(params);
        return this;
    }

    public UseCase<T, Params> on(Scheduler executorScheduler, Scheduler postScheduler) {
        holder.checkLegal();
        if (holder.observable != null) {
            holder.observable =
                    holder.observable.subscribeOn(executorScheduler)
                            .observeOn(postScheduler);
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

    /**
     * Executes the current use case.
     *
     * @param observer {@link DisposableObserver} which will be listening to the observable build
     *                 by {@link #buildUseCaseObservable(Params)} ()} method.
     */
    public Disposable execute(DisposableObserver<T> observer) {
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
