package www.test720.com.naneducation.activity;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.test720.com.naneducation.R;
import www.test720.com.naneducation.adapter.CommonAdaper;
import www.test720.com.naneducation.adapter.ViewHolder;
import www.test720.com.naneducation.baseui.BaseToolbarActivity;
import www.test720.com.naneducation.bean.Grade;

public class AddSignUpPersonActivity extends BaseToolbarActivity {


    @BindView(R.id.commonUseSignUpListView)
    ListView mCommonUseSignUpListView;
    @BindView(R.id.addNewSignUpPeoPle)
    Button mAddNewSignUpPeoPle;
    List<Grade> mPersonLists;
    CommonAdaper<Grade> commonAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_add_sign_up_person;
    }

    @Override
    protected void initData() {
        mPersonLists = new ArrayList<>();
        mPersonLists.add(new Grade(1, "杨泽民"));
        mPersonLists.add(new Grade(2, "王大仙"));
        setAdapter();
    }

    private void setAdapter() {
        if (commonAdapter == null) {
            commonAdapter = new CommonAdaper<Grade>(this, mPersonLists, R.layout.item_add_person_item2) {
                @Override
                public void convert(ViewHolder holder, Grade item, int position) {
                    holder.setText(R.id.personName, item.getGrade());
                }
            };
            mCommonUseSignUpListView.setAdapter(commonAdapter);
        } else {
            commonAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void setListener() {
        mCommonUseSignUpListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("result", mPersonLists.get(position));
                setResult(200, intent);
                finish();
            }
        });
    }

    @Override
    protected void initBase() {
        isShowBackImage = true;
        isShowToolBar = true;

    }

    @Override
    protected void initView() {
        initToobar("添加报名人");
        setToolbarColor(R.color.white);
        setTitleColor(R.color.black);
    }


    @OnClick(R.id.addNewSignUpPeoPle)
    public void onClick() {
        Intent intent = new Intent(this, AddNewSignUpPersonActivity.class);
        startActivityForResult(intent, 0x00002);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x00002 && resultCode == 300) {
            Grade grade = (Grade) data.getSerializableExtra("RESULT");
            mPersonLists.add(grade);
            commonAdapter.notifyDataSetChanged();

        }
    }
}
