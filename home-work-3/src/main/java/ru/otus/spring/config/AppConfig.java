package ru.otus.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;
import java.util.Map;

@ConfigurationProperties(prefix = "app.questions")
public class AppConfig implements LocaleConfig, TestConfig, QuestionFileNameProvider {

    private static final String FILE_PATH_PREFIX = "/locale/questions/";

    private final int countToPass;

    private Locale locale;

    private final Map<String, String> fileNameByLocaleTag;

    public AppConfig(int countToPass, Locale locale, Map<String, String> fileNameByLocaleTag) {
        this.countToPass = countToPass;
        this.locale = locale;
        this.fileNameByLocaleTag = fileNameByLocaleTag;
    }

    public void setLocale(String locale) {
        this.locale = Locale.forLanguageTag(locale);
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public int getCountToPass() {
        return countToPass;
    }

    @Override
    public String getQuestionFileName() {
        return FILE_PATH_PREFIX + fileNameByLocaleTag.get(locale.toLanguageTag());
    }
}
