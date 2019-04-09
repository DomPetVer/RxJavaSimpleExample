package com.example.rxjavasimpleexample;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class ViewModel {

    private final Observable<Object> buttonClickObservable;
    private CompositeDisposable subscriptions = new CompositeDisposable();
    private final Model model;
    private Observable<List<Person>> personObservable;

    public ViewModel(Observable<Object> buttonClickObservable){
        this.model = new Model();
        this.buttonClickObservable = buttonClickObservable;
        personObservable = this.model.getItems();
    }

    public Observable<List<String>> getNames(){
        //lets do some magic..
        Observable<List<String>> filteredNames = personObservable
                //flatmap lets you go through all single persons that are returned
                .flatMap(person -> Observable.fromIterable(person)
                                     //filter all persons that are female
                                    .filter(thePerson -> thePerson.getGender()== Gender.MALE ? true : false)
                                    .map(thePerson -> thePerson.getName())
                         .toList()
                         .toObservable());
        return filteredNames;
    }

    public void subscribe(){
        subscriptions.add(buttonClickObservable
            .subscribe(click -> model.addNewPerson()));
    }
    public void unsubscribe(){
        subscriptions.clear();
    }

}
