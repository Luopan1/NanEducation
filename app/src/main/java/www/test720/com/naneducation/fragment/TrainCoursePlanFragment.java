package www.test720.com.naneducation.fragment;

import android.widget.TextView;

import butterknife.BindView;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.baseui.BaseFragment;
import www.test720.com.naneducation.bean.TrainCourse;

/**
 * Created by LuoPan on 2017/10/18 17:19.
 */

public class TrainCoursePlanFragment extends BaseFragment {
    @BindView(R.id.text_time)
    TextView mTextTime;
    @BindView(R.id.text_courseKind)
    TextView mTextCourseKind;
    @BindView(R.id.text_allTime)
    TextView mTextAllTime;
    @BindView(R.id.courstStudyAddress)
    TextView mCourstStudyAddress;
    @BindView(R.id.courstStudyTime)
    TextView mCourstStudyTime;
    private static TrainCoursePlanFragment fragment;

    TrainCourse.DataBean.DetailBean.PlanBean data;

    public TrainCoursePlanFragment() {

    }

    public TrainCoursePlanFragment(TrainCourse.DataBean.DetailBean.PlanBean data) {
        this.data = data;
        setData();
    }

    private void setData() {

    }

    public static TrainCoursePlanFragment getInstance(TrainCourse.DataBean.DetailBean.PlanBean data) {

        if (fragment == null) {
            fragment = new TrainCoursePlanFragment(data);
        }
        return fragment;
    }


    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mTextTime.setText(data.getCourse_time() + "分钟");
        mTextCourseKind.setText(data.getCourse_type());
        mTextAllTime.setText("共" + data.getCourse_mun() + "课时");
        mCourstStudyAddress.setText(data.getTrain_address());
        mCourstStudyTime.setText("每周" + data.getC_openwenk() + "  " + data.getC_downtime());
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_train_course;
    }


}
