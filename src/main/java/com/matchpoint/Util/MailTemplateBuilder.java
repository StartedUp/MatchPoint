package com.matchpoint.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

/**
 * Created by Prithu on 6/9/17.
 */
@Service
public class MailTemplateBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public MailTemplateBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(Map<String, String> mailTemplateData) {
        Context context = new Context();
        for(Map.Entry<String, String> entry : mailTemplateData.entrySet()) {
            String templateVariableName = entry.getKey();
            String templateVariableValue = entry.getValue();
            context.setVariable(templateVariableName, templateVariableValue);
        }
        return templateEngine.process(mailTemplateData.get("templateName"), context);
    }
}
