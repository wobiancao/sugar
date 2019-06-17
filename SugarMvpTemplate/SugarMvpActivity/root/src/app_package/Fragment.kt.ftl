package ${packageName}

import android.os.Bundle
import com.sugar.sugarlibrary.base.BaseFragment
import com.sugar.sugarlibrary.base.anno.CreatePresenter
import com.sugar.sugarlibrary.base.anno.PresenterVariable
import ${applicationPackage}.R

@CreatePresenter(presenter = [${presenterName}Presenter::class])
class ${fragmentName}Fragment: BaseFragment(), ${contractName}Contract.IView{


    @PresenterVariable
    internal var mPresenter: ${presenterName}Presenter? = null


    override fun getLayoutId(): Int {
        return R.layout.${fragmentLayout}
    }

}
