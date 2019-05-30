package ${packageName};

import android.os.Bundle;
import com.sugar.sugarlibrary.base.BaseFragment;
import com.sugar.sugarlibrary.base.anno.CreatePresenter;
import com.sugar.sugarlibrary.base.anno.PresenterVariable;
import ${applicationPackage}.R;

@CreatePresenter(presenter = ${presenterName}Presenter.class)
public class ${fragmentName}Fragment extends BaseFragment implements ${contractName}Contract.IView{

    @PresenterVariable
    ${presenterName}Presenter mPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.${fragmentLayout};
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }


}
