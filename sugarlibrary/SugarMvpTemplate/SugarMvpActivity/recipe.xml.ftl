<?xml version="1.0"?>
<recipe>

    <merge from="root/AndroidManifest.xml.ftl"
        to="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />
    <instantiate from="root/src/app_package/Activity.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${activityName}Activity.java" />
    <instantiate from="root/src/app_package/Contract.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${contractName}Contract.java" />
    <instantiate from="root/src/app_package/Presenter.java.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${presenterName}Presenter.java" />
    <instantiate from="root/res/activity.xml.ftl"
        to="${escapeXmlAttribute(resOut)}/layout/${activityLayout}.xml" />
    <open file="${escapeXmlAttribute(manifestOut)}/AndroidManifest.xml" />
    <open file="${escapeXmlAttribute(srcOut)}/${activityName}Activity.java" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${activityLayout}.xml" />
    <open file="${escapeXmlAttribute(srcOut)}/${contractName}Contract.java" />
    <open file="${escapeXmlAttribute(srcOut)}/${presenterName}Presenter.java" />


</recipe>