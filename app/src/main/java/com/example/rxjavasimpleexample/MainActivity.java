package com.example.rxjavasimpleexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapter;
    private ViewModel viewModel;
    private CompositeDisposable subscriptions = new CompositeDisposable();
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup UI
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(arrayAdapter);

        Observable<Object> buttonClickObservable = RxView.clicks(findViewById(R.id.add_new_person));
        subscriptions.add(buttonClickObservable
                .subscribe(click -> model.addNewPerson()));

        //the model is often provided by the application
        this.model = new Model();

        //setup view model
        viewModel = new ViewModel(model.getPersons());

        //subscribe to changes and update the list in case the observable changes
        subscriptions.add(viewModel.getNames()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateList));
    }

    private void updateList(List<String> list) {
        arrayAdapter.clear();
        arrayAdapter.addAll(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscriptions.clear();
    }
}
