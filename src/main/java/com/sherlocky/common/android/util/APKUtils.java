package com.sherlocky.common.android.util;

import com.sherlocky.common.android.entity.ApkInfo;
import com.sherlocky.common.android.exception.ApkInfoParseException;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.apache.commons.collections4.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class APKUtils {
    private static final Logger log = LoggerFactory.getLogger(APKUtils.class);
    private static String ELEMENT_ACTION = "action";
    private static String ATTRIBUTE_NAME = "name";
    private static String ATTRIBUTE_ANDROID = "android";
    private static String ATTRIBUTE_ANDROID_NAME_VALUE = "android.intent.action.MAIN";

    public static ApkInfo getAPKInfo(String apkFilePath) {
        File apkFile = new File(apkFilePath);
        if (apkFile == null || !apkFile.exists() || !apkFile.isFile()) {
            throw new ApkInfoParseException("文件" + apkFilePath + "不存在");
        }
        ApkInfo apkInfo = null;
        try {
            apkInfo = APKUtils.getAPKInfo(new File(apkFilePath));
        } catch (Exception e) {
            throw new ApkInfoParseException("解析apk信息错误", e);
        }
        if (apkInfo == null) {
            throw new ApkInfoParseException("解析apk信息失败");
        }
        return apkInfo;
    }


    private static ApkInfo getAPKInfo(File file) throws IOException, DocumentException {
        ApkFile apkFile = new ApkFile(file);
        ApkMeta apkMeta = apkFile.getApkMeta();
        ApkInfo apkInfo = new ApkInfo();
        apkInfo.setLabel(apkMeta.getLabel());
        apkInfo.setVersionName(apkMeta.getVersionName());
        apkInfo.setVersionCode(String.valueOf(apkMeta.getVersionCode()));
        apkInfo.setPackageName(apkMeta.getPackageName());
        String manifestXml = apkFile.getManifestXml();
        apkInfo.setMainActivity(getMainActivity(manifestXml));
        apkFile.close();
        return apkInfo;
    }

    private static String getMainActivity(String manifestXml) throws DocumentException {
        Document document = DocumentHelper.parseText(manifestXml);
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        return getActionRecurrence(elements, null);
    }

    private static String getActionRecurrence(List<Element> elements, String mainActivity) {
        if (mainActivity != null) {
            return mainActivity;
        }
        if (CollectionUtils.isEmpty(elements)) {
            return null;
        }
        for (Element element : elements) {
            if (element.getName().equals(ELEMENT_ACTION)) {
                String s = element.attributeValue(ATTRIBUTE_NAME, ATTRIBUTE_ANDROID);
                if (ATTRIBUTE_ANDROID_NAME_VALUE.equals(s)) {
                    mainActivity = element.getParent().getParent().attributeValue(ATTRIBUTE_NAME, ATTRIBUTE_ANDROID);
                }
            } else {
                mainActivity = getActionRecurrence(element.elements(), mainActivity);
            }
        }
        return mainActivity;
    }
}
