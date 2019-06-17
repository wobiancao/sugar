package ${packageName}

import android.os.Bundle
import com.sugar.sugarlibrary.base.BaseActivity
import com.sugar.sugarlibrary.base.anno.CreatePresenter
import com.sugar.sugarlibrary.base.anno.PresenterVariable
import ${applicationPackage}.R

@CreatePresenter(presenter = [${presenterName}Presenter::class])
class ${activityName}Activity: BaseActivity(), ${contractName}Contract.IView{

    @PresenterVariable
    internal var mPresenter: ${presenterName}Presenter? = null


    override fun getContentView(): Int {
        return R.layout.${activityLayout}
    }

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun loadData() {

    }
}
