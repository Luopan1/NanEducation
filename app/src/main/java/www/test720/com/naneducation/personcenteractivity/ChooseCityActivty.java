package www.test720.com.naneducation.personcenteractivity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.CityListAdapter;
import www.test720.com.naneducation.adapter.CitySearchResultAdapter;
import www.test720.com.naneducation.adapter.CommonAdaper;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.City;
import www.test720.com.naneducation.bean.NetworkCityBean;
import www.test720.com.naneducation.utils.DBManager;
import www.test720.com.naneducation.utils.LocateState;
import www.test720.com.naneducation.view.SideLetterBar;

public class ChooseCityActivty extends BaseToolbarActivity {
    public static final String RESULT = "result";
    @BindView(R.id.listview_all_city)
    ListView mListviewAllCity;
    @BindView(R.id.tv_letter_overlay)
    TextView mTvLetterOverlay;
    @BindView(R.id.side_letter_bar)
    SideLetterBar mSideLetterBar;
    @BindView(R.id.listview_search_result)
    ListView mListviewSearchResult;
    @BindView(R.id.fanhui)
    RelativeLayout mFanhui;
    @BindView(R.id.searchEdit_text)
    EditText mSearchEditText;
    @BindView(R.id.titleLinear)
    LinearLayout mTitleLinear;

    private List<NetworkCityBean> networkCityBeen = new ArrayList<>();
    private List<NetworkCityBean> networkHotCityBeen = new ArrayList<>();
    private DBManager dbManager;
    private List<City> mAllCities = new ArrayList<>();
    private CityListAdapter mCityAdapter;
    private List<City> MSortList;
    private Subscription mSubscribe;
    private CommonAdaper<City> mResultAdapter;
    private CitySearchResultAdapter mCitySearchResultAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_choose_city_activty;
    }

    @Override
    protected void initData() {


        mSubscribe = Observable.create(new Observable.OnSubscribe<List<City>>() {
            @Override
            public void call(Subscriber<? super List<City>> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    dbManager = new DBManager(ChooseCityActivty.this);
                    dbManager.copyDBFile();
                    mAllCities = dbManager.getAllCities();
                    subscriber.onNext(mAllCities);
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<City>>() {

                    @Override
                    public void onStart() {
                        showLoadingDialog();
                    }

                    @Override
                    public void onCompleted() {
                        cancleLoadingDialog();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<City> cities) {
                        mCityAdapter = new CityListAdapter(ChooseCityActivty.this, cities, networkHotCityBeen);
                        initLocal();
                    }
                });

    }


    @Override
    protected void initBase() {
        isShowBackImage = false;
        isShowToolBar = false;
    }

    @Override
    protected void initView() {
        mSearchEditText.setHint("输入城市名或者拼音查询");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null && mSubscribe.isUnsubscribed()) {
            mSubscribe.unsubscribe();
        }
    }

    private void initLocal() {

        mListviewAllCity.setAdapter(mCityAdapter);

        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mSideLetterBar.setOverlay(overlay);
        mSideLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListviewAllCity.setSelection(position);
            }
        });

        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {

            @Override
            public void onCityClick(String city) {

                Intent intent = new Intent();
                intent.putExtra("city", city);
                setResult(0x00006, intent);
                finish();
            }

            @Override
            public void onLocateClick() {
                Log.e("onLocateClick", "重新定位...");

                mCityAdapter.updateLocateState(LocateState.LOCATING, null);

            }
        });
    }


    @Override
    protected void setListener() {
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    mListviewAllCity.setVisibility(View.VISIBLE);
                    mListviewSearchResult.setVisibility(View.GONE);
                } else {
                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        mListviewAllCity.setVisibility(View.VISIBLE);
                        mListviewSearchResult.setVisibility(View.GONE);
                    } else {
                        mListviewAllCity.setVisibility(View.GONE);
                        mListviewSearchResult.setVisibility(View.VISIBLE);
                        setResultAdapter(result);
                    }
                }
            }
        });


    }

    private void setResultAdapter(List<City> result) {
        if (mCitySearchResultAdapter == null) {
            mCitySearchResultAdapter = new CitySearchResultAdapter(this, result);
            mListviewSearchResult.setAdapter(mCitySearchResultAdapter);
        } else {
            mCitySearchResultAdapter.notifyDataSetChanged();
        }


    }


    @OnClick(R.id.fanhui)
    public void onClick() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (mListviewSearchResult.getVisibility() == View.VISIBLE) {
            mListviewAllCity.setVisibility(View.VISIBLE);
            mListviewSearchResult.setVisibility(View.GONE);
            mSearchEditText.setText("");
        } else {
            finish();
        }
    }
}
