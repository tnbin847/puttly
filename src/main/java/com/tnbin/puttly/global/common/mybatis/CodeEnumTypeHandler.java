package com.tnbin.puttly.global.common.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link CodeEnum} 인터페이스를 구현한 열거형과 데이터베이스의 컬럼 값 간의 매핑을 처리하는 마이바티스의 타입 핸들러.
 * <p>
 *     열거형에 정의된 코드값을 데이터베이스에 저장하거나 데이터베이스의 컬럼 값을 열거형으로 변환한다.
 * </p>
 *
 * @param <E>   타입 핸들러에서 처리할 열거형
 */

@MappedTypes(CodeEnum.class)
public class CodeEnumTypeHandler<E extends Enum<E> & CodeEnum> extends BaseTypeHandler<E> {

    private final Class<E> type;

    public CodeEnumTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null!");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return from(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return from(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return from(cs.getString(columnIndex));
    }

    /**
     * 데이터베이스의 코드값에 해당하는 열거형 상수를 찾아 반환한다.
     *
     * @param code  데이터베이스에서 읽어들인 코드값
     * @return      {@code code}과 일치한 상수값이 존재할 경우 해당 상수값을 정의한 열거형을, 존재하지 않을 경우 {@code null}을 반환
     */
    private E from(String code) {
        try {
            final E[] constants = type.getEnumConstants();
            for (E candidate : constants) {
                if (candidate.getCode().equals(code)) {
                    return candidate;
                }
            }
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot convert '" + code + "' to '" + type.getSimpleName() + "'!", e);
        }
    }
}