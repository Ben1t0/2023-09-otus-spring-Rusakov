package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.LocaleConfig;

@Service("localizedIo")
@RequiredArgsConstructor
public class LocalizedIOServiceImpl implements LocalizedIOService {

    private final LocaleConfig localeConfig;

    private final IOService ioService;

    private final MessageSource messageSource;

    @Override
    public void printMessage(String message) {
        ioService.printMessage(message);
    }

    @Override
    public String readString() {
        return ioService.readString();
    }

    @Override
    public String readStringWithPrompt(String message) {
        return ioService.readStringWithPrompt(message);
    }

    @Override
    public void printMessageLocalized(String code) {
        ioService.printMessage(messageSource.getMessage(code, new Object[]{}, localeConfig.getLocale()));
    }

    @Override
    public void printFormattedMessageLocalized(String code, Object... args) {
        ioService.printMessage(messageSource.getMessage(code, args, localeConfig.getLocale()));
    }

    @Override
    public String readStringWithPromptLocalized(String promptCode) {
        printMessageLocalized(promptCode);
        return ioService.readString();
    }
}
