<?xml version="1.0"?>
<recipe>



<instantiate from="root/src/app_package/Fragment.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${fragmentName}Fragment.java" />
    <instantiate from="root/src/app_package/Contract.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${contractName}Contract.java" />
    <instantiate from="root/src/app_package/Presenter.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${presenterName}Presenter.java" />
    <instantiate from="root/res/fragment.xml.ftl"
        to="${escapeXmlAttribute(resOut)}/layout/${fragmentLayout}.xml" />
    <open file="${escapeXmlAttribute(srcOut)}/${fragmentName}Fragment.java" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${fragmentLayout}.xml" />
    <open file="${escapeXmlAttribute(srcOut)}/${contractName}Contract.java" />
    <open file="${escapeXmlAttribute(srcOut)}/${presenterName}Presenter.java" />


</recipe>