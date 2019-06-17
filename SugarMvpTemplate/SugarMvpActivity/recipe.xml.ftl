<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>

<recipe>

    <merge from="root/AndroidManifest.xml.ftl"
        to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />

    <@kt.addAllKotlinDependencies />
    <instantiate from="root/src/app_package/Activity.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${activityName}Activity.${ktOrJavaExt}" />
    <instantiate from="root/src/app_package/Contract.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${contractName}Contract.${ktOrJavaExt}" />
    <instantiate from="root/src/app_package/Presenter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${presenterName}Presenter.${ktOrJavaExt}" />
    <instantiate from="root/res/activity.xml.ftl"
        to="${escapeXmlAttribute(resOut)}/layout/${activityLayout}.xml" />
    <open file="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />
    <open file="${escapeXmlAttribute(srcOut)}/${activityName}Activity.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${activityLayout}.xml" />
    <open file="${escapeXmlAttribute(srcOut)}/${contractName}Contract.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${presenterName}Presenter.${ktOrJavaExt}" />


</recipe>