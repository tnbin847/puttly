package com.tnbin.puttly.global.error;

import com.tnbin.puttly.global.utils.ExceptionUtils;
import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 클라이언트의 잘못된 요청에 의해 발생한 필드 에러에 대한 정보들을 {@link java.util.List}로 제공하는 클래스
 */

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FieldErrors {

    /**
     * 에러가 발생된 필드
     */
    private final String field;

    /**
     * 필드 값
     */
    private final String value;

    /**
     * 에러가 발생된 원인
     */
    private final String reason;

    /**
     * 특정 필드에 의해 발생된 에러에 대한 정보를 {@link List}로 반환한다.
     *
     * @param field     에러가 발생된 필드명
     * @param value     사용자 입력 값
     * @param reason    에러가 발생된 원인
     * @return          에러 정보 리스트
     */
    public static List<FieldErrors> of(final String field, final String value, final String reason) {
        final List<FieldErrors> errors = new ArrayList<>();
        errors.add(new FieldErrors(field, value, reason));
        return errors;
    }

    /**
     * {@code @Valid}를 통한 유효성 검증 실패로 인해 {@link org.springframework.web.bind.MethodArgumentNotValidException} 또는
     * {@link org.springframework.validation.BindException} 등이 발생했을 경우 {@link BindingResult}로부터 필드 오류 정보를 추출하여
     * {@link List}로 반환한다.
     *
     * @param bindingResult     유효성 검증 실패에 대한 바인딩 결과
     * @return                  에러 정보 리스트
     */
    public static List<FieldErrors> of(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors.stream().map(fieldError -> new FieldErrors(
                fieldError.getField(),
                ExceptionUtils.nullableToString(fieldError.getRejectedValue()),
                fieldError.getDefaultMessage()
        )).collect(Collectors.toList());
    }

    /**
     * {@code @Validated}를 통한 유효성 검증 실패로 인해 {@link jakarta.validation.ConstraintViolationException}이 발생했을 경우,
     * 제약 조건 위반 정보를 {@link List}로 반환한다.
     *
     * @param violations    제약 조건 위반 정보 리스트
     * @return              에러 정보 리스트
     */
    public static List<FieldErrors> of(final Set<ConstraintViolation<?>> violations) {
        final List<ConstraintViolation<?>> constraintViolations = new ArrayList<>(violations);
        return constraintViolations.stream().map(constraintViolation -> new FieldErrors(
                violatedProperty(constraintViolation.getPropertyPath().toString()),
                ExceptionUtils.nullableToString(constraintViolation.getInvalidValue()),
                constraintViolation.getMessageTemplate()
        )).collect(Collectors.toList());
    }

    /**
     * 제약 조건이 위반된 프로퍼티 경로에서 최하위 필드명을 추출하여 반환한다.
     *
     * @param propertyPath  제약 조건이 위반된 프로퍼티의 경로
     * @return              경로에서 점('.')이 포함되지 않은 경우 경로 자체를, 포함된 경우 최하위 필드명을 추출하여 반환
     */
    private static String violatedProperty(String propertyPath) {
        int lastIndex = propertyPath.lastIndexOf('.');
        return lastIndex == -1 ? propertyPath : propertyPath.substring(lastIndex + 1);
    }
}