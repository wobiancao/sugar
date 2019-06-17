<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>


    <@kt.addAllKotlinDependencies />
<instantiate from="root/src/app_package/Fragment.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${fragmentName}Fragment.${ktOrJavaExt}" />
    <instantiate from="root/src/app_package/Contract.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${contractName}Contract.${ktOrJavaExt}" />
    <instantiate from="root/src/app_package/Presenter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${presenterName}Presenter.${ktOrJavaExt}" />
    <instantiate from="root/res/fragment.xml.ftl"
        to="${escapeXmlAttribute(resOut)}/layout/${fragmentLayout}.xml" />
    <open file="${escapeXmlAttribute(srcOut)}/${fragmentName}Fragment.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${fragmentLayout}.xml" />
    <open file="${escapeXmlAttribute(srcOut)}/${contractName}Contract.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${presenterName}Presenter.${ktOrJavaExt}" />


</recipe>