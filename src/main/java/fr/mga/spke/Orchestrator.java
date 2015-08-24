package fr.mga.spke;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.Executors;

import static fr.mga.spke.State.*;

/**
 * @author mathieu.gandin
 */
public class Orchestrator {

    private final Api api;

    public Orchestrator(Api api) {
        this.api = api;
    }

    public Booking book(String id) {
        Booking started = new Booking(id,STARTED);

        Booking booked = Observable.just(started)
                .doOnNext(booking -> booking.change(api.book(id), BOOKED))
                .onErrorReturn(throwable -> {
                    api.sendMail(throwable);
                    return started;
                })
                .toBlocking().firstOrDefault(started);

        Booking payed = Observable.just(booked)
                .filter(booking -> booking.getState().equals(BOOKED))
                .doOnNext(booking -> booking.change(api.pay(id), PAYED))
                .onErrorReturn(throwable -> {
                    api.sendMail(throwable);
                    return booked;
                })
                .toBlocking().firstOrDefault(booked);

        Booking assured = Observable.just(payed)
                .filter(booking -> booking.getState().equals(PAYED)|| booking.getState().equals(BOOKED))
                .doOnNext(booking -> booking.change(api.assurance(id), ASSURED))
                .onErrorReturn(throwable -> {
                    api.sendMail(throwable);
                    return payed;
                })
                .toBlocking().firstOrDefault(payed);

        Observable.just(payed)
                .subscribeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
                .filter(booking -> booking.getState().equals(ASSURED) || booking.getState().equals(BOOKED) || booking.getState().equals(PAYED))
                .doOnNext(b -> b.change(api.compta(id), COMPTABILIZED))
                .doOnNext(b -> api.sendMail(b.getAccumulator()))
                .subscribe(b -> api.writePdf(b.getAccumulator()) );

        return assured;
    }

}
