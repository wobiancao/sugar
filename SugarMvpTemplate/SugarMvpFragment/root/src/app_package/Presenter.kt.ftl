package ${packageName}

import com.sugar.sugarlibrary.base.presenter.BasePresenter




class ${presenterName}Presenter: BasePresenter<${contractName}Contract.IView, ${repositoryName}Repository>(), ${contractName}Contract.PView {

         override fun initRepository() {
                mModel = ${repositoryName}Repository(mView)
         }


}