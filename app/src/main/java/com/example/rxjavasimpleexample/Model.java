package com.example.rxjavasimpleexample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class Model {

    private List<Person> fakeList = new ArrayList<>(
            Arrays.asList(
                    new Person("Stacey Starfish",Gender.FEMALE),
                    new Person("Jimmy Jellyfish", Gender.MALE),
                    new Person("Shawn Shark", Gender.MALE)));

    private BehaviorSubject<List<Person>> itemsSubject = BehaviorSubject.createDefault(fakeList);

    Model(){
        //connect to webservice?
    }

    public Observable<List<Person>> getPersons(){
        return itemsSubject.hide();
    }

    public void addNewPerson(){
        //call webservice
        fakeList.add(new Person("aPersonWithoutAName",Gender.MALE));
        fakeAllPersonsFromWebservice();
    }

    private void fakeAllPersonsFromWebservice(){
        //lets inform anyone who wants to know about our awesome team..
        itemsSubject.onNext(fakeList);
    }
}
