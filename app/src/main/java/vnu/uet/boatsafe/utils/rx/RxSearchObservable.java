package vnu.uet.boatsafe.utils.rx;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxSearchObservable {

    public static Observable<String> fromView(EditText searchView) {

        final PublishSubject<String> subject = PublishSubject.create();

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                subject.onNext(editable.toString());
            }
        });

        return subject;
    }

}
