package com.example.rxjavasimpleexample;

import java.util.List;

import io.reactivex.Observable;

public class ViewModel {

    private final Observable<List<String>> filteredNamesObservable;

    public ViewModel(Observable<List<Person>> personsObservable){

        //lets do some magic..
        filteredNamesObservable = personsObservable
                //flatmap lets you go through all single persons that are returned and combines them in the end back into one
                .flatMap(persons ->
                        Observable.fromIterable(persons)
                            //filter all persons that are female
                            .filter(person -> person.getGender()== Gender.MALE)
                            //map to only return name
                            .map(Person::getName)
                            .toList()
                            .toObservable());
    }

    public Observable<List<String>> getNames(){
        return filteredNamesObservable;
    }

}
