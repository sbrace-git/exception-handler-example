package org.example.exceptionhandlerexample.converter;

import org.example.exceptionhandlerexample.response.ProblemDetailResponse;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

@Component
public class ProblemDetailResponseConverter extends AbstractHttpMessageConverter<ProblemDetailResponse> {

    public ProblemDetailResponseConverter() {
        super(org.springframework.http.MediaType.APPLICATION_JSON);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz == ProblemDetailResponse.class;
    }

    @Override
    protected ProblemDetailResponse readInternal(Class<? extends ProblemDetailResponse> clazz, HttpInputMessage inputMessage) throws HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(ProblemDetailResponse problemDetailResponse, HttpOutputMessage outputMessage) throws HttpMessageNotWritableException {
        throw new HttpMessageNotWritableException("FaultyConverter");
    }
}
