package ${packageName};

import android.os.Bundle;
import com.sugar.sugarlibrary.base.BaseActivity;
import com.sugar.sugarlibrary.base.anno.CreatePresenter;
import com.sugar.sugarlibrary.base.anno.PresenterVariable;
import ${applicationPackage}.R;

@CreatePresenter(presenter = ${presenterName}Presenter.class)
public class ${activityName}Activity extends BaseActivity implements ${contractName}Contract.IView{

    @PresenterVariable
    ${presenterName}Presenter mPresenter;


    @Override
    protected int getContentView() {
        return R.layout.${activityLayout};
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public void loadData() {

    }
}
