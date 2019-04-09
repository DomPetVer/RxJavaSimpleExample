package com.example.rxjavasimpleexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> arrayAdapter;
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup UI
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(arrayAdapter);

        Observable<Object> buttonClickObservable = RxView.clicks(findViewById(R.id.add_new_person));

        //setup view model
        viewModel = new ViewModel(buttonClickObservable);
        viewModel.subscribe();

        //subscribe to changes and update the list in case the observable changes
        viewModel.getNames()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateList);
    }

    private void updateList(List<String> list) {
        arrayAdapter.clear();
        arrayAdapter.addAll(list);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.unsubscribe();
    }
}
