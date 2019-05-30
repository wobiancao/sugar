package ${packageName};

import com.sugar.sugarlibrary.base.presenter.BasePresenter;




public class ${presenterName}Presenter  extends BasePresenter<${contractName}Contract.IView, ${repositoryName}Repository> implements ${contractName}Contract.PView {

        @Override
        protected void initRepository() {
            mModel = new ${repositoryName}Repository(mView);
        }

}