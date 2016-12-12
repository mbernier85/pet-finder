package im.bernier.petfinder.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.bernier.petfinder.R;
import im.bernier.petfinder.activity.ShelterResultActivity;
import im.bernier.petfinder.mvp.presenter.ShelterSearchPresenter;
import im.bernier.petfinder.mvp.view.ShelterSearchView;

/**
 * Created by Michael on 2016-10-29.
 */

public class ShelterSearchViewTab extends FrameLayout implements ShelterSearchView {

    @BindView(R.id.search_shelter_location_edit_text)
    EditText locationEditText;

    @BindView(R.id.search_shelter_name_edit_text)
    EditText nameEditText;

    @BindView(R.id.search_shelter_button_submit)
    Button submitButton;

    private ShelterSearchPresenter presenter;

    public ShelterSearchViewTab(Context context) {
        super(context);
        init();
    }

    public ShelterSearchViewTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShelterSearchViewTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.view_shelter_search, this);
        ButterKnife.bind(this);

        presenter = new ShelterSearchPresenter(this);
    }

    @Override
    public void showLocationEmpty() {
        locationEditText.setError(getContext().getString(R.string.empty_zip_error));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.onAttach();
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.onDetach();
        super.onDetachedFromWindow();
    }

    @OnClick(R.id.search_shelter_button_submit)
    void submitClick() {
        String location = locationEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        presenter.submit(location, name);
    }

    @Override
    public void openShelter() {
        Intent intent = ShelterResultActivity.getIntent(this.getContext(), 0);
        getContext().startActivity(intent);
    }
}
