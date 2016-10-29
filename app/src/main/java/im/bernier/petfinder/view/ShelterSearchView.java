package im.bernier.petfinder.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import im.bernier.petfinder.R;
import im.bernier.petfinder.mvp.presenter.ShelterSearchPresenter;
import im.bernier.petfinder.mvp.view.IShelterSearchView;

/**
 * Created by Michael on 2016-10-29.
 */

public class ShelterSearchView extends FrameLayout implements IShelterSearchView {

    @BindView(R.id.search_shelter_location_text_view)
    TextView searchTextView;

    @BindView(R.id.search_shelter_name_text_view)
    TextView groupNameTextView;

    @BindView(R.id.search_shelter_button_submit)
    Button submitButton;

    private ShelterSearchPresenter presenter;

    public ShelterSearchView(Context context) {
        super(context);
        init();
    }

    public ShelterSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShelterSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.view_shelter_search, this);
        ButterKnife.bind(this);

        presenter = new ShelterSearchPresenter();
        presenter.setView(this);
        presenter.onAttach();
    }

    @OnClick(R.id.search_shelter_button_submit)
    void submitClick() {
        String location = searchTextView.getText().toString().trim();
        String name = groupNameTextView.getText().toString().trim();
        presenter.submit(location, name);
    }

    @Override
    public void openShelter() {

    }
}
