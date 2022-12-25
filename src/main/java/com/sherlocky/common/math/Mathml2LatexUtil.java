package com.sherlocky.common.math;

import com.sherlocky.common.util.RegexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Mathml转换为latex工具
 * @date: 2022/10/7 9:42
 * @since: 0.9.0
 * <p>
 *     参考 https://github.com/transpect/mml2tex
 * </p>
 */
public class Mathml2LatexUtil {
    private static final Logger log = LoggerFactory.getLogger(Mathml2LatexUtil.class);

    /**
     * mathML转LaTeX
     *
     * @param mathml mathML公式格式
     * @return String latex字符串
     */
    public String mathmlToLatex(String mathml) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        StreamSource xsltSource = new StreamSource(new File(getMml2TexXslt()));
        try {
            Transformer transformer = transformerFactory.newTransformer(xsltSource);
            StringWriter stringWriter = new StringWriter();
            InputStream is = new ByteArrayInputStream(mathml.getBytes());
            StreamSource source = new StreamSource(is);
            transformer.transform(source, new StreamResult(stringWriter));
            String latexStr = RegexUtils.get("<?mml2tex (.*?)\\?>", stringWriter.toString(), 1);
            return latexStr;
        } catch (TransformerException te) {
            log.warn("mathml:{}转latex失败", mathml, te);
            return null;
        }
    }

    private String getMml2TexXslt() {
        // TODO
        return "/mml2tex/xsl/invoke-mml2tex.xsl";
    }

}
