package www.test720.com.naneducation.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.BindView;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseFragment;

/**
 * Created by LuoPan on 2017/10/18 17:23.
 */

public class TrainCourseEvaluateFragment extends BaseFragment {
    @BindView(R.id.ratingBar)
    RatingBar mRatingBar;
    @BindView(R.id.value)
    TextView mValue;

    private static TrainCourseEvaluateFragment fragment;
    public int isEvaluate = 0;
    String grade;

    public TrainCourseEvaluateFragment() {
    }

    public TrainCourseEvaluateFragment(String grade, int isEvaluate) {
        this.grade = grade;
        this.isEvaluate = isEvaluate;
    }

    public static TrainCourseEvaluateFragment getInstance(String grade, int isEvaluate) {

        if (fragment == null) {
            fragment = new TrainCourseEvaluateFragment(grade, isEvaluate);
        }
        return fragment;
    }

    @Override
    protected void initView() {
        LayerDrawable layerDrawable = (LayerDrawable) mRatingBar.getProgressDrawable();

        layerDrawable.getDrawable(2).setColorFilter(Color.parseColor("#FFBC00"), PorterDuff.Mode.SRC_ATOP);
        mValue.setText(grade);

        mRatingBar.setIsIndicator(true);
        mRatingBar.setRating(Float.parseFloat(grade));

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_train_course_evaluate;
    }

}
